/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ElementDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.AttributeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ElementDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NodeDiffTree;
import java.util.AbstractList;
import java.util.List;
import org.w3c.dom.Element;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public final class ElementDiffTreeConsumer
        extends HierarchicalNodeDiffTreeConsumer
        implements ElementDiffConsumer {

    public interface Listener {
        public void onEnd(ElementDiffTree elementTree);
    }
    
    private final DocumentSide side;
    private final Element element;
    private final Listener listener;
    private List<AttributeDiffTree> attributes = null;

    public ElementDiffTreeConsumer(DocumentSide side, Element element, Listener listener) {
        this.side = side;
        this.element = element;
        this.listener = listener;
    }
    
    @Override
    public final NodeListDiffConsumer beginAttributes() {
        return new NodeListDiffTreeConsumer(new NodeListDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(final List<NodeDiffTree> result) {
                attributes = new AbstractList<AttributeDiffTree>() {

                    @Override
                    public AttributeDiffTree get(int index) {
                        return (AttributeDiffTree)result.get(index);
                    }

                    @Override
                    public int size() {
                        return result.size();
                    }
                };
            }
        });
    }

    @Override
    public final void end() {
        listener.onEnd(new ElementDiffTree(element, side, getChildren(), attributes));
    }
}
