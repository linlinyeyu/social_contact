package com.zheng.timeline.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.constants.BeanConstants;
import com.zheng.common.exception.ArgumentErrorException;
import com.zheng.common.util.ErrorCodeEnum;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.common.util.StringUtil;
import com.zheng.common.util.WowCollections;
import com.zheng.timeline.dao.mapper.UcenterTimelineMapper;
import com.zheng.timeline.dao.model.UcenterTimeline;
import com.zheng.timeline.dao.model.UcenterTimelineEvent;
import com.zheng.timeline.dao.model.UcenterTimelineNotify;
import com.zheng.timeline.dao.model.UcenterTimelineRelation;
import com.zheng.timeline.model.UcenterTimelineEventVO;
import com.zheng.timeline.model.UcenterTimelineVO;
import com.zheng.timeline.service.UcenterTimelineEventService;
import com.zheng.timeline.service.UcenterTimelineNotifyService;
import com.zheng.timeline.service.UcenterTimelineRelationService;
import com.zheng.timeline.service.UcenterTimelineService;
import com.zheng.user.dao.model.UcenterUser;
import com.zheng.user.model.UcenterUserVO;
import com.zheng.user.service.UcenterUserRelationService;
import com.zheng.user.service.UcenterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* UcenterTimelineService实现
* Created by shuzheng on 2017/12/26.
*/
@Service
@Transactional
@BaseService
public class UcenterTimelineServiceImpl extends BaseServiceImplPinet<UcenterTimelineMapper, UcenterTimeline> implements UcenterTimelineService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTimelineServiceImpl.class);

    @Autowired
    UcenterTimelineMapper ucenterTimelineMapper;

    @Autowired
    UcenterUserService ucenterUserService;

    @Autowired
    UcenterTimelineNotifyService ucenterTimelineNotifyService;

    @Autowired
    UcenterUserRelationService ucenterUserRelationService;

    @Autowired
    UcenterTimelineRelationService ucenterTimelineRelationService;

    @Autowired
    UcenterTimelineEventService ucenterTimelineEventService;

    @Override
    public BaseResult publish(UcenterTimeline ucenterTimeline) {
        ucenterTimeline.setCreateTime(new Date().getTime());
        ucenterTimeline.setUpdateTime(new Date().getTime());
        ucenterTimeline.setIsDeleted(false);
        ucenterTimeline.setIsReported(false);

        UcenterUser user = ucenterUserService.selectByUuid(ucenterTimeline.getUserUuid());

        if (user == null) {
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_USER_EMPTY);
        }

        if (user.getActive() == 0) {
            return FormatResponseUtil.error(ErrorCodeEnum.USER_NOT_ACTIVE);
        }
        ucenterTimeline.setAvatar(user.getAvatar());
        ucenterTimeline.setUsername(user.getNickname());


        int rows = ucenterTimelineMapper.insertSelective(ucenterTimeline);

        if (rows == 0) {
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_PUBLISH_FAIL);
        }
        String relationTypes = ucenterTimeline.getRelationTypes();

        short[] types = WowCollections.split2IntoShortArray(relationTypes, ",");


        List<UcenterTimelineRelation> timelineRelations = Lists.newArrayList();

        //添加朋友圈的关系映射，便于查询
        for (short t : types){
            UcenterTimelineRelation ucenterTimelineRelation =  new UcenterTimelineRelation();
            ucenterTimelineRelation.setTimelineId(ucenterTimeline.getTimelineId());
            ucenterTimelineRelation.setRelationType(t);
            timelineRelations.add(ucenterTimelineRelation);
        }
        rows = ucenterTimelineRelationService.insertTimelineRelationList(timelineRelations);

        if (rows == 0) {
            //因为需要回滚，因此此处发布失败需要抛出异常
            throw new ArgumentErrorException("发布失败");
        }

        List<Map<String, Object>> friends = ucenterUserRelationService.selectUserFriends(ucenterTimeline.getUserUuid(), types);

        List<UcenterTimelineNotify> userTimelines = Lists.newArrayList();

        UcenterTimelineNotify mine = new UcenterTimelineNotify();
        mine.setUserUuid(ucenterTimeline.getUserUuid());
        mine.setRelationType(BeanConstants.RELATION.MINE);
        mine.setCreateTime(new Date().getTime());
        mine.setTimelineId(ucenterTimeline.getTimelineId());
        mine.setIsRead(true);

        userTimelines.add(mine);

        for (Map<String, Object> f : friends){
            UcenterTimelineNotify userTimeline = new UcenterTimelineNotify();
            userTimeline.setRelationType((short)f.get("relation_type"));
            userTimeline.setCreateTime(new Date().getTime());
            userTimeline.setIsRead(false);
            userTimeline.setTimelineId(ucenterTimeline.getTimelineId());
            userTimeline.setUserUuid(f.get("uuid").toString());
            userTimeline.setPublishTime(new Date().getTime());
            userTimeline.setFriendUuid(ucenterTimeline.getUserUuid());
            userTimelines.add(userTimeline);
        }

        rows = ucenterTimelineNotifyService.insertTimelineNotifyList(userTimelines);

        if (rows == 0) {
            //因为需要回滚，因此此处发布失败需要抛出异常
            throw new ArgumentErrorException("发布失败");
        }

        Map<String, Object> timeline = this.selectTimelineDetail(ucenterTimeline.getTimelineId(), ucenterTimeline.getUserUuid());
        return FormatResponseUtil.formatResponse(WowCollections.map("timeline", timeline));
    }

    @Override
    public Map<String, Object> selectTimelineDetail(int timelineId, String uuid) {
        return ucenterTimelineMapper.selectById(timelineId);
    }

    @Override
    public List<UcenterTimelineVO> selectTimelineList(String uuid, short relationType, int page, long date){

        PageHelper.startPage(page, 15, false);
        List<UcenterTimelineVO> list = ucenterTimelineMapper.selectFriendsTimelineList(uuid, relationType, date);

        if(list == null || list.size() == 0){
            return Lists.newArrayList();
        }
        List<String> uuids = Lists.newArrayList();

        List<Integer> timeLineIds = Lists.newArrayList();

        for (UcenterTimelineVO timeline : list){
            uuids.add(timeline.getUserUuid());
            timeLineIds.add(timeline.getTimelineId());
        }

        List<UcenterTimelineEventVO> eventList = ucenterTimelineEventService.selectEvents(timeLineIds);

        for(UcenterTimelineEventVO e : eventList){
            uuids.add(e.getUserUuid());
            if(!StringUtil.isEmpty(e.getTargetUuid())){
                uuids.add(e.getTargetUuid());
            }
        }

        //批量寻找所需的user信息
        Map<String, UcenterUserVO>  userMap = ucenterUserService.selectFriendsByUuidWrappedMap(uuid, uuids, 1);

        //将找到的user放入评论内
        for (UcenterTimelineEventVO e : eventList){
            e.setUser(userMap.get(e.getUserUuid()));
            e.setTarget(userMap.get(e.getTargetUuid()));
        }

        //将找到的user放入朋友圈id内
        for(UcenterTimelineVO timeline : list){
            timeline.setUser(userMap.get(timeline.getUserUuid()));
        }

        Map<Integer, Map<String, List<UcenterTimelineEventVO>>> events = ucenterTimelineEventService.wrapMap(eventList);
        for (UcenterTimelineVO timeline : list){
            Map<String, List<UcenterTimelineEventVO>> eventMap = events.get(timeline.getTimelineId());
            if(eventMap == null){
                timeline.setComments(Lists.newArrayList());
                timeline.setLauds(Lists.newArrayList());
            }else{
                timeline.setComments(eventMap.get("comments"));
                timeline.setLauds(eventMap.get("lauds"));
            }
        }

        return list;
    }
}