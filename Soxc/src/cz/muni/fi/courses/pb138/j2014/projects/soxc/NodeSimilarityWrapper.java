/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import org.w3c.dom.Node;

/**
 * A wrapper that overrides {@link Object#hashCode()} and {@link Object#equals(java.lang.Object)}
 * to compare {@link Node}s for similarity according to the specified {@link Options}.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class NodeSimilarityWrapper {
    
    private final Node node;
    private final Options options;
    private final NodeListEqualityWrapper attributesEqualityWrapper;
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

    public NodeSimilarityWrapper(Node node, Options options) {
        this.node = node;
        this.options = options;
        
        if(!options.ignoreAttributesInSimilarity() && node.getAttributes() != null)
            attributesEqualityWrapper = new NodeListEqualityWrapper(
                    Utils.asList(node.getAttributes()), options);
        else
            attributesEqualityWrapper = null;
        
        hashCodeCached = calculateHashCode();
    }

    private int getNodeNameHashCode(Node node) {
        int hash = 3;
        // if local name is null, just compare node names,
        // otherwise do a NS aware comparison:
        if(node.getLocalName() == null) {
            hash = 83 * hash + node.getNodeName().hashCode();
        }
        else {
            hash = 83 * hash + node.getLocalName().hashCode();
            if(!options.ignoreNamespaceURI())
                hash = 83 * hash + Utils.getHashCode(node.getNamespaceURI());
            if(!options.ignorePrefix())
                hash = 83 * hash + Utils.getHashCode(node.getPrefix());
        }
        return hash;
    }
    
    /**
     * Compares the names of the given nodes.
     * @param a first node
     * @param b second node
     */
    private boolean nodeNameEquals(Node a, Node b) {
        // if local name is null, just compare node names,
        // otherwise do a NS aware comparison:
        if(a.getLocalName() == null) {
            if(!a.getNodeName().equals(b.getNodeName()))
                return false;
        }
        else {
            if(!a.getLocalName().equals(b.getLocalName()))
                return false;
            if(!options.ignoreNamespaceURI() && !Utils.equal(a.getNamespaceURI(), b.getNamespaceURI()))
                return false;
            if(!options.ignorePrefix() && !Utils.equal(a.getPrefix(), b.getPrefix()))
                return false;
        }
        return true;
    }
    
    private int calculateHashCode() {
        int hash = 3;
        hash = 83 * hash + node.getNodeType();
        hash = 83 * hash + getNodeNameHashCode(node);
        if(attributesEqualityWrapper != null)
            hash = 83 * hash + attributesEqualityWrapper.hashCode();
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
        final NodeSimilarityWrapper other = (NodeSimilarityWrapper)obj;
        
        // compare node types:
        if(this.node.getNodeType() != other.node.getNodeType())
            return false;
        
        // compare node names:
        if(!nodeNameEquals(this.node, other.node))
            return false;
        
        // optionally compare attributes:
        if(attributesEqualityWrapper != null &&
                !this.attributesEqualityWrapper.equals(other.attributesEqualityWrapper))
            return false;
        return true;
    }
}
