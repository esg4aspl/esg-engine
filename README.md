# esg-engine

ESG structure, its features and positive/negative test generation are implemented under this project. You can do the followings by using this project: 

- Create an ESG from different file types such as JSON, MXE or DOT
- Convert an ESG object to different file objects such as JSON, DOT or CSV
- Generate positive test suite of an ESG
- Generate negative test suite of an ESG
- Generate test suite of an ESG with Decision Table
- Generate test suite of an ESG with sub-ESG
- Do systematic testing of ESGs such as with two vertices, three vertices etc.

## Xtext & ImageViewer Installation
1. Go to Help -> Eclipse Marketplace
2. Search Xtend 2.26.0 and install
3. Search TextUML Toolkit install (For Eclipse Image Viewer)

## Graphviz Installation on Mac
1. Use command "brew install graphviz"
2. After run command "dot -v" to check installation finished successfully.
3. If there is error raised for dot.exe not found on mac apply the rules below:
  * Click Eclipse -> Preferences -> Graphviz -> Select Specify Manually then paste this -> "/usr/local/bin/dot"
  * Apply & Close

## How to Visualize a Dot File
1. Open Dot file with Eclipse Text Editor
2. On top bar Window -> Show View -> Other -> Select Image Viewer
3. Ready to launch :) !
