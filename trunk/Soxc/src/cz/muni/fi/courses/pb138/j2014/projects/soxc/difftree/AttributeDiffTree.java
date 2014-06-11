/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.AttributeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.Attr;

/**
 * A {@link DiffTree} node for an attribute.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class AttributeDiffTree extends HierarchicalNodeDiffTree {
    
    private final Attr node;
    private final List<NamespaceUriDiffTree> nsUri;
    private final List<LocalNameDiffTree> localName;
    private final List<PrefixDiffTree> prefix;

    @Override
    public final Attr getNode() {
        return node;
    }

    /**
     * Gets the nodes for the namespace URI of this attribute.
     * @return 
     */
    public final List<NamespaceUriDiffTree> getNamespaceUri() {
        return nsUri;
    }

    /**
     * Gets the nodes for the prefix of this attribute.
     * @return 
     */
    public final List<PrefixDiffTree> getPrefix() {
        return prefix;
    }
    
    /**
     * Gets the nodes for the local name of this attribute.
     * @return 
     */
    public final List<LocalNameDiffTree> getLocalNameTree() {
        return localName;
    }
    
    public AttributeDiffTree(DocumentSide side, Attr node,
            List<NamespaceUriDiffTree> nsUri,
            List<PrefixDiffTree> prefix,
            List<LocalNameDiffTree> localName,
            List<NodeDiffTree> children) {
        super(side, children);
        
        this.node = node;
        this.nsUri = Collections.unmodifiableList(nsUri);
        this.prefix = Collections.unmodifiableList(prefix);
        this.localName = Collections.unmodifiableList(localName);
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        AttributeDiffConsumer attrConsumer = consumer.beginAttribute(getSide(), node);
        
        for(NamespaceUriDiffTree nsUriTree : nsUri)
            nsUriTree.replay(attrConsumer);
        
        for(PrefixDiffTree prefixTree : prefix)
            prefixTree.replay(attrConsumer);
        
        for(LocalNameDiffTree localNameTree : localName)
            localNameTree.replay(attrConsumer);
        
        replayChildren(attrConsumer);
        
        attrConsumer.end();
    }
}
