/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

/**
 * Represents a consumer of elements.
 * 
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
interface Consumer<T> {
    
    /**
     * Feeds a next element to the consumer.
     * 
     * @param element the element to feed to the consumer
     */
    public void feed(T element);
}
