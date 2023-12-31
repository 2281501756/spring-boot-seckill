package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillUser {
    @Id
    private Long id;
    private String name;
    private String phone;
    private Date createTime;
    private Integer isDeleted;
}
