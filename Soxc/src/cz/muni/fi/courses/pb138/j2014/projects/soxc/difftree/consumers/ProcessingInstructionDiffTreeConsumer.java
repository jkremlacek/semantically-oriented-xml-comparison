/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ProcessingInstructionDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ProcessingInstructionDataDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ProcessingInstructionDiffTree;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.ProcessingInstruction;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class ProcessingInstructionDiffTreeConsumer implements ProcessingInstructionDiffConsumer {
    
    public interface Listener {
        public void onEnd(ProcessingInstructionDiffTree piTree);
    }
    
    private final DocumentSide side;
    private final ProcessingInstruction pi;
    private final Listener listener;
    private final List<ProcessingInstructionDataDiffTree> data = new ArrayList<>();

    public ProcessingInstructionDiffTreeConsumer(DocumentSide side, ProcessingInstruction pi, Listener listener) {
        this.side = side;
        this.pi = pi;
        this.listener = listener;
    }
    
    @Override
    public final void data(DocumentSide side, String data) {
        this.data.add(new ProcessingInstructionDataDiffTree(side, data));
    }

    @Override
    public final void end() {
        listener.onEnd(new ProcessingInstructionDiffTree(side, pi, data));
    }
}
