/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.courses.pb138.j2014.projects.soxc.GUI;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import static cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide.BOTH;
import static cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide.LEFT_DOCUMENT;
import static cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide.RIGHT_DOCUMENT;
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Jakub Kremláček
 */
public class GUITreeFactory {
    
    static String MISMATCH_COLOR = "red";
    private List<NodeDiffTree> parents;
    private DefaultMutableTreeNode rootLeft;
    private DefaultMutableTreeNode rootRight;
    private List<DefaultMutableTreeNode> newNodesLeft;
    private List<DefaultMutableTreeNode> newNodesRight;
    private DocumentSide side;
    
    public GUITreeFactory(List<NodeDiffTree> parents, DefaultMutableTreeNode rootLeft, DefaultMutableTreeNode rootRight) {
        this.parents = parents;
        this.rootLeft = rootLeft;
        this.rootRight = rootRight;
        newNodesLeft = new ArrayList<>();
        newNodesRight = new ArrayList<>();
    }
    
    public void processParents() {
        for (NodeDiffTree parent : parents) {
            side = parent.getSide();
            
            if (parent instanceof AttributeDiffTree) {
                AttributeDiffTree retypedParent = (AttributeDiffTree) parent;
                processParent(retypedParent);
            } else if (parent instanceof CDATASectionDiffTree) {
                CDATASectionDiffTree retypedParent = (CDATASectionDiffTree) parent; 
                processParent(retypedParent);
            } else if (parent instanceof CommentDiffTree) {
                CommentDiffTree retypedParent = (CommentDiffTree) parent;
                processParent(retypedParent);
            } else if (parent instanceof DocumentDiffTree) {
                DocumentDiffTree retypedParent = (DocumentDiffTree) parent;
                processParent(retypedParent);
            } else if (parent instanceof EntityReferenceDiffTree) {
                EntityReferenceDiffTree retypedParent = (EntityReferenceDiffTree) parent;
                processParent(retypedParent);
            } else if (parent instanceof ProcessingInstructionDiffTree) {
                ProcessingInstructionDiffTree retypedParent = (ProcessingInstructionDiffTree) parent;
                processParent(retypedParent);
            } else if (parent instanceof TextDiffTree) {
                TextDiffTree retypedParent = (TextDiffTree) parent;
                processParent(retypedParent);
            } else if (parent instanceof ElementDiffTree) {
                ElementDiffTree retypedParent = (ElementDiffTree) parent;
                processParent(retypedParent);
            }
        }
    }
    
    public void processParent(AttributeDiffTree parent) {
        Attr attr = parent.getNode();
        String text = attr.getName();
        text = text + " = " + attr.getValue();
        text = addMismatchTag(text);
        addToNewNodes(text);
    }
    
    public void processParent(CDATASectionDiffTree parent) {
        CDATASection cData = parent.getNode();
        String text = cData.getData();
        text = "<!CDATA[" + text + "]]>";
        text = addMismatchTag(text);
        addToNewNodes(text);
    }
    
    public void processParent(CommentDiffTree parent) {
        Comment comment = parent.getNode();
        String text = comment.getTextContent();
        text = addMismatchTag(text);
        addToNewNodes(text);
    }
    
    public void processParent(DocumentDiffTree parent) {
        Document document = parent.getNode();
        String encoding = "encoding=\"" + document.getXmlEncoding() + "\"";
        String version = "version=\"" + document.getXmlVersion() + "\"";
        String tagName = "<?xml " + version + " " + encoding + "?>";
        addToNewNodes(tagName);
    }
    
    public void processParent(EntityReferenceDiffTree parent) {
        
    }
    
    public void processParent(ProcessingInstructionDiffTree parent) {
        
    }
    
    public void processParent(TextDiffTree parent) {
        Text textNode = parent.getNode();
        String text = addMismatchTag(textNode.getWholeText());
        addToNewNodes(text);
    }
    
    public void processParent(ElementDiffTree parent) {
        Element element = parent.getNode();
        
        String text = addMismatchTag(element.getTagName());

        DefaultMutableTreeNode newElementTreeNode = new DefaultMutableTreeNode(text);
        newNodesLeft.add(newElementTreeNode);
        
        switch (side){
            case BOTH:
                DefaultMutableTreeNode newElementTreeNodeRight = new DefaultMutableTreeNode(text);
                newNodesLeft.add(newElementTreeNode);
                newNodesRight.add(newElementTreeNodeRight);
                
                GUITreeFactory factory = new GUITreeFactory(((HierarchicalNodeDiffTree) parent).getChildren(), newElementTreeNode, newElementTreeNodeRight);
                factory.processParents();
                factory.addNewNodes();
                break;
            case LEFT_DOCUMENT:
                newNodesLeft.add(newElementTreeNode);
                break;
            case RIGHT_DOCUMENT:
                newNodesRight.add(newElementTreeNode);
                break;
            default:
                throw new AssertionError(side.name());    
        } 
    }
       
    public String addMismatchTag(String text) {
        if(side == DocumentSide.BOTH) {
            return text;
        } else {
            return "<html><b><font color=" + MISMATCH_COLOR + "\">" +  text + "</tag><b></html>";
        }
        
    }
    
    public void addNewNodes() {
        for (DefaultMutableTreeNode treeNodeToBeAdded : newNodesLeft) {
            rootLeft.add(treeNodeToBeAdded);
        }
        
        for (DefaultMutableTreeNode treeNodeToBeAdded : newNodesRight) {
            rootRight.add(treeNodeToBeAdded);
        }
        
    }
    
    public void addToNewNodes(String text) {
        DefaultMutableTreeNode newElementTreeNode = new DefaultMutableTreeNode(text);
        switch (side){
            case BOTH:
                DefaultMutableTreeNode newElementTreeNodeRight = new DefaultMutableTreeNode(text);
                newNodesLeft.add(newElementTreeNode);
                newNodesRight.add(newElementTreeNodeRight);
                break;
            case LEFT_DOCUMENT:
                newNodesLeft.add(newElementTreeNode);
                break;
            case RIGHT_DOCUMENT:
                newNodesRight.add(newElementTreeNode);
                break;
            default:
                throw new AssertionError(side.name());    
        } 
        
    }
}
