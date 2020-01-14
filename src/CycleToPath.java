import java.util.ArrayList;
import java.util.List;

public class CycleToPath {

    public static Graph transformedInstance;
    public static List<List<Integer>> paths = new ArrayList<>();

    public static Graph transformCycleInstance(Graph graph) {

        for (int u = 1; u < graph.adjacencyList.size(); u++) {
            for (int v: graph.adjacencyList.get(u)) {
                CycleToPath.transformedInstance = new Graph(graph.edges, graph.adjacencyList.size() - 1);

                int x = graph.adjacencyList.size();
                int y = graph.adjacencyList.size() + 1;

                transformedInstance.edges.add(new Edge(x, u));
                transformedInstance.edges.add(new Edge(y, v));
                transformedInstance = new Graph(transformedInstance.edges, y);

                int index = transformedInstance.adjacencyList.get(u).indexOf(v);// Get the index from the list
                transformedInstance.adjacencyList.get(u).remove(index);         // Delete an arbitrary edge

                index = transformedInstance.adjacencyList.get(v).indexOf(u);    // We need to remove it from both indexes
                transformedInstance.adjacencyList.get(v).remove(index);

                int nodes = transformedInstance.adjacencyList.size();
                boolean []visited = new boolean[nodes];

                List<Integer> path = new ArrayList<>();
                path.add(x);
                visited[x] = true;

                HamiltonianPath.getPaths(transformedInstance, x, visited, path, nodes - 1); // This function outputs to stdout the paths
                                                                                            // only when the graph-instance has a cycle
            }
        }
        return transformedInstance;
    }
}
