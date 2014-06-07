/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.xmloutput;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatJustDocumentDiffConsumer;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * A {@link FlatJustDocumentDiffConsumer} that outputs the differences in
 * the XDiff XML format.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public class XmlOutputDiffConsumer implements FlatJustDocumentDiffConsumer {
    
    private static final XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
    static { xmlOutputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true); }
    
    private static final String XDIFF_PREFIX = "xdiff";
    private static final String XDIFF_NAMESPACE_URI = "http://www.fi.muni.cz/courses/PB138/j2014/projects/soxc/xdiff";
    
    private final XMLStreamWriter writer;
    
    private static String translateDocSide(DocumentSide side) {
        switch(side) {
            case LEFT_DOCUMENT:
                return "left";
            case RIGHT_DOCUMENT:
                return "right";
            case BOTH:
                return "both";
            default:
                throw new AssertionError(side.name());
        }
    }
    
    /**
     * Creates a new instance writing into the specified {@link Writer}.
     * @param writer    the {@link Writer} to write the XML output into
     * @throws XMLStreamException 
     */
    public XmlOutputDiffConsumer(Writer writer) throws XMLStreamException {
        this.writer = xmlOutputFactory.createXMLStreamWriter(writer);
    }

    @Override
    public void begin(Document docLeft, Document docRight, Options options) {
        try {
            writer.writeStartDocument();
            writer.writeStartElement(XDIFF_PREFIX, "xdiff", XDIFF_NAMESPACE_URI);
            //writer.setPrefix(XDIFF_PREFIX, XDIFF_NAMESPACE_URI);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void end() {
        try {
            writer.writeEndElement();
            writer.writeEndDocument();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginChildren() {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "children", XDIFF_NAMESPACE_URI);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endChildren() {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginDocument(DocumentSide side, Document doc) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "document", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endDocument(DocumentSide side, Document doc) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginElement(DocumentSide side, Element el) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "element", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
            
            writer.writeStartElement(XDIFF_PREFIX, "name", XDIFF_NAMESPACE_URI);
            writer.writeCharacters(el.getNodeName());
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endElement(DocumentSide side, Element el) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginAttributes() {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "attributes", XDIFF_NAMESPACE_URI);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginAttribute(DocumentSide side, Attr attr) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "attribute", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
            
            writer.writeStartElement(XDIFF_PREFIX, "name", XDIFF_NAMESPACE_URI);
            writer.writeCharacters(attr.getNodeName());
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endAttribute(DocumentSide side, Attr attr) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endAttributes() {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void entityReference(DocumentSide side, EntityReference ref) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "entityReference", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
            
            writer.writeStartElement(XDIFF_PREFIX, "name", XDIFF_PREFIX);
            writer.writeCharacters(ref.getNodeName());
            writer.writeEndElement();
            
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginText(DocumentSide side, Text text) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "text", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void textValue(DocumentSide side, String value) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "data", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
            
            writer.writeCharacters(value);
            
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endText(DocumentSide side, Text text) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginCDATASection(DocumentSide side, CDATASection cdata) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "cdataSection", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void CDATASectionData(DocumentSide side, String data) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "data", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
            
            writer.writeCharacters(data);
            
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endCDATASection(DocumentSide side, CDATASection cdata) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginComment(DocumentSide side, Comment comment) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "comment", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void commentData(DocumentSide side, String text) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "data", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
            
            writer.writeCharacters(text);
            
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endComment(DocumentSide side, Comment comment) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginProcessingInstruction(DocumentSide side, ProcessingInstruction pi) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "processingInstruction", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
            
            writer.writeStartElement(XDIFF_PREFIX, "target", XDIFF_NAMESPACE_URI);
            writer.writeCharacters(pi.getTarget());
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processingInstructionData(DocumentSide side, String data) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "data", XDIFF_NAMESPACE_URI);
            writer.writeAttribute("side", translateDocSide(side));
            
            writer.writeCharacters(data);
            
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endProcessingInstruction(DocumentSide side, ProcessingInstruction pi) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void namespaceURI(DocumentSide side, String uri) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "namespaceUri", XDIFF_NAMESPACE_URI);
            writer.writeCharacters(uri);
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void prefix(DocumentSide side, String prefix) {
        try {
            writer.writeStartElement(XDIFF_PREFIX, "prefix", XDIFF_NAMESPACE_URI);
            writer.writeCharacters(prefix);
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlOutputDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
