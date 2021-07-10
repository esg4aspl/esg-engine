package tr.edu.iyte.esg.model.validation;

import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexSimple;
import tr.edu.iyte.esg.model.VertexRefinedByESG;
import tr.edu.iyte.esg.model.VertexRefinedByDT;

public class VertexValidator extends AbstractValidator implements IValidator<Vertex> {

	public VertexValidator() {
		super();
	}

	@Override
	public ValidationResult validate(Vertex context) {
		//TODO refactor using polymorphism
		if (context instanceof VertexSimple) {
			// System.out.println("VertexSimple");
			VertexSimpleValidator vertexSimpleValidator = new VertexSimpleValidator();
			return vertexSimpleValidator.validate((VertexSimple)context);
		}
		if (context instanceof VertexRefinedByESG) {
			// System.out.println("VertexRefinedByESG");
			VertexRefinedByESGValidator vertexRefinedByESGValidator = new VertexRefinedByESGValidator();
			return vertexRefinedByESGValidator.validate((VertexRefinedByESG)context);
		}
		if (context instanceof VertexRefinedByDT) {
			// System.out.println("VertexRefinedByDT");
			VertexRefinedByDTValidator vertexRefinedByDTValidator = new VertexRefinedByDTValidator();
			return vertexRefinedByDTValidator.validate((VertexRefinedByDT)context);
		}
		return null;
	}

}

