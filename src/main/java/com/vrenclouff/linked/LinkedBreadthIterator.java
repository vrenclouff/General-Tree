package com.vrenclouff.linked;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

class LinkedBreadthIterator<T extends Serializable> implements Iterator<T> {

    private final Stack<TreeNode<T>> stack = new Stack<>();

    LinkedBreadthIterator(LinkedTree<T> tree) {
        stack.add(tree.root());
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public T next() {
        TreeNode<T> treeNode = stack.pop();
        List<TreeNode<T>> children = treeNode.children();
        for (int i = children.size() - 1; i >= 0; i--) {
            stack.add(children.get(i));
        }
        return treeNode.value();
    }
}