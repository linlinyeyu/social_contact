package com.zheng.im.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by acer on 2017/12/17.
 */

public interface ChatBase {

    GroupBase getGroupBase();

    UserBase getUserBase();

    MessageBase getMessageBase();
}
