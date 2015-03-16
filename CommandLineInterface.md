# Command line interface #

Command line interface is accessible as SoxcCmdInterface jar file. It requires three mandatory attributes. First two are URIs of XMLs to compare. The third is output mode. After those, you can add optional attributes to configure comparison options.

## Output modes ##
  * "text": outputs to command line
  * output file URI: application will create new XML file with marked differences

## Options ##
All options exist in ignoreOption/useOption variant and must start with "-".

  * ignorePrefix
  * ignoreNamespaceUri
  * ignoreElementOrder
  * ignoreAttributesInSimilarity
  * ignoreElementNameInSimilarity

### Preprocessing options ###
  * ignoreWhiteSpaceOnlyText
  * ignoreTrimWhiteSpaceInText
  * ignoreCDATA
  * ignoreTextData
  * ignoreProcessingInstructions
  * ignoreComments