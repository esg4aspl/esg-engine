package tr.edu.iyte.esg.eventsequence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import java.util.Set;

import tr.edu.iyte.esg.model.Vertex;

public class EventSequenceUtilties {

	public static void esgEventSequenceSetPrinter(Set<EventSequence> composedSequences) {
		for (EventSequence es : composedSequences) {
			System.out.println(/* es.length() + " - " + */es);
		}
		System.out.println();
	}

	public static void esgEventSequenceSetPrinter(Set<EventSequence> composedSequences, String header) {
		System.out.println(header);
		for (EventSequence es : composedSequences) {
			System.out.println(/* es.length() + " - " + */ es);
		}
		System.out.println();
	}

	public static void esgEventSequenceSetFileWriter(Set<EventSequence> composedSequences, String filePathAndName)
			throws IOException {
		Writer fileWriter = new FileWriter(filePathAndName);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for (EventSequence es : composedSequences) {
			// System.out.println("ES " + es.toString());
			String eventSequence = "";

			for (int i = 0; i < es.length(); i++) {
				Vertex event = es.getEventSequence().get(i);
				String eventName = event.getEvent().getName().trim().replaceAll(" ", "_");
				;
				if (i == es.length() - 1) {
					eventSequence += eventName;
				} else {
					eventSequence += eventName + ", ";
				}

			}

			printWriter.println(es.length() + " : " + eventSequence);
		}
		printWriter.close();
	}

	public static void esgEventSequenceSetFileWriterForMutationAnalysis(Set<EventSequence> composedSequences,
			String filePathAndName) throws IOException {
		Writer fileWriter = new FileWriter(filePathAndName);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for (EventSequence es : composedSequences) {
			// System.out.println("ES " + es.toString());
			String eventSequence = "";

			for (int i = 0; i < es.length(); i++) {
				Vertex event = es.getEventSequence().get(i);
				String eventName = event.getEvent().getName().trim().replaceAll("\\s", "");
				if (i == es.length() - 1) {
					eventSequence += eventName;
				} else {
					eventSequence += eventName + " ";
				}

			}

			printWriter.println(eventSequence);
		}
		printWriter.close();
	}

	public static List<Integer> indexesOfRepeatedEvent(EventSequence eventSequence, String repetaedEvent) {

		List<Integer> indexList = new ArrayList<Integer>();

		for (int i = 0; i < eventSequence.length(); i++) {
			Vertex vertex = eventSequence.getEventSequence().get(i);
			if (vertex.getEvent().getName().equals(repetaedEvent)) {
				indexList.add(i);
			}
		}

		return indexList;
	}

	public static Set<EventSequence> divideESsWithoutConnectionPointAsPreceeding(EventSequence eventSequence,
			String eventName) {

		Set<EventSequence> esSet = new LinkedHashSet<EventSequence>();

		List<Integer> indexList = indexesOfRepeatedEvent(eventSequence, eventName);

		for (int i = 0; i < indexList.size(); i++) {
			int index1 = indexList.get(i);

			List<Vertex> newES = eventSequence.getEventSequence().subList(0, index1 + 1);
			EventSequence newEventSequence = new EventSequence();
			newEventSequence.setEventSequence(newES);
			esSet.add(newEventSequence);

		}

		return esSet;
	}

	public static Set<EventSequence> divideESsWithoutConnectionPointAsProceeding(EventSequence eventSequence,
			String eventName) {

		Set<EventSequence> esSet = new LinkedHashSet<EventSequence>();

		List<Integer> indexList = indexesOfRepeatedEvent(eventSequence, eventName);
		// System.out.println("eventSequence.length() " + eventSequence.length());
		for (int i = 0; i < indexList.size(); i++) {
			int index = indexList.get(i);
			// System.out.println("index " + index);
			List<Vertex> newES = null;
			if ((index == (eventSequence.length() - 1))) {
				Vertex vertex = eventSequence.getEventSequence().get(index);
				newES = new LinkedList<>();
				newES.add(vertex);
			} else {
				newES = eventSequence.getEventSequence().subList(index, eventSequence.length());
			}
			EventSequence newEventSequence = new EventSequence();
			newEventSequence.setEventSequence(newES);
			esSet.add(newEventSequence);

		}

		return esSet;
	}

	public static boolean endsWith(EventSequence seq, String name) {
		Vertex v = seq.getEventSequence().get(seq.getEventSequence().size() - 1);
		return v.getEvent().getName().compareTo(name) == 0;
	}

	public static int getFirstIndexByEventName(EventSequence seq, String name) {
		int index = -1;
		int i = 0;
		Iterator<Vertex> itr = seq.getEventSequence().iterator();
		while (itr.hasNext() && index == -1) {
			Vertex v = itr.next();
//System.out.println("v.getEvent().getName() " + v.getEvent().getName() +":" + v.getEvent().getName().length());
//System.out.println("name "+name + ":" + name.length());
			if (v.getEvent().getName().compareTo(name) == 0) {
				index = i;
			}
			i++;
		}
		return index;
	}

	public static int getLastIndexByEventName(EventSequence seq, String name) {
		int index = -1;
		int i = seq.getEventSequence().size() - 1;
		ListIterator<Vertex> itr = seq.getEventSequence().listIterator(i + 1);
		while (itr.hasPrevious() && index == -1) {
			Vertex v = itr.previous();
			if (v.getEvent().getName().compareTo(name) == 0) {
				index = i;
			}
			i--;
		}
		return index;
	}

	public static int indexOfEvent(String eventName, EventSequence eventSequence) {

		int index = -1;
		Iterator<Vertex> eventSequenceIterator = eventSequence.getEventSequence().iterator();

		while (eventSequenceIterator.hasNext()) {
			Vertex current = eventSequenceIterator.next();
			if (current.getEvent().getName().equals(eventName)) {
				index = eventSequence.getEventSequence().indexOf(current);
				break;
			}
		}

		return index;

	}

	public static boolean eventSequenceContainsEvent(String eventName, EventSequence eventSequence) {
		boolean b = false;
		Iterator<Vertex> eventSequenceIterator = eventSequence.getEventSequence().iterator();

		while (eventSequenceIterator.hasNext()) {
			Vertex current = eventSequenceIterator.next();
			if (current.getEvent().getName().equals(eventName)) {
				b = true;
			}
		}

		return b;
	}

	public static List<Vertex> removeFinishEvent(EventSequence eventSequence) {
		List<Vertex> newSequence = eventSequence.getSubsequence(0, eventSequence.length() - 2);

		return newSequence;
	}

	public static List<Vertex> removeStartEvent(EventSequence eventSequence) {
		List<Vertex> newSequence = eventSequence.getSubsequence(1, eventSequence.length() - 1);

		return newSequence;
	}

	public static List<Vertex> connectTwoEventSequences(EventSequence eventSequence1, EventSequence eventSequence2) {
		List<Vertex> newSequence = new LinkedList<Vertex>();

		newSequence.addAll(eventSequence1.getEventSequence());
		newSequence.addAll(eventSequence2.getEventSequence());

		return newSequence;

	}
}
