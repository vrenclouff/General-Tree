package com.vrenclouff.linked;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class TreeNode<T extends Serializable> implements Serializable {

    private final T value;
    private final TreeNode<T> parent;
    private final List<TreeNode<T>> children;

    protected TreeNode(T value, TreeNode<T> parent) {
        this.value = value;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    public T value() {
        return value;
    }

    public TreeNode<T> parent() {
        return parent;
    }

    public List<TreeNode<T>> children() {
        return children;
    }

    public boolean isLeaf() {
        return children().isEmpty();
    }

    public boolean isRoot() {
        return parent == null;
    }
}