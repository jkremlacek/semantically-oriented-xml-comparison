/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Some tests for the Node*Wrapper classes.
 * 
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public class NodeWrappersTests {
    
    private DocumentBuilder docBuilder;

    @Before
    public void setUp() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        docBuilder = factory.newDocumentBuilder();
    }
    
    public Element loadNode(String text) throws SAXException, IOException {
        return docBuilder.parse(new InputSource(new StringReader(text))).getDocumentElement();
    }
    
    public void doTest(String node1, String node2, Options options, boolean similar, boolean equal) throws SAXException, IOException {
        NodeEqualityWrapper eq1 = new NodeEqualityWrapper(loadNode(node1), options);
        NodeEqualityWrapper eq2 = new NodeEqualityWrapper(loadNode(node2), options);
        
        if(equal)
            Assert.assertTrue(eq1.equals(eq2));
        else
            Assert.assertFalse(eq1.equals(eq2));

        NodeSimilarityWrapper sim1 = new NodeSimilarityWrapper(loadNode(node1), options);
        NodeSimilarityWrapper sim2 = new NodeSimilarityWrapper(loadNode(node2), options);
        
        if(similar)
            Assert.assertTrue(sim1.equals(sim2));
        else
            Assert.assertFalse(sim1.equals(sim2));
    }
    
    private static final Options OPTS_DEFAULT = new Options(false, false, true, false, false);
    private static final Options OPTS_SIM_ATT = new Options(false, false, false, false, false);
    private static final Options OPTS_EL_NO_ORDER = new Options(true, false, true, false, false);
    private static final Options OPTS_IGNORE_PREFIX = new Options(false, false, true, false, true);
    private static final Options OPTS_IGNORE_PREFIX_AND_NS = new Options(false, false, true, true, true);
    private static final Options OPTS_IGNORE_SIM_EL_NAME = new Options(false, true, true, false, false);
    
    @Test
    public void testEmptyElementVariants() throws Exception {
        doTest("<element></element>", "<element/>", OPTS_DEFAULT, true, true);
    }
    
    @Test
    public void testAttributePresenceEffectOnSimilarity() throws Exception {
        doTest("<element id=\"1\"></element>", "<element/>", OPTS_DEFAULT, true, false);
        doTest("<element id=\"1\"></element>", "<element/>", OPTS_SIM_ATT, false, false);
    }
    
    @Test
    public void testAttributeDifferenceEffectOnSimilarity() throws Exception {
        doTest("<element id=\"1\"></element>", "<element id=\"0\"/>", OPTS_DEFAULT, true, false);
        doTest("<element id=\"1\"></element>", "<element id=\"0\"/>", OPTS_SIM_ATT, false, false);
    }

    @Test
    public void testText() throws Exception {
        doTest("<element>Text</element>", "<element>Text</element>", OPTS_DEFAULT, true, true);
        doTest("<element>Text1</element>", "<element>Text2</element>", OPTS_DEFAULT, true, false);
        doTest("<element>Text1<a/>Text2</element>", "<element>Text1<a/>Text2</element>", OPTS_DEFAULT, true, true);
    }
    
    @Test
    public void testPI() throws Exception {
        doTest("<root><?target Data?></root>", "<root><?target \n   Data?></root>", OPTS_DEFAULT, true, true);
        doTest("<root><?target Data?></root>", "<root><?target \n   OtherData?></root>", OPTS_DEFAULT, true, false);
    }
    
    @Test
    public void testComment() throws Exception {
        doTest("<root><!--Comment--></root>", "<root><!--Comment--></root>", OPTS_DEFAULT, true, true);
        doTest("<root><!--Comment--></root>", "<root><!-- Comment --></root>", OPTS_DEFAULT, true, false);
        doTest("<root><!--Comm--><!--ent--></root>", "<root><!--Comment--></root>", OPTS_DEFAULT, true, false);
        doTest("<root><!--Comm--><!--ent--></root>", "<root><!--Comm--><!--ent--></root>", OPTS_DEFAULT, true, true);
    }
    
    @Test
    public void testOrderOfElements() throws Exception {
        doTest("<root><a/><b/></root>", "<root><a/><b/></root>", OPTS_DEFAULT, true, true);
        doTest("<root><a/><b/></root>", "<root><a/><b/></root>", OPTS_EL_NO_ORDER, true, true);

        doTest("<root><a/><b/></root>", "<root><b/><a/></root>", OPTS_DEFAULT, true, false);
        doTest("<root><a/><b/></root>", "<root><b/><a/></root>", OPTS_EL_NO_ORDER, true, true);

        doTest("<root><a/><b/><b/></root>", "<root><b/><a/><b/></root>", OPTS_EL_NO_ORDER, true, true);
        doTest("<root><a/><b/><b/></root>", "<root><a/><b/><b/></root>", OPTS_EL_NO_ORDER, true, true);

        doTest("<root><a/>x<b/>y</root>", "<root><a/>x<b/>y</root>", OPTS_EL_NO_ORDER, true, true);
        doTest("<root><a/>x<b/>y</root>", "<root><b/>x<a/>y</root>", OPTS_EL_NO_ORDER, true, true);
        doTest("<root><a/>x<b/>y</root>", "<root><b/>y<a/>x</root>", OPTS_EL_NO_ORDER, true, false);
    }
    
    @Test
    public void testPrefixAndNamespace() throws Exception {
        doTest("<x:root xmlns:x=\"somenamespace\" />", "<y:root xmlns:y=\"somenamespace\" />", OPTS_DEFAULT, false, false);
        doTest("<x:root xmlns:x=\"somenamespace\" />", "<y:root xmlns:y=\"somenamespace\" />", OPTS_IGNORE_PREFIX, true, false);
        doTest("<x:root xmlns:x=\"somenamespace\" />", "<y:root xmlns:y=\"othernamespace\" />", OPTS_IGNORE_PREFIX_AND_NS, true, false);
    }
    
    @Test
    public void testElementNameDifferenceEffectOnSimilarity() throws Exception {
        doTest("<x/>", "<x/>", OPTS_DEFAULT, true, true);
        doTest("<x/>", "<x/>", OPTS_IGNORE_SIM_EL_NAME, true, true);
        
        doTest("<x/>", "<y/>", OPTS_DEFAULT, false, false);
        doTest("<x/>", "<y/>", OPTS_IGNORE_SIM_EL_NAME, true, false);
    }
}
