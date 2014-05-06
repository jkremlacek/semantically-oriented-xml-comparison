/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

/**
 * Represents options for XML comparison.
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public final class Options {
    
    private final boolean ignoreElementOrder;
    private final boolean ignoreAttributeOrder;
    private final boolean ignoreAttributesInSimilarity;
    private final boolean ignoreNamespaceURI;
    private final boolean ignorePrefix;

    /**
     * Wheter to ignore element order. Default: {@code false}.
     * @return
     */
    public final boolean ignoreElementOrder() {
        return ignoreElementOrder;
    }

    /**
     * Wheter to ignore attribute order. Default: {@code true}.
     * @return
     */
    public final boolean ignoreAttributeOrder() {
        return ignoreAttributeOrder;
    }
    
    /**
     * Wheter to ignore attributes when comparing similarity. Default: {@code true}.
     * @return
     */
    public final boolean ignoreAttributesInSimilarity() {
        return ignoreAttributesInSimilarity;
    }
    
    /**
     * Wheter to ignore namespace URI. Default: {@code false}.
     * @return
     */
    public final boolean ignoreNamespaceURI() {
        return ignoreNamespaceURI;
    }

    /**
     * Wheter to ignore prefix. Default: {@code false}.
     * @return
     */
    public final boolean ignorePrefix() {
        return ignorePrefix;
    }
    
    /* TODO:
    
    ignoreAttributes [false]
    ignoreTextNodes [false]
    ignoreCDATASections [false]
    ignoreComments [false]
    ignoreProcessingIntructions [false]
    ignoreEntityReferences [false]
        ignoreEntityReferenceName [false]
        ignoreEntityReferenceContents [false]
    ignoreEntities [true]
    ignoreDocumentTypeDefinition [true]
    ignorePreamble [true]
        ignoreXmlVersion [true]
        ignoreXmlEncoding [true]
    */
    
    /**
     * Contructs default options.
     */
    public Options() {
        this(false, true, true, false, false);
    }

    /**
     * Constructs new options.
     * @param ignoreElementOrder
     * @param ignoreAttributeOrder
     * @param ignoreAttributesInSimilarity
     * @param ignoreNamespaceURI
     * @param ignorePrefix 
     */
    public Options(boolean ignoreElementOrder,
            boolean ignoreAttributeOrder,
            boolean ignoreAttributesInSimilarity,
            boolean ignoreNamespaceURI,
            boolean ignorePrefix) {
        this.ignoreElementOrder = ignoreElementOrder;
        this.ignoreAttributeOrder = ignoreAttributeOrder;
        this.ignoreAttributesInSimilarity = ignoreAttributesInSimilarity;
        this.ignoreNamespaceURI = ignoreNamespaceURI;
        this.ignorePrefix = ignorePrefix;
    }
    
    /**
     * A helper mutable class to facilitate creating {@link Options} instances.
     */
    public static class Builder {
        
        private final boolean[] flags = new boolean[] {
            false, true, true, false, false
        };
        
        /**
         * Gets a value indicating wheter to ignore element order.
         * Default: {@code false}.
         * @return
         */
        public final boolean getIgnoreElementOrder() {
            return flags[0];
        }
        
        /**
         * Sets a value indicating wheter to ignore element order.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnoreElementOrder(boolean value) {
            flags[0] = value;
        }

        /**
         * Gets a value indicating wheter to ignore attribute order.
         * Default: {@code true}.
         * @return
         */
        public final boolean getIgnoreAttributeOrder() {
            return flags[1];
        }

        /**
         * Sets a value indicating wheter to ignore attribute order.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnoreAttributeOrder(boolean value) {
            flags[1] = value;
        }

        /**
         * Gets a value indicating wheter to ignore attributes when comparing similarity.
         * Default: {@code true}.
         * @return
         */
        public final boolean getIgnoreAttributesInSimilarity() {
            return flags[2];
        }

        /**
         * Sets a value indicating wheter to ignore attributes when comparing similarity.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnoreAttributesInSimilarity(boolean value) {
            flags[2] = value;
        }

        /**
         * Gets a value indicating wheter to ignore namespace URI.
         * Default: {@code false}.
         * @return
         */
        public final boolean getIgnoreNamespaceURI() {
            return flags[3];
        }

        /**
         * Sets a value indicating wheter to ignore namespace URI.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnoreNamespaceURI(boolean value) {
            flags[3] = value;
        }

        /**
         * Gets a value indicating wheter to ignore prefix.
         * Default: {@code false}.
         * @return
         */
        public final boolean getIgnorePrefix() {
            return flags[4];
        }

        /**
         * Sets a value indicating wheter to ignore prefix.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnorePrefix(boolean value) {
            flags[4] = value;
        }
        
        public final Options buildOptions() {
            return new Options(flags[0], flags[1], flags[2], flags[3], flags[4]);
        }
    }
}
