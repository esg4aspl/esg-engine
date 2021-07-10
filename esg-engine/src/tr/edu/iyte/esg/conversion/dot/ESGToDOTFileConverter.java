package tr.edu.iyte.esg.conversion.dot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Model;


public class ESGToDOTFileConverter {
	

	public static void buildDOTFileFromModel(Model model, String filePathAndName) throws IOException {
		Writer fileWriter = new FileWriter(filePathAndName);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		printWriter.println("digraph " + model.getName() + " {");
		printWriter.println("graph [fontname=Arial, fontcolor=blue, fontsize=26];\n" + 
				"node [fixedsize=false,fontsize=26]");
		printWriter.println("rankdir=LR");
		printWriter.println("subgraph cluster {\n" + 
				"\n" + 
				"label = \" "+ model.getName() + "\";");
		for (ESG ESG : model.getEsgList()) {
			Set<String> esgContent = getProperESGContentForDOTFormat(ESG);
			printWriter.println("subgraph cluster" + ESG.getID() +" {" );
			printWriter.println("rankdir=LR");
			
			for(String content : esgContent) {
				printWriter.println(content);
			}
			printWriter.println("}");

			
		}
		printWriter.println("}");
		printWriter.println("}");
		
		printWriter.close();
	}
	
	public static  void buildDOTFileFromESG(ESG ESG, String filePathAndName) {
		Writer fileWriter = null;
		try {
			fileWriter = new FileWriter(filePathAndName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);

		printWriter.println("digraph G {\n" + 
				"rankdir = LR");
		Set<String> esgContent = getProperESGContentForDOTFormat(ESG);
	
		for(String content : esgContent) {
			printWriter.println(content);
		}
		printWriter.println("}");
		printWriter.close();
	}
	
	
	public static Set<String> getProperESGContentForDOTFormat(ESG ESG){
		Set<String> vertexSet = new LinkedHashSet<String>();
		Set<String> edgeSet = new LinkedHashSet<String>();
		
		String label = "label = \"" + ESG.getName() + "\";";
		edgeSet.add(label);
		
		String vertexName = "esg"+ ESG.getID() + "_vertex";
		String vertexLabelBeginning = " [label = \"";
		String vertexLabelEnd = "\"]";
		
		for (Edge edge : ESG.getEdgeList()) {
			
			String source = vertexName + edge.getSource().getID();
			String target = vertexName + edge.getTarget().getID();
			
			String sourceName = source + vertexLabelBeginning + getProperStringForDotFormat(edge.getSource().toString()) + vertexLabelEnd;
			String targetName = target + vertexLabelBeginning + getProperStringForDotFormat(edge.getTarget().toString()) + vertexLabelEnd;
			vertexSet.add(sourceName);
			vertexSet.add(targetName);

			String edgeName = source + " -> " + target + ";";
			edgeSet.add(edgeName);
		
		}
		edgeSet.addAll(vertexSet);
		
		return edgeSet;
	}

	public static String getProperStringForDotFormat(String str) {
		String properStr = str.trim() ;
		return properStr;
	}
}
