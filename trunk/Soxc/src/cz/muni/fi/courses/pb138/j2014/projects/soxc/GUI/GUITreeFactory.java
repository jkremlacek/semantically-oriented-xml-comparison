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
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.LocalNameDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NodeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ProcessingInstructionDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.TextDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.TextValueDiffTree;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
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
            
            if (parent instanceof CDATASectionDiffTree) {
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
        List<TextValueDiffTree> textValueList = parent.getValue();
        for (int i = 0; i < textValueList.size(); i++) {
            TextValueDiffTree textValue = textValueList.get(i);

            DefaultMutableTreeNode newTextTreeNodeLeft;
            DefaultMutableTreeNode newTextTreeNodeRight;
            
            switch (textValue.getSide()) {
                case BOTH:
                    newTextTreeNodeLeft = new DefaultMutableTreeNode(textValue.getValue());
                    newTextTreeNodeRight = new DefaultMutableTreeNode(textValue.getValue());
                    
                    newNodesLeft.add(newTextTreeNodeLeft);
                    newNodesRight.add(newTextTreeNodeRight);
                    break;
                case LEFT_DOCUMENT:
                    newTextTreeNodeLeft = new DefaultMutableTreeNode("<b><font color=" + MISMATCH_COLOR + "\">"
                            + textValue.getValue() 
                            + "</font></b>");
                    newNodesLeft.add(newTextTreeNodeLeft);
                    break;
                case RIGHT_DOCUMENT:
                    newTextTreeNodeRight = new DefaultMutableTreeNode("<b><font color=" + MISMATCH_COLOR + "\">" 
                            + textValue.getValue()
                            + "</font></b>");
                    newNodesRight.add(newTextTreeNodeRight);
                    break;
            }
        }
    }
    
    public void processParent(ElementDiffTree parent) {
        //Element element = parent.getNode();
        String leftElementTag = "";
        String rightElementTag = "";
        
        for(int i = 0; i < (parent.getLocalNameTree()).size(); i++) {
            LocalNameDiffTree nameTree = (parent.getLocalNameTree()).get(i);
            
            switch (nameTree.getSide()) {
                case BOTH:
                    leftElementTag = nameTree.getLocalName();
                    rightElementTag = nameTree.getLocalName();
                    break;
                case LEFT_DOCUMENT:
                    leftElementTag = nameTree.getLocalName();
                    break;
                case RIGHT_DOCUMENT:
                    rightElementTag = nameTree.getLocalName();
                    break;
            }
        }
        
        (parent.getLocalNameTree()).get(0).getLocalName();
        
        String attributeTextLeft = " ";
        String attributeTextRight = " ";
        
        List<AttributeDiffTree> attributes = parent.getAttributes();
        for(AttributeDiffTree attribute : attributes) {
            
            
            //attribute tag parsing
            
            String leftAttributeTag = "";
            String rightAttributeTag = "";
            
            for(int i = 0; i < (attribute.getLocalNameTree()).size(); i++) {
                LocalNameDiffTree nameTree = (attribute.getLocalNameTree()).get(i);

                switch (nameTree.getSide()) {
                    case BOTH:
                        leftAttributeTag = nameTree.getLocalName();
                        rightAttributeTag = nameTree.getLocalName();
                        break;
                    case LEFT_DOCUMENT:
                        leftAttributeTag = "<b><font color=" + MISMATCH_COLOR + "\">"
                                + nameTree.getLocalName()
                                + "</font></b>";
                        break;
                    case RIGHT_DOCUMENT:
                        rightAttributeTag = "<b><font color=" + MISMATCH_COLOR + "\">"
                                + nameTree.getLocalName()
                                + "</font></b>";
                        break;
                }
            }
            //attribute value parsing
            
            String attrValueLeft = "";
            String attrValueRight = "";
            
            List<NodeDiffTree> attrChildrenList = attribute.getChildren();
            for (NodeDiffTree attrChildren : attrChildrenList) {
                if (attrChildren instanceof TextDiffTree) {
                    
                    List<TextValueDiffTree> textValueList = ((TextDiffTree) attrChildren).getValue();
                    for (int i = 0; i < textValueList.size(); i++) {
                        TextValueDiffTree textValue = textValueList.get(i);
                        
                        switch (textValue.getSide()) {
                            case BOTH:
                                attrValueLeft = textValue.getValue();
                                attrValueRight = attrValueLeft;
                                break;
                            case LEFT_DOCUMENT:
                                attrValueLeft = "<b><font color=" + MISMATCH_COLOR + "\">"
                                        + textValue.getValue() 
                                        + "</font></b>";
                                break;
                            case RIGHT_DOCUMENT:
                                attrValueRight = "<b><font color=" + MISMATCH_COLOR + "\">" 
                                        + textValue.getValue()
                                        + "</font></b>";
                                break;
                        }
                    }
                } else if (attrChildren instanceof EntityReferenceDiffTree) {
                    switch (attrChildren.getSide()) {
                        case BOTH:
                            attrValueLeft = "&(" +(((EntityReferenceDiffTree) attrChildren).getNode()).getLocalName()+ ")";
                            attrValueRight = attrValueLeft;
                            break;
                        case LEFT_DOCUMENT:
                            attrValueLeft = "<b><font color=" + MISMATCH_COLOR + "\">" 
                                    + "&(" 
                                    + (((EntityReferenceDiffTree) attrChildren).getNode()).getLocalName() 
                                    + ")"
                                    + "</font></b>";
                            break;
                        case RIGHT_DOCUMENT:
                            attrValueRight = "<b><font color=" + MISMATCH_COLOR + "\">" 
                                    + "&("
                                    + (((EntityReferenceDiffTree) attrChildren).getNode()).getLocalName()
                                    + ")"
                                    + "</font></b>";
                            break;
                    }
                }
            }
            
            attributeTextLeft = attributeTextLeft + " " + leftAttributeTag + "=\"" + attrValueLeft + "\"";
            attributeTextRight = attributeTextRight + " "  + rightAttributeTag + "=\"" + attrValueRight + "\"";
        }
                
        String textLeft = "<html><b>" + addElementMismatchTag(leftElementTag) + "</b>" + attributeTextLeft + "</html>";
        String textRight = "<html><b>" + addElementMismatchTag(rightElementTag) + "</b>" + attributeTextRight + "</html>";

        DefaultMutableTreeNode newElementTreeNodeLeft = new DefaultMutableTreeNode(textLeft);
        DefaultMutableTreeNode newElementTreeNodeRight = new DefaultMutableTreeNode(textRight);
        //newNodesLeft.add(newElementTreeNode);
        
        switch (side){
            case BOTH:
                newNodesLeft.add(newElementTreeNodeLeft);
                newNodesRight.add(newElementTreeNodeRight);
                
                GUITreeFactory factory = new GUITreeFactory(((HierarchicalNodeDiffTree) parent).getChildren(), newElementTreeNodeLeft, newElementTreeNodeRight);
                factory.processParents();
                factory.addNewNodes();
                break;
            case LEFT_DOCUMENT:
                newNodesLeft.add(newElementTreeNodeLeft);
                break;
            case RIGHT_DOCUMENT:
                newNodesRight.add(newElementTreeNodeRight);
                break;
            default:
                throw new AssertionError(side.name());    
        } 
    }
       
    public String addMismatchTag(String text) {
        if(side == DocumentSide.BOTH) {
            return text;
        } else {
            return "<html><b><font color=" + MISMATCH_COLOR + "\">" +  text + "</font></b></html>";
        }       
    }
    
    public String addElementMismatchTag(String text) {
        if(side == DocumentSide.BOTH) {
            return text;
        } else {
            return "<b><font color=" + MISMATCH_COLOR + "\">" +  text + "</font></b>";
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
