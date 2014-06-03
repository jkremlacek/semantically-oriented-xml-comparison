/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Some common utility functions.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
class Utils {
    
    /**
     * Gets the hash code of the given object; checks for {@code null}.
     * @param obj
     * @return 
     */
    public static int getHashCode(Object obj) {
        if(obj == null)
            return 0;
        return obj.hashCode();
    }
    
    /**
     * Compares two objects for equality; checks for {@code null}.
     * @param a
     * @param b
     * @return 
     */
    public static boolean equal(Object a, Object b) {
        if(a == null)
            return b == null;
        if(b == null)
            return false;
        return a.equals(b);
    }

    private static class NodeListAsList extends AbstractList<Node> {
        
        private final NodeList nodeList;

        public NodeListAsList(NodeList nodeList) {
            this.nodeList = nodeList;
        }
        
        @Override
        public Node get(int index) {
            return nodeList.item(index);
        }

        @Override
        public int size() {
            return nodeList.getLength();
        }
    }
    
    private static class NamedNodeMapAsList extends AbstractList<Node> {
        
        private final NamedNodeMap nodeList;

        public NamedNodeMapAsList(NamedNodeMap nodeList) {
            this.nodeList = nodeList;
        }

        @Override
        public Node get(int index) {
            return nodeList.item(index);
        }
        
        @Override
        public int size() {
            return nodeList.getLength();
        }
    }
    
    /**
     * Views the given {@link NodeList} as a {@link List}.
     * @param nodeList
     * @return 
     */
    public static List<Node> asList(NodeList nodeList) {
        return new NodeListAsList(nodeList);
    }

    /**
     * Views the given {@link NamedNodeMap} as a {@link List}.
     * @param nodeList
     * @return 
     */
    public static List<Node> asList(NamedNodeMap nodeList) {
        return new NamedNodeMapAsList(nodeList);
    }
    
    public static <T> Consumer<T> asConsumer(final Collection<T> collection) {
        return new Consumer<T>() {

            @Override
            public void feed(T element) {
                collection.add(element);
            }
        };
    }
    
    public static <T> Consumer<T> asMultiSetConsumer(final Map<T, Integer> countMap) {
        return new Consumer<T>() {

            @Override
            public void feed(T e) {
                Integer count = countMap.get(e);
                if(count == null)
                    countMap.put(e, 1);
                else
                    countMap.put(e, count + 1);
            }
        };
    }
    
    public static Consumer<Node> autoWrapEquality(final Consumer<NodeEqualityWrapper> inner, final Options options) {
        return new Consumer<Node>() {

            @Override
            public void feed(Node element) {
                inner.feed(new NodeEqualityWrapper(element, options));
            }
        };
    }
    
    public static Consumer<Node> autoWrapSimilarity(final Consumer<NodeSimilarityWrapper> inner, final Options options) {
        return new Consumer<Node>() {

            @Override
            public void feed(Node element) {
                inner.feed(new NodeSimilarityWrapper(element, options));
            }
        };
    }
    
    public static void splitNodeList(List<Node> nodes, Consumer<Node> unordered, Consumer<Node> ordered, Options options) {
        for(Node node : nodes) {
            short nodeType = node.getNodeType();
            
            // find out if the order is to be ignored for this node:
            if((nodeType == Node.ELEMENT_NODE && options.ignoreElementOrder()) ||
                    nodeType == Node.ATTRIBUTE_NODE) {
                unordered.feed(node);
            }
            else {
                ordered.feed(node);
            }
        }
    }
}