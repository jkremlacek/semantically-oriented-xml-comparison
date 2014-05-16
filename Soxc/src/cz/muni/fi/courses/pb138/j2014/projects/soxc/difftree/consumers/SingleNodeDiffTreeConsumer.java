/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.SingleNodeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NodeDiffTree;
import java.util.List;
import org.w3c.dom.Node;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class SingleNodeDiffTreeConsumer implements SingleNodeDiffConsumer {
    
    private Node nodeLeft = null;
    private Node nodeRight = null;
    private Options options = null;
    private List<NodeDiffTree> nodeTree = null;

    public final Node getLeftNode() {
        return nodeLeft;
    }

    public final Node getRightNode() {
        return nodeRight;
    }

    public final Options getOptions() {
        return options;
    }

    public final List<NodeDiffTree> getNodeDiffTree() {
        return nodeTree;
    }

    @Override
    public final NodeListDiffConsumer begin(Node nodeLeft, Node nodeRight, Options options) {
        this.nodeLeft = nodeLeft;
        this.nodeRight = nodeRight;
        this.options = options;
        return new NodeListDiffTreeConsumer(new NodeListDiffTreeConsumer.Listener() {

            @Override
            public void onEnd(List<NodeDiffTree> result) {
                nodeTree = result;
            }
        });
    }

    @Override
    public final void end() { }
}
