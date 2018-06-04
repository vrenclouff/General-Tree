package com.vrenclouff.linked;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class TreeNode<T extends Serializable> implements Serializable {

    private final T value;
    private final TreeNode<T> parent;
    private final List<TreeNode<T>> children;

    TreeNode(T value, TreeNode<T> parent) {
        this.value = value;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    T value() {
        return value;
    }

    TreeNode<T> parent() {
        return parent;
    }

    List<TreeNode<T>> children() {
        return children;
    }

    boolean isLeaf() {
        return children().isEmpty();
    }

    boolean isRoot() {
        return parent == null;
    }
}