/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import org.w3c.dom.Node;

/**
 * A "flat" consumer for XML node diff stream. The "flat" versions recieve all
 * messages via a single interface, which may be easier to implement in some cases.
 * Use {@link FlatConsumers#toGeneral(cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatSingleNodeDiffConsumer)}
 * to convert this to a {@link SingleNodeDiffConsumer}.
 * 
 * @see FlatDiffConsumer
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public interface FlatSingleNodeDiffConsumer extends FlatDiffConsumer {
    
    /**
     * Called when the diff stream has begun.
     * @param nodeLeft  the left node being compared
     * @param nodeRight the right node being compared
     * @param options   the comparison options that will be used
     */
    public void begin(Node nodeLeft, Node nodeRight, Options options);
    
    /**
     * Called when the diff stream has ended.
     */
    public void end();
}
