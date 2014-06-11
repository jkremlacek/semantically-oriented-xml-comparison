/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NamespaceDiffConsumer;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public class LocalNameDiffTree extends DiffTree {
    
    private final String localName;

    public final String getLocalName() {
        return localName;
    }

    public LocalNameDiffTree(DocumentSide side, String localName) {
        super(side);
        this.localName = localName;
    }
    
    public final void replay(NamespaceDiffConsumer consumer) {
        consumer.localName(getSide(), localName);
    }
}
