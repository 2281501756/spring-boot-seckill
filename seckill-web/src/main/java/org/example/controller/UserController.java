package org.example.controller;


import org.example.base.BaseResponse;
import org.example.model.req.UserReq;
import org.example.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/sms/code")
    public BaseResponse<Boolean> sendSmsCode(@RequestBody @Valid UserReq.PhoneSmsReq phoneSmsReq) {
        return userService.sendSmsCode(phoneSmsReq.getPhone());
    }
}
