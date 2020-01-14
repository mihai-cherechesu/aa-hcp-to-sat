import java.util.ArrayList;
import java.util.List;

public class HamiltonianPath {

    public static void getPaths(Graph graph, int source, boolean []visited,
                                List<Integer> path, int nodes) {

        // Check if all vertices have been visited
        // If yes, then the hamiltonian path exists
        if (path.size() == nodes) {
            List<Integer> tmp = new ArrayList<>();
            tmp.addAll(path);
            CycleToPath.paths.add(tmp);

            System.out.println("Path: " + path);
            return;
        }

        for (int vertex: graph.adjacencyList.get(source)) {

            if (!visited[vertex]) {
                visited[vertex] = true;

                path.add(vertex);
                getPaths(graph, vertex, visited, path, nodes);

                visited[vertex] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}
