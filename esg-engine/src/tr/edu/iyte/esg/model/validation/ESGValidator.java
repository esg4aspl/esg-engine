package tr.edu.iyte.esg.model.validation;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import tr.edu.iyte.esg.model.*;

public class ESGValidator extends AbstractValidator implements IValidator<ESG> {

	Set<String> eventNameSet = new LinkedHashSet<String>();

	@Override
	public ValidationResult validate(ESG context) {
		validationResult.addAll(validateEvents(context.getEventList()));
		System.out.println("ESG Events " + validationResult);

		validationResult.addAll(validateVertices(context.getVertexList()));
		System.out.println("ESG Vertices " + validationResult);

		validationResult.addAll(validateEdges(context.getEdgeList()));
		System.out.println("ESG Edges " + validationResult);

		validationResult.add(checkHasStartVertex(context));
		System.out.println("ESG Start Vertex " + validationResult);

		validationResult.add(checkHasEndVertex(context));
		System.out.println("ESG End Vertex " + validationResult);

		validationResult.addAll(allVerticesReachingEndVertex(context));
		System.out.println("ESG All Vertices Reaching End " + validationResult);

		validationResult.addAll(startVertexReachingAllVertices(context));
		System.out.println("ESG Start Reaching All vertices " + validationResult);

		return validationResult;
	}

	private ValidationResult validateEvents(List<Event> eventList) {
		ValidationResult eventsValidationResult = new ValidationResult();
		EventValidator eventValidator = new EventValidator();
		for (Event event : eventList) {
			eventsValidationResult.addAll(eventValidator.validate(event));
		}
		eventsValidationResult.add(checkEventUniqueness(eventList));
		return eventsValidationResult;
	}

	private ValidationFailure checkEventUniqueness(List<Event> eventList) {
		Set<Event> uniqueEvents = new LinkedHashSet<Event>();
		for (Event event : eventList) {
			uniqueEvents.add(event);
		}
		if (!(eventList.size() == uniqueEvents.size())) {
			return new ValidationFailure("EventList", "Events are not unique",
					"eventList.size() == uniqueEvents.size()");
		}
		// TODO improve checking uniqueness by naming events that are not unique
		return null;
	}

	private ValidationResult validateVertices(List<Vertex> vertexList) {
		ValidationResult verticesValidationResult = new ValidationResult();
		VertexValidator vertexValidator = new VertexValidator();
		for (Vertex vertex : vertexList) {
			verticesValidationResult.addAll(vertexValidator.validate(vertex));
		}
		verticesValidationResult.add(checkVertexUniqueness(vertexList));
		return verticesValidationResult;
	}

	private ValidationFailure checkVertexUniqueness(List<Vertex> vertexList) {
		Set<Vertex> uniqueVertices = new LinkedHashSet<Vertex>();
		for (Vertex vertex : vertexList) {
			uniqueVertices.add(vertex);
		}
		if (!(vertexList.size() == uniqueVertices.size())) {
			return new ValidationFailure("VertexList", "Vertices are not unique",
					"vertexList.size() == uniqueVertices.size()");
		}
		// TODO improve checking uniqueness by naming vertices that are not unique
		return null;
	}

	private ValidationResult validateEdges(List<Edge> edgeList) {
		ValidationResult edgesValidationResult = new ValidationResult();
		EdgeValidator edgeValidator = new EdgeValidator();
		for (Edge edge : edgeList) {
			edgesValidationResult.addAll(edgeValidator.validate(edge));
		}
		edgesValidationResult.add(checkEdgeUniqueness(edgeList));
		return edgesValidationResult;
	}

	private ValidationFailure checkEdgeUniqueness(List<Edge> edgeList) {
		Set<Edge> uniqueEdges = new LinkedHashSet<Edge>();
		for (Edge edge : edgeList) {
			uniqueEdges.add(edge);
		}
		if (!(edgeList.size() == uniqueEdges.size())) {
			return new ValidationFailure("EdgeList", "Edges are not unique", "edgeList.size() == uniqueEdges.size()");
		}
		// TODO improve checking uniqueness by naming edges that are not unique
		return null;
	}

	private ValidationFailure checkHasStartVertex(ESG context) {
		int numberOfStartVertex = 0;
		for (Vertex vertex : context.getVertexList()) {
			if (vertex.isPseudoStartVertex())
				numberOfStartVertex++;
		}
		switch (numberOfStartVertex) {
		case (1):
			return null;
		case (0):
			return new ValidationFailure("ESG", "ESG does not have start vertex", context);
		default:
			return new ValidationFailure("ESG", "ESG has more than one start vertex", context);
		}
	}

	private ValidationFailure checkHasEndVertex(ESG context) {
		int numberOfEndVertex = 0;
		for (Vertex vertex : context.getVertexList()) {
			if (vertex.isPseudoEndVertex())
				numberOfEndVertex++;
		}
		switch (numberOfEndVertex) {
		case (1):
			return null;
		case (0):
			return new ValidationFailure("ESG", "ESG does not have end vertex", context);
		default:
			return new ValidationFailure("ESG", "ESG has more than one end vertex", context);
		}
	}

