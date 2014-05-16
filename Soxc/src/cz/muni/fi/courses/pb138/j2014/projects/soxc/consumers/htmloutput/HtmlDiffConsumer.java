/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.htmloutput;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatJustDocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatSingleNodeDiffConsumer;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * A dummy {@link GeneralDiffConsumer} that produces a human-readable HTML view of the
 * differences between two XML documents.
 * 
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class HtmlDiffConsumer implements FlatJustDocumentDiffConsumer, FlatSingleNodeDiffConsumer {
    
    private final HtmlDiffWriter writer;
    //private int padding = 0;
    
    /**
     * Creates a new instance.
     * @param writer        the writer to write the HTML output into
     * @param leftCssClass  the CSS class to use for the left document contents
     * @param rightCssClass the CSS class to use for the right document contents
     */
    public HtmlDiffConsumer(Writer writer, String leftCssClass, String rightCssClass) {
        this.writer = new HtmlDiffWriter(writer, leftCssClass, rightCssClass);
    }
    
    @Override
    public void begin(Document docLeft, Document docRight, Options options) {
        try {
            writer.write("<pre>");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void begin(Node nodeLeft, Node nodeRight, Options options) {
        try {
            writer.write("<pre>");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void end() {
        try {
            writer.write("</pre>");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void beginChildren() {
        //padding += 1;
    }

    @Override
    public void endChildren() {
        //padding -= 1;
    }

    @Override
    public void beginDocument(DocumentSide side, Document doc) {
        try {
            writer.colorBegin(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endDocument(DocumentSide side, Document doc) {
        try {
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginElement(DocumentSide side, Element el) {
        try {
            writer.colorBegin(side);
            
            writer.write("&lt;");
            
            writer.write(el.getNodeName());
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endElement(DocumentSide side, Element el) {
        try {
            writer.write("&lt;/");
            
            writer.write(el.getNodeName());
            
            writer.write("&gt;");
            
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginAttributes() { }

    @Override
    public void beginAttribute(DocumentSide side, Attr attr) {
        try {
            writer.colorBegin(side);
            writer.write(" ");
            writer.write(attr.getNodeName());
            writer.write("=\"");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endAttribute(DocumentSide side, Attr attr) {
        try {
            writer.write("\"");
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endAttributes() {
        try {
            writer.write("&gt;");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void entityReference(DocumentSide side, EntityReference ref) {
        try {
            writer.write("&amp;");
            writer.write(ref.getNodeName());
            writer.write(";");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginText(DocumentSide side, Text text) {
        try {
            writer.colorBegin(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void textValue(DocumentSide side, String value) {
        try {
            writer.colorBegin(side);
            
            writer.writeEscaped(value);
            
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endText(DocumentSide side, Text text) {
        try {
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginCDATASection(DocumentSide side, CDATASection cdata) {
        try {
            writer.colorBegin(side);
            
            writer.write("&lt;![CDATA[");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void CDATASectionData(DocumentSide side, String data) {
        try {
            writer.colorBegin(side);
            
            writer.writeEscaped(data);
            
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endCDATASection(DocumentSide side, CDATASection cdata) {
        try {
            writer.write("]]&gt;");
            
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginComment(DocumentSide side, Comment comment) {
        try {
            writer.colorBegin(side);
            
            writer.write("&lt;!--");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void commentData(DocumentSide side, String data) {
        try {
            writer.colorBegin(side);
            
            writer.writeEscaped(data);
            
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endComment(DocumentSide side, Comment comment) {
        try {
            writer.write("--&gt;");
            
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginProcessingInstruction(DocumentSide side, ProcessingInstruction pi) {
        try {
            writer.colorBegin(side);
            
            writer.write("&lt;?");
            writer.write(pi.getTarget());
            writer.write(" ");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processingInstructionData(DocumentSide side, String data) {
        try {
            writer.colorBegin(side);
            
            writer.writeEscaped(data);
            
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endProcessingInstruction(DocumentSide side, ProcessingInstruction pi) {
        try {
            writer.write("?&gt;");
            
            writer.colorEnd(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void namespaceURI(DocumentSide side, String uri) { }

    @Override
    public void prefix(DocumentSide side, String prefix) { }
}
