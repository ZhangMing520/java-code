package com.example.works.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangming
 * @date 2019/4/30 0:26
 * <p>
 * 树几点
 */
public class TreeNode {

    private String id;
    private String name;
    // 0 无 1 查看  2 编辑
    private int permission;

    private String parentId;

    private List<TreeNode> children = new ArrayList<>();

    public TreeNode() {
    }

    public TreeNode(String id, String name, int permission, String parentId) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permission=" + permission +
                ", parentId='" + parentId + '\'' +
                ", children=" + children +
                '}';
    }
}

