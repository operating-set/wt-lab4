package com.application.service.search;

import com.application.entity.UserInfo;

import java.util.List;

public class UserInfoSearch extends EntitySearch<UserInfo> {

    public UserInfoSearch(List<UserInfo> userInfoList) {
        super(userInfoList);
    }
}
