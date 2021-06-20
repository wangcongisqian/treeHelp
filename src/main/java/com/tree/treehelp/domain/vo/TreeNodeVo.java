package com.tree.treehelp.domain.vo;

import java.util.List;

public class TreeNodeVo {
    /** 对应中的id字段,前端数据树中的key*/
    private String key;

    /** 对应中的id字段,前端数据树中的value*/
    private String value;

    /** 对应Name字段,前端数据树中的title*/
    private String title;

    private String pId;

    private boolean isLeaf;

    private String treeLevel;

    private String treeCode;

    private List<String> allTreeKeys;

    public List<String> getAllTreeKeys() {
        return allTreeKeys;
    }

    public void setAllTreeKeys(List<String> allTreeKeys) {
        this.allTreeKeys = allTreeKeys;
    }

    private List<TreeNodeVo> children;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        if (children==null){
            this.isLeaf = false;
        }
        this.isLeaf = leaf;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getTreeCode() {
        return treeCode;
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode;
    }

    public List<TreeNodeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeVo> children) {
        this.children = children;
    }
}
