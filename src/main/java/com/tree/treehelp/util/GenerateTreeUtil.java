package com.tree.treehelp.util;

import com.tree.treehelp.domain.vo.TreeNodeVo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class GenerateTreeUtil {

    /**
     * @author: wc
     * @description: 根据所传入的参数生成树型结构 使用方式：
     *                  一、所需生成树的vo extends TreeNodeVo (key、value、title、pid均需按实际情况设置)
     *                  二、传入树根节点和所需拼到树根下的节点list
     * @param: rootList 树根节点
     * @param: childrenList 树孩子节点
     * @param: isExpanded 是否展开
     * @return: java.util.List<com.cssca.mars.reg.modules.treehelp.domain.vo.TreeNodeVo> 返回树结构列表
     */
    public static List<TreeNodeVo> generateTree(List<TreeNodeVo> rootList, List<TreeNodeVo> childrenList, boolean isExpanded){
        if(childrenList != null && !childrenList.isEmpty()){
            Map<String,String> map = new LinkedHashMap<>();
            List<String> expandedList = new ArrayList<>();
            rootList.forEach(beanTree -> {
                if(!beanTree.isLeaf() && isExpanded){
                    expandedList.add(beanTree.getKey());
                }
                beanTree.setAllTreeKeys(expandedList);
                generateChild(beanTree,map,childrenList,expandedList);
            });

            return rootList;
        }
        return rootList;
    }

    public static List<TreeNodeVo> getRootOrElseTree(List<TreeNodeVo> tree,String rootId,boolean isRoot) {
        List<TreeNodeVo> treeList = tree.stream()
                .filter((l -> isRoot ?
                        StringUtils.equals(rootId,l.getpId()) :
                        !StringUtils.equals(rootId,l.getpId())))
                .collect(Collectors.toList());
        return treeList;
    }

    private static void generateChild(TreeNodeVo beanTree, Map<String,String> map, List<TreeNodeVo> childrenList, List<String> expandedList){
        List<TreeNodeVo> childList = new ArrayList<>();
        childrenList.stream()
                .filter(c -> !map.containsKey(c.getKey()))
                .filter(c ->c.getpId().equals(beanTree.getKey()))
                .forEach(c ->{
                    map.put(c.getKey(),c.getpId());
                    generateChild(c,map,childrenList,expandedList);//递归调用
                    childList.add(c);
                });
        if(childList.isEmpty()){
            beanTree.setLeaf(true);
        }else{
            expandedList.add(beanTree.getKey());
        }
        beanTree.setChildren(childList);
    }

    /**
     * @author: wc
     * @description: 方法说明:
     *                 1、根节点生成
     *                    1) 库中没有任何节点 传参 code = null,生成第一个节点A01
     *                 2、子节点生成
     *                    1) getNextCode(code,2); code为父节点 如传入A01 生成A01A01
     *                 3、同级下一个节点生成
     *                    1) getNextCode(code,2,false); code 为同级最大节点 如A10 生成A11
     * @param: code 初始code
     * @param: numPlaceHolder 数字占位位数【父子之间的占位位数需相同，否则生成错误】
     * @return: java.lang.String
     */
    public static synchronized String getNextCode(String code, int numPlaceHolder, boolean isRoot) {
        //数据库无数据 调用getNextCode(null,numPlaceHolder);
        if(StringUtils.isBlank(code)){
            String prefixLetter = "A";
            String suffixNum = getStringNum(1,numPlaceHolder);
            return prefixLetter + suffixNum;
        }

        if(StringUtils.isNotBlank(code) && isRoot){//字母需+1
            String letter = code.substring(0,getPrefixCodeIndex(code));
            char prefixLetter = getNextLetter(letter.charAt(0));
            return prefixLetter + getStringNum(1,numPlaceHolder);
        }

        //添加子节点有兄弟元素 getNextCode(lastCode);
        int firstDigitIndex = getBeforeCodeIndex(code);
        String prefixLetter = code.substring(0,firstDigitIndex);
        String suffixNum = code.substring(firstDigitIndex);
        int realNum = Integer.parseInt(suffixNum);

        if(realNum < getMaxNumOfNumHolder(numPlaceHolder)){
            realNum ++;
            String retVal = prefixLetter + getStringNum(realNum,numPlaceHolder);
            return retVal;
        }else{
            //超过范围 如A99 仅支持A99之后 继续生成B01 其他将出问题
            char nextLetter = getNextLetter(prefixLetter.charAt(0));
            //获取最后一级
            String lastCode = getMidOf0AndLastLetterString(code);
            return lastCode + nextLetter + getStringNum(1,numPlaceHolder);
        }
    }

    /**
     * @author: wc
     * @description: 生成下级code 如A01 下级为A0101
     * @param: fatherCode
     * @param: childCode
     * @param: numPlaceHolder
     * @return: java.lang.String
     */
    public static synchronized String getNextCode(String fatherCode,int numPlaceHolder) {
        return fatherCode + fatherCode.substring(0,getPrefixCodeIndex(fatherCode)) + getStringNum(1,numPlaceHolder);
    }

    private static String getMidOf0AndLastLetterString(String code) {
        int begin = getPrefixCodeIndex(code) - 1;
        int end = getBeforeCodeIndex(code) - 1;
        return code.substring(begin,end);
    }

    /**
     * @author: wc
     * @description: TODO 生成下一个字母 目前从A到Z 然后再从A到Z
     * @param: letter
     * @return: char
     */
    private static char getNextLetter(char letter) {
        if (letter == 'Z') {
            return 'A';
        }
        letter++;
        return letter;
    }

    private static int getMaxNumOfNumHolder(int numPlaceHolder) {
        double returnValue = Math.pow(10,numPlaceHolder) - 1;
        return (int)returnValue;
    }

    /**
     * @author: wc
     * @description: 获取从0位置开始到第一个出现数字的位置
     * @param: fatherCode
     * @return: int
     */
    private static int getPrefixCodeIndex(String fatherCode) {
        int returnIndex = 1;
        char codechars[] = fatherCode.toCharArray();
        for (char codechar : codechars) {
            if (Character.isDigit(codechar)){
                returnIndex = fatherCode.indexOf(codechar);
                break;
            }
        }
        return returnIndex;
    }

    /**
     * @author: wc
     * @description: 获取从最后位置开始倒数第一个字母出现的位置
     * @param: code
     * @return: int
     */
    private static int getBeforeCodeIndex(String code) {
        int returnIndex = 1;
        char codechars[] = code.toCharArray();
        for (int i = codechars.length - 1;i > 0; i--) {
            if(Character.isLetter(codechars[i])){
                returnIndex = code.lastIndexOf(codechars[i]) + 1;
                break;
            }
        }
        return returnIndex;
    }

    private static String getStringNum(int num,int numPlaceHolder) {
        return String.format("%0" + numPlaceHolder + "d", num);
    }

}
