# Project assignment (Czech): #

Cílem tohoto projektu je vytvořit Java knihovnu pro porovnávání XML souborů. Výstupem programu je buďto tvrzení, že xml soubory jsou podobné, anebo rozdílné spolu s výpisem rozdílů. To co se považuje za podobná XML je vaším úkolem definovat. Podobnost xml je zcela jistě relace ekvivalence (xml soubor je sám sobě podobný, jeli podobný , B a B, C musí být i A, C). O některých XML souborech lze s jistotou říci že jsou si podobné, například:

  * Sobory A, B které se liší v aliasech namespaců
  * Sobory A, B kde v souboru A je element prázdný s ukončovacím tagem a v B je element prázdný nicméně bez ukončovacího tagu (sestává se pouze z jednoho tagu)
  * Soubory ve které jsou stejné, ale v některých mají jiné pořadí atributy.

Na druhou stranu existuje mnoho případů, kdy není zcela jasné jestli jsou dva XML soubory v relaci podobnosti, například:

  * Různé pořadí elementů
  * Bílé znaky navíc v obsahu elementu

Pro tyto případy musí mít knihovna nastavení, kde lze dodefinovat jestli uživatel považuje takové dokumenty za podobné. Součástí řešení musí být jasná dokumentace relace "podobná xml". Je vhodné aby si nejdříve řešitelé prošly existující metody a nástroje na porovnávání XML a v z nich si vypsali, co daný nástroj považuje za podobnost. V rámci implementace využijte pouze JDK. Projekt musí být jednotkově testován.

# Developers: #
  * Jakub Kremláček, učo<sup>*</sup> 410131, team leader
  * Matej Minárik, učo<sup>*</sup> 396546,
  * Ondrej Mosnáček, učo<sup>*</sup> 409879,
  * Ondřej Sobočík, učo<sup>*</sup> 359502,

<sup>*</sup> učo = university identification number ("univerzitní číslo osoby")

# Application/library: #

The library is based on traversing and comparing both XML files that are (together with comparison options) given as input. See [ComparisonAlgorithm](ComparisonAlgorithm.md) for more details about the comparison. The library will have a GUI and CLI frontend that will let the user specify the comparison options, process the given XML files and display them with the differences highlighted.

# GUI #

https://code.google.com/p/semantically-oriented-xml-comparison/wiki/GUI