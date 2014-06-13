/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.AttributeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CDATASectionDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.CommentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.DocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ElementDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.JustDocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.NodeListDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.ProcessingInstructionDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.SingleNodeDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.TextNodeDiffConsumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * Contains XML comparison functions.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public class Soxc {
    
    /**
     * Preprocesses a given DOM tree according to the specified options.
     * @param node      the root node of the DOM tree to preprocess
     * @param options   the preprocessing options to use
     */
    public static void preprocess(Node node, PreprocessingOptions options) {
        short nodeType = node.getNodeType();
        
        if(nodeType == Node.TEXT_NODE) {
            if(options.trimWhitespaceInText()) {
                String text = node.getNodeValue().trim().replaceAll("\\s+", " ");
                node.setNodeValue(text);
                
                // remove the node if the text would become empty:
                if(text.isEmpty()) {
                    Node parent = node.getParentNode();
                    if(parent != null)
                        node.getParentNode().removeChild(node);
                }
            }
        }
        
        // copy the list because we might remove some nodes while iterating:
        for(Node child : new ArrayList<>(Utils.asList(node.getChildNodes()))) {
            short childType = child.getNodeType();
            if(childType == Node.TEXT_NODE) {
                if(options.ignoreText() || (options.ignoreWhitespaceOnlyText() &&
                        child.getNodeValue().matches("\\s*"))) {
                    node.removeChild(child);
                    continue;
                }
            }
            preprocess(child, options);
        }
    }
    
    /**
     * Reports an unmatched node (that is only in one of the documents) to the consumer.
     * @param node
     * @param side
     * @param options
     * @param diffConsumer 
     */
    private static void reportNode(Node node, DocumentSide side, Options options, NodeListDiffConsumer diffConsumer) {
        switch(node.getNodeType()) {
            case Node.DOCUMENT_NODE: {
                DocumentDiffConsumer docConsumer = diffConsumer.beginDocument(side, (Document)node);

                NodeListDiffConsumer childrenConsumer = docConsumer.beginChildren();
                List<Node> children = Utils.asList(node.getChildNodes());
                for(Node child : children)
                    reportNode(child, side, options, childrenConsumer);
                childrenConsumer.end();
                
                docConsumer.end();
                break;
            }
            case Node.ELEMENT_NODE: {
                ElementDiffConsumer elementConsumer = diffConsumer.beginElement(side, (Element)node);
                
                if(!options.ignoreNamespaceURI()) {
                    String nsUri = node.getNamespaceURI();
                    if(nsUri != null)
                        elementConsumer.namespaceURI(side, nsUri);
                }
                if(!options.ignorePrefix()) {
                    String prefix = node.getPrefix();
                    if(prefix != null)
                        elementConsumer.prefix(side, prefix);
                }
                elementConsumer.localName(side, node.getLocalName());
                
                NodeListDiffConsumer attrsConsumer = elementConsumer.beginAttributes();
                List<Node> attrs = Utils.asList(node.getAttributes());
                for(Node attr : attrs)
                    reportNode(attr, side, options, attrsConsumer);
                attrsConsumer.end();
                
                NodeListDiffConsumer childrenConsumer = elementConsumer.beginChildren();
                List<Node> children = Utils.asList(node.getChildNodes());
                for(Node child : children)
                    reportNode(child, side, options, childrenConsumer);
                childrenConsumer.end();
                
                elementConsumer.end();
                break;
            }
            case Node.ATTRIBUTE_NODE: {
                AttributeDiffConsumer attrConsumer = diffConsumer.beginAttribute(side, (Attr)node);
                
                if(!options.ignoreNamespaceURI()) {
                    String nsUri = node.getNamespaceURI();
                    if(nsUri != null)
                        attrConsumer.namespaceURI(side, nsUri);
                }
                if(!options.ignorePrefix()) {
                    String prefix = node.getPrefix();
                    if(prefix != null)
                        attrConsumer.prefix(side, prefix);
                }
                attrConsumer.localName(side, node.getLocalName());
                
                NodeListDiffConsumer childrenConsumer = attrConsumer.beginChildren();
                List<Node> children = Utils.asList(node.getChildNodes());
                for(Node child : children)
                    reportNode(child, side, options, childrenConsumer);
                childrenConsumer.end();

                attrConsumer.end();
                break;
            }
            case Node.ENTITY_REFERENCE_NODE: {
                diffConsumer.entityReference(side, (EntityReference)node);
                break;
            }
            case Node.TEXT_NODE: {
                TextNodeDiffConsumer textConsumer =  diffConsumer.beginText(side, (Text)node);
                
                textConsumer.textValue(side, node.getNodeValue());
                
                textConsumer.end();
                break;
            }
            case Node.CDATA_SECTION_NODE: {
                CDATASectionDiffConsumer CDATAConsumer = diffConsumer.beginCDATASection(side, (CDATASection)node);
                
                CDATAConsumer.data(side, node.getNodeValue());
                
                CDATAConsumer.end();
                break;
            }
            case Node.COMMENT_NODE: {
                CommentDiffConsumer commentConsumer = diffConsumer.beginComment(side, (Comment)node);
                
                commentConsumer.data(side, node.getNodeValue());
                
                commentConsumer.end();
                break;
            }
            case Node.PROCESSING_INSTRUCTION_NODE: {
                ProcessingInstructionDiffConsumer piConsumer = diffConsumer.beginProcessingInstruction(side, (ProcessingInstruction)node);
                
                piConsumer.data(side, node.getNodeValue());
                
                piConsumer.end();
                break;
            }
            case Node.DOCUMENT_TYPE_NODE: {
                // currently, DocumentType nodes are just ignored 
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
     * @param options
     * @param diffConsumer
     * @return 
     */
    private static boolean diffNodeList(List<Node> nodesLeft, List<Node> nodesRight, Options options, NodeListDiffConsumer diffConsumer) {
        boolean equal = true;
        
        List<NodeSimilarityWrapper> unorderedLeft = new ArrayList<>(nodesLeft.size());
        List<NodeSimilarityWrapper> orderedLeft = new ArrayList<>(nodesLeft.size());
        Utils.splitNodeList(nodesLeft,
                Utils.autoWrapSimilarity(Utils.asConsumer(unorderedLeft), options),
                Utils.autoWrapSimilarity(Utils.asConsumer(orderedLeft), options),
                options);
        
        List<NodeSimilarityWrapper> unorderedRight = new ArrayList<>(nodesRight.size());
        List<NodeSimilarityWrapper> orderedRight = new ArrayList<>(nodesRight.size());
        Utils.splitNodeList(nodesRight,
                Utils.autoWrapSimilarity(Utils.asConsumer(unorderedRight), options),
                Utils.autoWrapSimilarity(Utils.asConsumer(orderedRight), options),
                options);
        
        // COMPARE UNORDERED:
        // Take one node at a time from the left side,
        // try to find a similar node on the other side.
        // If similar node was found, remove both from the lists
        // and diff them. Otherwise keep them in the lists - they
        // will be reported later.
        for(int i = 0; i < unorderedLeft.size(); i++) {
            NodeSimilarityWrapper left = unorderedLeft.get(i);
            
            for(int k = 0; k < unorderedRight.size(); k++) {
                NodeSimilarityWrapper right = unorderedRight.get(k);
                
                if(left.equals(right)) {
                    if(!diffSimilarNodes(left.getNode(), right.getNode(), options, diffConsumer))
                        equal = false;
                    unorderedLeft.remove(i);
                    unorderedRight.remove(k);
                    i--;
                    break;
                }
            }
        }
        if(!unorderedLeft.isEmpty() || !unorderedRight.isEmpty())
            equal = false;
        // report the unmatched nodes:
        for(NodeSimilarityWrapper wrapper : unorderedLeft)
            reportNode(wrapper.getNode(), DocumentSide.LEFT_DOCUMENT, options, diffConsumer);
        for(NodeSimilarityWrapper wrapper : unorderedRight)
            reportNode(wrapper.getNode(), DocumentSide.RIGHT_DOCUMENT, options, diffConsumer);
    
        // COMPARE ORDERED:
        // a very simple linear diff algorithm (to be improved):
        Iterator<NodeSimilarityWrapper> itLeft = orderedLeft.iterator();
        Iterator<NodeSimilarityWrapper> itRight = orderedRight.iterator();
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
    private static boolean diffSimilarNodes(Node nodeLeft, Node nodeRight, Options options, NodeListDiffConsumer diffConsumer) {
        boolean equal = true;
        switch(nodeLeft.getNodeType()) {
            case Node.DOCUMENT_NODE: {
                DocumentDiffConsumer docConsumer = diffConsumer.beginDocument(DocumentSide.BOTH, (Document)nodeLeft);

                NodeListDiffConsumer childrenConsumer = docConsumer.beginChildren();
                List<Node> childrenLeft = Utils.asList(nodeLeft.getChildNodes());
                List<Node> childrenRight = Utils.asList(nodeRight.getChildNodes());
                if(!diffNodeList(childrenLeft, childrenRight, options, childrenConsumer))
                    equal = false;
                childrenConsumer.end();
                
                docConsumer.end();
                break;
            }
            case Node.ELEMENT_NODE: {
                ElementDiffConsumer elementConsumer = diffConsumer.beginElement(DocumentSide.BOTH, (Element)nodeLeft);
                
                if(!options.ignoreNamespaceURI()) {
                    String nsUriLeft = nodeLeft.getNamespaceURI();
                    String nsUriRight = nodeRight.getNamespaceURI();
                    if(Utils.equal(nsUriLeft, nsUriRight)) {
                        if(nsUriLeft != null)
                            elementConsumer.namespaceURI(DocumentSide.BOTH, nsUriLeft);
                    }
                    else {
                        if(nsUriLeft != null)
                            elementConsumer.namespaceURI(DocumentSide.LEFT_DOCUMENT, nsUriLeft);
                        if(nsUriRight != null)
                            elementConsumer.namespaceURI(DocumentSide.RIGHT_DOCUMENT, nsUriRight);
                        equal = false;
                    }
                }
                if(!options.ignorePrefix()) {
                    String prefixLeft = nodeLeft.getPrefix();
                    String prefixRight = nodeRight.getPrefix();
                    if(Utils.equal(prefixLeft, prefixRight)) {
                        if(prefixLeft != null)
                            elementConsumer.prefix(DocumentSide.BOTH, prefixLeft);
                    }
                    else {
                        if(prefixLeft != null)
                            elementConsumer.prefix(DocumentSide.LEFT_DOCUMENT, prefixLeft);
                        if(prefixRight != null)
                            elementConsumer.prefix(DocumentSide.RIGHT_DOCUMENT, prefixRight);
                        equal = false;
                    }
                }
                
                String nameLeft = nodeLeft.getLocalName();
                String nameRight = nodeRight.getLocalName();
                if(nameLeft.equals(nameRight))
                    elementConsumer.localName(DocumentSide.BOTH, nameLeft);
                else {
                    elementConsumer.localName(DocumentSide.LEFT_DOCUMENT, nameLeft);
                    elementConsumer.localName(DocumentSide.RIGHT_DOCUMENT, nameRight);
                    equal = false;
                }

                NodeListDiffConsumer attrsConsumer = elementConsumer.beginAttributes();
                List<Node> attrsLeft = Utils.asList(nodeLeft.getAttributes());
                List<Node> attrsRight = Utils.asList(nodeRight.getAttributes());
                if(!diffNodeList(attrsLeft, attrsRight, options, attrsConsumer))
                    equal = false;
                attrsConsumer.end();
                
                NodeListDiffConsumer childrenConsumer = elementConsumer.beginChildren();
                List<Node> childrenLeft = Utils.asList(nodeLeft.getChildNodes());
                List<Node> childrenRight = Utils.asList(nodeRight.getChildNodes());
                if(!diffNodeList(childrenLeft, childrenRight, options, childrenConsumer))
                    equal = false;
                childrenConsumer.end();
                
                elementConsumer.end();
                break;
            }
            case Node.ATTRIBUTE_NODE: {
                AttributeDiffConsumer attrConsumer = diffConsumer.beginAttribute(DocumentSide.BOTH, (Attr)nodeLeft);
                
                if(!options.ignoreNamespaceURI()) {
                    String nsUriLeft = nodeLeft.getNamespaceURI();
                    String nsUriRight = nodeRight.getNamespaceURI();
                    if(Utils.equal(nsUriLeft, nsUriRight)) {
                        if(nsUriLeft != null)
                            attrConsumer.namespaceURI(DocumentSide.BOTH, nsUriLeft);
                    }
                    else {
                        if(nsUriLeft != null)
                            attrConsumer.namespaceURI(DocumentSide.LEFT_DOCUMENT, nsUriLeft);
                        if(nsUriRight != null)
                            attrConsumer.namespaceURI(DocumentSide.RIGHT_DOCUMENT, nsUriRight);
                        equal = false;
                    }
                }
                if(!options.ignorePrefix()) {
                    String prefixLeft = nodeLeft.getPrefix();
                    String prefixRight = nodeRight.getPrefix();
                    if(Utils.equal(prefixLeft, prefixRight)) {
                        if(prefixLeft != null)
                            attrConsumer.prefix(DocumentSide.BOTH, prefixLeft);
                    }
                    else {
                        if(prefixLeft != null)
                            attrConsumer.prefix(DocumentSide.LEFT_DOCUMENT, prefixLeft);
                        if(prefixRight != null)
                            attrConsumer.prefix(DocumentSide.RIGHT_DOCUMENT, prefixRight);
                        equal = false;
                    }
                }
                
                String nameLeft = nodeLeft.getLocalName();
                String nameRight = nodeRight.getLocalName();
                if(nameLeft.equals(nameRight))
                    attrConsumer.localName(DocumentSide.BOTH, nameLeft);
                else {
                    attrConsumer.localName(DocumentSide.LEFT_DOCUMENT, nameLeft);
                    attrConsumer.localName(DocumentSide.RIGHT_DOCUMENT, nameRight);
                    equal = false;
                }

                NodeListDiffConsumer childrenConsumer = attrConsumer.beginChildren();
                List<Node> childrenLeft = Utils.asList(nodeLeft.getChildNodes());
                List<Node> childrenRight = Utils.asList(nodeRight.getChildNodes());
                if(!diffNodeList(childrenLeft, childrenRight, options, childrenConsumer))
                    equal = false;
                childrenConsumer.end();

                attrConsumer.end();
                break;
            }
            case Node.ENTITY_REFERENCE_NODE: {
                diffConsumer.entityReference(DocumentSide.BOTH, (EntityReference)nodeLeft);
                break;
            }
            case Node.TEXT_NODE: {
                TextNodeDiffConsumer textConsumer = diffConsumer.beginText(DocumentSide.BOTH, (Text)nodeLeft);
                
                String valueLeft = nodeLeft.getNodeValue();
                String valueRight = nodeRight.getNodeValue();
                if(valueLeft.equals(valueRight))
                    textConsumer.textValue(DocumentSide.BOTH, valueLeft);
                else {
                    textConsumer.textValue(DocumentSide.LEFT_DOCUMENT, valueLeft);
                    textConsumer.textValue(DocumentSide.RIGHT_DOCUMENT, valueRight);
                    equal = false;
                }
                
                textConsumer.end();
                break;
            }
            case Node.CDATA_SECTION_NODE: {
                CDATASectionDiffConsumer CDATAConsumer = diffConsumer.beginCDATASection(DocumentSide.BOTH, (CDATASection)nodeLeft);
                
                String valueLeft = nodeLeft.getNodeValue();
                String valueRight = nodeRight.getNodeValue();
                if(valueLeft.equals(valueRight))
                    CDATAConsumer.data(DocumentSide.BOTH, valueLeft);
                else {
                    CDATAConsumer.data(DocumentSide.LEFT_DOCUMENT, valueLeft);
                    CDATAConsumer.data(DocumentSide.RIGHT_DOCUMENT, valueRight);
                    equal = false;
                }
                
                CDATAConsumer.end();
                break;
            }
            case Node.COMMENT_NODE: {
                CommentDiffConsumer commentConsumer = diffConsumer.beginComment(DocumentSide.BOTH, (Comment)nodeLeft);
                
                String valueLeft = nodeLeft.getNodeValue();
                String valueRight = nodeRight.getNodeValue();
                if(valueLeft.equals(valueRight))
                    commentConsumer.data(DocumentSide.BOTH, valueLeft);
                else {
                    commentConsumer.data(DocumentSide.LEFT_DOCUMENT, valueLeft);
                    commentConsumer.data(DocumentSide.RIGHT_DOCUMENT, valueRight);
                    equal = false;
                }
                
                commentConsumer.end();
                break;
            }
            case Node.PROCESSING_INSTRUCTION_NODE: {
                ProcessingInstructionDiffConsumer piConsumer = diffConsumer.beginProcessingInstruction(DocumentSide.BOTH, (ProcessingInstruction)nodeLeft);
                
                String valueLeft = nodeLeft.getNodeValue();
                String valueRight = nodeRight.getNodeValue();
                if(valueLeft.equals(valueRight))
                    piConsumer.data(DocumentSide.BOTH, valueLeft);
                else {
                    piConsumer.data(DocumentSide.LEFT_DOCUMENT, valueLeft);
                    piConsumer.data(DocumentSide.RIGHT_DOCUMENT, valueRight);
                    equal = false;
                }
                
                piConsumer.end();
                break;
            }
            case Node.DOCUMENT_TYPE_NODE: {
                // currently, DocumentType nodes are just ignored 
                break;
            }
            default:
                throw new UnsupportedOperationException("Node type #" + nodeLeft.getNodeType() + " not supported!");
        }
        return equal;
    }
    
    /**
     * Finds hierarchical differences between two DOM nodes.
     * @param nodeLeft      the 'left' node
     * @param nodeRight     the 'right' node
     * @param options       comparison options
     * @param diffConsumer  the listener that wil consume the diff information
     * @return {@code true} if the nodes are equal, otherwise {@code false}
     */
    public static boolean diffNodes(Node nodeLeft, Node nodeRight, Options options, SingleNodeDiffConsumer diffConsumer) {
        boolean equal;
        
        NodeListDiffConsumer nodeConsumer = diffConsumer.begin(nodeLeft, nodeRight, options);
        
        NodeSimilarityWrapper wrapperLeft = new NodeSimilarityWrapper(nodeLeft, options);
        NodeSimilarityWrapper wrapperRight = new NodeSimilarityWrapper(nodeRight, options);
        
        if(!wrapperLeft.equals(wrapperRight)) {
            reportNode(nodeLeft, DocumentSide.LEFT_DOCUMENT, options, nodeConsumer);
            reportNode(nodeRight, DocumentSide.RIGHT_DOCUMENT, options, nodeConsumer);
            equal = false;
        }
        else
            equal = diffSimilarNodes(nodeLeft, nodeRight, options, nodeConsumer);

        nodeConsumer.end();
        
        diffConsumer.end();
        
        return equal;
    }

    /**
     * Finds hierarchical differences between two DOM documents.
     * @param docLeft      the 'left' node
     * @param docRight     the 'right' node
     * @param options       comparison options
     * @param diffConsumer  the listener that wil consume the diff information
     * @return {@code true} if the nodes are equal, otherwise {@code false}
     */
    public static boolean diffDocuments(Document docLeft, Document docRight, Options options, JustDocumentDiffConsumer diffConsumer) {
        boolean equal = true;
        
        DocumentDiffConsumer docConsumer = diffConsumer.begin(docLeft, docRight, options);
        
        NodeListDiffConsumer childrenConsumer = docConsumer.beginChildren();
        List<Node> childrenLeft = Utils.asList(docLeft.getChildNodes());
        List<Node> childrenRight = Utils.asList(docRight.getChildNodes());
        if(!diffNodeList(childrenLeft, childrenRight, options, childrenConsumer))
            equal = false;
        childrenConsumer.end();
        
        docConsumer.end();

        diffConsumer.end();
        
        return equal;
    }
    
    /**
     * Finds hierarchical differences between two DOM nodes.
     * This version also calls
     * {@link #preprocess(org.w3c.dom.Node, cz.muni.fi.courses.pb138.j2014.projects.soxc.PreprocessingOptions)}
     * on both documents before comparison.
     * @param nodeLeft      the 'left' node
     * @param nodeRight     the 'right' node
     * @param options       comparison options
     * @param preprocOptions    preprocessing options
     * @param diffConsumer  the listener that wil consume the diff information
     * @return {@code true} if the nodes are equal, otherwise {@code false}
     */
    public static boolean diffNodesPreprocess(Node nodeLeft, Node nodeRight,
            Options options, PreprocessingOptions preprocOptions,
            SingleNodeDiffConsumer diffConsumer) {
        
        preprocess(nodeLeft, preprocOptions);
        preprocess(nodeRight, preprocOptions);
        
        return diffNodes(nodeLeft, nodeRight, options, diffConsumer);
    }
    
    /**
     * Finds hierarchical differences between two DOM documents.
     * This version also calls
     * {@link #preprocess(org.w3c.dom.Node, cz.muni.fi.courses.pb138.j2014.projects.soxc.PreprocessingOptions)}
     * on both documents before comparison.
     * @param docLeft           the 'left' node
     * @param docRight          the 'right' node
     * @param options           comparison options
     * @param preprocOptions    preprocessing options
     * @param diffConsumer      the listener that wil consume the diff information
     * @return {@code true} if the nodes are equal, otherwise {@code false}
     */
    public static boolean diffDocumentsPreprocess(Document docLeft, Document docRight,
            Options options, PreprocessingOptions preprocOptions,
            JustDocumentDiffConsumer diffConsumer) {
        
        preprocess(docLeft, preprocOptions);
        preprocess(docRight, preprocOptions);
        
        return diffDocuments(docLeft, docRight, options, diffConsumer);
    }
}
