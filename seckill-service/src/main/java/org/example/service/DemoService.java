package org.example.service;

import org.example.model.Demo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DemoService {
    List<Demo> getAll();
    int create(Demo d);
}
