/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import org.w3c.dom.Document;

/**
 * A "flat" consumer for XML document diff stream. The "flat" versions recieve
 * all events via a single interface, which may be easier to implement in some
 * cases. Use {@link FlatConsumers#toGeneral(cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatJustDocumentDiffConsumer)}
 * to convert this to a {@link JustDocumentDiffConsumer}.
 * 
 * @see FlatDiffConsumer
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public interface FlatJustDocumentDiffConsumer extends FlatDiffConsumer {
    
    /**
     * Called when the diff stream has begun.
     * @param docLeft   the left document being compared
     * @param docRight  the right document being compared
     * @param options   the comparison options that will be used
     */
    public void begin(Document docLeft, Document docRight, Options options);

    /**
     * Called when the diff stream has ended.
     */
    public void end();
}
