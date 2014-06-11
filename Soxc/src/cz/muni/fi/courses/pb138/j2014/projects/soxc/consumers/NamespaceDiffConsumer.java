/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;

/**
 * An abstract base for consumers for sub-streams of nodes that can belong to
 * a namespace.
 * 
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public interface NamespaceDiffConsumer {
    
    /**
     * Reports the namespace URI of the node.
     * @param side  the side on which the node has the given NS URI
     * @param uri   the namespace URI of the node
     */
    public void namespaceURI(DocumentSide side, String uri);

    /**
     * Reports the prefix of the node.
     * @param side      the side on which the node has the given prefix
     * @param prefix    the prefix of the node
     */
    public void prefix(DocumentSide side, String prefix);

    /**
     * Reports the local name of the node.
     * @param side  the side on which the node has the given local name
     * @param name  the local name of the node
     */
    public void localName(DocumentSide side, String name);
}
