/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import org.w3c.dom.EntityReference;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public final class EntityReferenceDiffTree extends NodeDiffTree {
    
    private final EntityReference node;

    @Override
    public final EntityReference getNode() {
        return node;
    }

    public EntityReferenceDiffTree(DocumentSide side, EntityReference node) {
        super(side);
        
        this.node = node;
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        consumer.entityReference(getSide(), node);
    }
}
