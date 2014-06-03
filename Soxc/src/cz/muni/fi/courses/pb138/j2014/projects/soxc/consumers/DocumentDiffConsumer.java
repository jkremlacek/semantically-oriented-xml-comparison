/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

/**
 * An XML CDATA section diff sub-stream consumer.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public interface DocumentDiffConsumer extends HierarchicalNodeDiffConsumer {
    
    /**
     * Called when the sub-stream has ended.
     */
    public void end();
}
