import java.util.ArrayList;
import java.util.List;

public class Graph implements Cloneable{
    public List<List<Integer>> adjacencyList;
    public List<Edge> edges;

    public Graph(List<Edge> edges, int N) {
        this.adjacencyList = new ArrayList<>(N);
        this.edges = new ArrayList<>(); // Constructor copies the edges
        this.edges.addAll(edges);       // In order to protect the reference

        for (int i = 0; i <= N; i++) {
            this.adjacencyList.add(i, new ArrayList<>());
        }

        for (Edge edge: edges) {
            int src = edge.source;
            int dst = edge.destination;

            this.adjacencyList.get(src).add(dst);
            this.adjacencyList.get(dst).add(src);
        }
    }
}
