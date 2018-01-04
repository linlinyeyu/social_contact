package com.zheng.timeline.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.util.WowCollections;
import com.zheng.timeline.dao.mapper.UcenterTimelineEventNotifyMapper;
import com.zheng.timeline.dao.model.UcenterTimelineEventNotify;
import com.zheng.timeline.service.UcenterTimelineEventNotifyService;
import com.zheng.timeline.service.UcenterTimelineEventService;
import com.zheng.user.dao.model.UcenterUser;
import com.zheng.user.model.UcenterUserVO;
import com.zheng.user.service.UcenterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
* UcenterTimelineEventNotifyService实现
* Created by shuzheng on 2017/12/29.
*/
@Service
@Transactional
@BaseService
public class UcenterTimelineEventNotifyServiceImpl extends BaseServiceImplPinet<UcenterTimelineEventNotifyMapper, UcenterTimelineEventNotify> implements UcenterTimelineEventNotifyService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTimelineEventNotifyServiceImpl.class);

    @Autowired
    UcenterTimelineEventNotifyMapper ucenterTimelineEventNotifyMapper;

    @Autowired
    UcenterTimelineEventService ucenterTimelineEventService;

    @Autowired
    UcenterUserService ucenterUserService;

    @Override
    public int insertTimelineEventNotifys(List<UcenterTimelineEventNotify> list){

        WowCollections.uniqList(list);
        return ucenterTimelineEventNotifyMapper.insertTimelineEventNotifys(list);
    }

    @Override
    public int deleteByEventId(int eventId){
        return ucenterTimelineEventNotifyMapper.deleteByEventId(eventId);
    }

    @Override
    public int selectNotifyCount(String uuid){
        int count = ucenterTimelineEventNotifyMapper.selectCountByUuid(uuid);

        return count;
    }

    @Override
    public List<Map<String, Object>> selectUsersNotifies(String uuid){
        //目前不分页，全部显示，仅找未读的
        //PageHelper.startPage(page, 15, false);
        List<Map<String,Object>> list = ucenterTimelineEventNotifyMapper.selectUsersNotifies(uuid);
        List<String> uuids = WowCollections.list();
        //填充user和target用户信息
        for (Map<String,Object> map :list){
            Object userUuid = map.get("user_uuid");
            Object targetUuid = map.get("target_uuid");
            if(userUuid != null){
                uuids.add(userUuid.toString());
            }
            if(targetUuid != null){
                uuids.add(targetUuid.toString());
            }
        }
        if(!uuids.isEmpty()){
            Map<String, UcenterUserVO> ucenterUserVOMap = ucenterUserService.selectFriendsByUuidWrappedMap(uuid, uuids, 1);
            for (Map<String,Object> map :list){
                Object userUuid = map.get("user_uuid");
                Object targetUuid = map.get("target_uuid");
                if(userUuid != null){
                    map.put("user", ucenterUserVOMap.get(userUuid.toString()));
                }
                if(targetUuid != null){
                    map.put("target", ucenterUserVOMap.get(targetUuid.toString()));
                }
            }
        }
        return list;
    }

    public int removeAllUnreadNotify(String uuid){
        return ucenterTimelineEventNotifyMapper.removeAllUnreadNotify(uuid);
    }
}