/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * An XML node list diff sub-stream consumer.
 * <p>
 * Order of method calls: unrestricted (sequential comparison).
 * </p>
 * 
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public interface NodeListDiffConsumer {
    
    /**
     * Called when the document sub-stream has begun.
     * This is a sequential comparison method.
     * @param side  the side on which the node has appeared
     * @param doc   the document (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     * @return the {@link DocumentDiffConsumer} to consume the sub-stream
     */
    public DocumentDiffConsumer beginDocument(DocumentSide side, Document doc);
    /**
     * Called when an entity reference was encoutered.
     * This is a sequential comparison method.
     * @param side      the side on which the node has appeared
     * @param entityRef the entity reference
     */
    public void entityReference(DocumentSide side, EntityReference entityRef);
    /**
     * Called when the element sub-stream has begun.
     * This is a sequential comparison method.
     * @param side      the side on which the node has appeared
     * @param element   the element (if {@code side} is {@link DocumentSide#BOTH},
     *                  only the properties defining similarity are guaranteed to
     *                  be valid for both sides)
     * @return the {@link ElementDiffConsumer} to consume the sub-stream
     */
    public ElementDiffConsumer beginElement(DocumentSide side, Element element);
    /**
     * Called when the attribute sub-stream has begun.
     * This is a sequential comparison method.
     * @param side  the side on which the node has appeared
     * @param attr  the attribute (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     * @return the {@link AttributeDiffConsumer} to consume the sub-stream
     */
    public AttributeDiffConsumer beginAttribute(DocumentSide side, Attr attr);
    /**
     * Called when the text node sub-stream has begun.
     * This is a sequential comparison method.
     * @param side  the side on which the node has appeared
     * @param text  the text node (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     * @return the {@link TextNodeDiffConsumer} to consume the sub-stream
     */
    public TextNodeDiffConsumer beginText(DocumentSide side, Text text);
    /**
     * Called when the CDATA section sub-stream has begun.
     * This is a sequential comparison method.
     * @param side  the side on which the node has appeared
     * @param cdata the CDATA section (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     * @return the {@link CDATASectionDiffConsumer} to consume the sub-stream
     */
    public CDATASectionDiffConsumer beginCDATASection(DocumentSide side, CDATASection cdata);
    /**
     * Called when the processing instruction sub-stream has begun.
     * This is a sequential comparison method.
     * @param side  the side on which the node has appeared
     * @param pi    the processing instruction (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     * @return the {@link ProcessingInstructionDiffConsumer} to consume the sub-stream
     */
    public ProcessingInstructionDiffConsumer beginProcessingInstruction(DocumentSide side, ProcessingInstruction pi);
    /**
     * Called when the comment sub-stream has begun.
     * This is a sequential comparison method.
     * @param side      the side on which the node has appeared
     * @param comment   the comment (if {@code side} is {@link DocumentSide#BOTH},
     *                  only the properties defining similarity are guaranteed to
     *                  be valid for both sides)
     * @return the {@link CommentDiffConsumer} to consume the sub-stream
     */
    public CommentDiffConsumer beginComment(DocumentSide side, Comment comment);
    
    // TODO DocumentType
    
    /**
     * Called when the sub-stream has ended.
     */
    public void end();
}
