package tr.edu.iyte.esg.eventsequence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jgrapht.alg.util.Pair;

import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedByDT;
import tr.edu.iyte.esg.model.decisiontable.Rule;

public class ESGWithDecisionTableEventSequence extends EventSequence {
	
	private Map<Pair<Integer,Vertex>, Rule> vertexRuleMap;

	public ESGWithDecisionTableEventSequence() {
		setEventSequence(new ArrayList<Vertex>());
		vertexRuleMap = new LinkedHashMap<Pair<Integer,Vertex>, Rule>();
		
	}
	
	public void addRuleVertexPair(int indexOfVertex, Vertex vertex, Rule rule) {
		//int indexOfVertex = getEventSequence().indexOf(vertex);
		Pair<Integer,Vertex> pair = new Pair<Integer,Vertex>(indexOfVertex, vertex);
		//System.out.println("PUTTED " + indexOfVertex + " " + vertex.toString());
		vertexRuleMap.put(pair, rule);
	}
	
	private Rule getRule(int index, Vertex vertex) {
		//System.out.println("Searched " + index + " " + vertex.toString() );
		Rule rule = null;
		for(Entry<Pair<Integer,Vertex>, Rule> entry : vertexRuleMap.entrySet()) {
			Pair<Integer,Vertex> pair = entry.getKey();
			
			if(pair.getFirst() == index && pair.getSecond().equals(vertex)) {
				rule = entry.getValue();
			}
		}
		
		return rule;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getEventSequence().size() + " : [");
		
		for(int i= 0; i < getEventSequence().size(); i++) {
			Vertex vertex = getEventSequence().get(i);
			//System.out.println("first " + vertex.toString());
			if(vertex instanceof VertexRefinedByDT) {
				//System.out.println("instanceof " + vertex.toString());
				sb.append(vertex.toString().trim());
				Rule rule = getRule(i,vertex);
				sb.append(rule.toString());
			}else {
				//System.out.println("second " + vertex.toString());
				sb.append(vertex.toString().trim());
			}
			if(i != getEventSequence().size() -1) {
				sb.append(", ");
			}
			
		}
		
		sb.append("]");
		return sb.toString();
		
	}
	
}
