/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.DocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.JustDocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.DocumentDiffTree;
import org.w3c.dom.Document;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class JustDocumentDiffTreeConsumer implements JustDocumentDiffConsumer {
    
    private Document docLeft = null;
    private Document docRight = null;
    private Options options = null;
    private DocumentDiffTree docTree = null;

    public final Document getLeftDocument() {
        return docLeft;
    }

    public final Document getRightDocument() {
        return docRight;
    }

    public final Options getOptions() {
        return options;
    }

    public final DocumentDiffTree getDocumentDiffTree() {
        return docTree;
    }

    @Override
    public final DocumentDiffConsumer begin(Document docLeft, Document docRight, Options options) {
        this.docLeft = docLeft;
        this.docRight = docRight;
        this.options = options;
        return new DocumentDiffTreeConsumer(DocumentSide.BOTH, docLeft, new DocumentDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(DocumentDiffTree docTree) {
                JustDocumentDiffTreeConsumer.this.docTree = docTree;
            }
        });
    }

    @Override
    public final void end() { }
}
