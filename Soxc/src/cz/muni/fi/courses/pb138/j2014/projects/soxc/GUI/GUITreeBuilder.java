/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.courses.pb138.j2014.projects.soxc.GUI;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.AttributeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CDATASectionDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CommentDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.DocumentDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ElementDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.EntityReferenceDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.HierarchicalNodeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NodeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ProcessingInstructionDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.TextDiffTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub Kremláček
 */
public class GUITreeBuilder {
    
    static String MISMATCH_COLOR = "#CA0000";
    
    public void append(AttributeDiffTree node, DefaultMutableTreeNode root, boolean mismatch) {
        Attr attr = node.getNode();
        String text = attr.getName();
        text = text + " = " + attr.getValue();
        
        if(mismatch) {
            text = addMismatchTag(text);
        }
        
        root.add(new DefaultMutableTreeNode(text));
        
    }
    
    public void append(CDATASectionDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        
    }
    
    public void append(CommentDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        
    }
    
    public void append(DocumentDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        
    }
    
    public void append(ElementDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        Element element = node.getNode();
        String text = element.getTagName();
        
        if(mismatch) {
            text = addMismatchTag(text);
        }
        
        DefaultMutableTreeNode newElementTreeNode = new DefaultMutableTreeNode(text);
        
        newElementTreeNode.add(newNode);      
        root.add(newElementTreeNode);
    }
    
    public void append(EntityReferenceDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        
    }
    
    public void append(HierarchicalNodeDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        
    }
    
    public void append(ProcessingInstructionDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        
    }
    
    public void append(TextDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        
    }
    
    public void append(NodeDiffTree node, DefaultMutableTreeNode root, 
            boolean mismatch, DefaultMutableTreeNode newNode) {
        
    }
    
    public String addMismatchTag(String text) {
        return "<html><b><tag style=\"color:" + MISMATCH_COLOR + "\">" +  text + "</tag><b></html>";
    }
}
