package org.example.model.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DemoReq implements Serializable {
    @NotNull
    @NotEmpty(message = "text 不能为空")
    public String text;
}
