import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PathToSat {

    public static BufferedReader reader;
    public static BufferedWriter writer;
    public static List<String> clauses;

    public static void transformPathInstance(Graph graph) throws IOException {
        clauses = new ArrayList<>();
        String clause;

        // Generate clauses that assure the "completeness" of the path
        for (int j = 1; j < graph.adjacencyList.size(); j++) {
            clause = "(";

            for (int i = 1; i < graph.adjacencyList.size(); i++) {
                clause += "x" + i + "-" + j;

                if (i == graph.adjacencyList.size() - 1) {
                    clause += ")";
                } else {
                    clause += "|";
                }
            }
            clauses.add(clause);
        }

        // Generate clauses that assure the "uniqueness" of the literals within the path
        for (int j = 1; j < graph.adjacencyList.size(); j++) {
            for (int k = 1; k < graph.adjacencyList.size(); k++) {
                for (int i = 1; i < graph.adjacencyList.size(); i++) {

                    if (k < i) {
                        clause = "(~x" + k + "-" + j;
                        clause += "|~x" + i + "-" + j + ")";
                        clauses.add(clause);
                    }
                }
            }
        }

        // Generate clauses that assure the consistency of the path
        for (int i = 1; i < graph.adjacencyList.size(); i++) {
            clause = "(";

            for (int j = 1; j < graph.adjacencyList.size(); j++) {
                clause += "x" + i + "-" + j;

                if (j == graph.adjacencyList.size() - 1) {
                    clause += ")";
                } else {
                    clause += "|";
                }
            }
        }

        // Generate clauses that assure the "uniqueness" of the positions within the path
        for (int i = 1; i < graph.adjacencyList.size(); i++) {
            for (int j = 1; j < graph.adjacencyList.size(); j++) {
                for (int k = 1; k < graph.adjacencyList.size(); k++) {

                    if (j < k) {
                        clause = "(~x" + i + "-" + j;
                        clause += "|~x" + i + "-" + k + ")";
                        clauses.add(clause);
                    }
                }
            }
        }

        // Generate clauses that assure the "correctness" of the adjacency within the path
        for (int i = 1; i < graph.adjacencyList.size(); i++) {
            for (int j = 1; j < graph.adjacencyList.size(); j++) {

                if (!graph.adjacencyList.get(i).contains(j) && j != i) {
                    for (int k = 1; k < graph.adjacencyList.size() - 1; k++) {
                        clause = "(~x" + k + "-" + i;
                        clause += "|~x" + (k + 1) + "-" + j + ")";
                        clauses.add(clause);
                    }
                }
            }
        }

        String conjunctiveNormalForm = "";
        for (int i = 0; i < clauses.size(); i++) {
            conjunctiveNormalForm += clauses.get(i);

            if (i != clauses.size() - 1) {
                conjunctiveNormalForm += "&";
            }
        }

        try {
            writer.write(conjunctiveNormalForm);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        writer.close();
    }


    public static void main(String[] args) throws IOException {

        List<Edge> edges = new ArrayList<>();
        int nodes = 0;

        try {
            File input = new File("graph.in");
            reader = new BufferedReader(new FileReader(input));

            File output = new File("bexpr.out");
            writer = new BufferedWriter(new FileWriter(output));

            String []tokens;
            String line = reader.readLine();
            nodes = Integer.parseInt(line);

            while ((line = reader.readLine()) != null) {
                if (line.equals("-1")) break;

                tokens = line.split(" ");
                int src = Integer.parseInt(tokens[0]);
                int dst = Integer.parseInt(tokens[1]);
                edges.add(new Edge(src, dst));
            }
            reader.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Graph graph = new Graph(edges, nodes);              // Init graph with the adjacency list
        graph = CycleToPath.transformCycleInstance(graph);  // The transformation for the HCP <= HP polynomial reduction
        transformPathInstance(graph);                       // The transformation for the HP <= SAT polynomial reduction
                                                            // The output is redirected in "bexpr.out"
    }
}
