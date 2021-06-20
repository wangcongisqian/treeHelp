package com.tree.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo {

    @RequestMapping("test")
    public String test1(){
        return "success";
    }
}
