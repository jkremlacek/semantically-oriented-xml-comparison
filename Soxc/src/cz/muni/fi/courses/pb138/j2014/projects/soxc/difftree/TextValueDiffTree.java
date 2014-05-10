/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.TextNodeDiffConsumer;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class TextValueDiffTree extends DiffTree {
    
    private final String value;
    
    public final String getValue() {
        return value;
    }

    public TextValueDiffTree(DocumentSide side, String value) {
        super(side);
        
        this.value = value;
    }
    
    public final void replay(TextNodeDiffConsumer consumer) {
        consumer.textValue(getSide(), value);
    }
}
