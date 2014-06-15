/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.xmldiff;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.PreprocessingOptions;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Soxc;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatConsumers;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatJustDocumentDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.textoutput.TextOutputDiffConsumer;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.xmloutput.XmlOutputDiffConsumer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Main class of the XML diff command-line tool.
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
        FlatJustDocumentDiffConsumer consumer = null;
        File firstDocument = new File(args[0]);
        File secondDocument = new File(args[1]);
        
        if(!firstDocument.exists()){
            System.err.println("Missing first document URI.");
            System.exit(2);
            
        }
        if(!secondDocument.exists()){
            System.err.println("Missing second document URI");
            System.exit(2);
        }             
        
        if(args[2].startsWith("-")){
            System.err.println("Third argument must be output method.");
            System.exit(2);
        }
        if(args[2]=="text"){
            consumer = new TextOutputDiffConsumer();
        }else{
            try {
                consumer = new XmlOutputDiffConsumer(new FileWriter(new File(args[2])));
            } catch (Exception ex) {
                System.err.println("Couldn't create output file "+args[2]);
            }
        }
        
        Options.Builder optBuilder = new Options.Builder();
        PreprocessingOptions.Builder preBuilder = new PreprocessingOptions.Builder();
        
        // Create the document builder factory:
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Turn on namespace awareness:
        factory.setNamespaceAware(true);
        
        parseCommandLine(args, optBuilder, preBuilder, factory);
        
        /*
            according to the options the consumer will be either a TextOutputDiffConsumer
            or an XmlOutputDiffConsumer (linked to a user-specified output file)
            for now cmd output
        */
        //consumer = new TextOutputDiffConsumer();
        // ...
        
        Options options = optBuilder.buildOptions();
        PreprocessingOptions preproOptions = preBuilder.buildOptions();
        
           
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
        Document docL = null;
        Document docR = null;
        try {
            docL = builder.parse(firstDocument);
        } catch (SAXException | IOException ex) {
            System.err.println("ERROR: Unable to load the left document:");
            System.err.println(ex);
            System.exit(2);
        }
        try {
            docR = builder.parse(secondDocument);
        } catch (SAXException | IOException ex) {
            System.err.println("ERROR: Unable to load the right document:");
            System.err.println(ex);
            System.exit(2);
        }
        
        // Compare the documents:
        boolean result = Soxc.diffDocumentsPreprocess(docL, docR, options, preproOptions, FlatConsumers.toGeneral(consumer));
        
        System.exit(result ? 0 : 1);
    }
    
    
    
    /**
     * The function for parsing command-line arguments
     * @param args the command line arguments
     * @param optBuilder the optBuilder to set otions
     */
    private static void parseCommandLine(String[] args, Options.Builder optBuilder,
            PreprocessingOptions.Builder preBuilder, DocumentBuilderFactory factory){
        int i=3;
        while(i < args.length && args[i].startsWith("-")){          
            //full arguments
            switch (args[i]) {
                case "-ignorePrefix":
                    optBuilder.setIgnorePrefix(true);
                    break;
                case "-usePrefix":
                    optBuilder.setIgnorePrefix(false);
                    break;
                case "-ignoreNamespaceURI":
                    optBuilder.setIgnoreNamespaceURI(true);
                    break;
                case "-useNamespaceURI":
                    optBuilder.setIgnoreNamespaceURI(false);
                    break;
                case "-ignoreElementOrder":
                    optBuilder.setIgnoreElementOrder(true);
                    break;
                case "-useElementOrder":
                    optBuilder.setIgnoreElementOrder(false);
                    break;
                case "-ignoreAttributesInSimilarity":
                    optBuilder.setIgnoreAttributesInSimilarity(true);
                    break;
                case "-useAttributesInSimilarity":
                    optBuilder.setIgnoreAttributesInSimilarity(false);
                    break;
                case "-ignoreElementNameInSimilarity":
                    optBuilder.setIgnoreElementNameInSimilarity(true);
                    break;
                case "-useElementNameInSimilarity":
                    optBuilder.setIgnoreElementNameInSimilarity(false);
                    break;
                //preprocesing options
                case "-ignoreWhiteSpaceOnlyText":
                    preBuilder.setIgnoreWhitespaceOnlyText(true);
                    break;
                case "-useWhiteSpaceOnlyText":
                    preBuilder.setIgnoreWhitespaceOnlyText(true);
                    break;
                case "-trimWhiteSpaceInText":
                    preBuilder.setTrimWhitespaceInText(true);
                    break;
                case "-doNotTrimWhiteSpaceInText":
                    preBuilder.setTrimWhitespaceInText(false);
                    break;    
                case "-ignoreCDATA":
                    preBuilder.setIgnoreCDATA(true);
                    break;
                case "-useCDATA":
                    preBuilder.setIgnoreCDATA(false);
                    break;
                case "-ignoreTextData":
                    preBuilder.setIgnoreText(true);
                    break;
                case "-useTextData":
                    preBuilder.setIgnoreText(false);
                    break;
                case "-ignoreProcessingInstructions":
                    preBuilder.setIgnoreProcessingInstructions(true);
                    break;
                case "-useProcessingInstructions":
                    preBuilder.setIgnoreProcessingInstructions(false);
                    break;
                case "-ignoreComments":
                    factory.setIgnoringComments(true);
                    break;
                default:                    
                    System.err.println("illegal option " + args[i]);
                    break;                        
            }  
            i++;             
        }
    } 
}  
    
    



