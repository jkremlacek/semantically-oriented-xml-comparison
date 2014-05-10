/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import org.w3c.dom.Document;

/**
 * A consumer for an XML document diff stream.
 * 
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public interface JustDocumentDiffConsumer {
    
    /**
     * Called when the stream has begun.
     * @param docLeft   the left document being compared
     * @param docRight  the right document being compared
     * @param options   the comparison options that will be used
     * @return the {@link DocumentDiffConsumer} to consume the document sub-stream
     */
    public DocumentDiffConsumer begin(Document docLeft, Document docRight, Options options);
    
    /**
     * Called when the stream has ended.
     */
    public void end();
}
