/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CommentDiffConsumer;

/**
 * A {@link DiffTree} node for comment data.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class CommentDataDiffTree extends DiffTree {
    
    private final String data;

    /**
     * Gets the data.
     * @return 
     */
    public final String getData() {
        return data;
    }

    public CommentDataDiffTree(DocumentSide side, String data) {
        super(side);
        
        this.data = data;
    }
    
    /**
     * Replays this subtree into the given consumer.
     * @param consumer the consumer to replay into
     */
    public final void replay(CommentDiffConsumer consumer) {
        consumer.data(getSide(), data);
    }
}
