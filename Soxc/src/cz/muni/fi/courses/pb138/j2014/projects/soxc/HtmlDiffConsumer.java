/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * A dummy {@link DiffConsumer} that produces a human-readable HTML view of the
 * differences between two XML documents.
 * 
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class HtmlDiffConsumer implements DiffConsumer {
    
    private final String leftClass;
    private final String rightClass;
    private final Writer writer;
    private final Stack<Element> elStack = new Stack<>();
    //private int padding = 0;
    private boolean entityRef = false;
    
    /**
     * Creates a new instance.
     * @param writer        the writer to write the HTML output into
     * @param leftCssClass  the CSS class to use for the left document contents
     * @param rightCssClass the CSS class to use for the right document contents
     */
    public HtmlDiffConsumer(Writer writer, String leftCssClass, String rightCssClass) {
        this.writer = writer;
        this.leftClass = leftCssClass;
        this.rightClass = rightCssClass;
    }
    
    private void colorBegin(DocumentSide side) throws IOException {
        String cssClass;
        switch(side) {
            case LEFT_DOCUMENT:
                cssClass = leftClass;
                break;
            case RIGHT_DOCUMENT:
                cssClass = rightClass;
                break;
            default:
                writer.write("<span>");
                return;
        }
        writer.write("<span class=\"" + cssClass + "\">");
    }
    
    private void colorEnd() throws IOException {
        writer.write("</span>");
    }
    
    private String escape(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    @Override
    public void begin() {
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
        if(entityRef)
            return;
        //padding += 1;
    }

    @Override
    public void endChildren() {
        if(entityRef)
            return;
        //padding -= 1;
    }

    @Override
    public void beginElement(DocumentSide side, Element el) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            
            writer.write("&lt;");
            
            String prefix = el.getPrefix();
            if(prefix != null)
                writer.write(prefix + ":");
            writer.write(el.getNodeName());
            
            elStack.push(el);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endElement() {
        if(entityRef)
            return;
        try {
            Element el = elStack.pop();
            
            writer.write("&lt;/");
            
            String prefix = el.getPrefix();
            if(prefix != null)
                writer.write(prefix + ":");
            writer.write(el.getNodeName());
            
            writer.write("&gt;");
            
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginAttributes() { }

    @Override
    public void beginAttribute(DocumentSide side, Attr attr) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            writer.write(" ");
            writer.write(attr.getNodeName());
            writer.write("=\"");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endAttribute() {
        if(entityRef)
            return;
        try {
            writer.write("\"");
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endAttributes() {
        if(entityRef)
            return;
        try {
            writer.write("&gt;");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginEntityReference(DocumentSide side, EntityReference ref) {
        entityRef = true;
        try {
            writer.write("&amp;");
            writer.write(ref.getNodeName());
            writer.write(";");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endEntityReference() {
        entityRef = false;
    }

    @Override
    public void beginText(DocumentSide side, Text text) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void textValue(DocumentSide side, String value) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            
            writer.write(escape(value));
            
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endText() {
        if(entityRef)
            return;
        try {
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginCDATASection(DocumentSide side, CDATASection cdata) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            
            writer.write("&lt;![CDATA[");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void CDATASectionData(DocumentSide side, String data) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            
            writer.write(escape(data));
            
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endCDATASection() {
        if(entityRef)
            return;
        try {
            writer.write("]]&gt;");
            
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginComment(DocumentSide side, Comment cdata) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            
            writer.write("&lt;!--");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void commentData(DocumentSide side, String data) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            
            writer.write(escape(data));
            
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endComment() {
        if(entityRef)
            return;
        try {
            writer.write("--&gt;");
            
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void beginProcessingInstruction(DocumentSide side, ProcessingInstruction pi) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            
            writer.write("&lt;?");
            writer.write(pi.getTarget());
            writer.write(" ");
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processingInstructionData(DocumentSide side, String data) {
        if(entityRef)
            return;
        try {
            colorBegin(side);
            
            writer.write(escape(data));
            
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void endProcessingInstruction() {
        if(entityRef)
            return;
        try {
            writer.write("?&gt;");
            
            colorEnd();
        } catch (IOException ex) {
            Logger.getLogger(HtmlDiffConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
