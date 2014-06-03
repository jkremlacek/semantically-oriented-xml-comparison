/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

/**
 * An abstract base for consumers for sub-streams of hierarchical nodes (nodes
 * with children).
 * 
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public interface HierarchicalNodeDiffConsumer {
    
    /**
     * Called when the diff sub-stream of child nodes has begun.
     * @return the {@link NodeListDiffConsumer} to consume the sub-stream
     */
    public NodeListDiffConsumer beginChildren();
}
