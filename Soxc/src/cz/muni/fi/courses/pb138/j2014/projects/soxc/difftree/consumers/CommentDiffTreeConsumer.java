/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CommentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CommentDataDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CommentDiffTree;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Comment;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class CommentDiffTreeConsumer implements CommentDiffConsumer {
    
    public interface Listener {
        public void onEnd(CommentDiffTree commentTree);
    }
    
    private final DocumentSide side;
    private final Comment comment;
    private final Listener listener;
    private final List<CommentDataDiffTree> data = new ArrayList<>();

    public CommentDiffTreeConsumer(DocumentSide side, Comment comment, Listener listener) {
        this.side = side;
        this.comment = comment;
        this.listener = listener;
    }
    
    @Override
    public final void data(DocumentSide side, String data) {
        this.data.add(new CommentDataDiffTree(side, data));
    }

    @Override
    public final void end() {
        listener.onEnd(new CommentDiffTree(side, comment, data));
    }
}
