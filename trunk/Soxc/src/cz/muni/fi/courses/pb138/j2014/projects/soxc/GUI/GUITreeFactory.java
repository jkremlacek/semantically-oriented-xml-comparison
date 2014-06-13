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
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CDATASectionDataDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CDATASectionDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CommentDataDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.CommentDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.DocumentDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ElementDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.EntityReferenceDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.HierarchicalNodeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.LocalNameDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.NodeDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ProcessingInstructionDataDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.ProcessingInstructionDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.TextDiffTree;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.TextValueDiffTree;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;

/**
 * Parses a list of NodeDiffTrees, building a TreeNode structure representing left and right file separately
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
    
    /**
     * Escapes text for HTML.
     * @param text  the text to escape
     * @return      the escaped result
     */
    private String escapeHtml(String text) {
        return text
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }
    
    /**
     * Wraps HTML content in a mismatch tag.
     * @param content   the content to wrap
     * @return          the result
     */
    private String wrapMismatch(String content) {
        return "<b><font color=" + MISMATCH_COLOR + "\">" +  content + "</font></b>";
    }
    
    /**
     * Wraps HTML content in a mismatch tag (only if current node is mismatching).
     * @param content   the content to wrap
     * @return          the result
     */
    private String wrapMismatchBySide(String content) {
        if(side == DocumentSide.BOTH) {
            return content;
        }
        return wrapMismatch(content);
    }
    
    /**
     * Wraps HTML content in a final 'html' tag.
     * @param content   the content to wrap
     * @return          the result
     */
    private String wrapHtml(String content) {
        return "<html>" + content + "</html>";
    }
    
    /*
     * Changes classtype of every member of parents list (from the class constructor), parsing them each by their instance
     */
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
    
    /*
     * Appends a node of CDATA type to the list of new nodes.
     * 
     * @param   parentCDATA DiffTree source of text value for the new node
     */
    public void processParent(CDATASectionDiffTree parent) {
        String leftCDATA = "";
        String rightCDATA = "";
        
        for (CDATASectionDataDiffTree commentData : parent.getData()) {
            switch (commentData.getSide()) {
                case BOTH:
                    leftCDATA = "&lt[CDATA[" + escapeHtml(commentData.getData()) + "]]&gt;";
                    rightCDATA = leftCDATA;
                    break;
                case LEFT_DOCUMENT:
                    leftCDATA = "&lt[CDATA[" + wrapMismatch(escapeHtml(commentData.getData())) + "]]&gt;";
                    break;
                case RIGHT_DOCUMENT:
                    rightCDATA = "&lt[CDATA[" + wrapMismatch(escapeHtml(commentData.getData())) + "]]&gt;";
                    break;
            }
        }
        
        if (!leftCDATA.isEmpty()) {
            newNodesLeft.add(new DefaultMutableTreeNode(wrapHtml(wrapMismatchBySide(leftCDATA))));
        }
        if (!rightCDATA.isEmpty()) {
            newNodesRight.add(new DefaultMutableTreeNode(wrapHtml(wrapMismatchBySide(rightCDATA))));
        }
    }
    
    /*
     * Appends a node of Comment type to the list of new nodes.
     * Each Document-side of the Node is added separately.
     * 
     * @param   parent      Comment DiffTree source of text value for the new node
     */
    public void processParent(CommentDiffTree parent) {
        String leftComment = "";
        String rightComment = "";
        
        for (CommentDataDiffTree commentData : parent.getData()) {
            switch (commentData.getSide()) {
                case BOTH:
                    leftComment = "&lt!--" + escapeHtml(commentData.getData()) + "--&gt;";
                    rightComment = leftComment;
                    break;
                case LEFT_DOCUMENT:
                    leftComment = "&lt!--" + wrapMismatch(escapeHtml(commentData.getData())) + "--&gt;";
                    break;
                case RIGHT_DOCUMENT:
                    rightComment = "&lt!--" + wrapMismatch(escapeHtml(commentData.getData())) + "--&gt;";
                    break;
            }
        }
        
        if (!leftComment.isEmpty()) {
            newNodesLeft.add(new DefaultMutableTreeNode(wrapHtml(wrapMismatchBySide(leftComment))));
        }
        if (!rightComment.isEmpty()) {
            newNodesRight.add(new DefaultMutableTreeNode(wrapHtml(wrapMismatchBySide(rightComment))));
        }
    }
    
    /*
     * Appends a node of Document type to the list of new nodes.
     * 
     * @param   parent      Document DiffTree source of text value for the new node
     */
    public void processParent(DocumentDiffTree parent) {
        Document document = parent.getNode();
        String encoding = "encoding=\"" + document.getXmlEncoding() + "\"";
        String version = "version=\"" + document.getXmlVersion() + "\"";
        String tagName = "<?xml " + version + " " + encoding + "?>";
        addToNewNodes(tagName);
    }
    
    /*
     * Appends a node of Entity type to the list of new nodes.
     * Each Document-side of the Node is added separately.
     * 
     * @param   parent      Entity DiffTree source of text value for the new node
     */
    public void processParent(EntityReferenceDiffTree parent) {
        DefaultMutableTreeNode newEntityReferenceNodeLeft;
        DefaultMutableTreeNode newEntityReferenceNodeRight;
        
        switch (parent.getSide()) {
            case BOTH:
                newEntityReferenceNodeLeft = new DefaultMutableTreeNode(wrapHtml("&amp;" + escapeHtml(((parent).getNode()).getNodeName())+ ";"));
                newEntityReferenceNodeRight = new DefaultMutableTreeNode(wrapHtml("&amp;" + escapeHtml(((parent).getNode()).getNodeName())+ ";"));
                
                newNodesLeft.add(newEntityReferenceNodeLeft);
                newNodesRight.add(newEntityReferenceNodeRight);
                break;
            case LEFT_DOCUMENT:
                newEntityReferenceNodeLeft = new DefaultMutableTreeNode(wrapHtml(wrapMismatch("&amp;" + escapeHtml(((parent).getNode()).getNodeName())+ ";")));
                newNodesLeft.add(newEntityReferenceNodeLeft);
                break;
            case RIGHT_DOCUMENT:
                newEntityReferenceNodeRight = new DefaultMutableTreeNode(wrapHtml(wrapMismatch("&amp;" + escapeHtml(((parent).getNode()).getNodeName())+ ";")));
                newNodesRight.add(newEntityReferenceNodeRight);
                break;
        }
    }
    
    /*
     * Appends a node of Processing Instruction type to the list of new nodes.
     * Each Document-side of the Node is added separately.
     * 
     * @param   parent      Processing Instruction DiffTree source of text value for the new node
     */
    public void processParent(ProcessingInstructionDiffTree parent) {
        List<ProcessingInstructionDataDiffTree> procInstrList = parent.getData();
        
        String leftProcInstr = "";
        String rightProcInstr = "";
        
        for (ProcessingInstructionDataDiffTree procInstr : procInstrList) {
            switch (procInstr.getSide()) {
                case BOTH:
                    leftProcInstr = escapeHtml(procInstr.getData());
                    rightProcInstr = escapeHtml(procInstr.getData());
                    break;
                case LEFT_DOCUMENT:
                    leftProcInstr = wrapMismatch(escapeHtml(procInstr.getData()));
                    break;
                case RIGHT_DOCUMENT:
                    rightProcInstr = wrapMismatch(escapeHtml(procInstr.getData()));
                    break;
            }
        }
        
        String target = parent.getNode().getTarget();
        if(!leftProcInstr.isEmpty()) {
            leftProcInstr = wrapHtml(wrapMismatchBySide("&lt;?" + target + " " + leftProcInstr + "?&gt;"));
            newNodesLeft.add(new DefaultMutableTreeNode(leftProcInstr));
        }
        
        if(!rightProcInstr.isEmpty()) {
            rightProcInstr = wrapHtml(wrapMismatchBySide("&lt;?" + target + " " + rightProcInstr + "?&gt;"));
            newNodesRight.add(new DefaultMutableTreeNode(rightProcInstr));
        }
    }
    
    /*
     * Appends a node of Text type to the list of new nodes.
     * Each Document-side of the Node is added separately.
     * 
     * @param   parent      Text DiffTree source of text value for the new node
     */
    public void processParent(TextDiffTree parent) {
        List<TextValueDiffTree> textValueList = parent.getValue();
        for (TextValueDiffTree textValue : textValueList) {
            DefaultMutableTreeNode newTextTreeNodeLeft;
            DefaultMutableTreeNode newTextTreeNodeRight;
            
            switch (textValue.getSide()) {
                case BOTH:
                    newTextTreeNodeLeft = new DefaultMutableTreeNode(wrapHtml(escapeHtml(textValue.getValue())));
                    newTextTreeNodeRight = new DefaultMutableTreeNode(wrapHtml(escapeHtml(textValue.getValue())));
                    
                    newNodesLeft.add(newTextTreeNodeLeft);
                    newNodesRight.add(newTextTreeNodeRight);
                    break;
                case LEFT_DOCUMENT:
                    newTextTreeNodeLeft = new DefaultMutableTreeNode(wrapHtml(wrapMismatch(escapeHtml(textValue.getValue()))));
                    newNodesLeft.add(newTextTreeNodeLeft);
                    break;
                case RIGHT_DOCUMENT:
                    newTextTreeNodeRight = new DefaultMutableTreeNode(wrapHtml(wrapMismatch(escapeHtml(textValue.getValue()))));
                    newNodesRight.add(newTextTreeNodeRight);
                    break;
            }
        }
    }
    
    /*
     * Appends a node of Element type to the list of new nodes.
     * Each Document-side of the Node is added separately.
     * Attributes of the element are parsed before appending the element node
     * to the list of new nodes. Attributes are added to the element tag
     * in order to simulate basic XML structure
     * 
     * @param   parent      Element DiffTree source of text value for the new node
     */
    public void processParent(ElementDiffTree parent) {
        //Element element = parent.getNode();
        String leftElementTag = "";
        String rightElementTag = "";
        
        for(int i = 0; i < (parent.getLocalNameTree()).size(); i++) {
            LocalNameDiffTree nameTree = (parent.getLocalNameTree()).get(i);
            
            switch (nameTree.getSide()) {
                case BOTH:
                    leftElementTag = escapeHtml(nameTree.getLocalName());
                    rightElementTag = escapeHtml(nameTree.getLocalName());
                    break;
                case LEFT_DOCUMENT:
                    leftElementTag = wrapMismatch(escapeHtml(nameTree.getLocalName()));
                    break;
                case RIGHT_DOCUMENT:
                    rightElementTag = wrapMismatch(escapeHtml(nameTree.getLocalName()));
                    break;
            }
        }
        
        String attributeTextLeft = " ";
        String attributeTextRight = " ";
        
        List<AttributeDiffTree> attributes = parent.getAttributes();
        for(AttributeDiffTree attribute : attributes) {
            
            NamespaceBuilder attributeNamespace = new NamespaceBuilder(attribute.getNamespaceUri(), attribute.getPrefix());
            
            //attribute tag parsing
            
            String leftAttributeTag = "";
            String rightAttributeTag = "";
            
            for(int i = 0; i < (attribute.getLocalNameTree()).size(); i++) {
                LocalNameDiffTree nameTree = (attribute.getLocalNameTree()).get(i);

                switch (nameTree.getSide()) {
                    case BOTH:
                        leftAttributeTag = escapeHtml(nameTree.getLocalName());
                        rightAttributeTag = escapeHtml(nameTree.getLocalName());
                        break;
                    case LEFT_DOCUMENT:
                        leftAttributeTag = wrapMismatch(escapeHtml(nameTree.getLocalName()));
                        break;
                    case RIGHT_DOCUMENT:
                        rightAttributeTag = wrapMismatch(escapeHtml(nameTree.getLocalName()));
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
                                attrValueLeft = escapeHtml(textValue.getValue());
                                attrValueRight = attrValueLeft;
                                break;
                            case LEFT_DOCUMENT:
                                attrValueLeft = wrapMismatch(escapeHtml(textValue.getValue()));
                                break;
                            case RIGHT_DOCUMENT:
                                attrValueRight = wrapMismatch(escapeHtml(textValue.getValue()));
                                break;
                        }
                    }
                } else if (attrChildren instanceof EntityReferenceDiffTree) {
                    String name = (((EntityReferenceDiffTree) attrChildren).getNode()).getNodeName();
                    switch (attrChildren.getSide()) {
                        case BOTH:
                            attrValueLeft = "&amp;" + escapeHtml(name) + ";";
                            attrValueRight = attrValueLeft;
                            break;
                        case LEFT_DOCUMENT:
                            attrValueLeft = wrapMismatch("&amp;" + escapeHtml(name) + ";");
                            break;
                        case RIGHT_DOCUMENT:
                            attrValueRight = wrapMismatch("&amp;" + escapeHtml(name) + ";");
                            break;
                    }
                }
            }
            
            if(!leftAttributeTag.isEmpty())
                attributeTextLeft = attributeTextLeft + " " + attributeNamespace.getLeftNamespaceWithoutURI(leftAttributeTag + "=\"" + attrValueLeft + "\"");
            if(!rightAttributeTag.isEmpty())
                attributeTextRight = attributeTextRight + " " + attributeNamespace.getRightNamespaceWithoutURI(rightAttributeTag + "=\"" + attrValueRight + "\"");
        }
        
        //finalization of tag
        
        NamespaceBuilder elementNamespace = new NamespaceBuilder(parent.getNamespaceUriTree(), parent.getPrefixTree());
        
        String textLeft = wrapHtml("<b>" + elementNamespace.getLeftNamespaceWithoutURI(wrapMismatchBySide(leftElementTag)) + "</b>" + attributeTextLeft);
        String textRight = wrapHtml("<b>" + elementNamespace.getRightNamespaceWithoutURI(wrapMismatchBySide(rightElementTag)) + "</b>" + attributeTextRight);

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
    
    /*
     * Method that is called last during parent parsing procedure. Appends new 
     * nodes to the root nodes.
     */
    public void addNewNodes() {
        for (DefaultMutableTreeNode treeNodeToBeAdded : newNodesLeft) {
            rootLeft.add(treeNodeToBeAdded);
        }
        
        for (DefaultMutableTreeNode treeNodeToBeAdded : newNodesRight) {
            rootRight.add(treeNodeToBeAdded);
        }
    }
    
    /*
     * Adds text as a new node to the list of new nodes.
     * 
     * @param   text    string to be added as a new node
     */
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
