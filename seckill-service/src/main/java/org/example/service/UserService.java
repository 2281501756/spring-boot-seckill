package org.example.service;

import org.example.base.BaseResponse;

public interface UserService {
    public BaseResponse<Boolean> sendSmsCode(String photo);

}
