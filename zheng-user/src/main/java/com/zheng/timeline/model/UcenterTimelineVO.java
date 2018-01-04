package com.zheng.timeline.model;

import com.zheng.timeline.dao.model.UcenterTimeline;
import com.zheng.user.dao.model.UcenterUser;

import java.util.Map;
import java.util.List;

/**
 * Created by acer on 2017/12/28.
 */
public class UcenterTimelineVO extends UcenterTimeline {
    private List<UcenterTimelineEventVO> lauds;

    private List<UcenterTimelineEventVO> comments;

    private UcenterUser user;

    private Short relationType;

    private Long readTime;

    public List<UcenterTimelineEventVO> getLauds() {
        return lauds;
    }

    public void setLauds(List<UcenterTimelineEventVO> lauds) {
        this.lauds = lauds;
    }

    public List<UcenterTimelineEventVO> getComments() {
        return comments;
    }

    public void setComments(List<UcenterTimelineEventVO> comments) {
        this.comments = comments;
    }

    public UcenterUser getUser() {
        return user;
    }

    public void setUser(UcenterUser user) {
        this.user = user;
    }

    public Short getRelationType() {
        return relationType;
    }

    public void setRelationType(Short relationType) {
        this.relationType = relationType;
    }

    public Long getReadTime() {
        return readTime;
    }

    public void setReadTime(Long readTime) {
        this.readTime = readTime;
    }
}
