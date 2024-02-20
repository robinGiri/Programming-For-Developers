
// To implement Kruskal's algorithm along with a priority queue using a minimum heap in Java, we'll follow a step-by-step approach. Kruskal's algorithm is a popular minimum spanning tree algorithm that builds the minimum spanning tree of a graph by adding edges in order of increasing length, ensuring no cycles are formed.

// Steps for Kruskal's Algorithm:
// Sort all the edges of the graph in non-decreasing order of their weight.
// Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far. If cycle is not formed, include this edge. Else, discard it.
// Repeat step 2 until there are (V-1) edges in the spanning tree, where V is the number of vertices.
// Priority Queue using Min Heap:
// Java's PriorityQueue class provides the functionality of a minimum heap. When adding edges to the priority queue, they are automatically ordered by their weights (assuming a corresponding Edge class is defined to represent the graph's edges and implements the Comparable interface to determine the order).

// Implementation:
// Let's define the classes needed for Kruskal's algorithm:

// Edge Class: To represent the edges of the graph.
// Disjoint Set Class: To check for cycles when adding edges.
// Kruskal's Algorithm Class: To apply the algorithm using a priority queue.

import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Compare edges based on their weight
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

// Disjoint sets class
class DisjointSet {
    int[] parent, rank;

    DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            // Initially, all vertices are in their own set.
            parent[i] = i;
        }
    }

    // Find the parent of a vertex
    int find(int i) {
        if (parent[i] != i)
            parent[i] = find(parent[i]);
        return parent[i];
    }

    // Union of two sets
    void union(int x, int y) {
        int xRoot = find(x), yRoot = find(y);

        if (xRoot == yRoot)
            return;

        if (rank[xRoot] < rank[yRoot])
            parent[xRoot] = yRoot;
        else if (rank[yRoot] < rank[xRoot])
            parent[yRoot] = xRoot;
        else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }
}

public class Question3B {
    // Function to construct MST using Kruskal's algorithm
    public static void KruskalMST(ArrayList<Edge> edges, int V) {
        // Sort edges in increasing order of cost
        PriorityQueue<Edge> pq = new PriorityQueue<>(edges);

        // Create disjoint sets
        DisjointSet ds = new DisjointSet(V);

        ArrayList<Edge> result = new ArrayList<>(); // This will store the resultant MST

        // Number of edges to be taken is equal to V-1
        while (result.size() < V - 1) {
            Edge next_edge = pq.remove();

            int x = ds.find(next_edge.src);
            int y = ds.find(next_edge.dest);

            // If including this edge doesn't cause cycle, include it in result
            // and move the index for next edge
            if (x != y) {
                result.add(next_edge);
                ds.union(x, y);
            }
            // Else discard the next_edge
        }

        // print the contents of result[] to display the built MST
        for (Edge e : result) {
            System.out.println(e.src + " -- " + e.dest + " == " + e.weight);
        }
    }

    public static void main(String[] args) {
        int V = 4;  // Number of vertices in graph
        ArrayList<Edge> edges = new ArrayList<Edge>();

        // Add edges
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        KruskalMST(edges, V);
    }
}
