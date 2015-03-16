# SOXC library #

The SOXC library can be used to structurally compare two XML documents or nodes (supplied as [XML DOM](https://en.wikipedia.org/wiki/Document_Object_Model) nodes). The library reports discovered differences via callback interfaces called DiffConsumers. The comparison process can be customized by specifying the [comparison options](Options.md). The library also provides a method for preprocessing (filtering) a DOM node. This can be customized by specifying the [preprocessing options](PreprocessingOptions.md). Additionally, the library provides a way to capture (materialize into memory) the output of the diff process (represented by the consumer calls) into a tree-like structure called DiffTree. From this structure, it can be repeatedly 'replayed' into multiple DiffConsumers.

The Soxc library can be found in the `trunk/Soxc` directory of the repository (it is a [Netbeans](http://netbeans.org) project).

> **NOTE:** The DOM nodes passed to the library must be created by a `DocumentBuilder` which has `isNamespaceAware()` set to `true` (i. e. `getLocalName()` must not return `null` on elements and attributes).

## Main methods ##
The core element of the library is the `Soxc` class (residing in the '`cz.muni.fi.courses.pb138.j2014.projects.soxc`' package) which contains the following public methods:

```
public static void preprocess(Node node, PreprocessingOptions options);
```
Preprocesses the given `node` using the given `options` (see PreprocessingOptions for more details). Note that this operation will modify the DOM tree in-place.

```
public static boolean diffNodes(Node nodeLeft, Node nodeRight,
                                Options options, SingleNodeDiffConsumer diffConsumer)
```
Compares two nodes using the given `options`, feeding the differences discovered into the given `diffConsumer` (for more information see [Options](Options.md) and DiffConsumers).

```
public static boolean diffDocuments(Document docLeft, Document docRight,
                                    Options options, JustDocumentDiffConsumer diffConsumer)
```
Compares two documents using the given `options`, feeding the differences discovered into the given `diffConsumer` (for more information see [Options](Options.md) and DiffConsumers).

```
public static boolean diffNodesPreprocess(Node nodeLeft, Node nodeRight,
                                          Options options, PreprocessingOptions preprocOptions,
                                          SingleNodeDiffConsumer diffConsumer)
```
Same as `diffNodes` above, but calls `preprocess` on both nodes with the given preprocessing options before comparing.

```
public static boolean diffDocumentsPreprocess(Document docLeft, Document docRight,
                                              Options options, PreprocessingOptions preprocOptions,
                                              JustDocumentDiffConsumer diffConsumer)
```
Same as `diffDocuments` above, but calls `preprocess` on both documents with the given preprocessing options before comparing.
## Example ##
The following code compares two documents with text nodes filtered out, ignoring prefix in comparison and prints the diff information to standard output:
```
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Soxc;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.Options;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.ProcessingOptions;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatConsumers;
import cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.textoutput.TextOutputDiffConsumer;
import org.w3c.dom.Document;

...

Document leftDoc = ...;
Document rightDoc = ...;

// set up preprocessing:
ProcessingOptions.Builder procOptsBuilder = new ProcessingOptions.Builder();
procOptsBuilder.setIgnoreText(true);

// set up comparison options:
Options.Builder optsBuilder = new Options.Builder();
optsBuilder.setIgnorePrefix(true);

// perform the comparison with preprocessing:
Options opts = optsBuilder.buildOptions();
ProcessingOptions procOpts = procOptsBuilder.buildOptions();

boolean equal = Soxc.diffDocuments(leftDoc, rightDoc,
                                   opts, preprocOpts,
                                   FlatConsumers.toGeneral(new TextOutputDiffConsumer()));

if(equal) {
    System.out.println("The documents are equal!");
} else {
    System.out.println("The documents are different!");
}
```