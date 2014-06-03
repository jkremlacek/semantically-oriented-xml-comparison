/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.TextNodeDiffConsumer;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.Text;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class TextDiffTree extends NodeDiffTree {
    
    private final Text node;
    private final List<TextValueDiffTree> value;

    @Override
    public final Text getNode() {
        return node;
    }

    public final List<TextValueDiffTree> getValue() {
        return value;
    }

    public TextDiffTree(Text node, DocumentSide side, List<TextValueDiffTree> value) {
        super(side);
        
        this.node = node;
        this.value = Collections.unmodifiableList(value);
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        TextNodeDiffConsumer textConsumer = consumer.beginText(getSide(), node);
        
        for(TextValueDiffTree valueTree : value)
            valueTree.replay(textConsumer);
        
        textConsumer.end();
    }
}
