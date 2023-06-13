package org.example.model.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SeckillReq implements Serializable {
    @Data
    public static class SimpleSeckillReq implements Serializable {
        @NotNull(message = "userid 不能为空")
        private Long userID;

        @NotNull(message = "productID 不能为空")
        private Long productID;
    }
}
