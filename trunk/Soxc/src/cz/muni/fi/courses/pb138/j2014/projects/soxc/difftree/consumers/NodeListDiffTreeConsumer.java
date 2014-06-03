/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.AttributeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CDATASectionDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CommentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.DocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ElementDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ProcessingInstructionDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.TextNodeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.AttributeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CDATASectionDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CommentDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.DocumentDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ElementDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.EntityReferenceDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NodeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ProcessingInstructionDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.TextDiffTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class NodeListDiffTreeConsumer implements NodeListDiffConsumer {
    
    public interface Listener {
        public void onEnd(List<NodeDiffTree> result);
    }
    
    private final Listener listener;
    private final List<NodeDiffTree> list = new ArrayList<>();

    public NodeListDiffTreeConsumer(Listener listener) {
        this.listener = listener;
    }

    @Override
    public final DocumentDiffConsumer beginDocument(DocumentSide side, Document doc) {
        return new DocumentDiffTreeConsumer(side, doc, new DocumentDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(DocumentDiffTree docTree) {
                list.add(docTree);
            }
        });
    }

    @Override
    public final void entityReference(DocumentSide side, EntityReference entityRef) {
        list.add(new EntityReferenceDiffTree(side, entityRef));
    }

    @Override
    public final ElementDiffConsumer beginElement(DocumentSide side, Element element) {
        return new ElementDiffTreeConsumer(side, element, new ElementDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(ElementDiffTree elementTree) {
                list.add(elementTree);
            }
        });
    }

    @Override
    public final AttributeDiffConsumer beginAttribute(DocumentSide side, Attr attr) {
        return new AttributeDiffTreeConsumer(side, attr, new AttributeDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(AttributeDiffTree attrTree) {
                list.add(attrTree);
            }
        });
    }

    @Override
    public final TextNodeDiffConsumer beginText(DocumentSide side, Text text) {
        return new TextDiffTreeConsumer(side, text, new TextDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(TextDiffTree commentTree) {
                list.add(commentTree);
            }
        });
    }

    @Override
    public final CDATASectionDiffConsumer beginCDATASection(DocumentSide side, CDATASection cdata) {
        return new CDATASectionDiffTreeConsumer(side, cdata, new CDATASectionDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(CDATASectionDiffTree cdataTree) {
                list.add(cdataTree);
            }
        });
    }

    @Override
    public final ProcessingInstructionDiffConsumer beginProcessingInstruction(DocumentSide side, ProcessingInstruction pi) {
        return new ProcessingInstructionDiffTreeConsumer(side, pi, new ProcessingInstructionDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(ProcessingInstructionDiffTree piTree) {
                list.add(piTree);
            }
        });
    }

    @Override
    public final CommentDiffConsumer beginComment(DocumentSide side, Comment comment) {
        return new CommentDiffTreeConsumer(side, comment, new CommentDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(CommentDiffTree commentTree) {
                list.add(commentTree);
            }
        });
    }

    @Override
    public final void end() {
        listener.onEnd(Collections.unmodifiableList(list));
    }
}
