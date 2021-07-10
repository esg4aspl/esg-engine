package tr.edu.iyte.esg.model.comparators;

import java.util.Comparator;

import tr.edu.iyte.esg.model.Event;

public class EventComparator implements Comparator<Event> {

	@Override
	public int compare(Event arg0, Event arg1) {
		return Integer.valueOf(arg0.getID()).compareTo(Integer.valueOf(arg1.getID()));
	}
}
