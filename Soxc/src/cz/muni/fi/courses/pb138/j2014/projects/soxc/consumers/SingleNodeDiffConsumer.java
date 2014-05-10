/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import org.w3c.dom.Node;

/**
 * A consumer for an XML node diff stream.
 * 
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public interface SingleNodeDiffConsumer {

    /**
     * Called when the diff stream has begun.
     * @param nodeLeft  the left node being compared
     * @param nodeRight the right node being compared
     * @param options   the comparison options that will be used
     * @return the {@link NodeListDiffConsumer} to consume the node sub-stream
     *      (only one node will be reported)
     */
    public NodeListDiffConsumer begin(Node nodeLeft, Node nodeRight, Options options);
    
    /**
     * Called when the diff stream has ended.
     */
    public void end();
}
