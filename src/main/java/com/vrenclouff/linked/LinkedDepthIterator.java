package com.vrenclouff.linked;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


class LinkedDepthIterator<T extends Serializable> implements Iterator<T> {

    private enum NodeType { WHITE, GRAY }

    private class IteratorWrapper {
        TreeNode<T> treeNode;
        Iterator<TreeNode<T>> nodeIterator;
        NodeType type;

        IteratorWrapper(Iterator<TreeNode<T>> nodeIterator) {
            this.nodeIterator = nodeIterator;
            this.move();
        }

        TreeNode<T> move() {
            TreeNode<T> result = treeNode;
            treeNode = nodeIterator.next();
            type = NodeType.WHITE;
            return result;
        }
    }

    private final Stack<IteratorWrapper> stack = new Stack<>();

    LinkedDepthIterator(LinkedTree<T> tree) {
        stack.add(new IteratorWrapper(Arrays.asList(tree.root()).iterator()));
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public T next() {
        for (;;) {
            IteratorWrapper wrapper = stack.peek();

            if (wrapper.type.equals(NodeType.GRAY)) {
                if (wrapper.nodeIterator.hasNext()) {
                    return wrapper.move().value();
                } else {
                    return stack.pop().treeNode.value();
                }
            }
            wrapper.type = NodeType.GRAY;
            List<TreeNode<T>> children = wrapper.treeNode.children();
            if (!children.isEmpty()) {
                stack.push(new IteratorWrapper(children.iterator()));
            }
        }
    }
}