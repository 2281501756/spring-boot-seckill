package org.example.mapper;

import org.example.model.SeckillUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface UserMapper extends Mapper<SeckillUser> {
   SeckillUser SelectByPhone(String phone);
}
