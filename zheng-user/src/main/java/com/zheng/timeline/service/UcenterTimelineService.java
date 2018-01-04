package com.zheng.timeline.service;

import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServicePinet;
import com.zheng.timeline.dao.model.UcenterTimeline;
import com.zheng.timeline.model.UcenterTimelineVO;

import java.util.List;
import java.util.Map;

/**
* UcenterTimelineService接口
* Created by pinet on 2017/12/26.
*/
public interface UcenterTimelineService extends BaseServicePinet<UcenterTimeline> {

    BaseResult publish(UcenterTimeline ucenterTimeline);

    Map<String, Object> selectTimelineDetail(int timeline_id, String uuid);

    List<UcenterTimelineVO> selectTimelineList(String uuid, short relationType, int page, long date);
}