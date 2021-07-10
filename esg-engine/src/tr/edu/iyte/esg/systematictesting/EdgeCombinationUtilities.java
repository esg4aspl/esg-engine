package tr.edu.iyte.esg.systematictesting;

import java.util.LinkedHashSet;
import java.util.Set;

public class EdgeCombinationUtilities {
	
	private static Set<String> startEdgeSet = new LinkedHashSet<String>();
	private static Set<String> finishEdgeSet = new LinkedHashSet<String>();
	private static Set<String> ordinaryEdgeSet = new LinkedHashSet<String>();
	private static Set<String> selfEdgeSet = new LinkedHashSet<String>();
	
	public static void buildEdgeCombinationSetsFromEventSet(Set<String> eventSet) {
		
		for(String event1 : eventSet) {
			String startEdge = "[" + event1;
			startEdgeSet.add(startEdge);
			String finishEdge =  event1 + "]";
			finishEdgeSet.add(finishEdge);
			
			for(String event2 : eventSet) {
				if(!event1.equals(event2)) {
					String ordinaryEdge = event1 + event2;
					ordinaryEdgeSet.add(ordinaryEdge);
				}else {
					String selfEdge = event1 + event2;
					selfEdgeSet.add(selfEdge);

				}
			}
		}

		//ordinaryEdgeSet.forEach(e->System.out.print(e + " "));
	}
	
	public static Set<String> getStartEdgeSet(){
		return startEdgeSet;
	}
	
	public static Set<String> getFinishEdgeSet(){
		return finishEdgeSet;
	}
	public static Set<String> getOrdinaryEdgeSet(){
		return ordinaryEdgeSet;
	}
	public static Set<String> getSelfEdgeSet(){
		return selfEdgeSet;
	}
		
	private static Set<Set<String>> getStartFinishEdgeCombinationSets(){
		Set<Set<String>> startEdgePowerSet = SetOperationUtilities.powerSet(startEdgeSet);
		Set<Set<String>> finishEdgePowerSet = SetOperationUtilities.powerSet(finishEdgeSet);
		
		Set<Set<String>> cartesianProductStartFinishEdgeSet = SetOperationUtilities.cartesianProductOfPowerSets(startEdgePowerSet, finishEdgePowerSet);
		
		return cartesianProductStartFinishEdgeSet;
	}
	
	public static Set<Set<String>> getEdgeCombinationSets(){
		Set<Set<String>> cartesianProductStartFinishEdgeSet = getStartFinishEdgeCombinationSets();
		Set<Set<String>> ordinaryEdgePowerSet = SetOperationUtilities.powerSet(ordinaryEdgeSet);
		Set<Set<String>> cartesianProductSet = SetOperationUtilities.cartesianProductOfPowerSets(cartesianProductStartFinishEdgeSet, ordinaryEdgePowerSet);
		
		return cartesianProductSet;
	}
	
}
