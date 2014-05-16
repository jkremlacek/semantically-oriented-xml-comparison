/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;

/**
 *
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class PrefixDiffTree extends DiffTree {
    
    private final String prefix;

    public String getPrefix() {
        return prefix;
    }

    public PrefixDiffTree(DocumentSide side, String prefix) {
        super(side);
        this.prefix = prefix;
    }
}
