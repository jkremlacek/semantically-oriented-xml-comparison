/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import org.w3c.dom.Document;

/**
 * A consumer for XML document diff stream.
 * 
 * @see GeneralDiffConsumer
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public interface GeneralDocumentDiffConsumer extends GeneralDiffConsumer {
    
    /**
     * Called when the diff stream begins.
     * @param docLeft   the left document being compared
     * @param docRight  the right document being compared
     * @param options   the comparison options that will be used
     */
    public void begin(Document docLeft, Document docRight, Options options);

    /**
     * Called when the diff stream ends.
     */
    public void end();
}
