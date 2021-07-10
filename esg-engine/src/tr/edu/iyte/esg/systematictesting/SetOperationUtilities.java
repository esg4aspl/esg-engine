package tr.edu.iyte.esg.systematictesting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SetOperationUtilities {
	
	public static Set<Set<String>> powerSet(Set<String> originalSet) {
        Set<Set<String>> sets = new HashSet<Set<String>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<String>());
            return sets;
        }
        List<String> list = new ArrayList<String>(originalSet);
        String head = list.get(0);
        Set<String> rest = new HashSet<String>(list.subList(1, list.size()));
        for (Set<String> set : powerSet(rest)) {
            Set<String> newSet = new HashSet<String>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }
		
	public static Set<Set<String>> cartesianProductOfPowerSets(Set<Set<String>> startPowerSet, Set<Set<String>> finishPowerSet){
		Set<Set<String>> cartesianProductSet = new LinkedHashSet<Set<String>>();
		
		for(Set<String> startSubset : startPowerSet) {
			for(Set<String> finishSubset : finishPowerSet) {
				if(!startSubset.isEmpty() && !finishSubset.isEmpty()) {
					Set<String> cartesianSet = new LinkedHashSet<String>();
					cartesianSet.addAll(startSubset);
					cartesianSet.addAll(finishSubset);
					cartesianProductSet.add(cartesianSet);
				}
			}
		}
		
		return cartesianProductSet;
		
	}
	
	private static String printSubset(Set<String> subset){
		
		String str = "{ ";
		
		for(String string : subset) {
			str += string + " ";
		}
		
		str += "}";
		
		return str;
		
	}
	
	public static String toStringPowerSet(Set<Set<String>> powerSet){
		String str = "";
		
		for(Set<String> subset : powerSet) {
			str += printSubset(subset) + "\n";
		}
		
		return str;
		
		
	}

}
