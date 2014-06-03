/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ProcessingInstructionDiffConsumer;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.ProcessingInstruction;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public final class ProcessingInstructionDiffTree extends NodeDiffTree {
    
    private final ProcessingInstruction node;
    private final List<ProcessingInstructionDataDiffTree> data;

    @Override
    public final ProcessingInstruction getNode() {
        return node;
    }

    public final List<ProcessingInstructionDataDiffTree> getData() {
        return data;
    }

    public ProcessingInstructionDiffTree(DocumentSide side, ProcessingInstruction node,
            List<ProcessingInstructionDataDiffTree> data) {
        super(side);
        
        this.node = node;
        this.data = Collections.unmodifiableList(data);
    }

    @Override
    public final void replay(NodeListDiffConsumer consumer) {
        ProcessingInstructionDiffConsumer piConsumer =
                consumer.beginProcessingInstruction(getSide(), node);
        
        for(ProcessingInstructionDataDiffTree dataTree : data)
            dataTree.replay(piConsumer);
        
        piConsumer.end();
    }
}
