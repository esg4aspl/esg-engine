package tr.edu.iyte.esg.conversion;

import java.util.ArrayList;

import java.util.List;

import org.jgrapht.Graph;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexSimple;


public class JGraphToESGConverter {

    /**
     * Builds a new ESGFx instance from a JGraphT Graph<Vertex, Edge>.
     *
     * Note: This assumes the graph already contains your real Vertex and Edge instances.
     */
    public static ESG buildESGFromJGraph(ESG ESG,Graph<Vertex, Edge> graph) {

        List<Vertex> vertices = new ArrayList<>(graph.vertexSet());
        for (Vertex v : vertices) {
        	Event event = new EventSimple(v.getEvent().getID(), v.getEvent().getName());
        	ESG.addEvent(event);
        	Vertex vertex = new VertexSimple(v.getID(), event);
            ESG.addVertex(vertex);
        }

        for (Edge e : graph.edgeSet()) {
            Vertex source = graph.getEdgeSource(e);
            Vertex target = graph.getEdgeTarget(e);
            
            Vertex sourceOnESG = ESG.getVertexByEventName(source.getEvent().getName());
            Vertex targetOnESG = ESG.getVertexByEventName(target.getEvent().getName());

            ESG.addEdge(new EdgeSimple(e.getID(), sourceOnESG, targetOnESG));
        }
        return ESG;
    }
}
