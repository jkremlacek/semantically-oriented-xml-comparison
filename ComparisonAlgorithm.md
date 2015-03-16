# Comparison Algorithm #

The Soxc library compares two XML DOM trees hierarchically and reports all differences to a consumer interface.

When comparing two node lists, the library looks for **similar** elements (definition of similarity is described in the next section) on both sides - these are then considered equivalent and their contents/properties are compared further. The nodes that do not have a matching similar node in the other document are reported with as 'extra' nodes occurring only in one of the documents (as are all of their contents and properties).

The comparison functions in the library also return a boolean value indicating whether the DOM trees are **equal** or not. The definition of equality is described further on this page.


## Similarity ##

By default, two DOM nodes are **similar** IFF (if and only if) all of the following holds true:

  1. the _node types_ of both nodes are equal
  1. the _namespace URIs_ of both nodes are equal (or both nodes are in no namespace)
  1. the _prefixes_ of both nodes are equal (or both nodes have no prefix)
  1. the _local names_ (or _node nmes_ if nodes are neither elements nor attributes) of both nodes are equal (or both nodes have no node name)

The default behavior can be customized by changing the following comparison options (see the `Options` class in package `cz.muni.fi.courses.pb138.j2014.projects.soxc`):

  * **ignoreNamespaceURI** - if set to `true` condition 2 is ignored
  * **ignorePrefix** - if set to `true, condition 3 is ignored
  * **ignoreAttributesInSimilarity** - if set to `false`, also the attribute lists (if the nodes are elements) of both
  * **ignoreElementNameInSimilarity** - if set to `true, conditions 2,3 and 4 are ignored
nodes must be equal for the nodes to be similar


## Equality ##

Two nodes are **equal** IFF all of the following holds true:

  * they are _similar_
  * their _attribute lists_ are equal (or both nodes are not elements)
  * _lists of their children_ are equal

Furthermore, the equality of two **node lists** (attribute lists or lists of children) is defined as such (let one of the lists be called _right_ and the other _left_):

Let _O<sub>l</sub>_ (_O<sub>r</sub>_) be the collection of nodes from the left (right) list (in the same order as they appear in the document) for which one of the following is true:
  * the node is an element and the **ignoreElementOrder** option is set to `false`
  * the node is neither element nor attribute
Next, let _U<sub>l</sub>_ (_U<sub>r</sub>_) be the collection of nodes from the left (right) list for which one of the following is true:
  * the node is an element and the **ignoreElementOrder** option is set to `true`
  * the node is an attribute
Then the two node lists are equal IFF _O<sub>l</sub>_ is sequentially equal to _O<sub>r</sub>_ (both have the same size and the nodes at each index are equal) and for each node in the set union of _U<sub>l</sub>_ and _U<sub>r</sub>_ (with equality of two nodes as defined above) the number of occurrences in both _U<sub>l</sub>_ and _U<sub>r</sub>_ is the same.