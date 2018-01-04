package com.zheng.user.service;

import com.zheng.common.base.BaseServicePinet;
import com.zheng.user.dao.model.UcenterUserIdentity;

/**
* UcenterUserIdentityService接口
* Created by pinet on 2017/12/20.
*/
public interface UcenterUserIdentityService extends BaseServicePinet<UcenterUserIdentity> {

    UcenterUserIdentity selectLastIdentity(int userId);

    UcenterUserIdentity selectPassedIdentity(int userId);

    UcenterUserIdentity selectPassedOrAuditIdentity(int userId);
}