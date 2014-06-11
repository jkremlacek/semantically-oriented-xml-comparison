package cz.muni.fi.courses.pb138.j2014.projects.soxc;

/**
 *
 * @author sob
 */
public class Options {
    private final boolean ignoreElementOrder;
    private final boolean ignoreAttributesInSimilarity;
    private final boolean ignoreNamespaceURI;
    private final boolean ignorePrefix;
    private final boolean ignoreXMLDeclaration;
    private final boolean ignoreElementNames;
    private final boolean ignoreTextData;
    private final boolean trimWhiteSpaces;

    /**
     * Whether to ignore element order. Default: {@code false}.
     * @return
     */
    public final boolean ignoreElementOrder() {
        return ignoreElementOrder;
    }

    /**
     * Whether to ignore attributes when comparing similarity. Default: {@code false}. !!!!!!!!!!! change
     * @return
     */
    public final boolean ignoreAttributesInSimilarity() {
        return ignoreAttributesInSimilarity;
    }
    
    /**
     * Whether to ignore namespace URI. Default: {@code false}.
     * @return
     */
    public final boolean ignoreNamespaceURI() {
        return ignoreNamespaceURI;
    }

    /**
     * Whether to ignore prefix. Default: {@code false}.
     * @return
     */
    public final boolean ignorePrefix() {
        return ignorePrefix;
    }
    
    public final boolean isIgnoreXMLDeclaration() {
        return ignoreXMLDeclaration;
    }
    
    public final boolean isIgnoreElementNames() {
        return ignoreElementNames;
    }
    
    public final boolean isIgnoreTextData() {
        return ignoreTextData;
    }
    
    public final boolean isTrimWhiteSpaces() {
        return trimWhiteSpaces;
    }
    
    /**
     * Constructs default options.
     */
    public Options() {
        this(false, false, false, false, false, false, false, false);
    }

    /**
     * Constructs new options.
     * @param ignoreElementOrder
     * @param ignoreAttributesInSimilarity
     * @param ignoreNamespaceURI
     * @param ignorePrefix 
     * @param ignoreXMLDeclaration
     * @param ignoreElementNames
     * @param ignoreTextData
     * @param trimWhiteSpaces
     */
    public Options(boolean ignoreElementOrder,
            boolean ignoreAttributesInSimilarity,
            boolean ignoreNamespaceURI,
            boolean ignorePrefix,
            boolean ignoreXMLDeclaration,
            boolean ignoreElementNames,
            boolean ignoreTextData,
            boolean trimWhiteSpaces) {
        this.ignoreElementOrder = ignoreElementOrder;
        this.ignoreAttributesInSimilarity = ignoreAttributesInSimilarity;
        this.ignoreNamespaceURI = ignoreNamespaceURI;
        this.ignorePrefix = ignorePrefix;
        this.ignoreXMLDeclaration = ignoreXMLDeclaration;
        this.ignoreElementNames = ignoreElementNames;
        this.ignoreTextData = ignoreTextData;
        this.trimWhiteSpaces = trimWhiteSpaces;
    }
    
    /**
     * A mutable class that can be used to build instances of {@link Options}.
     * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
     */
    public static class Builder {
        
        private final boolean[] flags = new boolean[] {
            false, false, false, false, false, false, false, false
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
         * Gets a value indicating wheter to ignore attributes when comparing similarity.
         * Default: {@code true}.
         * @return
         */
        public final boolean getIgnoreAttributesInSimilarity() {
            return flags[1];
        }

        /**
         * Sets a value indicating wheter to ignore attributes when comparing similarity.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnoreAttributesInSimilarity(boolean value) {
            flags[1] = value;
        }

        /**
         * Gets a value indicating wheter to ignore namespace URI.
         * Default: {@code false}.
         * @return
         */
        public final boolean getIgnoreNamespaceURI() {
            return flags[2];
        }

        /**
         * Sets a value indicating wheter to ignore namespace URI.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnoreNamespaceURI(boolean value) {
            flags[2] = value;
        }

        /**
         * Gets a value indicating wheter to ignore prefix.
         * Default: {@code false}.
         * @return
         */
        public final boolean getIgnorePrefix() {
            return flags[3];
        }

        /**
         * Sets a value indicating wheter to ignore prefix.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnorePrefix(boolean value) {
            flags[3] = value;
        }
        
        public final boolean getIgnoreXMLDeclaration() {
            return flags[4];
        }
        
        public final void setIgnoreXMLDeclaration(boolean value) {
            flags[4] = value;
        }
        
        public final boolean getIgnoreElementNames() {
            return flags[5];
        }
        
        public final void setIgnoreElementNames(boolean value) {
            flags[5] = value;
        }
        
        public final boolean getIgnoreTextData() {
            return flags[6];
        }
        
        public final void setIgnoreTextData(boolean value) {
            flags[6] = value;
        }
            
        public final boolean getTrimWhiteSpaces() {
            return flags[7];
        }
        
        public final void setTrimWhiteSpaces(boolean value) {
            flags[7] = value;
        }
        
        /**
         * Builds an instance of the {@link Options} class from currently set
         * options.
         * @return the new {@link Options} instance
         */
        public final Options buildOptions() {
            return new Options(flags[0], flags[1], flags[2], flags[3], flags[4], flags[5], flags[6], flags[7]);
        }
    }
}
