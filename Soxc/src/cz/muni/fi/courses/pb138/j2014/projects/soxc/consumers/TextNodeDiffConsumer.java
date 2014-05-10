/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;

/**
 * An XML text node diff sub-stream consumer.
 * 
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public interface TextNodeDiffConsumer {
    
    /**
     * Reports the data on the given document side.
     * @param side  the document in which the value appears
     * @param value the text value of the node
     */
    public void textValue(DocumentSide side, String value);
    
    /**
     * Called when the sub-stream has ended.
     */
    public void end();
}
