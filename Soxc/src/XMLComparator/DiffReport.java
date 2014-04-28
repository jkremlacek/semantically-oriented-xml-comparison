/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLComparator;

import org.w3c.dom.Node;

/**
 *
 * @author Jakub Kremláček
 */
public class DiffReport {
    private String message;
    private Node node;
    
    public DiffReport(String message, Node node) {
        this.message = message;
        this.node = node;
    }
    
}
