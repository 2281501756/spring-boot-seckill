package org.example.service;

import org.example.base.BaseResponse;
import org.example.model.req.UserReq;
import org.example.model.resp.UserResp;

public interface UserService {
    BaseResponse<Boolean> sendSmsCode(String photo);
    BaseResponse<UserResp.LoginResp> login(UserReq.LoginReq loginReq);
    BaseResponse<UserResp.LoginResp> checkToken(String token);


}
