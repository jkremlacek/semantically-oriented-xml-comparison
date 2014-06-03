/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CDATASectionDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.CDATASection;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class CDATASectionDiffTree extends NodeDiffTree {
    
    private final CDATASection node;
    private final List<CDATASectionDataDiffTree> data;

    @Override
    public final CDATASection getNode() {
        return node;
    }

    public final List<CDATASectionDataDiffTree> getData() {
        return data;
    }

    public CDATASectionDiffTree(DocumentSide side, CDATASection node, List<CDATASectionDataDiffTree> data) {
        super(side);
        
        this.node = node;
        this.data = Collections.unmodifiableList(data);
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        CDATASectionDiffConsumer CDATAConsumer = consumer.beginCDATASection(getSide(), node);
        
        for(CDATASectionDataDiffTree dataTree : data)
            dataTree.replay(CDATAConsumer);
        
        CDATAConsumer.end();
    }
}
