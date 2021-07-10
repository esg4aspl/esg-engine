package tr.edu.iyte.esg.applications;

import org.jgrapht.Graph;

import tr.edu.iyte.esg.conversion.json.JSONFileToModelConverterWithSchemaValidation;
import tr.edu.iyte.esg.esgbalancing.StronglyConnectedBalancedESGGenerator;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.Vertex;

public class StronglyConnectedAndBalancedESGGenerationApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String schemaFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertexSchema.json";
		String JSONFile = "files/JSONFiles/InputContractTestingOfGUIsFigure2.json";

		JSONFileToModelConverterWithSchemaValidation jsonFileToModelConverterWithSchemaValidation = new JSONFileToModelConverterWithSchemaValidation(
				schemaFile, JSONFile);

		jsonFileToModelConverterWithSchemaValidation.JSONSchemaValidation();
		Model model = jsonFileToModelConverterWithSchemaValidation.JSONFileToModelConverter();

		StronglyConnectedBalancedESGGenerator balancedESGGenerator = new StronglyConnectedBalancedESGGenerator();

		for (ESG ESG : model.getEsgList()) {

			Graph<Vertex, Edge> balancedAndStronglyConnectedESG = balancedESGGenerator.generateBalancedAndStronglyConnectedESG(ESG);

			System.out.print(balancedAndStronglyConnectedESG.toString());

			for (Vertex vertex : balancedAndStronglyConnectedESG.vertexSet()) {
				System.out.println(vertex.getID() +" " + vertex.getEvent().getName() + " degree: " + vertex.getDegree());

			}

		}

	}
}
