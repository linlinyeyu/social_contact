package com.zheng.timeline.service;

import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServicePinet;
import com.zheng.timeline.dao.model.UcenterTimelineEvent;
import com.zheng.timeline.model.UcenterTimelineEventVO;

import java.util.List;
import java.util.Map;

/**
* UcenterTimelineEventService接口
* Created by pinet on 2017/12/26.
*/
public interface UcenterTimelineEventService extends BaseServicePinet<UcenterTimelineEvent> {



    List<UcenterTimelineEventVO> selectEvents(List<Integer> timelineIds);


    List<UcenterTimelineEventVO> selectEventsByEventIds(List<Integer> eventIds);
    /**
     * 根据传来的timelineId，返回对应事件信息
     * 传回的事件信息中将点赞和评论分离
     * 即二级map中的string为lauds和comments，即可获取到对应的数组信息,
     *
     * @return 如果为空，则回传空的map，不会为null
     * */
    Map<Integer, Map<String, List<UcenterTimelineEventVO>>> selectEventsWrappedTimelineId(List<Integer> timelineIds);

    /**
     * 上述方法的包装方法，开放出来供某些情况使用，如需要使用list以及map的情况
     * */
    Map<Integer, Map<String, List<UcenterTimelineEventVO>>> wrapMap(List<UcenterTimelineEventVO> list);

    /**
     * 点赞
     * @param isCanceled 是否是取消点赞
     * */
    BaseResult laud(int timelineId, String uuid,  boolean isCanceled);

    /**
     * 评论
     * */
    BaseResult comment(int timelineId, String uuid, String targetUuid, int targetId, String text);
}