/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.courses.pb138.j2014.projects.soxc.GUI;

import static cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide.BOTH;
import static cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide.LEFT_DOCUMENT;
import static cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide.RIGHT_DOCUMENT;
import static cz.muni.fi.courses.pb138.j2014.projects.soxc.GUI.GUITreeFactory.MISMATCH_COLOR;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NamespaceUriDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.PrefixDiffTree;
import java.util.List;

/**
 *
 * @author Jakub Kremláček
 */
public class NamespaceBuilder {
    
    private String leftNamespacePrefix = "";
    private String rightNamespacePrefix = "";
    private String leftNamespaceURI = "";
    private String rightNamespaceURI = "";
    
    public NamespaceBuilder(List<NamespaceUriDiffTree> nsUriList, List<PrefixDiffTree> prefixList) {
        for(PrefixDiffTree prefix : prefixList) {
            switch (prefix.getSide()) {
                case BOTH:
                    leftNamespacePrefix = prefix.getPrefix();
                    rightNamespacePrefix = prefix.getPrefix();
                    break;
                case LEFT_DOCUMENT:
                    leftNamespacePrefix = addMismatchTag(prefix.getPrefix());
                    break;
                case RIGHT_DOCUMENT:
                    rightNamespacePrefix = addMismatchTag(prefix.getPrefix());
                    break;
            }
        }
        
        for(NamespaceUriDiffTree nsUri : nsUriList) {
            switch (nsUri.getSide()) {
                case BOTH:
                    leftNamespaceURI = nsUri.getNamespaceURI();
                    rightNamespaceURI = nsUri.getNamespaceURI();
                    break;
                case LEFT_DOCUMENT:
                    leftNamespaceURI = addMismatchTag(nsUri.getNamespaceURI());
                    break;
                case RIGHT_DOCUMENT:
                    rightNamespaceURI = addMismatchTag(nsUri.getNamespaceURI());
                    break;
            }
        } 
    }
    
    public String addMismatchTag(String text) {
        return "<b><font color=" + MISMATCH_COLOR + "\">" +  text + "</font></b>";      
    }
    
    public String getLeftNamespace(String text) {
        if (!leftNamespacePrefix.isEmpty()) {
            text = leftNamespacePrefix + ":" + text;
            if(!leftNamespaceURI.isEmpty()) {
                return text + " xmlns:" + leftNamespacePrefix + "=\"" + leftNamespaceURI + "\"";
            } else return text;
        } else return text;
    }
    
    public String getLeftNamespaceWithoutURI(String text) {
        if (!leftNamespacePrefix.isEmpty()) {
            return text = leftNamespacePrefix + ":" + text;
        } else return text;
    }
    
    public String getRightNamespace(String text) {
        if (!rightNamespacePrefix.isEmpty()) {
            text = rightNamespacePrefix + ":" + text;
            if(!rightNamespaceURI.isEmpty()) {
                return text + " xmlns:" + rightNamespacePrefix + "=\"" + rightNamespaceURI + "\"";
            } else return text;
        } else return text;
    }
    
    public String getRightNamespaceWithoutURI(String text) {
        if (!rightNamespacePrefix.isEmpty()) {
            return text = rightNamespacePrefix + ":" + text;
        } else return text;
    }
}
