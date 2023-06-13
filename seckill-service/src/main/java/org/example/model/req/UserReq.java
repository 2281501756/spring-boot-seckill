package org.example.model.req;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class UserReq implements Serializable {
    @Data
    public static class PhoneSmsReq implements Serializable{
        @NotNull(message = "手机号码不能为空")
        @NotEmpty(message = "必须传入手机号码")
        @Size(min = 10, max = 12)
        private String phone;
    }
    @Data
    public static class LoginReq extends PhoneSmsReq implements Serializable {
        @NotNull(message = "验证码不能为空")
        @NotEmpty(message = "必须传入验证码")
        private String code;
    }
}
