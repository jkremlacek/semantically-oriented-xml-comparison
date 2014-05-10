/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.htmloutput;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import java.io.IOException;
import java.io.Writer;

/**
 * A helper class for {@link HtmlDiffConsumer}.
 * 
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
class HtmlDiffWriter {
    
    private final Writer writer;
    private final String leftClass;
    private final String rightClass;
    
    public HtmlDiffWriter(Writer writer, String leftCssClass, String rightCssClass) {
        this.writer = writer;
        this.leftClass = leftCssClass;
        this.rightClass = rightCssClass;
    }
    
    public final void colorBegin(DocumentSide side) throws IOException {
        String cssClass;
        switch(side) {
            case LEFT_DOCUMENT:
                cssClass = leftClass;
                break;
            case RIGHT_DOCUMENT:
                cssClass = rightClass;
                break;
            default:
                return;
        }
        writer.write("<span class=\"" + cssClass + "\">");
    }
    
    public final void colorEnd(DocumentSide side) throws IOException {
        if(side != DocumentSide.BOTH)
            writer.write("</span>");
    }
    
    private String escape(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
    
    public final void write(String text) throws IOException {
        writer.write(text);
    }
    
    public final void writeEscaped(String text) throws IOException {
        writer.write(escape(text));
    }
}
