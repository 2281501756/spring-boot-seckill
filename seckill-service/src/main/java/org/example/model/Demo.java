package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Demo {
    private Long id;
    private String text;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
    private Integer isEnabled;
}
