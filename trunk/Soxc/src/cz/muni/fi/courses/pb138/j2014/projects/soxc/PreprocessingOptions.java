/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc;

/**
 * Represents options for preprocessing XML before comparison. This is an immutable class.
 * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
 */
public class PreprocessingOptions {
    
    private final boolean ignoreText;
    private final boolean ignoreWhitespaceOnlyText;
    private final boolean trimWhitespaceInText;

    /**
     * Whether to ignore text nodes. Default: {@code false}.
     * @return
     */
    public final boolean ignoreText() {
        return ignoreText;
    }
    
    /**
     * Whether to ignore whitespace-only text nodes. Default: {@code true}.
     * @return
     */
    public final boolean ignoreWhitespaceOnlyText() {
        return ignoreWhitespaceOnlyText;
    }
    
    /**
     * Whether to trim whitespace inside text nodes. Default: {@code false}.
     * @return
     */
    public final boolean trimWhitespaceInText() {
        return trimWhitespaceInText;
    }

    /**
     * Constructs default options.
     */
    public PreprocessingOptions() {
        this(false, true, false);
    }
    
    /**
     * Constructs new options.
     * @param ignoreText
     * @param ignoreWhitespaceOnlyText
     * @param trimWhitespaceInText
     */
    public PreprocessingOptions(boolean ignoreText,
            boolean ignoreWhitespaceOnlyText,
            boolean trimWhitespaceInText) {
        this.ignoreText = ignoreText;
        this.ignoreWhitespaceOnlyText = ignoreWhitespaceOnlyText;
        this.trimWhitespaceInText = trimWhitespaceInText;
    }
    
    /**
     * A mutable class that can be used to build instances of {@link PreprocessingOptions}.
     * @author Ondrej Mosnacek &lt;omosnacek@gmail.com&gt;
     */
    public static class Builder {
        
        private final boolean[] flags = new boolean[] {
            false, true, false
        };
        
        /**
         * Gets a value indicating wheter to ignore prefix.
         * Default: {@code false}.
         * @return
         */
        public final boolean getIgnoreText() {
            return flags[0];
        }

        /**
         * Sets a value indicating wheter to ignore prefix.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setIgnoreText(boolean value) {
            flags[0] = value;
        }
        
       /**
         * Gets a value indicating wheter to ignore prefix.
         * Default: {@code true}.
         * @return
         */
        public final boolean getIgnoreWhitespaceOnlyText() {
            return flags[1];
        }

        /**
         * Sets a value indicating wheter to ignore prefix.
         * Default: {@code true}.
         * @param value the new value
         */
        public final void setIgnoreWhitespaceOnlyText(boolean value) {
            flags[1] = value;
        }
        
       /**
         * Gets a value indicating wheter to ignore prefix.
         * Default: {@code false}.
         * @return
         */
        public final boolean getTrimWhitespaceInText() {
            return flags[2];
        }

        /**
         * Sets a value indicating wheter to ignore prefix.
         * Default: {@code false}.
         * @param value the new value
         */
        public final void setTrimWhitespaceInText(boolean value) {
            flags[2] = value;
        }
        
        /**
         * Builds an instance of the {@link PreprocessingOptions} class from
         * currently set options.
         * @return the new {@link PreprocessingOptions} instance
         */
        public final PreprocessingOptions buildOptions() {
            return new PreprocessingOptions(flags[0], flags[1], flags[2]);
        }
    }
}