	private boolean checkSourceVertexReachingTargetVertex(Vertex source, Vertex target, ESG context) {

		boolean visited[] = new boolean[context.getVertexList().size()];
		Queue<Vertex> queue = new LinkedList<Vertex>();
		visited[source.getID()] = true;
		queue.add(source);

		while (queue.size() != 0) {
			source = queue.poll();
			//System.out.println("POLLED " + source.toString());

			Vertex adjacent;
			List<Vertex> adjacencyList = new LinkedList<Vertex>();
			Set<Vertex> adjacencySet = context.getVertexMap().get(source);
			//System.out.println("adjacencySet == null " + adjacencySet == null);
			if(adjacencySet == null && !source.isPseudoEndVertex()) {
				return false;
			}else {
				adjacencyList.addAll(adjacencySet);
				//System.out.print("adjacency List of polled: ".toUpperCase());
				//adjacencyList.forEach(e->System.out.print(e + " "));
				//System.out.println();
				Iterator<Vertex> adjacentIterator = adjacencyList.iterator();

				while (adjacentIterator.hasNext()) {

					adjacent = adjacentIterator.next();
					//System.out.println("ADJACENT " + adjacent.toString());

					if (adjacent.equals(target)) {
						return true;
					}

					if (!visited[adjacent.getID()] && !adjacent.isPseudoEndVertex()) {
						//System.out.println(adjacent.toString() + " VISITED");
						visited[adjacent.getID()] = true;
						queue.add(adjacent);
					}
				}
			}
		}

		return false;
	}

	@SuppressWarnings("unused")
	private boolean checkSourceVertexReachingTargetVertex(Vertex source, Vertex target,
			Map<Vertex, Set<Vertex>> vertexMap) {
		System.out.print( source.toString() + "->");
		System.out.println(target.toString());
		if (source == null) {
			return false;
		}
		if (target == null) {
			return false;
		}
		if (vertexMap.isEmpty()) {
			return false;
		} else {

			Set<Vertex> targetVertices = vertexMap.get(source);

			boolean isValid = false;
			if(targetVertices == null) {
				System.out.println("Target Vertices Set is null");
				return false;
			}
			if (targetVertices.isEmpty() && !source.isPseudoEndVertex()) {
				System.out.println("Target Vertices Set is empty");
				return false;
			} else {
				if (targetVertices.contains(target)) {

					return true;
				} else {

					for (Vertex newSource : targetVertices) {
						isValid = checkSourceVertexReachingTargetVertex(newSource, target, vertexMap);
						if (isValid)
							break;
					}
				}
			}
			return isValid;
		}

	}

	private ValidationResult allVerticesReachingEndVertex(ESG context) {
		ValidationResult allVerticesReachingEndVertexValidationResult = new ValidationResult();
		Vertex endVertex = context.getPseudoEndVertex();

		boolean isAllVerticesReachingEndVertex = true;

		for (Vertex source : context.getVertexList()) {
			if (source.equals(context.getPseudoEndVertex()))
				continue;
			isAllVerticesReachingEndVertex = isAllVerticesReachingEndVertex && checkSourceVertexReachingTargetVertex(source, endVertex, context) ;
			if (!isAllVerticesReachingEndVertex) {
				allVerticesReachingEndVertexValidationResult.add(new ValidationFailure(
						"Vertex with id " + source.getID() + " ", "Vertex does not reach to EndVertex", context));
			}
		}
		if (!isAllVerticesReachingEndVertex) {
			allVerticesReachingEndVertexValidationResult
					.add(new ValidationFailure("ESG", "All vertices do not reach to end vertex", context));
		}
		return allVerticesReachingEndVertexValidationResult;
	}

	private ValidationResult startVertexReachingAllVertices(ESG context) {
		ValidationResult startVertexReachingAllVerticesValidationResult = new ValidationResult();
		Vertex startVertex = context.getPseudoStartVertex();
		// Map<Vertex, Set<Vertex>> vertexMap = context.getVertexMap();
		boolean isStartVertexReachingAllVertices = true;

		for (Vertex target : context.getVertexList()) {
			if (target.equals(context.getPseudoStartVertex()))
				continue;

			isStartVertexReachingAllVertices = isStartVertexReachingAllVertices
					&& checkSourceVertexReachingTargetVertex(startVertex, target, context);

			if (!isStartVertexReachingAllVertices) {

				startVertexReachingAllVerticesValidationResult.add(new ValidationFailure(
						"Vertex with id " + target.getID() + " ", "StartVertex does not reach to vertex", context));

			}
		}
		if (!isStartVertexReachingAllVertices) {
			startVertexReachingAllVerticesValidationResult
					.add(new ValidationFailure("ESG", "Start does not reach to all vertices", context));
		}
		return startVertexReachingAllVerticesValidationResult;
	}
	
	public boolean isValid(ESG context) {
		Vertex startVertex = context.getPseudoStartVertex();		
		boolean isStartVertexReachingAllVertices = true;
		
		Vertex endVertex = context.getPseudoEndVertex();		
		boolean isAllVerticesReachingEndVertex = true;
		
		for (Vertex vertex : context.getVertexList()) {
			if (!vertex.isPseudoStartVertex()) {
				isStartVertexReachingAllVertices = isStartVertexReachingAllVertices && checkSourceVertexReachingTargetVertex(startVertex, vertex, context);
			}
			if (!vertex.isPseudoEndVertex()) {
				isAllVerticesReachingEndVertex = isAllVerticesReachingEndVertex
						&& checkSourceVertexReachingTargetVertex(vertex, endVertex, context);
			}
		}
		
		return isStartVertexReachingAllVertices && isAllVerticesReachingEndVertex;
	}

}
