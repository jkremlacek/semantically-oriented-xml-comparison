/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.courses.pb138.j2014.projects.soxc;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.difftree.consumers.JustDocumentDiffTreeConsumer;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author sob
 */
public class ComplexXMLTests {
    
    DocumentBuilder builder;
    JustDocumentDiffTreeConsumer consumer;
    Options falseOptions;
    
    public ComplexXMLTests() {
    }

    @Before
    public void setUp() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringComments(true);
        builder = factory.newDocumentBuilder();
        consumer = new JustDocumentDiffTreeConsumer();
        falseOptions = new Options(false,false,false,false,false);
    }
    
    @Test
    public void testSameXML() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/model.xml"));
        Document doc2 = builder.parse(new File("../testdata/model.xml"));      
        
        assertTrue(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
    }
    
    @Test
    public void testDiffAttributesOrder() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/model.xml"));
        Document doc2 = builder.parse(new File("../testdata/difAttributeOrder.xml"));
        
        assertTrue(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
    }
    
    @Test
    public void testDiffAttributesValues() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/model.xml"));
        Document doc2 = builder.parse(new File("../testdata/difAttributeValues.xml"));
        
        //System.out.println(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        //System.out.println(Soxc.diffDocuments(doc1, doc2, new Options(false, false, true, false, false), consumer));
        
        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        //option IgnoreAttributesInSimilarity = true
        assertTrue(Soxc.diffDocuments(doc1, doc2, new Options(false, false, true, false, false), consumer));
    }
    
    @Test
    public void testDiffElementName() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/difElementNames.xml"));
        Document doc2 = builder.parse(new File("../testdata/model.xml"));
        
        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        //option IgnoreElementName = true
        assertTrue(Soxc.diffDocuments(doc1, doc2, new Options(false, true, false, false, false), consumer));
    }
    
    @Test
    public void testDiffElementOrder() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/difElementOrder.xml"));
        Document doc2 = builder.parse(new File("../testdata/model.xml"));

        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        // ignoreElementOrder = true
        assertTrue(Soxc.diffDocuments(doc1, doc2, new Options(true, false, false, false, false), consumer));
    }
    
    @Test
    public void testDiffElementValues() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/difElementsValues.xml"));
        Document doc2 = builder.parse(new File("../testdata/model.xml"));

        assertFalse(Soxc.diffDocumentsPreprocess(doc1, doc2, falseOptions, 
                new PreprocessingOptions(false, false, false, true, true), consumer));
        //ignore textNodes
        assertTrue(Soxc.diffDocumentsPreprocess(doc1, doc2, falseOptions, 
                new PreprocessingOptions(true, false, false, true, true), consumer));    
    }
       
    @Test
    public void testEmptyTag() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/emptyTag.xml"));
        Document doc2 = builder.parse(new File("../testdata/model.xml"));
        
        assertTrue(Soxc.diffDocumentsPreprocess(doc1, doc2, falseOptions,
                new PreprocessingOptions(false, true, false, true, true), consumer));
    }
    
    @Test
    public void testExtraAndMissingNodes() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/extraAttribute.xml"));
        Document doc2 = builder.parse(new File("../testdata/model.xml"));        
        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        
        doc1 = builder.parse(new File("../testdata/extraChildElement.xml"));
        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        
        doc1 = builder.parse(new File("../testdata/missingAttribute.xml"));
        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        
        doc1 = builder.parse(new File("../testdata/missingChildElement.xml"));
        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
    }
    
    @Test
    public void testNamespaceAndPrefix() throws SAXException, IOException{
        Document doc1 = builder.parse(new File("../testdata/withPrefix.xml"));
        Document doc2 = builder.parse(new File("../testdata/model.xml"));  
        
        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        //ignorePrefix
        assertTrue(Soxc.diffDocuments(doc1, doc2, new Options(false, false, false, false, true), consumer));
        
        doc1 = builder.parse(new File("../testdata/difNamespace.xml"));
        assertFalse(Soxc.diffDocuments(doc1, doc2, falseOptions, consumer));
        //ignoreNamespace
        assertTrue(Soxc.diffDocuments(doc1, doc2, new Options(false, false, false, true, false), consumer));
    }
    
}
