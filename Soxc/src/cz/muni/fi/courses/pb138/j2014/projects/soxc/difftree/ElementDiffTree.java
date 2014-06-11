/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ElementDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.Element;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class ElementDiffTree extends HierarchicalNodeDiffTree {
    
    private final Element node;
    private final List<NamespaceUriDiffTree> nsUri;
    private final List<PrefixDiffTree> prefix;
    private final List<LocalNameDiffTree> localName;
    private final List<AttributeDiffTree> attributes;
    
    @Override
    public final Element getNode() {
        return node;
    }

    public final List<AttributeDiffTree> getAttributes() {
        return attributes;
    }

    public final List<NamespaceUriDiffTree> getNamespaceUriTree() {
        return nsUri;
    }

    public final List<PrefixDiffTree> getPrefixTree() {
        return prefix;
    }
    
    /**
     * Gets the nodes for the local name of this element.
     * @return 
     */
    public final List<LocalNameDiffTree> getLocalNameTree() {
        return localName;
    }
    
    public ElementDiffTree(DocumentSide side, Element node,
            List<NamespaceUriDiffTree> nsUri,
            List<PrefixDiffTree> prefix,
            List<LocalNameDiffTree> localName,
            List<NodeDiffTree> children,
            List<AttributeDiffTree> attributes) {
        super(side, children);
        
        this.node = node;
        this.nsUri = Collections.unmodifiableList(nsUri);
        this.prefix = Collections.unmodifiableList(prefix);
        this.localName = Collections.unmodifiableList(localName);
        this.attributes = Collections.unmodifiableList(attributes);
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        ElementDiffConsumer elementConsumer = consumer.beginElement(getSide(), node);
        
        for(NamespaceUriDiffTree nsUriTree : nsUri)
            nsUriTree.replay(elementConsumer);
        
        for(PrefixDiffTree prefixTree : prefix)
            prefixTree.replay(elementConsumer);
        
        for(LocalNameDiffTree localNameTree : localName)
            localNameTree.replay(elementConsumer);
        
        NodeListDiffConsumer attrsConsumer = elementConsumer.beginAttributes();
        for(NodeDiffTree attr : attributes)
            attr.replay(attrsConsumer);
        attrsConsumer.end();
        
        replayChildren(elementConsumer);
        
        elementConsumer.end();
    }
}
