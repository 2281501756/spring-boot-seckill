package org.example.model.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.SeckillUser;

import java.io.Serializable;

public class UserResp {
    @Data
    @AllArgsConstructor
    public static class LoginResp implements Serializable {
        private String token;
        private SeckillUser user;
    }
}
