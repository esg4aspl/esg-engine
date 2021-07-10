package tr.edu.iyte.esg.conversion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;

public class ESGToRRGFileConversion {

	private static String tripleUnderscore = "___";

	public static void convertESGToRRGFile(ESG ESG, String filePath) {
		File file = new File(filePath + ESG.getName() + "_rrg-1seq-tb.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Vertex pseudoStartVertex = ESG.getPseudoStartVertex();
		String pseudoStartEventName = pseudoStartVertex.getEvent().getName().replaceAll("\\s", "");

		try {
			fileWriter.write("S -> " + pseudoStartEventName + tripleUnderscore + pseudoStartVertex.getID() + " nt("
					+ pseudoStartVertex.getEvent().getName() + tripleUnderscore + pseudoStartVertex.getID() + ")\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Vertex pseudoEndVertex = ESG.getPseudoEndVertex();
		String pseudoEndEventName = pseudoEndVertex.getEvent().getName().replaceAll("\\s", "");

		try {
			fileWriter.write(
					"nt(" + pseudoEndEventName + tripleUnderscore + pseudoEndVertex.getID() + ") -> <epsilon>\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Iterator<Edge> edgeListIterator = ESG.getEdgeList().iterator();
		while (edgeListIterator.hasNext()) {
			Edge currentEdge = edgeListIterator.next();
			Vertex source = currentEdge.getSource();
			Vertex target = currentEdge.getTarget();

			try {
				fileWriter.write(getTextOfVertices(source, target));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void convertESGToRRGFileWithoutPseudoEvents(ESG ESG, String filePath) {
		File file = new File(filePath + /*ESG.getName() + */"_rrg-1seq-tb.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator<Edge> edgeListIterator = ESG.getEdgeList().iterator();
		while (edgeListIterator.hasNext()) {
			Edge currentEdge = edgeListIterator.next();
			Vertex source = currentEdge.getSource();
			Vertex target = currentEdge.getTarget();
			String sourceEventName = source.getEvent().getName().replaceAll("\\s", "");
			String targetEventName = target.getEvent().getName().replaceAll("\\s", "");

			if (source.isPseudoStartVertex()) {
				try {
					fileWriter.write("S -> " + targetEventName + tripleUnderscore + target.getID() + " nt("
							+ targetEventName + tripleUnderscore + target.getID() + ")\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (target.isPseudoEndVertex()) {
				try {
					fileWriter.write("nt(" + sourceEventName + tripleUnderscore + source.getID() + ") -> <epsilon>\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				try {
					fileWriter.write(getTextOfVertices(source, target));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getTextOfVertices(Vertex source, Vertex target) {
		String sourceEventName = source.getEvent().getName().replaceAll("\\s", "");
		String targetEventName = target.getEvent().getName().replaceAll("\\s", "");

		return "nt(" + sourceEventName + tripleUnderscore + source.getID() + ") -> " + targetEventName
				+ tripleUnderscore + target.getID() + " nt(" + targetEventName + tripleUnderscore + target.getID()
				+ ")\n";
	}

}
