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
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public final class NamespaceUriDiffTree extends DiffTree {
    
    private final String namespaceURI;

    public final String getNamespaceURI() {
        return namespaceURI;
    }

    public NamespaceUriDiffTree(DocumentSide side, String namespaceURI) {
        super(side);
        this.namespaceURI = namespaceURI;
    }
    
    public final void replay(NamespaceDiffConsumer consumer) {
        consumer.namespaceURI(getSide(), namespaceURI);
    }
}
