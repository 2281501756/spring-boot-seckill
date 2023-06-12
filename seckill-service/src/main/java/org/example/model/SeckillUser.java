package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillUser {
    private Long id;
    private String name;
    private String phone;
    private Date createTime;
    private Integer isDeleted;
}
