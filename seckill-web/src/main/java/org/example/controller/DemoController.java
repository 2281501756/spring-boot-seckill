package org.example.controller;


import org.example.base.BaseResponse;
import org.example.model.Demo;
import org.example.model.req.DemoReq;
import org.example.service.Impl.DemoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoServiceImpl demoService;

    @GetMapping("")
    public BaseResponse<List<Demo>> getALl() {
        List<Demo> all = demoService.getAll();
        return new BaseResponse<>(all);
    }
    @PostMapping("")
    public BaseResponse<Integer> create(@Valid @RequestBody  DemoReq demoReq) {
        Demo demo = new Demo();
        demo.setText(demoReq.text);
        int t = demoService.create(demo);
        return new BaseResponse<>(t);
    }

}
