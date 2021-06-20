package com.tree.treehelp.repository.mapper;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wc
 * @description: 通用SQL装配
 */
public class InitialiseSQL {

    /**
     * @author: wc
     * @description: 参数填充 仅支持一个字段的条件
     *                  若此值为主键如ID 需设置@TableId(value = "ID", type = **)
     * @param: column
     * @param: format
     * @return: java.lang.String
     */
    private String paramFill(SFunction<?, ?> column,String format) {
        SerializedLambda lambda = LambdaUtils.resolve(column);
        Class<?> aClass = lambda.getInstantiatedType();
        String tableName = TableInfoHelper.getTableInfo(aClass).getTableName();//获取表名
        String columnNames = TableInfoHelper.getTableInfo(aClass).getAllSqlSelect();//获取所有字段 形如:ID,NAME...
        TableInfoHelper.getTableInfo(aClass).getFieldList().get(0).getColumn();
        List<TableFieldInfo> fieldInfos = TableInfoHelper.getTableInfo(aClass).getFieldList().stream()
                .filter(property -> StringUtils.equals(property.getProperty(),PropertyNamer.methodToProperty(lambda.getImplMethodName())))
                .collect(Collectors.toList());
        String fieldName = "";
        if(fieldInfos.isEmpty()){//不存在字段 赋值为主键 id
            fieldName = TableInfoHelper.getTableInfo(aClass).getKeyProperty();
        }else{
            fieldName = fieldInfos.get(0).getColumn();//获取查询字段名
        }
        return String.format(format,columnNames,tableName,fieldName);
    }

    public String getListByOneField(SFunction<?, ?> column) {
        String format = "SELECT %s FROM %s WHERE %s = #{param} ORDER BY SORT DESC,TREE_LEVEL DESC";
        return paramFill(column,format);
    }

}