/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.DocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.DocumentDiffTree;
import org.w3c.dom.Document;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class DocumentDiffTreeConsumer
        extends HierarchicalNodeDiffTreeConsumer
        implements DocumentDiffConsumer {
    
    public interface Listener {
        public void onEnd(DocumentDiffTree docTree);
    }
    
    private final DocumentSide side;
    private final Document doc;
    private final Listener listener;

    public DocumentDiffTreeConsumer(DocumentSide side, Document doc, Listener listener) {
        this.side = side;
        this.doc = doc;
        this.listener = listener;
    }

    @Override
    public final void end() {
        listener.onEnd(new DocumentDiffTree(side, doc, getChildren()));
    }
}
