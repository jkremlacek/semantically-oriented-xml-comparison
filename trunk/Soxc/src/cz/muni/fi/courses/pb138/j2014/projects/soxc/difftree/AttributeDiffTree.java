/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.AttributeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import java.util.List;
import org.w3c.dom.Attr;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class AttributeDiffTree extends HierarchicalNodeDiffTree {
    
    private final Attr node;

    @Override
    public final Attr getNode() {
        return node;
    }
    
    public AttributeDiffTree(Attr node, DocumentSide side, List<NodeDiffTree> children) {
        super(side, children);
        
        this.node = node;
    }

    @Override
    public void replay(NodeListDiffConsumer consumer) {
        AttributeDiffConsumer attrConsumer = consumer.beginAttribute(getSide(), node);
        
        replayChildren(attrConsumer);
        
        attrConsumer.end();
    }
}
