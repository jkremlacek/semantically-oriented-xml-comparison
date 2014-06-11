/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.xmldiff;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatJustDocumentDiffConsumer;
import java.io.PrintStream;
import java.util.Stack;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * A {@link FlatJustDocumentDiffConsumer} that outputs the differences to the console.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class TextOutputDiffConsumer implements FlatJustDocumentDiffConsumer {
    
    private final PrintStream out;
    
    private int depth = 0;
    private final Stack<DocumentSide> currentSide = new Stack<>();
    
    /**
     * Creates a new instance writing to {@link System#out}.
     */
    public TextOutputDiffConsumer() {
        this(System.out);
    }
    
    /**
     * Creates a new instance writing to the given {@link PrintStream}.
     * @param stream    the stream to write to
     */
    public TextOutputDiffConsumer(PrintStream stream) {
        this.out = stream;
    }
    
    private void pushSide(DocumentSide side) {
        currentSide.push(side);
    }
    
    private DocumentSide popSide() {
        return currentSide.pop();
    }
    
    private void indent() {
        DocumentSide side = currentSide.peek();
        switch(side) {
            case BOTH:
                out.print("[BOTH]  ");
                break;
            case LEFT_DOCUMENT:
                out.print("[LEFT]  ");
                break;
            case RIGHT_DOCUMENT:
                out.print("[RIGHT] ");
                break;
            default:
                throw new AssertionError(side.name());
        }
        for(int i = 0; i < depth; i++)
            out.print("  ");
    }

    @Override
    public void begin(Document docLeft, Document docRight, Options options) {
        out.println(String.format("Comparison of documents '%s' and '%s'", docLeft.getDocumentURI(), docRight.getDocumentURI()));
        out.println("--------------------");
    }

    @Override
    public void end() {
        out.println("DONE.");
    }

    @Override
    public void beginChildren() {
        indent();
        out.println("Children:");
        depth++;
    }

    @Override
    public void endChildren() {
        depth--;
    }

    @Override
    public void beginDocument(DocumentSide side, Document doc) {
        pushSide(side);
        indent();
        out.println("DOCUMENT");
        depth++;
    }

    @Override
    public void endDocument(DocumentSide side, Document doc) {
        popSide();
        depth--;
    }

    @Override
    public void beginElement(DocumentSide side, Element el) {
        pushSide(side);
        indent();
        out.println("ELEMENT");
        depth++;
    }

    @Override
    public void endElement(DocumentSide side, Element el) {
        popSide();
        depth--;
    }

    @Override
    public void beginAttributes() {
        indent();
        out.println("Attributes:");
        depth++;
    }

    @Override
    public void beginAttribute(DocumentSide side, Attr attr) {
        pushSide(side);
        indent();
        out.println("ATTRIBUTE");
        depth++;
    }

    @Override
    public void endAttribute(DocumentSide side, Attr attr) {
        popSide();
        depth--;
    }

    @Override
    public void endAttributes() {
        depth--;
    }

    @Override
    public void entityReference(DocumentSide side, EntityReference ref) {
        pushSide(side);
        indent();
        out.println(String.format("ENTITY REFERENCE (%s)", ref.getNodeName()));
        popSide();
    }

    @Override
    public void beginText(DocumentSide side, Text text) {
        pushSide(side);
        indent();
        out.println("TEXT");
        depth++;
    }

    @Override
    public void textValue(DocumentSide side, String value) {
        pushSide(side);
        indent();
        out.println(String.format("Data: %s", value));
        popSide();
    }

    @Override
    public void endText(DocumentSide side, Text text) {
        popSide();
        depth--;
    }

    @Override
    public void beginCDATASection(DocumentSide side, CDATASection cdata) {
        pushSide(side);
        indent();
        out.println("CDATA SECTION");
        depth++;
    }

    @Override
    public void CDATASectionData(DocumentSide side, String data) {
        pushSide(side);
        indent();
        out.println(String.format("Data: %s", data));
        popSide();
    }

    @Override
    public void endCDATASection(DocumentSide side, CDATASection cdata) {
        popSide();
        depth--;
    }

    @Override
    public void beginComment(DocumentSide side, Comment comment) {
        pushSide(side);
        indent();
        out.println("COMMENT");
        depth++;
    }

    @Override
    public void commentData(DocumentSide side, String text) {
        pushSide(side);
        indent();
        out.println(String.format("Data: %s", text));
        popSide();
    }

    @Override
    public void endComment(DocumentSide side, Comment comment) {
        popSide();
        depth--;
    }

    @Override
    public void beginProcessingInstruction(DocumentSide side, ProcessingInstruction pi) {
        pushSide(side);
        indent();
        out.println("PROCESSING INSTRUCTION");
        depth++;
        indent();
        out.println(String.format("Target: %s", pi.getTarget()));
    }

    @Override
    public void processingInstructionData(DocumentSide side, String data) {
        pushSide(side);
        indent();
        out.println(String.format("Data: %s", data));
        popSide();
    }

    @Override
    public void endProcessingInstruction(DocumentSide side, ProcessingInstruction pi) {
        popSide();
        depth--;
    }

    @Override
    public void namespaceURI(DocumentSide side, String uri) {
        pushSide(side);
        indent();
        out.println(String.format("Namespace URI: %s", uri));
        popSide();
    }

    @Override
    public void prefix(DocumentSide side, String prefix) {
        pushSide(side);
        indent();
        out.println(String.format("Prefix: %s", prefix));
        popSide();
    }

    @Override
    public void localName(DocumentSide side, String name) {
        pushSide(side);
        indent();
        out.println(String.format("Local name: %s", name));
        popSide();
    }
}
