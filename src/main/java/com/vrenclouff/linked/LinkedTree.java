package com.vrenclouff.linked;


import com.vrenclouff.Tree;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LinkedTree<T extends Serializable> implements Tree<T> {

    private final TreeNode<T> root;
    private int size;

    public LinkedTree() {
        this(new TreeNode<>((T)null, null), 0);
    }

    public LinkedTree(T rootValue) {
        this(new TreeNode<>(rootValue, null));
    }

    public LinkedTree(TreeNode<T> root) {
        this(root, 1);
    }

    private LinkedTree(TreeNode<T> root, int size) {
        this.root = root;
        this.size = size;
    }

    @SafeVarargs @Override
    public final void add(T... values) {
        add(Arrays.asList(values).iterator(), root);
    }

    @SafeVarargs @Override
    public final void addWithRoot(T... values) {
        Iterator<T> valuesIterator = Arrays.asList(values).iterator();
        TreeNode<T> treeNode = root;

        if (!treeNode.value().equals(valuesIterator.next())) {
            throw new IllegalArgumentException("First value must be root value");
        }

        add(valuesIterator, treeNode);
    }

    private void add(Iterator<T> valuesIterator, TreeNode<T> root) {
        TreeNode<T> treeNode = root;
        branch:
        while (valuesIterator.hasNext()) {
            T value = valuesIterator.next();

            List<TreeNode<T>> children = treeNode.children();
            for (TreeNode<T> child : children) {
                if (child.value().equals(value)) {
                    treeNode = child;
                    continue branch;
                }
            }
            treeNode = newNodeInstance(value, treeNode);
            children.add(treeNode);
            size++;
        }
    }

    private TreeNode<T> newNodeInstance(T value, TreeNode<T> treeNode) {
        try {
            return root.getClass().getConstructor(value.getClass(), TreeNode.class).newInstance(value, treeNode);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            return new TreeNode<>(value, treeNode);
        }
    }

    @SafeVarargs @Override
    public final void remove(T... values) {
        Iterator<T> valuesIterator = Arrays.asList(values).iterator();
        TreeNode<T> treeNode = root;
        while (valuesIterator.hasNext()) {
            List<TreeNode<T>> children = treeNode.children();
            T value = valuesIterator.next();
            for (TreeNode<T> child : children) {
                if (child.value().equals(value)) {
                    treeNode = child;
                }
            }
        }

        while(!treeNode.isRoot() && treeNode.isLeaf()) {
            TreeNode<T> parent = treeNode.parent();
            parent.children().remove(treeNode);
            treeNode = parent;
            size--;
        }
    }

    TreeNode<T> root() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clean() {
        this.root.children().clear();
        this.size = root.value() == null ? 0 : 1;
    }

    @Override
    public Iterator<T> depthIterator() {
        return new LinkedDepthIterator<>(this);
    }

    @Override
    public Iterator<T> breadthIterator() {
        return new LinkedBreadthIterator<>(this);
    }


}
