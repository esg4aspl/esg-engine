package tr.edu.iyte.esg.model.sequenceesg;

import java.util.Comparator;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedBySequence;
import tr.edu.iyte.esg.model.comparators.SequenceComparator;
import tr.edu.iyte.esg.model.comparators.VertexComparator;

public class SequenceESGTransformer {

	public SequenceESGTransformer() {
		
	}
	
	/**
	 - ID and name for ESG
	   -- ID of the (k+1)-ESG is obtained by incrementing the ID of the input k-ESG.
	   -- Name of the (k+1)-ESG is obtained by appending "t" to the name of the input k-ESG.
	 - Events in the event list
	   -- Event list of the (k+1)-ESG contains the event instances in the event list of the input 1-ESG.
	 - Vertices in sequences in sequence vertices
	   -- (k+1)-ESG uses existing vertex instances from the input k-ESG and 1-ESG to construct new sequences.
	 - Avoiding the creation of redundant sequence vertices
	   -- A new sequence vertex instance is not created if there is a sequence vertex instance containing an equivalent sequence.
	   -- Instances are looked-up from the sequence vertex list with respect to the sequences. This decreases the performance.
	 - A sequence vertex, its event and its vertex sequence
	   -- An event of a sequence vertex is not an actual event; therefore, it is not included in the event list.
	   -- Name of a sequence vertex event is the string form of the sequence constructed using contexted string forms of the events in the vertices of the sequence.
	 */
	public ESG transform(ESG kESG, ESG oneESG) {
		ESG kp1ESG = new ESG(kESG.getID()+1, kESG.getName()+"t"); //!!! esg id and name
		for(Event e : oneESG.getEventList()) {
			kp1ESG.addEvent(e);
		}
		Comparator<Vertex> vc = new VertexComparator();
		Comparator<Sequence<Vertex>> comp = new SequenceComparator<Vertex>(vc);
		for(Edge qr : kESG.getEdgeList()) {
			VertexRefinedBySequence r = (VertexRefinedBySequence)qr.getTarget();
			//r.getSequence().forEach(e->System.out.println("r "+e.getEvent().getName()));
			if(!r.isPseudoEndVertex()) {
				VertexRefinedBySequence q = (VertexRefinedBySequence)qr.getSource();
				//.getSequence().forEach(e->System.out.println("q "+e.getEvent().getName()));
				Vertex rLast = r.getSequence().getElement(r.getSequence().getSize()-1);
				//System.out.println("rLast " + rLast.getEvent().getName());
				for(Edge ab : oneESG.getEdgeList()) {
					VertexRefinedBySequence a = (VertexRefinedBySequence)ab.getSource();
					Vertex a1 = a.getSequence().getElement(0);
					//System.out.println("a1 " + a1.getEvent().getName());
					if(vc.compare(rLast, a1)==0) {
						VertexRefinedBySequence b = (VertexRefinedBySequence)ab.getTarget();
						Vertex b1 = b.getSequence().getElement(0);
						//System.out.println("b1 " + b1.getEvent().getName());
						//below part is simplified from the old transformation method.
						if(!(q.isPseudoStartVertex() && b1.isPseudoEndVertex())) {
							Sequence<Vertex> s = new Sequence<Vertex>();
							Sequence<Vertex> t = new Sequence<Vertex>();
							s.addElements(q.getSequence()); //!!! existing vertex instances
							if(!q.isPseudoStartVertex()) {
								s.addElement(rLast); //!!! existing vertex instances
							}
							//s.forEach(e->System.out.println("s "+e.getEvent().getName()));
							if(!b1.isPseudoEndVertex()) {
								t.addElements(r.getSequence()); //!!! existing vertex instances
							}
							t.addElement(b1); //!!! existing vertex instances
							//t.forEach(e->System.out.println("t "+e.getEvent().getName()));
							VertexRefinedBySequence v = (VertexRefinedBySequence) SequenceESGUtilities.getVertexByVertexSequence(kp1ESG, s, comp); //!!! look up to avoid using redundant instances (performance decrease)
							if(v == null) {
								Event e = new EventSimple(kp1ESG.getNextEventID(), VertexSequenceUtilities.getStringFormWithContextedEvents(s));
								v = new VertexRefinedBySequence(kp1ESG.getNextVertexID(), e, s);
								
								kp1ESG.addVertex(v);
							}
							VertexRefinedBySequence w = (VertexRefinedBySequence) SequenceESGUtilities.getVertexByVertexSequence(kp1ESG, t, comp); //!!! look up to avoid using redundant instances (performance decrease)
							if(w == null) {
								Event e = new EventSimple(kp1ESG.getNextEventID(), VertexSequenceUtilities.getStringFormWithContextedEvents(t));
								w = new VertexRefinedBySequence(kp1ESG.getNextVertexID(), e, t);
								
								kp1ESG.addVertex(w);
							}
							//System.out.println("v "+v.getEvent().getName());
							//System.out.println("w "+w.getEvent().getName());
							kp1ESG.addEdge(new EdgeSimple(kp1ESG.getNextEdgeID(), v, w));
						}
						else {
							//q is start and b1 is end.
							//if the only following vertex of rLast is the end vertex in the 1-ESG, 
							//then k-sequence r cannot be included in a longer sequence.
							//such sequences are discarded.
							//to include each of them, two edges must be added (start,r) and (r,end).
						}
					}
				}
			}
		}
		return kp1ESG;
	}
	
