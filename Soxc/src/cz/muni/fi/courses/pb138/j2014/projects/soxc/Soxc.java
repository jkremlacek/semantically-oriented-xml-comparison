/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.util.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * Contains XML comparison functions.
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class Soxc {
    
    /**
     * Reports an unmatched node (that is only in one of the documents) to the consumer.
     * @param node
     * @param side
     * @param options
     * @param diffConsumer 
     */
    private static void reportNode(Node node, DocumentSide side, Options options, DiffConsumer diffConsumer) {
        switch(node.getNodeType()) {
            case Node.ELEMENT_NODE: {
                diffConsumer.beginElement(side, (Element)node);
                
                diffConsumer.beginAttributes();
                List<Node> attrs = Utils.asList(node.getAttributes());
                for(Node attr : attrs)
                    reportNode(attr, side, options, diffConsumer);
                diffConsumer.endAttributes();
                
                diffConsumer.beginChildren();
                List<Node> children = Utils.asList(node.getAttributes());
                for(Node child : children)
                    reportNode(child, side, options, diffConsumer);
                diffConsumer.endChildren();
                
                diffConsumer.endElement();
                break;
            }
            case Node.ATTRIBUTE_NODE: {
                diffConsumer.beginAttribute(side, (Attr)node);
                
                diffConsumer.beginChildren();
                List<Node> children = Utils.asList(node.getAttributes());
                for(Node child : children)
                    reportNode(child, side, options, diffConsumer);
                diffConsumer.endChildren();

                diffConsumer.endAttribute();
                break;
            }
            case Node.ENTITY_REFERENCE_NODE: {
                diffConsumer.beginEntityReference(side, (EntityReference)node);
                
                diffConsumer.beginChildren();
                List<Node> children = Utils.asList(node.getAttributes());
                for(Node child : children)
                    reportNode(child, side, options, diffConsumer);
                diffConsumer.endChildren();

                diffConsumer.endEntityReference();
                break;
            }
            case Node.TEXT_NODE: {
                diffConsumer.beginText(side, (Text)node);
                
                diffConsumer.textValue(side, node.getNodeValue());
                
                diffConsumer.endText();
                break;
            }
            case Node.CDATA_SECTION_NODE: {
                diffConsumer.beginCDATASection(side, (CDATASection)node);
                
                diffConsumer.CDATASectionData(side, node.getNodeValue());
                
                diffConsumer.endCDATASection();
                break;
            }
            case Node.COMMENT_NODE: {
                diffConsumer.beginComment(side, (Comment)node);
                
                diffConsumer.commentData(side, node.getNodeValue());
                
                diffConsumer.endComment();
                break;
            }
            case Node.PROCESSING_INSTRUCTION_NODE: {
                diffConsumer.beginProcessingInstruction(side, (ProcessingInstruction)node);
                
                diffConsumer.processingInstructionData(side, node.getNodeValue());
                
                diffConsumer.endProcessingInstruction();
                break;
            }
            default:
                throw new UnsupportedOperationException("Node type #" + node.getNodeType() + " not supported!");
        }
     }
    
    /**
     * Compare two node lists.
     * @param nodesLeft
     * @param nodesRight
     * @param ignoreOrder
     * @param options
     * @param diffConsumer
     * @return 
     */
    private static boolean diffNodeList(List<Node> nodesLeft, List<Node> nodesRight, Options options, DiffConsumer diffConsumer) {
        boolean equal = true;
        
        // this would look nicer in a separate functions, but stupid Java doesn't
        // support passing variables by reference >:-(
        Map<NodeSimilarityWrapper, Integer> elsOrAttrsLeft = new HashMap<>(nodesLeft.size());
        List<NodeSimilarityWrapper> otherNodesLeft = new ArrayList<>(nodesLeft.size());
        for(Node node : nodesLeft) {
            NodeSimilarityWrapper wrapper = new NodeSimilarityWrapper(node, options);
            // find out if the order is to be ignored for this node:
            if((node.getNodeType() == Node.ELEMENT_NODE && options.ignoreElementOrder()) ||
                    (node.getNodeType() == Node.ATTRIBUTE_NODE && options.ignoreAttributeOrder())) {
                // add into the "multiset":
                Integer count = elsOrAttrsLeft.get(wrapper);
                if(count == null)
                    elsOrAttrsLeft.put(wrapper, 1);
                else
                    elsOrAttrsLeft.put(wrapper, count + 1);
            }
            else
                otherNodesLeft.add(wrapper);
        }
        
        Map<NodeSimilarityWrapper, Integer> elsOrAttrsRight = new HashMap<>(nodesRight.size());
        List<NodeSimilarityWrapper> otherNodesRight = new ArrayList<>(nodesRight.size());
        for(Node node : nodesRight) {
            NodeSimilarityWrapper wrapper = new NodeSimilarityWrapper(node, options);
            // find out if the order is to be ignored for this node:
            if((node.getNodeType() == Node.ELEMENT_NODE && options.ignoreElementOrder()) ||
                    (node.getNodeType() == Node.ATTRIBUTE_NODE && options.ignoreAttributeOrder())) {
                // add into the "multiset":
                Integer count = elsOrAttrsRight.get(wrapper);
                if(count == null)
                    elsOrAttrsRight.put(wrapper, 1);
                else
                    elsOrAttrsRight.put(wrapper, count + 1);
            }
            else
                otherNodesRight.add(wrapper);
        }
        
        // COMPARE UNORDERED:
        // TODO: compare the unordered stuff here

        // COMPARE ORDERED:
        // a very simple linear diff algorithm (to be improved):
        Iterator<NodeSimilarityWrapper> itLeft = otherNodesLeft.iterator();
        Iterator<NodeSimilarityWrapper> itRight = otherNodesRight.iterator();
        List<NodeSimilarityWrapper> leftBuffer = new ArrayList<>();
        List<NodeSimilarityWrapper> rightBuffer = new ArrayList<>();

        while(true) {
            NodeSimilarityWrapper left = itLeft.hasNext() ? itLeft.next() : null;
            NodeSimilarityWrapper right = itRight.hasNext() ? itRight.next() : null;
            if(left == null && right == null) {
                if(!leftBuffer.isEmpty() || !rightBuffer.isEmpty()) {
                    for(NodeSimilarityWrapper nodeWrapper : leftBuffer)
                        reportNode(nodeWrapper.getNode(), DocumentSide.LEFT_DOCUMENT, options, diffConsumer);
                    for(NodeSimilarityWrapper nodeWrapper : rightBuffer)
                        reportNode(nodeWrapper.getNode(), DocumentSide.RIGHT_DOCUMENT, options, diffConsumer);
                    equal = false;
                }
                break;
            }

            if(left != null)
                leftBuffer.add(left);
            if(right != null)
                rightBuffer.add(right);

            // currently, nodes from the left document have priority => the matches found may not be optimal
            if(left != null) {
                for(int i = 0; i < rightBuffer.size(); i++)
                    if(left.equals(rightBuffer.get(i))) {
                        leftBuffer.remove(leftBuffer.size() - 1);
                        for(NodeSimilarityWrapper nodeWrapper : leftBuffer)
                            reportNode(nodeWrapper.getNode(), DocumentSide.LEFT_DOCUMENT, options, diffConsumer);
                        leftBuffer.clear();

                        for(int k = 0; k < i; k++)
                            reportNode(rightBuffer.remove(0).getNode(), DocumentSide.RIGHT_DOCUMENT, options, diffConsumer);
                        if(!diffSimilarNodes(left.getNode(), rightBuffer.remove(0).getNode(), options, diffConsumer))
                            equal = false;
                    }
            }
            // if left node already matched, the leftBuffer will be empty, thus in the following block
            // the right node will not match anything (as it should be):
            if(right != null) {
                for(int i = 0; i < leftBuffer.size(); i++)
                    if(right.equals(leftBuffer.get(i))) {
                        rightBuffer.remove(rightBuffer.size() - 1);
                        for(NodeSimilarityWrapper nodeWrapper : rightBuffer)
                            reportNode(nodeWrapper.getNode(), DocumentSide.RIGHT_DOCUMENT, options, diffConsumer);
                        rightBuffer.clear();

                        for(int k = 0; k < i; k++)
                            reportNode(leftBuffer.remove(0).getNode(), DocumentSide.LEFT_DOCUMENT, options, diffConsumer);
                        if(!diffSimilarNodes(right.getNode(), leftBuffer.remove(0).getNode(), options, diffConsumer))
                            equal = false;
                    }
            }

            // if any of the buffers are used, the lists aren't equal:
            if(!leftBuffer.isEmpty() || !rightBuffer.isEmpty())
                equal = false;
        }
        
        return equal;
    }
    
    /**
     * Finds differences between two nodes that are similar.
     * @param nodeLeft
     * @param nodeRight
     * @param options
     * @param diffConsumer
     * @return 
     */
    private static boolean diffSimilarNodes(Node nodeLeft, Node nodeRight, Options options, DiffConsumer diffConsumer) {
        boolean equal = true;
        switch(nodeLeft.getNodeType()) {
            case Node.ELEMENT_NODE: {
                diffConsumer.beginElement(DocumentSide.BOTH, (Element)nodeLeft);
                
                diffConsumer.beginAttributes();
                List<Node> attrsLeft = Utils.asList(nodeLeft.getAttributes());
                List<Node> attrsRight = Utils.asList(nodeRight.getAttributes());
                if(!diffNodeList(attrsLeft, attrsRight, options, diffConsumer))
                    equal = false;
                diffConsumer.endAttributes();
                
                diffConsumer.beginChildren();
                List<Node> childrenLeft = Utils.asList(nodeLeft.getChildNodes());
                List<Node> childrenRight = Utils.asList(nodeRight.getChildNodes());
                if(!diffNodeList(childrenLeft, childrenRight, options, diffConsumer))
                    equal = false;
                diffConsumer.endChildren();
                
                diffConsumer.endElement();
                break;
            }
            case Node.ATTRIBUTE_NODE: {
                diffConsumer.beginAttribute(DocumentSide.BOTH, (Attr)nodeLeft);
                
                diffConsumer.beginChildren();
                List<Node> childrenLeft = Utils.asList(nodeLeft.getChildNodes());
                List<Node> childrenRight = Utils.asList(nodeRight.getChildNodes());
                if(!diffNodeList(childrenLeft, childrenRight, options, diffConsumer))
                    equal = false;
                diffConsumer.endChildren();

                diffConsumer.endAttribute();
                break;
            }
            case Node.ENTITY_REFERENCE_NODE: {
                diffConsumer.beginEntityReference(DocumentSide.BOTH, (EntityReference)nodeLeft);
                
                diffConsumer.beginChildren();
                List<Node> childrenLeft = Utils.asList(nodeLeft.getChildNodes());
                List<Node> childrenRight = Utils.asList(nodeRight.getChildNodes());
                if(!diffNodeList(childrenLeft, childrenRight, options, diffConsumer))
                    equal = false;
                diffConsumer.endChildren();

                diffConsumer.endEntityReference();
                break;
            }
            case Node.TEXT_NODE: {
                diffConsumer.beginText(DocumentSide.BOTH, (Text)nodeLeft);
                
                String valueLeft = nodeLeft.getNodeValue();
                String valueRight = nodeRight.getNodeValue();
                if(valueLeft.equals(valueRight))
                    diffConsumer.textValue(DocumentSide.BOTH, valueLeft);
                else {
                    diffConsumer.textValue(DocumentSide.LEFT_DOCUMENT, valueLeft);
                    diffConsumer.textValue(DocumentSide.RIGHT_DOCUMENT, valueRight);
                    equal = false;
                }
                
                diffConsumer.endText();
                break;
            }
            case Node.CDATA_SECTION_NODE: {
                diffConsumer.beginCDATASection(DocumentSide.BOTH, (CDATASection)nodeLeft);
                
                String valueLeft = nodeLeft.getNodeValue();
                String valueRight = nodeRight.getNodeValue();
                if(valueLeft.equals(valueRight))
                    diffConsumer.CDATASectionData(DocumentSide.BOTH, valueLeft);
                else {
                    diffConsumer.CDATASectionData(DocumentSide.LEFT_DOCUMENT, valueLeft);
                    diffConsumer.CDATASectionData(DocumentSide.RIGHT_DOCUMENT, valueRight);
                    equal = false;
                }
                
                diffConsumer.endCDATASection();
                break;
            }
            case Node.COMMENT_NODE: {
                diffConsumer.beginComment(DocumentSide.BOTH, (Comment)nodeLeft);
                
                String valueLeft = nodeLeft.getNodeValue();
                String valueRight = nodeRight.getNodeValue();
                if(valueLeft.equals(valueRight))
                    diffConsumer.commentData(DocumentSide.BOTH, valueLeft);
                else {
                    diffConsumer.commentData(DocumentSide.LEFT_DOCUMENT, valueLeft);
                    diffConsumer.commentData(DocumentSide.RIGHT_DOCUMENT, valueRight);
                    equal = false;
                }
                
                diffConsumer.endComment();
                break;
            }
            case Node.PROCESSING_INSTRUCTION_NODE: {
                diffConsumer.beginProcessingInstruction(DocumentSide.BOTH, (ProcessingInstruction)nodeLeft);
                
                String valueLeft = nodeLeft.getNodeValue();
                String valueRight = nodeRight.getNodeValue();
                if(valueLeft.equals(valueRight))
                    diffConsumer.processingInstructionData(DocumentSide.BOTH, valueLeft);
                else {
                    diffConsumer.processingInstructionData(DocumentSide.LEFT_DOCUMENT, valueLeft);
                    diffConsumer.processingInstructionData(DocumentSide.RIGHT_DOCUMENT, valueRight);
                    equal = false;
                }
                
                diffConsumer.endProcessingInstruction();
                break;
            }
            default:
                throw new UnsupportedOperationException("Node type #" + nodeLeft.getNodeType() + " not supported!");
        }
        return equal;
    }
    
    /**
     * Finds hierarchical differences between two nodes.
     * @param nodeLeft      the 'left' node
     * @param nodeRight     the 'right' node
     * @param options       comparison options
     * @param diffConsumer  the listener that wil consume the diff information
     * @return {@code true} if the nodes are equal, otherwise {@code false}
     */
    public static boolean diffNodes(Node nodeLeft, Node nodeRight, Options options, DiffConsumer diffConsumer) {
        NodeSimilarityWrapper wrapperLeft = new NodeSimilarityWrapper(nodeLeft, options);
        NodeSimilarityWrapper wrapperRight = new NodeSimilarityWrapper(nodeRight, options);
        
        boolean equal;
        
        diffConsumer.begin();
        
        if(!wrapperLeft.equals(wrapperRight)) {
            reportNode(nodeLeft, DocumentSide.LEFT_DOCUMENT, options, diffConsumer);
            reportNode(nodeRight, DocumentSide.RIGHT_DOCUMENT, options, diffConsumer);
            equal = false;
        }
        else
            equal = diffSimilarNodes(nodeLeft, nodeRight, options, diffConsumer);

        diffConsumer.end();
        
        return equal;
    }
}
