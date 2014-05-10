/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.HierarchicalNodeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NodeDiffTree;
import java.util.List;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class HierarchicalNodeDiffTreeConsumer implements HierarchicalNodeDiffConsumer {
    
    private List<NodeDiffTree> children = null;

    protected final List<NodeDiffTree> getChildren() {
        return children;
    }

    @Override
    public final NodeListDiffConsumer beginChildren() {
        return new NodeListDiffTreeConsumer(new NodeListDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(List<NodeDiffTree> result) {
                children = result;
            }
        });
    }
}
