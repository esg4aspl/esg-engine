package tr.edu.iyte.esg.model.comparators;

import java.util.Comparator;
import tr.edu.iyte.esg.model.Vertex;

public class VertexComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex vertex0, Vertex vertex1) {
		return Integer.valueOf(vertex0.getID()).compareTo(Integer.valueOf(vertex1.getID()));
	}
	
}
