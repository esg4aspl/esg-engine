package tr.edu.iyte.esg.conversion.dot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexSimple;

public class DOTFileToESGConverter {

	public static ESG parseDOTFileForESGCreation(String filePath) {

		File file = new File(filePath);
		@SuppressWarnings("unused")
		Reader reader = null;
		ESG ESG = new ESG(0, filePath);
		Map<String, Vertex> tagVertexMap = new LinkedHashMap<String, Vertex>();

		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;

		try {
			while ((line = br.readLine()) != null) {
				if (line.startsWith("esg")) {
					if (!line.endsWith(";")) {
						String[] lineArray = line.split(" ");
						String vertexTag = lineArray[0];
						//System.out.println(vertexTag);
						String s = lineArray[lineArray.length - 1];
						String s1 = s.substring(s.indexOf("\"") + 1);
						s1.trim();
						String s2 = s1.substring(0, s1.indexOf("\""));
						s2.trim();
						//System.out.println(s2);
						Event event = new EventSimple(ESG.getNextEventID(), s2);
						ESG.addEvent(event);
						Vertex vertex = new VertexSimple(ESG.getNextVertexID(), event);
						ESG.addVertex(vertex);
						tagVertexMap.put(vertexTag.trim(), vertex);

					}
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br2 = null;
		try {
			br2 = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line2;
		try {
			while ((line2 = br2.readLine()) != null) {
				if (line2.startsWith("esg")) {
					if (line2.endsWith(";")) {
						String[] lineArray = line2.split("->");
						String sourceTag = lineArray[0];
						String targetTag = lineArray[1].substring(0, lineArray[1].length()-1);
						//System.out.println(sourceTag + "->" + targetTag);
						Vertex source = tagVertexMap.get(sourceTag.trim());
						Vertex target = tagVertexMap.get(targetTag.trim());
						Edge edge = new EdgeSimple(ESG.getNextEdgeID(), source, target);
						ESG.addEdge(edge);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(ESG.toString());
		return ESG;
	}


}
