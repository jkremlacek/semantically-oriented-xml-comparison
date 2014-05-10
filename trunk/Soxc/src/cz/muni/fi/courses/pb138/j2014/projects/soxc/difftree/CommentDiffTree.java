/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CommentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import org.w3c.dom.Comment;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class CommentDiffTree extends NodeDiffTree {
    
    private final Comment node;
    private final CommentDataDiffTree data;

    public final Comment getNode() {
        return node;
    }

    public final CommentDataDiffTree getData() {
        return data;
    }

    public CommentDiffTree(DocumentSide side, Comment node, CommentDataDiffTree data) {
        super(side);
        
        this.node = node;
        this.data = data;
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        CommentDiffConsumer commentConsumer = consumer.beginComment(getSide(), node);
        
        data.replay(commentConsumer);
        
        commentConsumer.end();
    }
}
