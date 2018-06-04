package com.vrenclouff;

import com.vrenclouff.linked.LinkedTree;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


class TreeTest {

    @Test
    void createTreeWithoutRoot() {

        /*
               null
                |
                a
               / \
              b   c
         */

        Tree<String> tree = new LinkedTree<>();

        tree.add("a", "b");
        tree.add("a", "c");

        assertEquals(3, tree.size());

    }

    @Test
    void createTreeWithRoot() {

        /*
                o
                |
                a
               / \
              b   c

         */

        Tree<String> tree = new LinkedTree<>("o");

        tree.add("a", "b");
        tree.add("a", "c");

        assertEquals(4, tree.size());

    }

    @Test
    void removeElement() {

        /*
                o                   o
                |                   |
                a       ->          a
               / \                 /
              b   c               b

         */

        Tree<String> tree = new LinkedTree<>("o");

        tree.add("a", "b");
        tree.add("a", "c");

        tree.remove("a", "c");

        assertEquals(3, tree.size());

    }

    @Test
    void addWithRootElement() {

        /*
                o
                |
                a
               / \
              b   c

         */

        Tree<String> tree = new LinkedTree<>("o");

        tree.addWithRoot("o", "a", "b");
        tree.addWithRoot("o", "a", "c");

        assertEquals(4, tree.size());
    }

    @Test
    void cleanTreeWithoutRoot() {

         /*
               null             null
                |
                a      ->
               / \
              b   c
         */

        Tree<String> tree = new LinkedTree<>();

        tree.add("a", "b");
        tree.add("a", "c");

        tree.clean();

        assertEquals(0, tree.size());

    }

    @Test
    void cleanTreeWithRoot() {

         /*
                o             o
                |
                a      ->
               / \
              b   c
         */

        Tree<String> tree = new LinkedTree<>("o");

        tree.add("a", "b");
        tree.add("a", "c");

        tree.clean();

        assertEquals(1, tree.size());

    }

    @Test
    void breadthIterator() {

        /*
                       .
                       |
                       a
                       |
                       b
                     /  \
                    c    d
                        / \
                       e   f
         */

        Tree<String> tree = new LinkedTree<>(".");

        tree.add("a", "b", "c");
        tree.add("a", "b", "d", "e");
        tree.add("a", "b", "d", "f");

        assertEquals(7, tree.size());

        Iterator<String> iterator = tree.breadthIterator();

        assertEquals(".", iterator.next());
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertEquals("c", iterator.next());
        assertEquals("d", iterator.next());
        assertEquals("e", iterator.next());
        assertEquals("f", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void depthIterator() {

        /*
                       .
                       |
                       a
                       |
                       b
                     /  \
                    c    d
                        / \
                       e   f
         */

        Tree<String> tree = new LinkedTree<>(".");

        tree.add("a", "b", "c");
        tree.add("a", "b", "d", "e");
        tree.add("a", "b", "d", "f");

        assertEquals(7, tree.size());

        Iterator<String> iterator = tree.depthIterator();

        assertEquals("c", iterator.next());
        assertEquals("e", iterator.next());
        assertEquals("f", iterator.next());
        assertEquals("d", iterator.next());
        assertEquals("b", iterator.next());
        assertEquals("a", iterator.next());
        assertEquals(".", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void notRootException() {

        /*
                .
                |
                a

         */

        Tree<String> tree = new LinkedTree<>(".");

        assertThrows(IllegalArgumentException.class, () -> tree.addWithRoot("a"));
    }

}