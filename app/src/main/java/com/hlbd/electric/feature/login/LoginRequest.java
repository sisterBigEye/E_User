package com.hlbd.electric.feature.login;

import com.hlbd.electric.base.BaseResult;

public class LoginRequest extends BaseResult {

    public String username;

    public String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
