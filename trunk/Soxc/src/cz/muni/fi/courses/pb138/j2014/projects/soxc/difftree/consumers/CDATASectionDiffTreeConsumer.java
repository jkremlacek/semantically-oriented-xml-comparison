/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CDATASectionDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CDATASectionDataDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CDATASectionDiffTree;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.CDATASection;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class CDATASectionDiffTreeConsumer implements CDATASectionDiffConsumer {

    public interface Listener {
        public void onEnd(CDATASectionDiffTree cdataTree);
    }
    
    private final DocumentSide side;
    private final CDATASection cdata;
    private final Listener listener;
    private final List<CDATASectionDataDiffTree> data = new ArrayList<>();

    public CDATASectionDiffTreeConsumer(DocumentSide side, CDATASection cdata, Listener listener) {
        this.side = side;
        this.cdata = cdata;
        this.listener = listener;
    }
    
    @Override
    public final void data(DocumentSide side, String data) {
        this.data.add(new CDATASectionDataDiffTree(side, data));
    }

    @Override
    public final void end() {
        listener.onEnd(new CDATASectionDiffTree(side, cdata, data));
    }
}
