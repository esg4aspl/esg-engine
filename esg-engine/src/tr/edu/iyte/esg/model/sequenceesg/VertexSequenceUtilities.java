package tr.edu.iyte.esg.model.sequenceesg;

import java.util.Iterator;

import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.Vertex;

public class VertexSequenceUtilities {
	public static final String CSTRDELIM = ":";
	
	/**
	 - String form of a sequence is constructed by appending contexted string forms of the events in the vertices in the sequence.
	 - ":" is used as delimiter.
	 */
	public static String getStringFormWithContextedEvents(Sequence<Vertex> s) {
		StringBuilder sb = new StringBuilder();
		if(s.getSize() > 0) {
			Iterator<Vertex> i = s.iterator();
			Event e = i.next().getEvent();
			sb.append(EventUtilities.getContextedStringForm(e));
			while(i.hasNext()) {
				sb.append(CSTRDELIM);
				e = i.next().getEvent();
				sb.append(EventUtilities.getContextedStringForm(e));
			}
		}
		return sb.toString();
	}
}
