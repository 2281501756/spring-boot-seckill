package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillProducts {
    @Id
    private Long id;
    private String name;
    private Integer count;
    private Integer saled;
    private Date createTime;
    private Integer isDeleted;
    private Date startBuyTime;
    private Date updatedTime;
    private String productDesc;
    private Integer status;
    private String memo;
    private String productPeriodKey;
}
