package com.example.works.tree;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangming
 * @date 2019/4/30 0:29
 */
public class TreeMain {

    public static void main(String[] args) {
        //
        TreeNode root = new TreeNode("1", "java", 2, null);
        TreeNode spring = new TreeNode("2", "spring", 2, "1");
        TreeNode hibernate = new TreeNode("3", "hibernate", 2, "1");
        TreeNode mybatis = new TreeNode("4", "mybatis", 2, "1");

        TreeNode springJdbc = new TreeNode("5", "springJdbc", 0, "2");
        TreeNode springJpa = new TreeNode("6", "springJpa", 1, "7");

        TreeNode mapper = new TreeNode("7", "mapper", 2, "4");
        TreeNode hsql = new TreeNode("8", "hsql", 0, "3");

        TreeNode springTemplate = new TreeNode("9", "springTemplate", 1, "5");

        List<TreeNode> treeNodeList = new ArrayList<>();
        treeNodeList.add(spring);
        treeNodeList.add(hibernate);
        treeNodeList.add(mybatis);
        treeNodeList.add(springJpa);
        treeNodeList.add(springJdbc);
        treeNodeList.add(mapper);
        treeNodeList.add(hsql);
        treeNodeList.add(springTemplate);

//        filterTreeNodeByPermission(treeNodeList);

        List<TreeNode> nameNodeList = filterTreeNodeByName(treeNodeList.iterator(), "spring");

        List<TreeNode> nameFilterList = new ArrayList<>();
        filterTreeNodeByName(nameNodeList, treeNodeList, nameFilterList);

//        buildTree(root, treeNodeList);
        buildTree(root, nameFilterList);

        System.out.println(JSON.toJSON(root));
    }


    /**
     * 构建结构树
     *
     * @param treeNodeList 其他节点
     * @param parentNode   父节点
     */
    static void buildTree(TreeNode parentNode, List<TreeNode> treeNodeList) {
        Iterator<TreeNode> iterator = treeNodeList.iterator();
        List<TreeNode> childrenList = getChildrenTreeNode(iterator, parentNode.getId());

        // 设置子节点
        parentNode.setChildren(childrenList);
        if (childrenList.size() == 0) {
            return;
        } else {
            for (TreeNode childNode : childrenList) {
                buildTree(childNode, treeNodeList);
            }
        }
    }

    /**
     * @param nodeIterator 可以删除已经被获取的元素
     * @param parentId     父id
     * @return 获取 parentId 的子类
     */
    static List<TreeNode> getChildrenTreeNode(Iterator<TreeNode> nodeIterator, String parentId) {
        List<TreeNode> list = new ArrayList<>();

        while (nodeIterator.hasNext()) {
            TreeNode next = nodeIterator.next();
            boolean addFlag = false;
            if (parentId == null) {
                if (next.getParentId() == null) {
                    addFlag = true;
                }
            } else {
                if (next.getParentId().equals(parentId)) {
                    addFlag = true;
                }
            }

            if (addFlag) {
                list.add(next);
                // 移除
                nodeIterator.remove();
            }
        }

        return list;
    }

    /**
     * 根据权限过滤
     *
     * @param treeNodeList
     */
    static void filterTreeNodeByPermission(List<TreeNode> treeNodeList) {
        Iterator<TreeNode> iterator = treeNodeList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getPermission() == 0) {
                iterator.remove();
            }
        }
    }

    /**
     * 只显示此 name 节点和其父节点
     *
     * @param nameNodeList 包含名字的节点
     * @param treeNodeList
     */
    static void filterTreeNodeByName(List<TreeNode> nameNodeList, List<TreeNode> treeNodeList, List<TreeNode> resultList) {
        for (TreeNode nameNode : nameNodeList) {
            resultList.add(nameNode);
            findParentNode(nameNode, treeNodeList, resultList);
        }
    }

    static void findParentNode(TreeNode nameNode, List<TreeNode> treeNodeList, List<TreeNode> resultList) {
        Iterator<TreeNode> iterator = treeNodeList.iterator();
        while (iterator.hasNext()) {
            TreeNode next = iterator.next();
            if (nameNode.getParentId().equals(next.getId())) {
                resultList.add(next);
                // 不需要多次添加
                iterator.remove();

                // 这里是等于 1 到达了最上层
                if (!"1".equals(next.getParentId())) {
                    findParentNode(next, treeNodeList, resultList);
                }
            }
        }
    }

    /**
     * @param nodeIterator
     * @param name
     * @return
     */
    static List<TreeNode> filterTreeNodeByName(Iterator<TreeNode> nodeIterator, String name) {
        List<TreeNode> list = new ArrayList<>();
        while (nodeIterator.hasNext()) {
            TreeNode next = nodeIterator.next();
            if (next.getName().contains(name)) {
                list.add(next);
                // 移除
                nodeIterator.remove();
            }
        }
        return list;
    }
}
