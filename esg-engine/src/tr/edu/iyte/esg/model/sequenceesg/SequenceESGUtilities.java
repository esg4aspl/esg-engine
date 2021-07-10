package tr.edu.iyte.esg.model.sequenceesg;

import java.util.Comparator;
import java.util.Iterator;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedBySequence;

public class SequenceESGUtilities {

	public static VertexRefinedBySequence getVertexByVertexSequence(ESG sesg, Sequence<Vertex> sequence, Comparator<Sequence<Vertex>> comparator) {
		VertexRefinedBySequence z = null;
		Iterator<Vertex> i = sesg.getVertexList().iterator();
		boolean notfound = true;
		while(i.hasNext() && notfound) {
			z = (VertexRefinedBySequence) i.next();
			notfound = (comparator.compare(z.getSequence(), sequence) != 0);
		}
		if(notfound) {
			z = null;
		}
		return z;
	}
	
	public static Edge getEdgeByVertexSequences(ESG sesg, Sequence<Vertex> ssequence, Sequence<Vertex> tsequence, Comparator<Sequence<Vertex>> comparator) {
		Edge z = null;
		Iterator<Edge> i = sesg.getEdgeList().iterator();
		boolean notfound = true;
		while(i.hasNext() && notfound) {
			z = (Edge) i.next();
			Sequence<Vertex> ss = ((VertexRefinedBySequence)z.getSource()).getSequence();
			Sequence<Vertex> ts = ((VertexRefinedBySequence)z.getTarget()).getSequence();
			notfound = (comparator.compare(ss, ssequence) != 0 || comparator.compare(ts, tsequence) != 0);
		}
		if(notfound) {
			z = null;
		}
		return z;
	}
	
	public static void printEdges(ESG sesg) {
		for(Edge xy : sesg.getEdgeList()) {
//			VertexRefinedBySequence x = (VertexRefinedBySequence)xy.getSource();
//			VertexRefinedBySequence y = (VertexRefinedBySequence)xy.getTarget();
//			for(Vertex v : x.getSequence()) {
//				System.out.print(v.getEvent().getName()+",");
//			}
//			System.out.print(" -> ");
//			for(Vertex v : y.getSequence()) {
//				System.out.print(v.getEvent().getName()+",");
//			}
			System.out.print("       "+xy);
			System.out.println();
		}
	}
	
	public static void printEdgesDetailed(ESG sesg) {
		for(Edge xy : sesg.getEdgeList()) {
			VertexRefinedBySequence x = (VertexRefinedBySequence)xy.getSource();
			VertexRefinedBySequence y = (VertexRefinedBySequence)xy.getTarget();
			System.out.print("[" + xy.getID() + "," + xy.getLabel());
				System.out.print(",[" + x.getID());
					System.out.print(",[" + x.getEvent().getID() + "," + x.getEvent().getName() + "]");
					//System.out.print("," + x.getSequence());
				System.out.print("]");
				System.out.print(",[" + y.getID());
					System.out.print(",[" + y.getEvent().getID() + "," + y.getEvent().getName() + "]");
					//System.out.print("," + y.getSequence());
				System.out.print("]");
			System.out.println("]");
		}
	}
}
