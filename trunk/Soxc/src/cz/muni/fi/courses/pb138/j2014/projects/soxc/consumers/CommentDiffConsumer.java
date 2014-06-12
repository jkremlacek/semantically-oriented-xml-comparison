/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;

/**
 * An XML comment diff sub-stream consumer.
 * <p>
 * Order of method calls:
 * <ol>
 * <li>{@link #data(cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide, java.lang.String)}</li>
 * </ol>
 * </p>
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public interface CommentDiffConsumer {
    
    /**
     * Reports the data on the given document side.
     * Called just once for each side.
     * @param side  the document in which the data appears
     * @param data  the data
     */
    public void data(DocumentSide side, String data);
    
    /**
     * Called when the sub-stream has ended.
     */
    public void end();
}
