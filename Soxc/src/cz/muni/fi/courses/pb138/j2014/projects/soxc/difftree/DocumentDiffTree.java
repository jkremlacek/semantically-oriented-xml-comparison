/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.DocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import java.util.List;
import org.w3c.dom.Document;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class DocumentDiffTree extends HierarchicalNodeDiffTree {
    
    private final Document node;

    @Override
    public final Document getNode() {
        return node;
    }

    public DocumentDiffTree(DocumentSide side, Document node, List<NodeDiffTree> children) {
        super(side, children);
        
        this.node = node;
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        DocumentDiffConsumer docConsumer = consumer.beginDocument(getSide(), node);
        
        replayChildren(docConsumer);
        
        docConsumer.end();
    }
}
