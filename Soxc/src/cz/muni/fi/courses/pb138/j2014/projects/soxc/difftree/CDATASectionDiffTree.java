/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CDATASectionDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import org.w3c.dom.CDATASection;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class CDATASectionDiffTree extends NodeDiffTree {
    
    private final CDATASection node;
    private final CDATASectionDataDiffTree data;

    @Override
    public final CDATASection getNode() {
        return node;
    }

    public CDATASectionDataDiffTree getData() {
        return data;
    }

    public CDATASectionDiffTree(DocumentSide side, CDATASection node, CDATASectionDataDiffTree data) {
        super(side);
        
        this.node = node;
        this.data = data;
    }

    @Override
    public void replay(NodeListDiffConsumer consumer) {
        CDATASectionDiffConsumer CDATAConsumer = consumer.beginCDATASection(getSide(), node);
        
        data.replay(CDATAConsumer);
        
        CDATAConsumer.end();
    }
}
