/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import org.w3c.dom.Node;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public abstract class NodeDiffTree extends DiffTree {
    
    public abstract Node getNode();
    
    protected NodeDiffTree(DocumentSide side) {
        super(side);
    }
    
    public abstract void replay(NodeListDiffConsumer consumer);
}
