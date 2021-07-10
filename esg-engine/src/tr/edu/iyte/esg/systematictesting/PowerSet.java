package tr.edu.iyte.esg.systematictesting;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PowerSet {
	
	private Set<String> elementSet;
	private Map<Integer,String> idElementMap;
	private int[] intPowerSetSource;
	private int[][] intPowerSet;
	
	
	public PowerSet(Set<String> elementSet) {
		this.elementSet = elementSet;
		setIdElementMap(new LinkedHashMap<Integer, String>());
	}
	
	public Set<String> getElementSet() {
		return elementSet;
	}

	
	public Map<Integer,String> getIdElementMap() {
		return idElementMap;
	}

	private void setIdElementMap(Map<Integer,String> idElementMap) {
		this.idElementMap = idElementMap;
		
		int counter = 0;
		intPowerSetSource = new int[elementSet.size()];
		//System.out.println("elementSet.size() " + elementSet.size());
		for(String element : elementSet) {
			idElementMap.put(counter,element);
			intPowerSetSource[counter] = counter;
			counter++;
		}
		
		intPowerSet = this.powerSet();
	}
	
	public Set<Set<String>> getPowerSet(){
		Set<Set<String>> powerSet = new LinkedHashSet<Set<String>>();
		
		for(int i = 0; i < intPowerSet.length; i++) {
			Set<String> subSet = new LinkedHashSet<String>();
			for(int j = 0; j <  intPowerSet[i].length; j++) {
				int key = intPowerSet[i][j];
				subSet.add(idElementMap.get(key));
			}
			powerSet.add(subSet);
		}
		return powerSet;
	}
	
	public Set<String> getRandomPowerSetElement(){
		Set<String> randomPowerSetElement = new LinkedHashSet<String>();
		Random random = new Random();
		int index = random.nextInt(intPowerSet.length);
		int[] subset = intPowerSet[index];
		
		for(int i = 0; i < subset.length; i++) {
			int key = subset[i];
			String element = idElementMap.get(key);
			randomPowerSetElement.add(element);
		}
		
		return randomPowerSetElement;
	}
	
	
	private int[][] powerSet() {
	    // Java's max array size is Integer.MAX_VALUE, which is 2^31 - 1.  So,
	    // the max size we can handle is 30 elements, since the powerset will be
	    // 2^30 elements long.
	    assert intPowerSetSource.length < 31 : "length must be less than 31";

	    int powersetSize = 1 << intPowerSetSource.length;

	    // Initialize array of nulls that we'll fill below.
	    int[][] powerset = new int[powersetSize][];
	    
	    for (int i = 0; i < powersetSize; i++) {
	        // We can use integers as masks for what to select, e.g. 0010 means
	        // select the first element.
	        int mask = i;
	        // The number of set bits in the mask is the number of elements we
	        // pick for this specific set
	        int destSize = Integer.bitCount(mask);
	        int[] dest = new int[destSize];
	        int destIndex = 0;

	        // Loop through the set bits by clearing the least significant bit
	        // until no bits are set.
	        for (int m = mask; m != 0; m = m & (m - 1)) {
	            int lowestBit = Integer.lowestOneBit(m);

	            // Translate the lowestBit into an index into the src array.  We
	            // want the position of the low bit in the integer which we get
	            // with log2(n).  log2(n) is efficiently computed by counting
	            // the leading number of zeroes.
	            int srcIndex = 31 - Integer.numberOfLeadingZeros(lowestBit);
	            dest[destIndex] = intPowerSetSource[srcIndex];
	            destIndex++;
	        }
	        
	        powerset[i] = dest;
	        /*for(int a = 0; a < powerset[i].length; a++) {
	        	System.out.print(powerset[i][a] + " ");
	        }
	        System.out.println();*/
	    }
	   
	    return powerset;
	}






}
