package com.tree.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.tree.demo.entity.Dept;
import com.tree.demo.service.DeptService;
import com.tree.demo.vo.DeptVo;
import com.tree.treehelp.domain.vo.TreeNodeVo;
import com.tree.treehelp.util.GenerateTreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloWorldController {
    //father id
    private final String pid = "0";

    @Resource
    private DeptService deptService;

    /**
     * @author: wc
     * @description: 查询整颗dept树 根节设置为默认值 pid = 0
     * 原则上支持任意节点为根节点，仅需通过GenerateTreeUtil.generateTree方法，
     *      其中第一个参数为根节点的列表，第二个参数为子节点的列表
     *      查询子节点列表可配合使用TREE_CODE、TREE_LEVEL、SORT等
     * @param:
     * @return: java.lang.String
     */
    @GetMapping(value = "/getDeptTreeList")
    public String getDeptTreeList(){
        //get dept list from database
        List<Dept> deptList = deptService.list();
        //thansfor to vo
        List<TreeNodeVo> voTree = deptList.stream().map(DeptVo::wrapEntity).collect(Collectors.toList());

        //get rootlist and children list
        List<TreeNodeVo> root = GenerateTreeUtil.getRootOrElseTree(voTree,pid,true);
        List<TreeNodeVo> child = GenerateTreeUtil.getRootOrElseTree(voTree, pid,false);

        //generate tree
        List<TreeNodeVo> treeDeepList = GenerateTreeUtil.generateTree(root,child,false);
        JSONObject obj = new JSONObject();
        obj.put("treeDeepList",treeDeepList);
        return obj.toString();
    }
    /**
     * @author: wc
     * @description: 添加的方法
     *              生成树的TREE_CODE,生成节点的TREE_LEVEL表示此根级别,SORT 表示生成树的本级别内的排序
     *                  TREE_CODE          TREE_LEVEL   SORT
     * 	                    A01					1		 1
     * 	                      --A0101			2        1
     * 	                      --A0102			2		 2
     * 	                    A02					1		 2
     * 	                      --A0201			2        1
     * 	                      --A0202			2        2
     * 	                      --A020201			3        1
     * 	                    ...					...      ...
     * @param: addDTO
     * @return: java.lang.String
     */
    @PostMapping("revenueCodes")
    public boolean add(@RequestBody Dept addDTO) {
        JSONObject obj = new JSONObject();
        if(StringUtils.isBlank(addDTO.getPId())){
            addDTO.setPId(pid);
        }
        boolean returnValue = this.deptService.add(addDTO,pid);
        return returnValue;
    }
}
