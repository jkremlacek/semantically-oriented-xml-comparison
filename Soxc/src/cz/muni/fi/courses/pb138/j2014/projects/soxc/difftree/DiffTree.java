/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers.JustDocumentDiffTreeConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers.SingleNodeDiffTreeConsumer;

/**
 * A base class for all DiffTree nodes. A DiffTree can be used to materialize
 * the output of a diff operation on two XML documents/nodes. You can create
 * a DiffTree by passing {@link JustDocumentDiffTreeConsumer} or
 * {@link SingleNodeDiffTreeConsumer} to the corresponding diff method. It can
 * then be replayed into other consumers using the corresponding {@code replay}
 * methods.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public abstract class DiffTree {
    
    private final DocumentSide side;
    
    /**
     * Gets the {@link DocumentSide} to which this subtree belongs.
     * @return 
     */
    public final DocumentSide getSide() {
        return side;
    }

    /**
     * Initializes a new instance.
     * @param side 
     */
    protected DiffTree(DocumentSide side) {
        this.side = side;
    }
}
