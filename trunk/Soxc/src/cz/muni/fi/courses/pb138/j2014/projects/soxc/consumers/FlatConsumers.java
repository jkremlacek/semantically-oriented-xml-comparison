/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers;

import cz.muni.fi.courses.pb138.j2014.projects.soxc.DocumentSide;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * Contains utility methods that return adapters for the Flat*DiffConsumer interfaces.
 * @author Ondrej Mosnacek <omosnacek@gmail.com>
 */
public class FlatConsumers {
    
    /** This class is not instantiable. */
    private FlatConsumers() { }
    
    private static abstract class FlatAsGeneral implements NodeListDiffConsumer {
        
        protected final FlatDiffConsumer inner;

        public FlatAsGeneral(FlatDiffConsumer inner) {
            this.inner = inner;
        }

        @Override
        public DocumentDiffConsumer beginDocument(final DocumentSide side, final Document doc) {
            inner.beginDocument(side, doc);
            return new DocumentDiffConsumer() {

                @Override
                public NodeListDiffConsumer beginChildren() {
                    return new ChildrenFlatAsGeneral(inner);
                }

                @Override
                public void end() {
                    inner.endDocument(side, doc);
                }
            };
        }
        
        @Override
        public final void entityReference(DocumentSide side, EntityReference entityRef) {
            inner.entityReference(side, entityRef);
        }

        @Override
        public final ElementDiffConsumer beginElement(final DocumentSide side, final Element element) {
            inner.beginElement(side, element);
            return new ElementDiffConsumer() {

                @Override
                public NodeListDiffConsumer beginChildren() {
                    return new ChildrenFlatAsGeneral(inner);
                }

                @Override
                public NodeListDiffConsumer beginAttributes() {
                    return new AttributesFlatAsGeneral(inner);
                }

                @Override
                public void end() {
                    inner.endElement(side, element);
                }
            };
        }

        @Override
        public final AttributeDiffConsumer beginAttribute(final DocumentSide side, final Attr attr) {
            inner.beginAttribute(side, attr);
            return new AttributeDiffConsumer() {

                @Override
                public NodeListDiffConsumer beginChildren() {
                    return new ChildrenFlatAsGeneral(inner);
                }

                @Override
                public void end() {
                    inner.endAttribute(side, attr);
                }
            };
        }

        @Override
        public final TextNodeDiffConsumer beginText(final DocumentSide side, final Text text) {
            inner.beginText(side, text);
            return new TextNodeDiffConsumer() {

                @Override
                public void textValue(DocumentSide side, String value) {
                    inner.textValue(side, value);
                }

                @Override
                public void end() {
                    inner.endText(side, text);
                }
            };
        }

        @Override
        public CDATASectionDiffConsumer beginCDATASection(final DocumentSide side, final CDATASection cdata) {
            inner.beginCDATASection(side, cdata);
            return new CDATASectionDiffConsumer() {

                @Override
                public void data(DocumentSide side, String data) {
                    inner.CDATASectionData(side, data);
                }

                @Override
                public void end() {
                    inner.endCDATASection(side, cdata);
                }
            };
        }

        @Override
        public ProcessingInstructionDiffConsumer beginProcessingInstruction(final DocumentSide side, final ProcessingInstruction pi) {
            inner.beginProcessingInstruction(side, pi);
            return new ProcessingInstructionDiffConsumer() {

                @Override
                public void data(DocumentSide side, String data) {
                    inner.processingInstructionData(side, data);
                }

                @Override
                public void end() {
                    inner.endProcessingInstruction(side, pi);
                }
            };
        }

        @Override
        public CommentDiffConsumer beginComment(final DocumentSide side, final Comment comment) {
            inner.beginComment(side, comment);
            return new CommentDiffConsumer() {

                @Override
                public void data(DocumentSide side, String data) {
                    inner.commentData(side, data);
                }

                @Override
                public void end() {
                    inner.endComment(side, comment);
                }
            };
        }
    }
    
    private static final class ChildrenFlatAsGeneral extends FlatAsGeneral {

        public ChildrenFlatAsGeneral(FlatDiffConsumer inner) {
            super(inner);
            
            inner.beginChildren();
        }

        @Override
        public void end() {
            inner.endChildren();
        }
    }
    private static final class AttributesFlatAsGeneral extends FlatAsGeneral {
        
        public AttributesFlatAsGeneral(FlatDiffConsumer inner) {
            super(inner);
            
            inner.beginAttributes();
        }

        @Override
        public void end() {
            inner.endAttributes();
        }
    }
    private static final class AnyNodeFlatAsGeneral extends FlatAsGeneral {
        
        public AnyNodeFlatAsGeneral(FlatDiffConsumer inner) {
            super(inner);
        }

        @Override
        public void end() {
        }
    }

    /**
     * Returns a {@link SingleNodeDiffConsumer} adapter for the given
     * {@link FlatSingleNodeDiffConsumer}.
     * @param inner the {@link FlatSingleNodeDiffConsumer} to adapt
     * @return
     */
    public static SingleNodeDiffConsumer toGeneral(final FlatSingleNodeDiffConsumer inner) {
        return new SingleNodeDiffConsumer() {

            @Override
            public NodeListDiffConsumer begin(Node nodeLeft, Node nodeRight, Options options) {
                inner.begin(nodeLeft, nodeRight, options);
                return new AnyNodeFlatAsGeneral(inner);
            }

            @Override
            public void end() {
                inner.end();
            }
        };
    }
    
    /**
     * Returns a {@link JustDocumentDiffConsumer} adapter for the given
     * {@link FlatJustDocumentDiffConsumer}.
     * @param inner the {@link FlatJustDocumentDiffConsumer} to adapt
     * @return
     */
    public static JustDocumentDiffConsumer toGeneral(final FlatJustDocumentDiffConsumer inner) {
        return new JustDocumentDiffConsumer() {

            @Override
            public DocumentDiffConsumer begin(Document docLeft, Document docRight, Options options) {
                inner.begin(docLeft, docRight, options);
                return new DocumentDiffConsumer() {

                    @Override
                    public NodeListDiffConsumer beginChildren() {
                        return new ChildrenFlatAsGeneral(inner);
                    }

                    @Override
                    public void end() { }
                };
            }

            @Override
            public void end() {
                inner.end();
            }
        };
    }
}
