package tr.edu.iyte.esg.testgeneration.faulty;

import java.util.LinkedList;
import java.util.List;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Vertex;

public class CompleteESG {
	
	private ESG ESG;
	
	public CompleteESG(ESG ESG) {
		this.ESG = ESG;
		
	}
	
	public List<Vertex> getVertexList(){
		return ESG.getVertexList();
	}
	
	public List<Edge> getEdgeList(){
		List<Edge> completeEdgeList = new LinkedList<Edge>();
		completeEdgeList.addAll(ESG.getEdgeList());
		List<Vertex> targetRealVertexList = ESG.getRealVertexList();
		targetRealVertexList.add(ESG.getPseudoEndVertex());
		
		for(Vertex source: ESG.getRealVertexList()) {
			for(Vertex target: targetRealVertexList) {
				if(!isExistOnRealEdgeList(source, target)) {
					
					if(!target.isPseudoEndVertex()) {
						Edge edge1 = new EdgeSimple(ESG.getLastEdgeID(), source, target);
						completeEdgeList.add(edge1);
					}else {
						if(!(ESG.getEntryVertexSet().contains(source) && ESG.getExitVertexSet().contains(source))){
							Edge edge2 = new EdgeSimple(ESG.getLastEdgeID(), source, target);
							completeEdgeList.add(edge2);
						}
					}
					//System.out.println("NOW ADDED " + edge.toString());
	
				}
			}
		}
		//completeEdgeList.forEach(e->System.out.println(e.getSource().toString() + "->" + e.getTarget().toString()));
		return completeEdgeList;
		
	}
	
	private boolean isExistOnRealEdgeList(Vertex source, Vertex target) {
		
		boolean isExist = false;
		
		if(!ESG.getRealEdgeList().isEmpty()) {
			for(Edge edge : ESG.getRealEdgeList()) {
	
				isExist = isExist || (edge.getSource().equals(source) && edge.getTarget().equals(target));
				
			}
			return isExist;
		}else {
			return false;
		}
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
		String toString = "Complete ESG " + ESG.getID() + ", " + ESG.getName() + "\n";
		toString += vertexListToString();
		toString += edgeListToString();
		
		return toString;
	}

}
