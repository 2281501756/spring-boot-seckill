package org.example.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class PageReq implements Serializable {

    @NotNull(message = "page 不能为空")
    private Integer page;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;

}
