package tr.edu.iyte.esg.model.comparators;

import java.util.Comparator;
import java.util.Iterator;

import tr.edu.iyte.esg.model.sequenceesg.Sequence;

public class SequenceComparator<T> implements Comparator<Sequence<T>> {
	private Comparator<T> comparator;
	
	public SequenceComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public int compare(Sequence<T> arg0, Sequence<T> arg1) {
		int i = 0;
		Iterator<T> i1 = arg0.iterator();
		Iterator<T> i2 = arg1.iterator();
		int cval = 0;
		while(i1.hasNext() && i2.hasNext() && (cval==0)) {
			i++;
			T e1 = i1.next();
			T e2 = i2.next();
			cval = comparator.compare(e1, e2);
		}
		if(cval > 0) {
			return i;
		}
		else if(cval < 0) {
			return -i;
		}
		else {
			if(i1.hasNext()) {
				return i;
			}
			else if(i2.hasNext()) {
				return -i;
			}
			else {
				return 0;	
			}
		}
	}
}
