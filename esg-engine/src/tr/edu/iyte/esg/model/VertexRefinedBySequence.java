package tr.edu.iyte.esg.model;

import tr.edu.iyte.esg.model.sequenceesg.Sequence;

public class VertexRefinedBySequence extends Vertex {
	private Sequence<Vertex> sequence;

	public VertexRefinedBySequence(int ID, Event event, Sequence<Vertex> sequence) {
		super(ID, event);
		this.sequence = sequence;
	}
	
	public Sequence<Vertex> getSequence() {
		return sequence;
	}
	
	public boolean isPseudoStartVertex() {
		return (sequence.getSize()==1) && 
				(sequence.getElement(0).isPseudoStartVertex());
	}
	
	public boolean isPseudoEndVertex() {
		return (sequence.getSize()==1) && 
				(sequence.getElement(0).isPseudoEndVertex());
	}
}
