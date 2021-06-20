package com.tree.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.demo.entity.Dept;
import com.tree.demo.mapper.SysDeptMapper;
import com.tree.demo.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl extends ServiceImpl<SysDeptMapper, Dept> implements DeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Override
    public boolean add(Dept addDTO,String pid) {
        String[] codeArray = this.sysDeptMapper.generateCode(Dept::getParentId,Dept::getId,addDTO.getParentId(),pid);
        addDTO.setSort(codeArray[0]);
        addDTO.setTreeCode(codeArray[1]);
        String orgType = codeArray[2];
        addDTO.setTreeLevel(orgType);
        this.sysDeptMapper.insert(addDTO);
        return true;
    }
}
