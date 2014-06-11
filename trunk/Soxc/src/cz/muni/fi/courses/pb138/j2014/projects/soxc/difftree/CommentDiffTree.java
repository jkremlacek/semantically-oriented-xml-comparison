/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CommentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.Comment;

/**
 * A {@link DiffTree} node for a comment.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class CommentDiffTree extends NodeDiffTree {
    
    private final Comment node;
    private final List<CommentDataDiffTree> data;

    @Override
    public final Comment getNode() {
        return node;
    }

    /**
     * Gets the nodes for the data.
     * @return 
     */
    public final List<CommentDataDiffTree> getData() {
        return data;
    }

    public CommentDiffTree(DocumentSide side, Comment node, List<CommentDataDiffTree> data) {
        super(side);
        
        this.node = node;
        this.data = Collections.unmodifiableList(data);
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        CommentDiffConsumer commentConsumer = consumer.beginComment(getSide(), node);
        
        for(CommentDataDiffTree dataTree : data)
            dataTree.replay(commentConsumer);
        
        commentConsumer.end();
    }
}
