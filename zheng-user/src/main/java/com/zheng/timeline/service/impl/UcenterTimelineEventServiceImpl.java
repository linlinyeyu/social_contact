package com.zheng.timeline.service.impl;

import com.google.common.collect.Lists;
import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.constants.BeanConstants;
import com.zheng.common.util.ErrorCodeEnum;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.common.util.StringUtil;
import com.zheng.common.util.WowCollections;
import com.zheng.timeline.dao.mapper.UcenterTimelineEventMapper;
import com.zheng.timeline.dao.model.UcenterTimeline;
import com.zheng.timeline.dao.model.UcenterTimelineEvent;
import com.zheng.timeline.dao.model.UcenterTimelineEventNotify;
import com.zheng.timeline.model.UcenterTimelineEventVO;
import com.zheng.timeline.service.UcenterTimelineEventNotifyService;
import com.zheng.timeline.service.UcenterTimelineEventService;
import com.zheng.timeline.service.UcenterTimelineService;
import com.zheng.user.model.UcenterUserVO;
import com.zheng.user.service.UcenterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.zheng.common.util.ErrorCodeEnum.PARAM_ERROR;

/**
* UcenterTimelineEventService实现
* Created by shuzheng on 2017/12/26.
*/
@Service
@Transactional
@BaseService
public class UcenterTimelineEventServiceImpl extends BaseServiceImplPinet<UcenterTimelineEventMapper, UcenterTimelineEvent> implements UcenterTimelineEventService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTimelineEventServiceImpl.class);

    @Autowired
    UcenterTimelineEventMapper ucenterTimelineEventMapper;

    @Autowired
    UcenterUserService ucenterUserService;

    @Autowired
    UcenterTimelineService ucenterTimelineService;

    @Autowired
    UcenterTimelineEventNotifyService ucenterTimelineEventNotifyService;



    @Override
    public List<UcenterTimelineEventVO> selectEvents(List<Integer> timelineIds){
        if(timelineIds == null || timelineIds.size() == 0){
            return Lists.newArrayList();
        }
        return ucenterTimelineEventMapper.selectEvents(timelineIds);
    }

    @Override
    public List<UcenterTimelineEventVO> selectEventsByEventIds(List<Integer> eventIds){
        if(eventIds == null || eventIds.size() == 0){
            return Lists.newArrayList();
        }
        return ucenterTimelineEventMapper.selectEventsByEventIds(eventIds);
    }

    @Override
    public Map<Integer, Map<String, List<UcenterTimelineEventVO>>> selectEventsWrappedTimelineId(List<Integer> timelineIds) {
        List<UcenterTimelineEventVO> list = this.selectEvents(timelineIds);
        return wrapMap(list);
    }

    @Override
    public Map<Integer, Map<String, List<UcenterTimelineEventVO>>> wrapMap(List<UcenterTimelineEventVO> list){
        if(list == null || list.size() == 0){
            return WowCollections.map();
        }
        Map<Integer, Map<String, List<UcenterTimelineEventVO>>> map = WowCollections.map();
        for (UcenterTimelineEventVO e : list){
            //判断map是否拥有过timelineId，如果没有则要添加
            Map<String, List<UcenterTimelineEventVO>> events = map.get(e.getTimelineId());
            if(events == null){
                //创建新的event，并创建点赞和评论列表
                events = WowCollections.map();
                events.put("lauds", Lists.newArrayList());
                events.put("comments", Lists.newArrayList());
                map.put(e.getTimelineId(), events);
            }

            //添加对应的列表
            if(e.getEventType() == BeanConstants.EVENT_TYPE.LAUD){
                events.get("lauds").add(e);
            }else{
                events.get("comments").add(e);
            }
        }
        return map;
    }

    @Override
    @Transactional
    public BaseResult laud(int timelineId, String uuid,  boolean isCanceled) {

        //如果点赞，则判断是否已经点过赞
        if(!isCanceled){
            List<UcenterTimelineEventVO> list = ucenterTimelineEventMapper.selectEventsByUuid(uuid, timelineId, BeanConstants.EVENT_TYPE.LAUD);

            if(list.size() != 0){
                return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_LAUD_ERROR);
            }

            UcenterTimeline timeline = ucenterTimelineService.selectByPrimaryKey(timelineId);

            if(timeline == null){
                return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_EMPTY);
            }


            UcenterTimelineEventVO ucenterTimelineEvent = new UcenterTimelineEventVO();
            ucenterTimelineEvent.setTimelineId(timelineId);
            ucenterTimelineEvent.setUserUuid(uuid);
            ucenterTimelineEvent.setEventType(BeanConstants.EVENT_TYPE.LAUD);
            ucenterTimelineEvent.setCreateTime(new Date().getTime());
            ucenterTimelineEventMapper.insertSelective(ucenterTimelineEvent);

            //TODO 如果不是自己给自己的动态点赞，则需要提示发布动态的人，后期拓展可放在队列中
            if(!uuid.equals(timeline.getUserUuid())){
                UcenterTimelineEventNotify notify = new UcenterTimelineEventNotify();
                notify.setIsRead(false);
                notify.setCreateTime(new Date().getTime());
                notify.setEventId(ucenterTimelineEvent.getId());
                notify.setTimelineId(timelineId);
                notify.setUserUuid(uuid);
                notify.setTargetUuid(timeline.getUserUuid());

                List<UcenterTimelineEventNotify> notifys = WowCollections.list(notify);
                ucenterTimelineEventNotifyService.insertTimelineEventNotifys(notifys);
            }




            List<String> uuids = WowCollections.list(uuid);
            Map<String, UcenterUserVO> map = ucenterUserService.selectFriendsByUuidWrappedMap(uuid, uuids, 1);

            ucenterTimelineEvent.setUser(map.get(uuid));

            return FormatResponseUtil.formatResponse(WowCollections.map("laud", ucenterTimelineEvent));
        }else{
            List<UcenterTimelineEventVO> list = ucenterTimelineEventMapper.selectEventsByUuid(uuid, timelineId, BeanConstants.EVENT_TYPE.LAUD);

            if(list.size() == 0){
                return FormatResponseUtil.formatResponse();
            }

            ucenterTimelineEventMapper.deleteByUuidAndTimelineId(uuid, timelineId, BeanConstants.EVENT_TYPE.LAUD);
            //TODO 删除通知类，如果后期拓展可放在队列中
            ucenterTimelineEventNotifyService.deleteByEventId(list.get(0).getId());

        }

        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult comment(int timelineId, String uuid, String targetUuid,int targetId, String text){

        if(targetId > 0){
            UcenterTimelineEvent target = this.selectByPrimaryKey(targetId);

            //回复的信息不对称，回复的人的uuid和此评论id的发布人不一致，此评论的发布圈id与朋友圈id不一致，会返回失败
            if(target == null || !target.getUserUuid().equals(targetUuid) || target.getTimelineId() != timelineId){
                return FormatResponseUtil.error(ErrorCodeEnum.PARAM_ERROR);
            }
        }
        //找到对应的朋友圈类
        UcenterTimeline timeline = ucenterTimelineService.selectByPrimaryKey(timelineId);

        if(timeline == null){
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_EMPTY);
        }

        UcenterTimelineEventVO ucenterTimelineEventVO = new UcenterTimelineEventVO();
        ucenterTimelineEventVO.setCreateTime(new Date().getTime());
        ucenterTimelineEventVO.setTargetUuid(targetUuid);
        ucenterTimelineEventVO.setUserUuid(uuid);
        ucenterTimelineEventVO.setTimelineId(timelineId);
        ucenterTimelineEventVO.setText(text);
        ucenterTimelineEventVO.setTargetId(targetId);
        ucenterTimelineEventVO.setEventType(BeanConstants.EVENT_TYPE.COMMENT);
        int rows = ucenterTimelineEventMapper.insertSelective(ucenterTimelineEventVO);

        if(rows == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }
        //评论通知类，通知回复的人和动态主人
        List<UcenterTimelineEventNotify> notifies = WowCollections.list();

        //uuid必须放前面，防止timeline的useruuid为空
        if(!uuid.equals(timeline.getUserUuid())){
            UcenterTimelineEventNotify notify = new UcenterTimelineEventNotify();
            notify.setIsRead(false);
            notify.setTimelineId(timelineId);
            notify.setEventId(ucenterTimelineEventVO.getId());
            notify.setCreateTime(new Date().getTime());
            notify.setUserUuid(uuid);
            notify.setTargetUuid(timeline.getUserUuid());
            notifies.add(notify);
        }
        if(!StringUtil.isEmpty(targetUuid)){
            UcenterTimelineEventNotify notify = new UcenterTimelineEventNotify();
            notify.setIsRead(false);
            notify.setTimelineId(timelineId);
            notify.setEventId(ucenterTimelineEventVO.getId());
            notify.setCreateTime(new Date().getTime());
            notify.setUserUuid(uuid);
            notify.setTargetUuid(targetUuid);
            notifies.add(notify);
        }
        if(notifies.size() > 0){
            ucenterTimelineEventNotifyService.insertTimelineEventNotifys(notifies);
        }


        List<String> uuids = WowCollections.list(uuid, targetUuid);
        Map<String, UcenterUserVO> map = ucenterUserService.selectFriendsByUuidWrappedMap(uuid, uuids, 1);

        ucenterTimelineEventVO.setUser(map.get(uuid));
        ucenterTimelineEventVO.setTarget(map.get(targetUuid));

        return FormatResponseUtil.formatResponse(WowCollections.map("comment", ucenterTimelineEventVO));
    }


}