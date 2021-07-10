package tr.edu.iyte.esg.conversion.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import tr.edu.iyte.esg.model.ESG;


public class ESGToCSVFileConverter {
	
	
	public static void writeESGSetToCSVFile(List<List<String>> rows, String filePathAndName) throws IOException {
		FileWriter csvWriter = null;
		try {
			csvWriter = new FileWriter(filePathAndName + ".csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		csvWriter.append("id");
		csvWriter.append(",");
		csvWriter.append("name");
		csvWriter.append(",");
		csvWriter.append("#OfVertices");
		csvWriter.append(",");
		csvWriter.append("#OfRealVertices");
		csvWriter.append(",");
		csvWriter.append("#OfEdges");
		csvWriter.append(",");
		csvWriter.append("#OfRealEdges");
		csvWriter.append(",");
		csvWriter.append("\n");
		
		for (List<String> rowData : rows) {
		    csvWriter.append(String.join(",", rowData));
		    csvWriter.append("\n");
		}

		csvWriter.flush();
		csvWriter.close();
	}
		
	public static List<List<String>> fillRowsForESGSet(Set<ESG> ESGSet) {
		List<List<String>> rows = new LinkedList<List<String>>();
		
		for(ESG ESG: ESGSet) {
			List<String> row = new LinkedList<String>();
			String id = Integer.toString(ESG.getID());
			String name = ESG.getName();
			String numberOfVertices = Integer.toString(ESG.getVertexList().size());
			String numberOfRealVertices = Integer.toString(ESG.getRealVertexList().size());
			String numberOfEdges = Integer.toString(ESG.getEdgeList().size());
			String numberOfRealEdges = Integer.toString(ESG.getRealEdgeList().size());
			
			row.add(id);
			row.add(name);
			row.add(numberOfVertices);
			row.add(numberOfRealVertices);
			row.add(numberOfEdges);
			row.add(numberOfRealEdges);
			
			rows.add(row);
		}
		
		
		return rows;
	}
}
