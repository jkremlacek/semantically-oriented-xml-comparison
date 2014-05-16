/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.AttributeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.AttributeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NamespaceUriDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.PrefixDiffTree;
import org.w3c.dom.Attr;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public final class AttributeDiffTreeConsumer
        extends HierarchicalNodeDiffTreeConsumer
        implements AttributeDiffConsumer {

    public interface Listener {
        public void onEnd(AttributeDiffTree attrTree);
    }
    
    private final DocumentSide side;
    private final Attr attr;
    private final Listener listener;
    private NamespaceUriDiffTree nsUriTree = null;
    private PrefixDiffTree prefixTree = null;

    public AttributeDiffTreeConsumer(DocumentSide side, Attr attr, Listener listener) {
        this.side = side;
        this.attr = attr;
        this.listener = listener;
    }
    
    @Override
    public void namespaceURI(DocumentSide side, String uri) {
        nsUriTree = new NamespaceUriDiffTree(side, uri);
    }

    @Override
    public void prefix(DocumentSide side, String prefix) {
        prefixTree = new PrefixDiffTree(side, prefix);
    }
    
    @Override
    public final void end() {
        listener.onEnd(new AttributeDiffTree(side, attr, nsUriTree, prefixTree, getChildren()));
    }
}
