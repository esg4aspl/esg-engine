package tr.edu.iyte.esg.model.comparators;

import java.util.Comparator;

import tr.edu.iyte.esg.eventsequence.EventSequence;

public class EventSequenceComparator implements Comparator<EventSequence> {

	@Override
	public int compare(EventSequence o1, EventSequence o2) {
		// TODO Auto-generated method stub
		return o1.length() - o2.length();
	}

}
