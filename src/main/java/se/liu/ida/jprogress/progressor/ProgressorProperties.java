package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.Main;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.progressor.ProgressionStrategy;

/**
 * Created by Squig on 06/05/2018.
 */
public class ProgressorProperties {

    private ProgressionStrategy strategy;
    private int edgeCount;
    private int vertexCount;
    private int componentCount;
    private int ttl;
    private int maxNodes;

    public ProgressorProperties(ProgressionStrategy strategy, int edgeCount, int vertexCount, int componentCount, int ttl, int maxNodes) {
        this.strategy = strategy;
        this.edgeCount = edgeCount;
        this.vertexCount = vertexCount;
        this.componentCount = componentCount;
        this.ttl = ttl;
        this.maxNodes = maxNodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Progressor properties:\n");

        sb.append("Strategy\t\t:\t");
        sb.append(this.strategy);
        sb.append("\n");

        sb.append("Component count\t\t:\t");
        sb.append(this.componentCount);
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
        sb.append(this.ttl == Integer.MAX_VALUE ? "Inf" : this.ttl);
        sb.append("\n");

        sb.append("Max node bound\t\t:\t");
        sb.append(this.maxNodes == Integer.MAX_VALUE ? "Inf" : this.maxNodes);
        sb.append("\n");

        return sb.toString();
    }
}
