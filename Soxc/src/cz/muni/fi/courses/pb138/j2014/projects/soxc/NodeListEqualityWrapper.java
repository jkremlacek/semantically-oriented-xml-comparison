/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Node;

/**
 * A wrapper that overrides {@link Object#hashCode()} and {@link Object#equals(java.lang.Object)}
 * to compare {@link Node} lists for equality according to the specified {@link Options}.
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public final class NodeListEqualityWrapper {
    
    private final List<Node> nodeList;
    private final Options options;
    private final Map<NodeEqualityWrapper, Integer> elsOrAttrs;
    private final List<NodeEqualityWrapper> otherNodes;
    private final int hashCodeCached;
    
    /**
     * Gets the original node list.
     * @return 
     */
    public final List<Node> getNodeList() {
        return nodeList;
    }

    /**
     * Gets the options.
     * @return 
     */
    public final Options getOptions() {
        return options;
    }
    
    public NodeListEqualityWrapper(List<Node> nodeList, Options options) {
        this.nodeList = nodeList;
        this.options = options;
        
        // divide nodes into groups:
        elsOrAttrs = new HashMap<>(nodeList.size());
        otherNodes = new ArrayList<>(nodeList.size());
        for(Node node : nodeList) {
            NodeEqualityWrapper wrapper = new NodeEqualityWrapper(node, options);
            
            // find out if the order is to be ignored for this node:
            if(node.getNodeType() == Node.ELEMENT_NODE && options.ignoreElementOrder()) {
                // add into the "multiset":
                Integer count = elsOrAttrs.get(wrapper);
                if(count == null)
                    elsOrAttrs.put(wrapper, 1);
                else
                    elsOrAttrs.put(wrapper, count + 1);
            }
            else
                otherNodes.add(wrapper);
        }
        
        hashCodeCached = calculateHashCode();
    }
    
    private int calculateHashCode() {
        int hashCode = 7;
        hashCode = 71 * hashCode + elsOrAttrs.hashCode();
        hashCode = 71 * hashCode + otherNodes.hashCode();
        return hashCode;
    }

    @Override
    public final int hashCode() {
        return hashCodeCached;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodeListEqualityWrapper other = (NodeListEqualityWrapper) obj;
        return elsOrAttrs.equals(other.elsOrAttrs) && otherNodes.equals(other.otherNodes);
    }
}
