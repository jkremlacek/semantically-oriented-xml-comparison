/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;

/**
 * An XML processing instruction diff sub-stream consumer.
 * 
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public interface ProcessingInstructionDiffConsumer {
    
    /**
     * Reports the data on the given document side.
     * @param side  the document in which the data appears
     * @param data  the data
     */
    public void data(DocumentSide side, String data);
    
    /**
     * Called when the sub-stream has ended.
     */
    public void end();
}
