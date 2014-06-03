/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import org.w3c.dom.Node;

/**
 * A wrapper that overrides {@link Object#hashCode()} and {@link Object#equals(java.lang.Object)}
 * to compare {@link Node}s for equality according to the specified {@link Options}.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public class NodeEqualityWrapper {

    private final Node node;
    private final Options options;
    private final NodeSimilarityWrapper similarityWrapper;
    private final NodeListEqualityWrapper attributesEqualityWrapper;
    private final NodeListEqualityWrapper childrenEqualityWrapper;
    private final int hashCodeCached; // cache the hash code for performance (the node won't change)

    /**
     * Gets the wrapped node.
     * @return 
     */
    public final Node getNode() {
        return node;
    }

    /**
     * Gets the options.
     * @return 
     */
    public final Options getOptions() {
        return options;
    }

    public NodeEqualityWrapper(Node node, Options options) {
        this.node = node;
        this.options = options;
        
        similarityWrapper = new NodeSimilarityWrapper(node, options);
        
        if(options.ignoreAttributesInSimilarity() && node.getAttributes() != null)
            attributesEqualityWrapper = new NodeListEqualityWrapper(
                    Utils.asList(node.getAttributes()), options);
        else
            attributesEqualityWrapper = null;
        
        childrenEqualityWrapper = new NodeListEqualityWrapper(
                Utils.asList(node.getChildNodes()), options);
        
        hashCodeCached = calculateHashCode();
    }
    
    private int calculateHashCode() {
        int hash = similarityWrapper.hashCode();
        if(attributesEqualityWrapper != null)
            hash = 83 * hash + attributesEqualityWrapper.hashCode();
        
        hash = 83 * hash + childrenEqualityWrapper.hashCode();
        
        String value = node.getNodeValue();
        if(!node.hasChildNodes() && value != null)
            hash = 83 * hash + value.hashCode();
        else
            hash = 83 * hash + 0;
        return hash;
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
        final NodeEqualityWrapper other = (NodeEqualityWrapper) obj;
        // compare similarity:
        if(!similarityWrapper.equals(other.similarityWrapper))
            return false;
        
        // if similarity doesn't compare attributes, compare them now:
        if(attributesEqualityWrapper != null &&
                !attributesEqualityWrapper.equals(other.attributesEqualityWrapper))
            return false;
        
        // compare children:
        if(!childrenEqualityWrapper.equals(other.childrenEqualityWrapper))
            return false;
        
        // compare value only if the node has one and doesn't have any children
        // (so that we don't compare attributes by children and then also by value):
        String value = node.getNodeValue();
        if(!node.hasChildNodes() && value != null && !value.equals(other.node.getNodeValue()))
            return false;
        return true;
    }
}
