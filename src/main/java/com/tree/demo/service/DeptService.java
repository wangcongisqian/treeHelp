package com.tree.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.demo.entity.Dept;

public interface DeptService extends IService<Dept> {
    boolean add(Dept addDTO,String pid);
}
