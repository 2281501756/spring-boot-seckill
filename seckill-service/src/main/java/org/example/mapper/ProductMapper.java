package org.example.mapper;

import org.example.model.SeckillProducts;
import tk.mybatis.mapper.common.Mapper;

public interface ProductMapper extends Mapper<SeckillProducts> {
    SeckillProducts selectByIdForUpdate(Long id);
}
