package com.tree.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tree.demo.vo.DeptVo;
import com.tree.treehelp.domain.entity.TreeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@TableName("SYS_DEPT")
public class Dept extends TreeEntity {
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @JsonSerialize(using= ToStringSerializer.class)
    private String id;
    @TableField(value = "NAME")
    private String name;
    @TableField(value = "P_ID")
    private String pId;
    @TableField(value = "TREE_LEVEL")
    private String treeLevel;
    @TableField(value = "TREE_CODE")
    private String treeCode;
    @TableField(value = "SORT")
    private String sort;

    public static DeptVo wrapEntity(Dept entity) {
        DeptVo vo = new DeptVo();
        vo.setKey(entity.getId());
        vo.setValue(entity.getId());
        vo.setTitle(entity.getName());
        vo.setpId(entity.getParentId());
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

}