	public ESG transformIncludingShorterSequences(ESG kESG, ESG oneESG) {
		ESG kp1ESG = new ESG(kESG.getID()+1, kESG.getName()+"t"); //!!! esg id and name
		for(Event e : oneESG.getEventList()) {
			kp1ESG.addEvent(e);
		}
		Comparator<Vertex> vc = new VertexComparator();
		Comparator<Sequence<Vertex>> comp = new SequenceComparator<Vertex>(vc);
		for(Edge qr : kESG.getEdgeList()) {
			VertexRefinedBySequence r = (VertexRefinedBySequence)qr.getTarget();
			//r.getSequence().forEach(e->System.out.println("r "+e.getEvent().getName()));
			if(!r.isPseudoEndVertex()) {
				VertexRefinedBySequence q = (VertexRefinedBySequence)qr.getSource();
				//q.getSequence().forEach(e->System.out.println("q "+e.getEvent().getName()));
				Vertex rLast = r.getSequence().getElement(r.getSequence().getSize()-1);
				//System.out.println("rLast " + rLast.getEvent().getName());
				for(Edge ab : oneESG.getEdgeList()) {
					VertexRefinedBySequence a = (VertexRefinedBySequence)ab.getSource();
					Vertex a1 = a.getSequence().getElement(0);
					//System.out.println("a1 " + a1.getEvent().getName());
					if(vc.compare(rLast, a1)==0) {
						VertexRefinedBySequence b = (VertexRefinedBySequence)ab.getTarget();
						Vertex b1 = b.getSequence().getElement(0);
						//System.out.println("b1 " + b1.getEvent().getName());
						//below part is simplified from the old transformation method.
						if(!(q.isPseudoStartVertex() && b1.isPseudoEndVertex())) {
							Sequence<Vertex> s = new Sequence<Vertex>();
							Sequence<Vertex> t = new Sequence<Vertex>();
							s.addElements(q.getSequence()); //!!! existing vertex instances
							if(!q.isPseudoStartVertex()) {
								s.addElement(rLast); //!!! existing vertex instances
							}
							//s.forEach(e->System.out.println("s "+e.getEvent().getName()));
							if(!b1.isPseudoEndVertex()) {
								t.addElements(r.getSequence()); //!!! existing vertex instances
							}
							t.addElement(b1); //!!! existing vertex instances
							//t.forEach(e->System.out.println("t "+e.getEvent().getName()));
							VertexRefinedBySequence v = (VertexRefinedBySequence) SequenceESGUtilities.getVertexByVertexSequence(kp1ESG, s, comp); //!!! look up to avoid using redundant instances (performance decrease)
							if(v == null) {
								Event e = new EventSimple(kp1ESG.getNextEventID(), VertexSequenceUtilities.getStringFormWithContextedEvents(s));
								v = new VertexRefinedBySequence(kp1ESG.getNextVertexID(), e, s);
								
								kp1ESG.addVertex(v);
							}
							VertexRefinedBySequence w = (VertexRefinedBySequence) SequenceESGUtilities.getVertexByVertexSequence(kp1ESG, t, comp); //!!! look up to avoid using redundant instances (performance decrease)
							if(w == null) {
								Event e = new EventSimple(kp1ESG.getNextEventID(), VertexSequenceUtilities.getStringFormWithContextedEvents(t));
								w = new VertexRefinedBySequence(kp1ESG.getNextVertexID(), e, t);
								
								kp1ESG.addVertex(w);
							}
							//System.out.println("v "+v.getEvent().getName());
							//System.out.println("w "+w.getEvent().getName());
							kp1ESG.addEdge(new EdgeSimple(kp1ESG.getNextEdgeID(), v, w));
						}
						else {
							//q is start and b1 is end.
							//if the only following vertex of rLast is the end vertex in the 1-ESG, 
							//then k-sequence r cannot be included in a longer sequence.
							//such sequences are discarded.
							//to include each of them, two edges must be added (start,r) and (r,end).
							//if(a1.getOutDegree() == 1) {
								Sequence<Vertex> s = new Sequence<Vertex>();
								Sequence<Vertex> t = new Sequence<Vertex>();
								Sequence<Vertex> u = new Sequence<Vertex>();
								s.addElements(q.getSequence());
								t.addElements(r.getSequence());
								u.addElements(b.getSequence());
								VertexRefinedBySequence v = (VertexRefinedBySequence) SequenceESGUtilities.getVertexByVertexSequence(kp1ESG, s, comp); //!!! look up to avoid using redundant instances (performance decrease)
								if(v == null) {
									Event e = new EventSimple(kp1ESG.getNextEventID(), VertexSequenceUtilities.getStringFormWithContextedEvents(s));
									v = new VertexRefinedBySequence(kp1ESG.getNextVertexID(), e, s);
									
									kp1ESG.addVertex(v);
								}
								VertexRefinedBySequence w = (VertexRefinedBySequence) SequenceESGUtilities.getVertexByVertexSequence(kp1ESG, t, comp); //!!! look up to avoid using redundant instances (performance decrease)
								if(w == null) {
									Event e = new EventSimple(kp1ESG.getNextEventID(), VertexSequenceUtilities.getStringFormWithContextedEvents(t));
									w = new VertexRefinedBySequence(kp1ESG.getNextVertexID(), e, t);
									
									kp1ESG.addVertex(w);
								}
								VertexRefinedBySequence x = (VertexRefinedBySequence) SequenceESGUtilities.getVertexByVertexSequence(kp1ESG, u, comp); //!!! look up to avoid using redundant instances (performance decrease)
								if(x == null) {
									Event e = new EventSimple(kp1ESG.getNextEventID(), VertexSequenceUtilities.getStringFormWithContextedEvents(u));
									x = new VertexRefinedBySequence(kp1ESG.getNextVertexID(), e, u);
									
									kp1ESG.addVertex(x);
								}
								kp1ESG.addEdge(new EdgeSimple(kp1ESG.getNextEdgeID(), v, w));
								kp1ESG.addEdge(new EdgeSimple(kp1ESG.getNextEdgeID(), w, x));
							//}
						}
					}
				}
			}
		}
		return kp1ESG;
	}

}
