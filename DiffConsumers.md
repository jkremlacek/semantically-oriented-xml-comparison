# DiffConsumers #

DiffConsumers (or simply 'consumers') are interfaces one needs to implement in order to receive comparison events from the XML comparison methods. The main consumers are used hierarchically - some consumers must return another (child) consumer (which will process certain group of events) upon call of a `begin*(...)` method. The `end()` method is called on this new consumer after the group of events is completed. The next method called will be a method of the parent consumer. To facilitate some implementations that don't need to track the hierarchy in this way, "flat" versions of root consumers are provided (see below).

## Flat Consumers ##

Flat consumers differ from hierarchical consumers in that all relevant methods are within a single interface and the `begin*(...)` methods return `void`. These consumers may be easier to implement in some cases. The class `cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.FlatConsumers` provides methods to convert a flat consumer into a normal hierarchical consumer.

## Root Consumers ##

The SOXC library has two so called 'root' consumers which are passed directly to the comparison methods - `JustDocumentDiffConsumer` and `SingleNodeDiffConsumer` (for comparing documents and nodes resp.).

## Order of method calls ##

There are certain restrictions on the order and number of method calls on consumers (per-instance). These are specified in the Javadoc for each (main) consumer (and its methods). Some explanations:
  * **called just once for each side** - such method usually reports a required property of a DOM node; it can be called either once (with `side == DocumentSide.BOTH`) or twice (once with `side == DocumentSide.LEFT_DOCUMENT` and once with `side == DocumentSide.RIGHT_DOCUMENT` - in no particular order).
  * **called at most once for each side** - such method usually reports an optional property of a DOM node; unlike previous type, it doesn't have to specify value for one or both sides
  * **this is a sequential comparison method** - this method can be called any number of times together with other methods in the same consumer.

## Built-in consumer implementations ##

The library comes with two built-in consumer implementations:

### `TextOutputDiffConsumer` ###
(package `cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.textoutput`)
Prints a simple human-readable dump of the comparison to a `java.io.PrintStream` (`System.out` by default).

Example output:

```
Comparison of documents 'xml1.xml' and 'xml2.xml'
--------------------
[BOTH]  DOCUMENT
[BOTH]    Children:
[BOTH]      ELEMENT
[BOTH]        Local name: a
[BOTH]        Attributes:
[BOTH]          ATTRIBUTE
[BOTH]            Local name: a
[BOTH]            Children:
[BOTH]              TEXT
[LEFT]                Data: b
[RIGHT]               Data: c
[BOTH]          ATTRIBUTE
[BOTH]            Local name: x
[BOTH]            Children:
[BOTH]              TEXT
[BOTH]                Data: y
[BOTH]        Children:
DONE.
```

### `XmlOutputDiffConsumer` ###
(package `cz.muni.fi.courses.pb138.j2014.projects.soxc.consumers.xmloutput`)
Writes the whole comparison data into a `java.io.Writer` in the [XDiff](XDiffFormat.md) XML format.

Example output (with indentation):
```
<?xml version="1.0" ?>
<xdiff:xdiff xmlns:xdiff="http://www.fi.muni.cz/courses/PB138/j2014/projects/soxc/xdiff">
    <xdiff:document side="both">
        <xdiff:children>
            <xdiff:element side="both">
                <xdiff:localName side="both">a</xdiff:localName>
                <xdiff:attributes>
                    <xdiff:attribute side="both">
                        <xdiff:localName side="both">a</xdiff:localName>
                        <xdiff:children>
                            <xdiff:text side="both">
                                <xdiff:data side="left">b</xdiff:data>
                                <xdiff:data side="right">c</xdiff:data>
                            </xdiff:text>
                        </xdiff:children>
                    </xdiff:attribute>
                    <xdiff:attribute side="both">
                        <xdiff:localName side="both">x</xdiff:localName>
                        <xdiff:children>
                            <xdiff:text side="both">
                                <xdiff:data side="both">y</xdiff:data>
                            </xdiff:text>
                        </xdiff:children>
                    </xdiff:attribute>
                </xdiff:attributes>
                <xdiff:children></xdiff:children>
           </xdiff:element>
       </xdiff:children>
    </xdiff:document>
</xdiff:xdiff>
```