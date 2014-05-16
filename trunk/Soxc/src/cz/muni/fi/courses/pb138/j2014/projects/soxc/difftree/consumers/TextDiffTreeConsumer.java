/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.TextNodeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.TextDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.TextValueDiffTree;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Text;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public final class TextDiffTreeConsumer implements TextNodeDiffConsumer {

    public interface Listener {
        public void onEnd(TextDiffTree commentTree);
    }
    
    private final DocumentSide side;
    private final Text text;
    private final Listener listener;
    private final List<TextValueDiffTree> value = new ArrayList<>();

    public TextDiffTreeConsumer(DocumentSide side, Text text, Listener listener) {
        this.side = side;
        this.text = text;
        this.listener = listener;
    }

    @Override
    public final void textValue(DocumentSide side, String value) {
        this.value.add(new TextValueDiffTree(side, value));
    }

    @Override
    public final void end() {
        listener.onEnd(new TextDiffTree(text, side, value));
    }
}
