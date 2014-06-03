/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;

/**
 *
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public abstract class DiffTree {
    
    private final DocumentSide side;
    
    public final DocumentSide getSide() {
        return side;
    }

    protected DiffTree(DocumentSide side) {
        this.side = side;
    }
}
