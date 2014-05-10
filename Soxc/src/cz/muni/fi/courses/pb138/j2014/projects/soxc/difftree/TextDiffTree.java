/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.TextNodeDiffConsumer;
import org.w3c.dom.Text;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class TextDiffTree extends NodeDiffTree {
    
    private final Text node;
    private final TextValueDiffTree value;

    @Override
    public Text getNode() {
        return node;
    }

    public TextValueDiffTree getValue() {
        return value;
    }

    public TextDiffTree(Text node, DocumentSide side, TextValueDiffTree value) {
        super(side);
        this.node = node;
        this.value = value;
    }

    @Override
    public void replay(NodeListDiffConsumer consumer) {
        TextNodeDiffConsumer textConsumer = consumer.beginText(getSide(), node);
        
        value.replay(textConsumer);
        
        textConsumer.end();
    }
}
