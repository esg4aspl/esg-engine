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
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedByDT;
import tr.edu.iyte.esg.model.VertexRefinedByESG;
import tr.edu.iyte.esg.model.decisiontable.Action;
import tr.edu.iyte.esg.model.decisiontable.Condition;
import tr.edu.iyte.esg.model.decisiontable.ConditionResult;
import tr.edu.iyte.esg.model.decisiontable.DecisionTable;
import tr.edu.iyte.esg.model.decisiontable.Rule;


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
		
		drawSubESGRule(ESG, printWriter);
		drawDecisionTable(ESG, printWriter);
		Set<String> esgContent = getProperESGContentForDOTFormat(ESG);
	
		for(String content : esgContent) {
			printWriter.println(content);
		}
		printWriter.println("}");
		printWriter.close();
	}
	
	/**
	 * We choose reverse indexed for loop because of reverse order representation of graphs.
	 * @param ESG
	 * @param printWriter
	 */
	public static void drawSubESGRule(ESG ESG, PrintWriter printWriter) {
		for(int i = ESG.getVertexList().size() - 1; i >= 0; i--) {
			Vertex v = ESG.getVertexList().get(i);
			if (v instanceof VertexRefinedByESG) {
				ESG subEsg = ((VertexRefinedByESG) v).getSubESG();
				printWriter.println("subgraph cluster" + subEsg.getName().replace(" ", "") + " {");
				printWriter.println("label = " + subEsg.getName().replace(" ", ""));
				printWriter.println("color = \"blue\"");
				drawInnerESG(subEsg, printWriter);
				drawDecisionTable(subEsg, printWriter);
				Set<String> esgContent = getProperESGContentForDOTFormat(subEsg);

				for(String content : esgContent) {
					printWriter.println(content);
				}
				printWriter.println("}");
			}
		}
	}

	public static void drawInnerESG(ESG esg, PrintWriter printWriter) {
		for(Vertex v: esg.getVertexList()) {
			if(v instanceof VertexRefinedByESG) {
				ESG innerESG = ((VertexRefinedByESG) v).getSubESG();

				printWriter.println("subgraph cluster" + innerESG.getName().replace(" ", "") + " {");
				printWriter.println("label = " + innerESG.getName().replace(" ", ""));
				printWriter.println("color = \"red\"");
				Set<String> esgContent = getProperESGContentForDOTFormat(innerESG);

				for(String content : esgContent) {
					printWriter.println(content);
				}
				printWriter.println("}");
			}
		}
	}

	public static void drawDecisionTable(ESG ESG, PrintWriter printWriter) {
		for(Vertex v : ESG.getVertexList()) {
			if (v instanceof VertexRefinedByDT) {
				DecisionTable dt = ((VertexRefinedByDT) v).getDecisionTable();
				printDetails(dt, printWriter);
				printWriter.println("subgraph cluster" + dt.getName().replace(" ", "")+ " {");
				printWriter.println("label = " + "DT_" + v.getEvent().getName().replace(" " , ""));
				printWriter.println("color = \"blue\"");
				printWriter.println("n1[shape=none label=<<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">");
				constructTable(dt, printWriter);
				printWriter.println("</table>>];");
				printWriter.println("}");
			}
		}
	}
	
	public static void constructTable(DecisionTable dt, PrintWriter printWriter) {
		printWriter.println("<tr>");
		printWriter.println("<td>      -     </td>");
		for(int i = 0; i < dt.getRuleList().size(); i++) {
			printWriter.println("<td>     R"+ i + "    </td>");
		}
		printWriter.println("</tr>");
		printConditions(dt, printWriter);
		printActions(dt, printWriter);
	}

	public static void printConditions(DecisionTable dt, PrintWriter printWriter) {
		for(Condition c: dt.getConditionList()) {
			printWriter.println("<tr>");
			printWriter.println("<td>    C" + c.getID()+ "     </td>");
			for(Rule r: dt.getRuleList()) {
				ConditionResult result = r.get(c);
				result.toString();
				printWriter.println("<td><i>      "+ result.toString() + "    </i></td>");
			}
			printWriter.println("</tr>");
		}
	}

	public static void printActions(DecisionTable dt, PrintWriter printWriter) {
		for(Action a: dt.getActionList()) {
			printWriter.println("<tr>");
			printWriter.println("<td>    A" + a.getID()+ "     </td>");
			for(Rule r: dt.getRuleList()) {
				Set<Action> actions = dt.getAction(r);
				if(actions.isEmpty() || !actions.contains(a)) {
					printWriter.println("<td>      "+ "-" + "    </td>");
				} else {
					printWriter.println("<td><i>      "+ "X" + "    </i></td>");
				}
			}
			printWriter.println("</tr>");
		}
	}

	private static void printDetails(DecisionTable dt, PrintWriter printWriter) {
		printWriter.println("subgraph cluster2" + dt.getName().replace(" ", "")+ " {");
		printWriter.println("label = \"Table Properties\"");
		printWriter.println("color = \"red\"");
		printWriter.println("n2[shape=none label=<<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">");
		for(int i = 0; i < dt.getConditionList().size(); i++) {
			Condition c = dt.getConditionList().get(i);
			printWriter.println("<tr>");
			printWriter.println("<td>"+ "C" + c.getID() + "</td>");
			printWriter.println("<td>"+ convertMathSignToString(c.toString()) + "</td>");
			printWriter.println("</tr>");
		}
		for(int i = 0; i < dt.getActionList().size(); i++) {
			Action a = dt.getActionList().get(i);
			printWriter.println("<tr>");
			printWriter.println("<td>"+ "A" + a.getID() + "</td>");
			printWriter.println("<td>"+ convertMathSignToString(a.toString()) + "</td>");
			printWriter.println("</tr>");
		}
		printWriter.println("</table>>];");
		printWriter.println("}");
	}

	/**
	 * @param condition
	 * @return Formatted Condition String for Contents Table Representation
	 * Please give a look at graphviz table representation for better understanding.
	 */
	public static String convertMathSignToString(String condition) {
		String result = condition
				.replace(">=", "greater than or eq.")
				.replace("<=", "less than or eq")
				.replace(">", "greater than")
				.replace("<", "less than")
				.replace("]", "Exit")
				.replace("[", "Start");

		return result;
	}
	
	
	public static Set<String> getProperESGContentForDOTFormat(ESG ESG){
		Set<String> vertexSet = new LinkedHashSet<String>();
		Set<String> edgeSet = new LinkedHashSet<String>();
		
		String label = "label = \"" + ESG.getName() + "\";";
		edgeSet.add(label);
		
		String vertexName = "esg"+ ESG.getID() + "_vertex";
		String vertexLabelBeginning = " [label = \"";
		String vertexLabelEnd = "]";
		
		for (Edge edge : ESG.getEdgeList()) {
			
			String source = vertexName + edge.getSource().getID();
			String target = vertexName + edge.getTarget().getID();
			
			String sourceNamePrefix = source + vertexLabelBeginning + getProperStringForDotFormat(edge.getSource().getDotLanguageFormat());
			String sourceName = 
					sourceNamePrefix +
					edge.getSource().getShape() +
					getVertexColor(edge.getSource()) +
					vertexLabelEnd;

			String targetNamePrefix = target + vertexLabelBeginning + getProperStringForDotFormat(edge.getTarget().getDotLanguageFormat());
			String targetName =
					targetNamePrefix +
					edge.getTarget().getShape() +
					getVertexColor(edge.getTarget()) +
					vertexLabelEnd;
			
			vertexSet.add(sourceName);
			vertexSet.add(targetName);

			String edgeName = source + " -> " + target + getEdgeColor(edge) + ";";
			edgeSet.add(edgeName);
		
		}
		edgeSet.addAll(vertexSet);
		
		return edgeSet;
	}
	
	public static String getVertexColor(Vertex v) {
		String colorPrefix = ", color=";

		return colorPrefix + v.getColor();
	}

	public static String getEdgeColor(Edge e) {
		String colorPrefix = "[ color = ";
		String colorPostfix = "]";

		return colorPrefix + e.getColor() + colorPostfix; 
	}

	public static String getProperStringForDotFormat(String str) {
		String properStr = str.trim() ;
		return properStr;
	}
}
