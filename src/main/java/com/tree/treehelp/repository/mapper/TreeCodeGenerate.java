package com.tree.treehelp.repository.mapper;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.tree.treehelp.domain.entity.TreeEntity;
import com.tree.treehelp.util.GenerateTreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface TreeCodeGenerate<T extends TreeEntity>{

    /**
     * @author: wc
     * @description: treeCode 生成方法
     * @param: fatherFunction
     * @param: childFunction
     * @param: parentId 父节点id
     * @param: pid real pid number like 0
     * @return: java.lang.String[]
     */
    default String[] generateCode(SFunction<T, ?> fatherFunction, SFunction<T, ?> childFunction, String parentId,String pid){
        String[] strArray = new String[3];
        List<T> getByFatherIdList = this.getListByOneField(fatherFunction,parentId);
        if(getByFatherIdList != null && !getByFatherIdList.isEmpty()){
            TreeEntity treeEntity = getByFatherIdList.get(0);
            String treeCode = treeEntity.getTreeCode();
            String treeLevel = treeEntity.getTreeLevel();
            strArray[0] = String.valueOf(getByFatherIdList.size() + 1);
            strArray[1] = GenerateTreeUtil.getNextCode(treeCode,2,false);
            strArray[2] = String.valueOf(Integer.valueOf(treeLevel));
        }else{
            if(StringUtils.equals(parentId,pid)){
                strArray[0] = "1";
                strArray[1] = GenerateTreeUtil.getNextCode(null,2,false);
                strArray[2] = "1";
            }else{
                List<T> getByIdList = this.getListByOneField(childFunction,parentId);
                if(getByIdList.isEmpty()){
                    throw ExceptionUtils.mpe("fail loading");
                }

                String treeCode = getByIdList.get(0).getTreeCode();
                String treeLevel = getByIdList.get(0).getTreeLevel();
                strArray[0] = "1";
                strArray[1] = GenerateTreeUtil.getNextCode(treeCode,2);
                strArray[2] = String.valueOf(Integer.valueOf(treeLevel) + 1);
            }

        }
        return strArray;
    }
    /**
     * 通过属性获取实体列表
     * @param column 属性Getter方法
     * @param param 属性值
     * @return 实体列表
     */
    @SelectProvider(type = InitialiseSQL.class,method = "getListByOneField")
    List<T> getListByOneField(SFunction<T, ?> column, Object param);

}
