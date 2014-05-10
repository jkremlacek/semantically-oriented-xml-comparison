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
 * A "flat" consumer for XML diff sub-stream. The "flat" versions recieve all messages
 * via a single interface, which may be easier to implement in some cases.
 * 
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public interface FlatDiffConsumer {
    
    /**
     * Marks the beginning of a document.
     * @param side  the document in which the node appears
     * @param doc   the document (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     */
    public void beginDocument(DocumentSide side, Document doc);
    /**
     * Marks the end of a document.
     * @param side  the document in which the node appears
     * @param doc   the document (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     */
    public void endDocument(DocumentSide side, Document doc);
    
    /**
     * Marks the beginning of the child nodes of the current node.
     */
    public void beginChildren();
    /**
     * Marks the end of the child nodes of the current node.
     */
    public void endChildren();

    /**
     * Marks the beginning of an element.
     * @param side  the document in which the node appears
     * @param el    the element (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     */
    public void beginElement(DocumentSide side, Element el);
    /**
     * Marks the end of an element.
     * @param side  the document in which the node appears
     * @param el    the element (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     */
    public void endElement(DocumentSide side, Element el);
    
    /**
     * Marks the beginning of the attribute list of the current node.
     */
    public void beginAttributes();
    /**
     * Marks the beginning of an attribute.
     * @param side  the document in which the node appears
     * @param attr  the attribute (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     */
    public void beginAttribute(DocumentSide side, Attr attr);
    /**
     * Marks the end of an attribute.
     * @param side  the document in which the node appears
     * @param attr  the attribute (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to
     *              be valid for both sides)
     */
    public void endAttribute(DocumentSide side, Attr attr);
    /**
     * Marks the end of the attribute list of the current node.
     */
    public void endAttributes();
    
    /**
     * Marks the beginning of an entity reference.
     * @param side  the document in which the node appears
     * @param ref   the entity reference (if {@code side} is {@link
     *              DocumentSide#BOTH}, only the properties defining similarity
     *              are guaranteed to be valid for both sides)
     */
    public void entityReference(DocumentSide side, EntityReference ref);
    
    /**
     * Marks the beginning of a text node.
     * @param side  the document in which the node appears
     * @param text  the text node (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to be
     *              valid)
     */
    public void beginText(DocumentSide side, Text text);
    /**
     * Reports the value of a text node.
     * @param side  the document in which the value appears
     * @param value the value of the current text node
     */
    public void textValue(DocumentSide side, String value);
    /**
     * Marks the end of a text node.
     * @param side  the document in which the node appears
     * @param text  the text node (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to be
     *              valid)
     */
    public void endText(DocumentSide side, Text text);
    
    /**
     * Marks the beginning of a CDATA section.
     * @param side  the document in which the node appears
     * @param cdata the CDATA section node (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to be
     *              valid)
     */
    public void beginCDATASection(DocumentSide side, CDATASection cdata);
    /**
     * Reports the data in a CDATA section.
     * @param side  the document in which the value appears
     * @param data  the data in the current CDATA section
     */
    public void CDATASectionData(DocumentSide side, String data);
    /**
     * Marks the end of a CDATA section.
     * @param side  the document in which the node appears
     * @param cdata the CDATA section node (if {@code side} is {@link DocumentSide#BOTH},
     *              only the properties defining similarity are guaranteed to be
     *              valid)
     */
    public void endCDATASection(DocumentSide side, CDATASection cdata);
    
    /**
     * Marks the beginning of a comment.
     * @param side      the document in which the node appears
     * @param comment   the comment node (if {@code side} is {@link
     *                  DocumentSide#BOTH}, only the properties defining similarity
     *                  are guaranteed to be valid for both sides)
     */
    public void beginComment(DocumentSide side, Comment comment);
    /**
     * Reports the data in a comment node.
     * @param side  the document in which the value appears
     * @param data  the text of the current comment node
     */
    public void commentData(DocumentSide side, String data);
    /**
     * Marks the end of a comment.
     * @param side      the document in which the node appears
     * @param comment   the comment node (if {@code side} is {@link
     *                  DocumentSide#BOTH}, only the properties defining similarity
     *                  are guaranteed to be valid for both sides)
     */
    public void endComment(DocumentSide side, Comment comment);
    
    /**
     * Marks the beginning of a processing instruction.
     * @param side  the document in which the node appears
     * @param pi    the processing instruction (if {@code side} is {@link
     *              DocumentSide#BOTH}, only the properties defining similarity
     *              are guaranteed to be valid for both sides)
     */
    public void beginProcessingInstruction(DocumentSide side, ProcessingInstruction pi);
    /**
     * Reports the data in a processing instruction.
     * @param side  the document in which the value appears
     * @param data  the data in the current processing instruction node
     */
    public void processingInstructionData(DocumentSide side, String data);
    /**
     * Marks the end of a processing instruction.
     * @param side  the document in which the node appears
     * @param pi    the processing instruction (if {@code side} is {@link
     *              DocumentSide#BOTH}, only the properties defining similarity
     *              are guaranteed to be valid for both sides)
     */
    public void endProcessingInstruction(DocumentSide side, ProcessingInstruction pi);
    
    // TODO add more methods
}
