/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ProcessingInstructionDiffConsumer;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public final class ProcessingInstructionDataDiffTree extends DiffTree {
    
    private final String data;

    public final String getData() {
        return data;
    }

    public ProcessingInstructionDataDiffTree(DocumentSide side, String data) {
        super(side);
        
        this.data = data;
    }
    
    public final void replay(ProcessingInstructionDiffConsumer consumer) {
        consumer.data(getSide(), data);
    }
}
