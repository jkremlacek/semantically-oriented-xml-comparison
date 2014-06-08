/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.xmldiff;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Soxc;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatConsumers;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatJustDocumentDiffConsumer;
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
        FlatJustDocumentDiffConsumer consumer;
        
        Options.Builder optBuilder = new Options.Builder();
        
        
        String[] tmp = parseCommandLine(args, optBuilder);
        documentLeftURI = tmp[0];
        documentRightURI = tmp[1];
        /*
            according to the options the consumer will be either a TextOutputDiffConsumer
            or an XmlOutputDiffConsumer (linked to a user-specified output file)
        */
        consumer = null;
        // ...
        
        Options options = optBuilder.buildOptions();
        
        // Create the document builder factory:
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        // Turn on namespace awareness:
        factory.setNamespaceAware(true);
        
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
        boolean result = Soxc.diffDocuments(docL, docR, options, FlatConsumers.toGeneral(consumer));
        
        System.exit(result ? 0 : 1);
    }
    
    
    
    /**
     * The function for parsing command-line arguments
     * @param args the command line arguments
     * @param optBuilder the optBuilder to set otions
     * @return 2 Strings, left and right documents URI
     */
    private static String[] parseCommandLine(String[] args, Options.Builder optBuilder){
        String[] retval = new String[]{"",""};
        int i=1;
        String arg;
        char flag;
        while(i < args.length && args[i].startsWith("-")){
            arg = args[i++];
            //full arguments
            switch (arg) {
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
                default:
                    //flags argumensts
                    for (int j = 1; j < arg.length(); j++) {
                        flag = arg.charAt(j);
                        switch (flag) {
                            case 'p':
                                optBuilder.setIgnorePrefix(true);
                                break;
                            case 'u':
                                optBuilder.setIgnoreNamespaceURI(true);
                                break;
                            default:
                                System.err.println("illegal option " + flag);
                                break;
                        }
                    }  
                break;
            }
        } 
        if(i == args.length){
            System.err.println("ERROR: missing left document URI");
        } else {
            i++;
            retval[0] = args[i];
        }
        if(i == args.length){
            System.err.println("ERROR: missing right document URI");
        } else {
            i++;
            retval[1] = args[i];
        }
        return retval;
    }
    
    
}


