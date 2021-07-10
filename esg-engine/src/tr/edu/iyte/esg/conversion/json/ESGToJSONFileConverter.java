package tr.edu.iyte.esg.conversion.json;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;

public class ESGToJSONFileConverter {
	
	
	public static void writeESGToJSONFile(ESG ESG, String filePathAndName) {
		
		Writer fileWriter = null;
		try {
			fileWriter = new FileWriter(filePathAndName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		
		printWriter.println("{");
		printWriter.println("\t" + "\"ID\"" + "\t:" + ESG.getID() + ",");
		printWriter.println("\t" + "\"name\"" + "\t:" + "\"" + ESG.getName() + "\"" + ",");
		printWriter.println("\t" + "\"vertices\"" + "\t:" + "[");
		
		int i = 0;
		for(;i < ESG.getVertexList().size() - 1; i++) {
			printVertex(printWriter, ESG.getVertexList().get(i));
			printWriter.println(",");
		}
		printVertex(printWriter, ESG.getVertexList().get(i));
		printWriter.println();
		printWriter.println("\t\t\t],");
		
		printWriter.println("\t" + "\"edges\"" + "\t:" + "[");
		int j = 0; 
		for(;j < ESG.getEdgeList().size() - 1; j++) {
			printEdge(printWriter, ESG.getEdgeList().get(j));
			printWriter.println(",");
		}
		printEdge(printWriter, ESG.getEdgeList().get(j));
		printWriter.println();
		printWriter.println("\t\t\t]");
		printWriter.println("}");
		
		printWriter.close();
	}
	
	private static void printVertex(PrintWriter printWriter, Vertex vertex) {
		
		printWriter.println("\t\t" + "{");
		printWriter.println("\t\t\t" + "\"ID\"" + "\t:" + vertex.getID()+ ",");
		printWriter.println("\t\t\t" + "\"event\"" + "\t:" + "\"" + vertex.getEvent().getName() + "\"");
		printWriter.print("\t\t" + "}");
	}
	
	private static void printEdge(PrintWriter printWriter, Edge edge) {
		
		printWriter.println("\t\t" + "{");
		printWriter.println("\t\t\t" + "\"ID\"" + "\t:" + edge.getID() + ",");
		printWriter.println("\t\t\t" + "\"source\"" + "\t:" + edge.getSource().getID() + ",");
		printWriter.println("\t\t\t" + "\"target\"" + "\t:" + edge.getTarget().getID());
		printWriter.print("\t\t" + "}");
	}

}
