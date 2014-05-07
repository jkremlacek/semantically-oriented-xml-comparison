/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.xmldiff;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.GeneralDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Soxc;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Main class of the XML diff command-line tool.
 * @author Jakub Kremlacek
 * @author Matej Minarik
 * @author Ondrej Mosnacek
 * @author Ondrej Sobocik
 */
public class XmlDiff {

    /**
     * The main method.
     * <p>
     * <b>Exit code meaning:</b>
     * <ul>
     *      <li>0 - success, documents are equal</li>
     *      <li>1 - success, documents are different</li>
     *      <li>2 - failure</li>
     * </ul>
     * </p>
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String documentLeftURI;
        String documentRightURI;
        GeneralDiffConsumer consumer;
        
        Options.Builder optBuilder = new Options.Builder();
        
        // TODO parse the command-line arguments
        documentLeftURI = null;
        documentRightURI = null;
        /*
            according to the options the consumer will be either a ConsoleOutputDiffConsumer
            or an XmlOutputDiffConsumer (linked to a user-specified output file)
        */
        consumer = null;
        // ...
        
        Options options = optBuilder.buildOptions();
        
        // Create the document builder factory:
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        // Get the DocumentBuilder instance from the factory:
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            System.err.println("FATAL: Unable to create DocumentBuilder instance:");
            System.err.println(ex);
            System.exit(2);
            return;
        }
        
        // Load the documents into a DOM tree:
        Document docL, docR;
        try {
            docL = builder.parse(documentLeftURI);
        } catch (SAXException | IOException ex) {
            System.err.println("ERROR: Unable to load the left document:");
            System.err.println(ex);
            System.exit(2);
            return;
        }
        try {
            docR = builder.parse(documentRightURI);
        } catch (SAXException | IOException ex) {
            System.err.println("ERROR: Unable to load the right document:");
            System.err.println(ex);
            System.exit(2);
            return;
        }
        
        // Compare the documents:
        boolean result = Soxc.diffNodes(docL, docR, options, null);
        
        System.exit(result ? 0 : 1);
    }
    
}
