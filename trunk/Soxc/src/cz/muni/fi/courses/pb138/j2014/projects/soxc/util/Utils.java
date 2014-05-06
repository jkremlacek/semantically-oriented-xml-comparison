/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.util;

import java.util.AbstractList;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Some common utility functions.
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class Utils {
    
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
}