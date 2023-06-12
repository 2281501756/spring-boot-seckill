package org.example.base;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PageReq implements Serializable {

    @NotNull
    private Integer page;
    private Integer pageSize;

}
