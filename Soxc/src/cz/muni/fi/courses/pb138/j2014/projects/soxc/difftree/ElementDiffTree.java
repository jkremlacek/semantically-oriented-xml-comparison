/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ElementDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import java.util.List;
import org.w3c.dom.Element;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class ElementDiffTree extends HierarchicalNodeDiffTree {
    
    private final Element node;
    private final List<AttributeDiffTree> attributes;
    
    @Override
    public final Element getNode() {
        return node;
    }

    public final List<AttributeDiffTree> getAttributes() {
        return attributes;
    }

    public ElementDiffTree(Element node, DocumentSide side, List<NodeDiffTree> children, List<AttributeDiffTree> attributes) {
        super(side, children);
        this.node = node;
        this.attributes = attributes;
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        ElementDiffConsumer elementConsumer = consumer.beginElement(getSide(), node);
        
        NodeListDiffConsumer attrsConsumer = elementConsumer.beginAttributes();
        for(NodeDiffTree attr : attributes)
            attr.replay(attrsConsumer);
        attrsConsumer.end();
        
        replayChildren(elementConsumer);
        
        elementConsumer.end();
    }
}
