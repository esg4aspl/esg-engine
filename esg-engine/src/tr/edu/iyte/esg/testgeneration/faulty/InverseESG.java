package tr.edu.iyte.esg.testgeneration.faulty;

import java.util.List;

import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.ESG;

public class InverseESG {

	private CompleteESG completeESG;
	private ESG ESG;
	
	public InverseESG(ESG ESG) {
		this.ESG = ESG;
		completeESG = new CompleteESG(ESG);
	}
	
	public List<Vertex> getVertexList(){
		return ESG.getVertexList();
	}
	
	public List<Edge> getEdgeList() {
		List<Edge> completeEdgeList = completeESG.getEdgeList();
		List<Edge> edgeList = ESG.getEdgeList();
		completeEdgeList.removeAll(edgeList);
		List<Edge> inverseEdgeList = completeEdgeList;
		
		return inverseEdgeList;
	}
	
	private String vertexListToString() {
		String vertexListToString = "Vertex List as (ID)Event: \n";
		for (Vertex vertex : getVertexList())
			vertexListToString += "(" + vertex.getID() + ")" + vertex.getEvent().getName() + ", ";
		vertexListToString += "\n";
		return vertexListToString;
	}
	
	private String edgeListToString() {
		String edgeListToString = "Edge List as (ID): \n";
		for (Edge edge : getEdgeList())
			edgeListToString += "(" + edge.getID() + ")" + "<" + edge.getSource().getID() + "-"
					+ edge.getTarget().getID() + ">" + ", ";
		edgeListToString += "\n";
		return edgeListToString;
	}
	
	@Override
	public String toString() {
		String toString = "Inverse ESG " + ESG.getID() + ", " + ESG.getName() + "\n";
		toString += vertexListToString();
		toString += edgeListToString();
		
		return toString;
	}

}
