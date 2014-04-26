/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLComparator;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Jakub Kremlacek
 * @author Matej Minarik
 * @author Ondrej Mosnacek
 * @author Ondrej Sobocik
 */
public class XMLComp {
    
    private Document XMLDoc1;
    private Document XMLDoc2;

    public XMLComp(String path1, String path2, String[] args) throws SAXException, 
            ParserConfigurationException, IOException {
        File inputFile1 = new File(path1);
        File inputFile2 = new File(path2);
        
        // Vytvorime instanci tovarni tridy
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
        
        // Pomoci tovarni tridy ziskame instanci DocumentBuilderu
        DocumentBuilder builder1 = factory1.newDocumentBuilder();
        DocumentBuilder builder2 = factory2.newDocumentBuilder();
        
        // DocumentBuilder pouzijeme pro zpracovani XML dokumentu
        // a ziskame model dokumentu ve formatu W3C DOM
        XMLDoc1 = builder1.parse((inputFile1.toURI()).toString());
        XMLDoc2 = builder2.parse((inputFile2.toURI()).toString());
    }
    
   
    
    
    public void compareXMLFiles(String[] args) {
        //spusteni porovnavaciho procesu
        
        /*
         * nakouskovani a napumpovani argumentu z @args do listu a
         * nasledne spusteni dodatecnych procedur, ktere budou vyzadovany
         */
    }
    
}
