package com.tree.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tree.demo.entity.Dept;
import com.tree.treehelp.repository.mapper.TreeCodeGenerate;

public interface SysDeptMapper extends BaseMapper<Dept> , TreeCodeGenerate<Dept> {
}
