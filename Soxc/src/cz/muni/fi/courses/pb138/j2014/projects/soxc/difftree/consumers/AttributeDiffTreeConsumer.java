/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.AttributeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.AttributeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.LocalNameDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NamespaceUriDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.PrefixDiffTree;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Attr;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
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
    private final List<NamespaceUriDiffTree> nsUriTree = new ArrayList<>();
    private final List<PrefixDiffTree> prefixTree = new ArrayList<>();
    private final List<LocalNameDiffTree> localNameTree = new ArrayList<>();

    public AttributeDiffTreeConsumer(DocumentSide side, Attr attr, Listener listener) {
        this.side = side;
        this.attr = attr;
        this.listener = listener;
    }
    
    @Override
    public final void namespaceURI(DocumentSide side, String uri) {
        nsUriTree.add(new NamespaceUriDiffTree(side, uri));
    }

    @Override
    public final void prefix(DocumentSide side, String prefix) {
        prefixTree.add(new PrefixDiffTree(side, prefix));
    }

    @Override
    public void localName(DocumentSide side, String name) {
        localNameTree.add(new LocalNameDiffTree(side, name));
    }
    
    @Override
    public final void end() {
        listener.onEnd(new AttributeDiffTree(side, attr,
                nsUriTree, prefixTree, localNameTree,
                getChildren()));
    }
}
