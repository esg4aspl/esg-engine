package tr.edu.iyte.esg.model.sequenceesg;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sequence<T> implements Iterable<T> {
	private List<T> sequence;
	
	public Sequence() {
		sequence = new LinkedList<T>();
	}
	
	public boolean addElement(T e) {
		return sequence.add(e);
	}
	
	public T getElement(int i) {
		return sequence.get(i);
	}
	
	public int getSize() {
		return sequence.size();
	}
	
	public Iterator<T> iterator() {
		return sequence.iterator();
	}
	
	public boolean addElements(Sequence<T> s) {
		boolean z = true;
		boolean b;
		for(T e : s) {
			b = sequence.add(e);
			z = z && b;
		}
		return z;
	}
	
	public void addElements(Sequence<T> s, int n) {
		boolean z = true;
		boolean b;
		Iterator<T> itr = s.iterator();
		int i=0;
		while(itr.hasNext() && i<n && z) {
			T e = itr.next();
			b = sequence.add(e);
			z = z && b;
			i++;
		}
	}
	
	public void addElementsAfterSkip(Sequence<T> s, int n, int skip) {
		boolean z = true;
		boolean b;
		Iterator<T> itr = s.iterator();
		int i=0;
		while(i<skip && itr.hasNext()) {
			itr.next();
			i++;
		}
		i = 0;
		while(itr.hasNext() && i<n && z) {
			T e = itr.next();
			b = sequence.add(e);
			z = z && b;
			i++;
		}
	}
	
	public T replace(int i, T e) {
		T z = sequence.remove(i);
		sequence.add(i, e);
		return z;
	}
}
