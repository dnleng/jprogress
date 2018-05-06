package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.Main;
import se.liu.ida.jprogress.formula.Formula;

/**
 * Created by Squig on 06/05/2018.
 */
public class GraphStatus {

    private ProgressionStrategy strategy;
    private int edgeCount;
    private int vertexCount;
    private int ttl;
    private int maxNodes;

    public GraphStatus(ProgressionStrategy strategy, int edgeCount, int vertexCount, int ttl, int maxNodes) {
        this.strategy = strategy;
        this.edgeCount = edgeCount;
        this.vertexCount = vertexCount;
        this.ttl = ttl;
        this.maxNodes = maxNodes;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Graph properties:\n");

	sb.append("Graph strategy\t\t:\t");
	sb.append(this.strategy);
	sb.append("\n");

	sb.append("Component count\t\t:\t");
	sb.append(Formula.getCount());
	sb.append("\n");

	sb.append("Vertex count\t\t:\t");
	sb.append(this.vertexCount);
	sb.append("\n");

	sb.append("Edge count  \t\t:\t");
	sb.append(this.edgeCount);
	sb.append("\n");

	sb.append("Thread count  \t\t:\t");
	sb.append(Main.MAX_THREADS);
	sb.append("\n");

	sb.append("Time-to-live\t\t:\t");
	sb.append(this.ttl);
	sb.append("\n");

	sb.append("Max node bound\t\t:\t");
	sb.append(this.maxNodes);
	sb.append("\n");

	return sb.toString();
    }
}
