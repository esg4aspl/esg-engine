package tr.edu.iyte.esg.model.sequenceesg;

import tr.edu.iyte.esg.model.Event;

public class EventUtilities {
	
	public static final String CDELIM = "_";
	
	/**
	 - Contexted string form of an event is constructed by appending the name and the id of the event.
	 - "_" is used as delimiter.
	 */
	public static String getContextedStringForm(Event event) {
		return event.getName() + CDELIM + event.getID();
	}
}
