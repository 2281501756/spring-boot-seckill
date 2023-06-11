package org.example.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResp<T> implements Serializable {
    private List<T> list;
    private Integer totalNum;
    private Integer totalPage;
    private Integer pageNum;
    private Integer pageSize;

}
