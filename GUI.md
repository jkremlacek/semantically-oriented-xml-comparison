# GUI #
Renders JTree structure of DiffTree structure of the XML files provided by SOXC consumer.

## Algorithm description ##

Upon acquiring NodeDiffTree from consumer an instance of GUITreeFactory is created.

Every NodeDiffTree send into factory is checked for its instance than scanned by their instance.

If an ElementDiffTree is caught attribute scan is called because attributes have to be part of element text in TreeNode. After attribute scanning process a new TreeFactory is created and supplied with a list of element child nodes.

After scanning the new TreeNode is appended to its parent. Once all DiffTrees have been scanned both JTrees are put into display frame and the frame is rendered.

## How-to-use ##

After executing application a Selector JFrame window shows.

https://is.muni.cz/auth/www/410131/49250063/SOXC_Selector.png?studium=614144

Additional options can be selected in the right side of the Selector window.

Both pathfiles have to be set, if either is missing an error will show up.

https://is.muni.cz/auth/www/410131/49250063/SOXC_Error.png?studium=614144

Run button executes comparsing algorithm and shows a result:

  * If XML files are equal a simple notification message will show up:
https://is.muni.cz/auth/www/410131/49250063/SOXC_Equal.png?studium=614144
  * If XML are not equal a JTree Display window will show up and highlights with red color any mismatch found:
https://is.muni.cz/auth/www/410131/49250063/SOXC_Display.png?studium=614144-