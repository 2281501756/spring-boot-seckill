package org.example.mapper;

import org.example.model.Demo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface DemoMapper extends Mapper<Demo> {
}
