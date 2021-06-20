package com.tree.treehelp.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TreeEntity {
    private String id;
    private String parentId;
    private String sort;
    private String treeLevel;
    private String treeCode;
}