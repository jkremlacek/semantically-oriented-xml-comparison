/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import org.w3c.dom.Node;

/**
 * A consumer for XML node diff stream.
 * 
 * @see GeneralDiffConsumer
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public interface GeneralSingleNodeDiffConsumer extends GeneralDiffConsumer {
    
    /**
     * Called when the diff stream begins.
     * @param nodeLeft  the left node being compared
     * @param nodeRight the right node being compared
     * @param options   the comparison options that will be used
     */
    public void begin(Node nodeLeft, Node nodeRight, Options options);
    
    /**
     * Called when the diff stream ends.
     */
    public void end();
}
