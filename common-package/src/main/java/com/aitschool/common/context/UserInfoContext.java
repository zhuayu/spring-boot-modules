package com.aitschool.common.context;

import com.aitschool.common.response.UserInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class UserInfoContext {

    private static final Logger LOG = LoggerFactory.getLogger(UserInfoContext.class);

    private static ThreadLocal<UserInfoResponse> userInfo = new ThreadLocal<>();

    public static UserInfoResponse getUserInfo() {
        return userInfo.get();
    }


    public static void setUserInfo(UserInfoResponse userInfo) {
        UserInfoContext.userInfo.set(userInfo);
    }

    public static Long getId() {
        try {
            return userInfo.get().getId();
        } catch (Exception e) {
            LOG.error("获取登录会员信息异常", e);
            throw e;
        }
    }

}
