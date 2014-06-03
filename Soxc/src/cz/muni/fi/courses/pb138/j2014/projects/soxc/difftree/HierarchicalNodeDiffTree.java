/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.HierarchicalNodeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public abstract class HierarchicalNodeDiffTree extends NodeDiffTree {
    
    private final List<NodeDiffTree> children;

    public final List<NodeDiffTree> getChildren() {
        return children;
    }

    protected HierarchicalNodeDiffTree(DocumentSide side, List<NodeDiffTree> children) {
        super(side);
        
        this.children = Collections.unmodifiableList(children);
    }
    
    public final void replayChildren(HierarchicalNodeDiffConsumer consumer) {
        NodeListDiffConsumer childrenConsumer = consumer.beginChildren();
        for(NodeDiffTree child : children)
            child.replay(childrenConsumer);
        childrenConsumer.end();
    }
}
