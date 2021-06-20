package com.tree.demo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tree.demo.entity.Dept;
import com.tree.treehelp.domain.vo.TreeNodeVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

@Data
@TableName("sys_dept")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class DeptVo extends TreeNodeVo {
    private String id;
    private String name;
    private String pId;

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
