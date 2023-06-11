package org.example.service.Impl;

import org.example.mapper.DemoMapper;
import org.example.model.Demo;
import org.example.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private DemoMapper demoMapper;

    @Override
    public List<Demo> getAll() {
        return demoMapper.selectAll();
    }

    @Override
    public int create(Demo d) {
        return demoMapper.insert(d);
    }
}
