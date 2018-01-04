package com.zheng.timeline.model;

import com.zheng.timeline.dao.model.UcenterTimelineEvent;
import com.zheng.user.dao.model.UcenterUser;

/**
 * Created by acer on 2017/12/28.
 */
public class UcenterTimelineEventVO extends UcenterTimelineEvent {

    private UcenterUser user;

    private UcenterUser target;

    public UcenterUser getUser() {
        return user;
    }

    public void setUser(UcenterUser user) {
        this.user = user;
    }

    public UcenterUser getTarget() {
        return target;
    }

    public void setTarget(UcenterUser target) {
        this.target = target;
    }
}
