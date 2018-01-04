package com.zheng.api.server.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zheng.api.server.aop.ValidateLoginAspect;
import com.zheng.common.base.BaseApiController;
import com.zheng.common.base.BaseResult;
import com.zheng.common.constants.BeanConstants;
import com.zheng.common.constants.CacheConstants;
import com.zheng.common.util.*;
import com.zheng.common.util.key.SystemClock;
import com.zheng.timeline.dao.model.UcenterTimeline;
import com.zheng.timeline.dao.model.UcenterTimelineEvent;
import com.zheng.timeline.model.UcenterTimelineVO;
import com.zheng.timeline.service.UcenterTimelineEventNotifyService;
import com.zheng.timeline.service.UcenterTimelineEventService;
import com.zheng.timeline.service.UcenterTimelineService;
import com.zheng.user.service.UcenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.sun.tools.doclint.Entity.ge;
import static javafx.scene.input.KeyCode.R;

/**
 * Created by acer on 2017/12/26.
 */
@RestController
@RequestMapping("/api/timeline")
public class TimeLineController extends BaseApiController {

    @Autowired
    private UcenterTimelineService ucenterTimelineService;


    @Autowired
    private UcenterTimelineEventService ucenterTimelineEventService;


    @Autowired
    private UcenterTimelineEventNotifyService ucenterTimelineEventNotifyService;

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ValidateLoginAspect
    public BaseResult publish(@RequestBody UcenterTimeline timeline){
        String uuid = getUuid();
        timeline.setUserUuid(uuid);

        if(StringUtil.isEmpty(timeline.getText()) && StringUtil.isEmpty(timeline.getBody())){
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_BODY_EMPTY);
        }
        if(StringUtil.isEmpty(timeline.getRelationTypes())){
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_RELATION_ERROR);
        }

        if(!StringUtil.isEmpty(timeline.getBody())){
            try{
                JSONObject body = JSONObject.parseObject(timeline.getBody());
                short media = BeanConstants.MEDIA_TYPE.MEDIA_TEXT;
                if(body.containsKey("images")) {
                    media = BeanConstants.MEDIA_TYPE.MEDIA_IMAGE;
                }else if(body.containsKey("audio")){
                    media = BeanConstants.MEDIA_TYPE.MEDIA_AUDIO;
                }else if(body.containsKey("video")){
                    media = BeanConstants.MEDIA_TYPE.MEDIA_VIDEO;
                }else if(body.containsKey("relate")){
                    media = BeanConstants.MEDIA_TYPE.MEDIA_RELATE;
                }
                timeline.setMediaType(media);
            }catch (Exception e){
               return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_BODY_ERROR);
            }
        }

        BaseResult baseResult = ucenterTimelineService.publish(timeline);

        return baseResult;
    }

    @RequestMapping( value = "/detail", method = RequestMethod.GET)
    @ValidateLoginAspect
    public BaseResult detail(int timeline_id){

        Map<String, Object> ucenterTimeline = ucenterTimelineService.selectTimelineDetail(timeline_id, getUuid());

        return FormatResponseUtil.formatResponse(WowCollections.map("timeline" , ucenterTimeline));
    }


    @RequestMapping( value = "list", method = RequestMethod.GET)
    @ValidateLoginAspect
    public BaseResult list(@RequestParam(required = false) Short relation_type,
                           @RequestParam(required = false) String action,
                           @RequestParam(required = false) Integer page){

        if(page == null || page < 0){
            page = 1;
        }
        if(relation_type == null){
            relation_type = 0;
        }
        String uuid = getUuid();

        //获取此用户上次刷新时间
        String time = RedisUtil.get(CacheConstants.TIMELINE_DATE + + relation_type + "_" + uuid);


        //如果上次刷新时间为空或者action不为空，即刷新操作的话，则更新用户刷新时间
        if(StringUtil.isEmpty(time) || !StringUtil.isEmpty(action)){
            //获取当前时间
            String now = String.valueOf(SystemClock.now());
            time = now;
            RedisUtil.set(CacheConstants.TIMELINE_DATE + + relation_type + "_" + uuid, now);
        }

        //TODO 添加大事件
        if(relation_type == 0 || relation_type == BeanConstants.RELATION.FAMILY){

        }

        //获取列表
        List<UcenterTimelineVO> lists = ucenterTimelineService.selectTimelineList(uuid,relation_type, page, Long.parseLong(time));


        //前端需要弹出的消息
        String popText = null;

        Map<String, Object> result = WowCollections.map(
                "timelines" , lists,
                "events", Lists.newArrayList()
        );
        //如果为全部关系，则需要
        if(relation_type == 0 && !StringUtil.isEmpty(action)){
            int count = ucenterTimelineEventNotifyService.selectNotifyCount(uuid);
            result.put("notify", WowCollections.map("count", count));
            if(count > 0){
                popText = count + "条新消息";
            }
        }

        result.put("pop_text", popText);

        return FormatResponseUtil.formatResponse(result);
    }

    @RequestMapping(value = "laud", method = RequestMethod.POST)
    @ValidateLoginAspect
    public BaseResult laud(@RequestBody Map<String, String> map){
        int timelineId = paramAsInt(map, "timeline_id", 0);

        boolean isCanceled = paramAsBoolean(map, "is_canceled", false);
        if(timelineId == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_EMPTY);
        }


        return ucenterTimelineEventService.laud(timelineId, getUuid(),  isCanceled);
    }

    /**
     * 评论
     * @param text 文本
     * @param target_uuid 指定回复人，可为空
     * @param target_id 被回复的评论id
     * @param timeline_id 动态id，必传
     * */
    @RequestMapping(value = "comment", method = RequestMethod.POST)
    @ValidateLoginAspect
    public BaseResult comment(@RequestBody Map<String, String> map){
        int timelineId = paramAsInt(map, "timeline_id", 0);

        if(timelineId == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_EMPTY);
        }

        String text = param(map, "text");

        if(StringUtil.isEmpty(text)){
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_COMMENT_EMPTY);
        }

        String targetUuid = param(map, "target_uuid");

        int targetId = paramAsInt(map, "target_id", 0);

        String uuid = getUuid();

        if(uuid.equals(targetUuid)){
            return FormatResponseUtil.error(ErrorCodeEnum.TIMELINE_COMMENT_DUPLICATE);
        }
        return ucenterTimelineEventService.comment(timelineId, uuid, targetUuid, targetId, text);
    }

    @RequestMapping(value = "deleteComment", method = RequestMethod.POST)
    @ValidateLoginAspect
    public BaseResult deleteComemnt(@RequestBody Map<String, String> map){
        int id = paramAsInt(map, "id", 0);

        if(id == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.ID_EMPTY);
        }

        UcenterTimelineEvent event = ucenterTimelineEventService.selectByPrimaryKey(id);

        String uuid = getUuid();

        //如果找不到此评论或者不是本人发布的则无法删除此评论
        if(event == null || !uuid.equals(event.getUserUuid()) ){
            return FormatResponseUtil.error(ErrorCodeEnum.DELETE_ERROR);
        }

        int rows = ucenterTimelineEventService.deleteByPrimaryKey(id);

        if(rows == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.DELETE_ERROR);
        }

        return FormatResponseUtil.formatResponse();
    }

    @RequestMapping(value = "listEventNotify", method = RequestMethod.GET)
    @ValidateLoginAspect
    public BaseResult listEventNotify(){

        String uuid = getUuid();
        List<Map<String, Object>> list =  ucenterTimelineEventNotifyService.selectUsersNotifies(uuid);
        //移除所有未读信息，即设为已读
        if(!list.isEmpty() ){
            ucenterTimelineEventNotifyService.removeAllUnreadNotify(uuid);
        }
        return FormatResponseUtil.formatResponse(WowCollections.map("notifies", list));
    }

}
