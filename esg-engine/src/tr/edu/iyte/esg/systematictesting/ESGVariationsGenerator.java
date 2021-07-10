package tr.edu.iyte.esg.systematictesting;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexSimple;
import tr.edu.iyte.esg.model.validation.ESGValidator;


public class ESGVariationsGenerator {
	
	private Map<String,Vertex> eventNameVertexMap;
	
	public ESGVariationsGenerator() {
		eventNameVertexMap = new LinkedHashMap<String, Vertex>();
		
	}

	public Map<String,Vertex> getEventNameVertexMap() {
		return eventNameVertexMap;
	}
	
	public Set<ESG> getAllESGVariationsSet(Set<String> eventSet){
		
		Set<ESG> ESGVariationsSet = new LinkedHashSet<ESG>();
		EdgeCombinationUtilities.buildEdgeCombinationSetsFromEventSet(eventSet);
		Set<Set<String>> edgeCombinationsSet = EdgeCombinationUtilities.getEdgeCombinationSets();
		ESG rootESG = buildRootESG(eventSet);
		Iterator<Set<String>> edgeCombinationsSetIterator = edgeCombinationsSet.iterator();
		ESGValidator esgValidator = new ESGValidator();
		while(edgeCombinationsSetIterator.hasNext()) {
			Set<String> edgeCombinations = edgeCombinationsSetIterator.next();

			ESG variantESG = new ESG(rootESG);
			buildVariantESG(variantESG, edgeCombinations);

				if(esgValidator.isValid(variantESG)) {

					ESGVariationsSet.add(variantESG);
				}
		
		}
		
		return ESGVariationsSet;
	}
	
	public ESG getESGVariant(Set<String> eventSet) {

		ESG rootESG = buildRootESG(eventSet);

		EdgeCombinationUtilities.buildEdgeCombinationSetsFromEventSet(eventSet);
		Set<String> startEdgeSet = EdgeCombinationUtilities.getStartEdgeSet();
		Set<String> finishEdgeSet = EdgeCombinationUtilities.getFinishEdgeSet();
		Set<String> ordinaryEdgeSet = EdgeCombinationUtilities.getOrdinaryEdgeSet();
		Set<String> selfEdgeSet = EdgeCombinationUtilities.getSelfEdgeSet();
		
		PowerSet startEdgePowerSet = new PowerSet(startEdgeSet);
		PowerSet finishEdgePowerSet = new PowerSet(finishEdgeSet);
		PowerSet ordinaryEdgePowerSet = new PowerSet(ordinaryEdgeSet);
		PowerSet selfEdgePowerSet = new PowerSet(selfEdgeSet);
		
		Set<String> startEdgeSubset = startEdgePowerSet.getRandomPowerSetElement();
		Set<String> finishEdgeSubset = finishEdgePowerSet.getRandomPowerSetElement();
		Set<String> ordinaryEdgeSubset = ordinaryEdgePowerSet.getRandomPowerSetElement();
		Set<String> selfEdgeSubset = selfEdgePowerSet.getRandomPowerSetElement();
		
		Set<String> edgeCombinations = new LinkedHashSet<String>();
		edgeCombinations.addAll(startEdgeSubset);
		edgeCombinations.addAll(finishEdgeSubset);
		edgeCombinations.addAll(ordinaryEdgeSubset);
		
		Random random = new Random();
		int rand = random.nextInt();
		if(rand % 2 == 0) {
			edgeCombinations.addAll(selfEdgeSubset);
		}
		
		ESG variantESG = new ESG(rootESG);
		buildVariantESG(variantESG, edgeCombinations);
		
		return variantESG;

	}
	
	public Set<ESG> getESGVariationsSet(int numberOfVariants, Set<String> eventSet){
		Set<ESG> ESGVariationsSet = new LinkedHashSet<ESG>();
		ESGValidator esgValidator = new ESGValidator();
		while(ESGVariationsSet.size() < numberOfVariants) {
			ESG variantESG = getESGVariant( eventSet);
			
			if(esgValidator.isValid(variantESG)) {
				ESGVariationsSet.add(variantESG);
			
				}
		}
		
		return ESGVariationsSet;
		
		
	}
	
	private ESG buildRootESG(Set<String> eventSet) {
		
		Iterator<String> eventSetIterator = eventSet.iterator();
		ESG ESG = new ESG(0,"");
		
		Event startEvent = new EventSimple(ESG.getNextEventID(), "[");
		Vertex startVertex = new VertexSimple(ESG.getNextVertexID(),startEvent);
		ESG.addEvent(startEvent);
		ESG.addVertex(startVertex);
		eventNameVertexMap.put("[", startVertex);
		
		while(eventSetIterator.hasNext()) {
			String eventName = eventSetIterator.next();
			Event event = new EventSimple(ESG.getNextEventID(), eventName);
			Vertex vertex = new VertexSimple(ESG.getNextVertexID(),event);
			eventNameVertexMap.put(eventName, vertex);
			ESG.addEvent(event);
			ESG.addVertex(vertex);
		}
		
		Event finishEvent = new EventSimple(ESG.getNextEventID(), "]");
		Vertex finishVertex = new VertexSimple(ESG.getNextVertexID(),finishEvent);
		ESG.addEvent(finishEvent);
		ESG.addVertex(finishVertex);
		eventNameVertexMap.put("]", finishVertex);
		
		return ESG;
	}
	
	private void buildVariantESG(ESG ESG,Set<String> edgeCombinations ) {
		
		for(String edgeComb : edgeCombinations) {
			String source = edgeComb.substring(0, 1);
			String target = edgeComb.substring(1);
			//System.out.println("EDGE " + source + "-" + target);
			Vertex sourceVertex = eventNameVertexMap.get(source);
			Vertex targetVertex = eventNameVertexMap.get(target);
			Edge edge = new EdgeSimple(ESG.getNextEdgeID(), sourceVertex, targetVertex);
			ESG.addEdge(edge);
		}
		
	}
	




}
