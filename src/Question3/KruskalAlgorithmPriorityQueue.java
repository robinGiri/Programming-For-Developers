package Question3;

/*
Strategy: The approach utilizes a priority queue structured as a min heap. In a min heap, the arrangement 
ensures that the parent node holds a value less than that of its offspring, effectively maintaining the heap's 
integrity by comparing parent nodes to their left and right children.

For constructing a minimum spanning tree from a given graph, the Kruskal algorithm is employed. This algorithm 
incorporates a min heap to uphold the heap's characteristics. The initial step in generating a minimum spanning tree 
involves sorting the graph's edges. This sorting is accomplished using heap sort, which arranges the edges in ascending
order. The implementation of heap sort, particularly visible in the heapify function, assures that the edges
are organized from smallest to largest, facilitating the construction of the minimum spanning tree.
*/

public class KruskalAlgorithmPriorityQueue {
    // Class used to create priority queue using min heap
    public static class PriorityQueue {
        // Min heap implementation using array
        int heap[];
        int size;
        int n;

        PriorityQueue(int size) {
            this.size = size;
            heap = new int[size];
            n = -1;
        }

        // This swap function is used to arrange the min heap in the priority queue in ascending order
        void swapPriorityQueue(int arr[], int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        // Function to insert data in the queue
        void insert(int data) {
            n = n+1;
            heap[n] = data;
            int i = n;

            // On inserting a data, it is compared with its parent to see if it is of smaller value or not
            // If it is, it is swapped with the parent so that it comes above the parent, as a min heap should be ordered
            while(i>0) {
                int parentIndex = (i-1)/2;
                if(heap[i] < heap[parentIndex]) {
                    swapPriorityQueue(heap, i, parentIndex);
                    i = parentIndex;
                }
                else {
                    return;
                }
            }
        }

        // Function to extract the top of the priority queue, which is the smallest data
        int extractMin() {
            int temp = heap[0];
            // The top data is swapped with the lowest data in the queue i.e. swap occurs between the smallest and largest data
            heap[0] = heap[n];
            // That smallest data is logically removed from the queue
            heap[n] = 0;
            n = n-1;
            int i = 0;
            int smallestIndex = i;

            // After removing the top data, the next smallest data is determined i.e. the left and right child of the top
            while(i<=n) {
                int leftIndex = 2*i+1;
                int rightIndex = 2*i+2;

                if(leftIndex > n || rightIndex > n) {
                    break;
                }

                if(heap[leftIndex] < heap[rightIndex]) {
                    smallestIndex = leftIndex;
                }
                else {
                    smallestIndex = rightIndex;
                }

                // On finding the next smallest data, it is compared with the top, and if it is smaller, swap occurs to maintain the order of min heap
                if(heap[smallestIndex] < heap[i]) {
                    swapPriorityQueue(heap, smallestIndex, i);
                    i = smallestIndex;
                }
                else {
                    return -1;
                }
            }
            return temp;
        }
    }

    // Class to create edges of the tree
    public static class Edge {
        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    int v;
    Edge edges[];
    int e;

    KruskalAlgorithmPriorityQueue(int v, int e) {
        this.v = v;
        this.e = e;
        edges = new Edge[e];
    }

    int edgeCount = -1;

    // Function to create edges and store them in "edges" array of type Edge class
    void addEdge(int source, int destination, int weight) {
        edges[++edgeCount] = new Edge(source, destination, weight);
    }

    void swapEdge(Edge arr[], int i, int j) {
        Edge temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // The function heapify is used to sort the tree in the order of min heap
    void heapify(Edge arr[], int n, int i) {
        int minIndex = i;
        int leftChild = 2*i+1;
        int rightChild = 2*i+2;

        // It checks if the left child or right child of the current node is smaller
        if (leftChild < n && arr[leftChild].weight < arr[minIndex].weight) {
            minIndex = leftChild;
        }

        if (rightChild < n && arr[rightChild].weight < arr[minIndex].weight) {
            minIndex = rightChild;
        }

        // According to the comparsion, the swap is made
        if(minIndex != i) {
            swapEdge(arr, i, minIndex);
            // Heapify is then recursively called
            heapify(arr, n, minIndex);
        }
    }

    // With the application of min heap, heap sort has been used to sort the tree
    void heapSort(Edge arr[]) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            swapEdge(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    // Function to create minimum spanning tree
    void minimumSpanningTree() {
        int mstGraph[][] = new int[v][v];
        // The edges of the tree is sorted using heap sort, this makes it easier to create the minimum spanning tree
        heapSort(edges);
        int edgeCounter = 0;
        int edgeTaken = 1;
        // Array that carries parent of all vertex
        int parent[] = new int[v];
        // Array that holds the rank of each vertex in a sub tree
        int rank[] = new int[v];

        for(int i = 0; i<v; i++) {
            parent[i] = -1;
        }

        // edgeTaken should go uptp v-1 because the minimum spanning tree must have edges equal to the original number of vertices - 1
        while(edgeTaken <= v-1) {
            Edge edgeInst = edges[edgeCounter++];

            if(cycleDetected(edgeInst.source, edgeInst.destination, parent)) {
                continue;
            }

            // First disjoint sets are added the graph
            mstGraph[edgeInst.source][edgeInst.destination] = edgeInst.weight;
            mstGraph[edgeInst.destination][edgeInst.source] = edgeInst.weight;
            edgeTaken++;
            // The disjoint sets are combined using union function, which uses find function to find the root of the source and destination
            // The union function here determines which set is to be attached to the root of the other set to create the spanning tree
            union(find(edgeInst.source, parent), find(edgeInst.destination, parent), rank, parent);
        }

        for(int i = 0; i<v; i++) {
            for(int j = 0; j<v; j++) {
                System.out.print(mstGraph[i][j] + "");
            }
            System.out.println();
        }
    }

    // This function is used to avoid cycle detections
    boolean cycleDetected(int u, int v, int parent[]) {
        //find absolute root of u - source and v - destination
        return find(u, parent) == find(v, parent);
    }

    // This function is used to find the absolute root of the vertex
    int find(int vertex, int parent[]) {
        //the case where the provided vertex is the absolute root
        if(parent[vertex] == -1) {
            return vertex;
        }

        return parent[vertex] = find(parent[vertex], parent);
    }

    // By checking the rank between two nodes, the one with the higher rank proves to be at a greater depth
    // Thus the higher ranking node becomes the parent of the other node
    void union(int u_absolute, int v_absolute, int rank[], int parent[]) {
        if(rank[u_absolute] > rank[v_absolute]) {
            parent[v_absolute] = u_absolute;
        }
        else if(rank[v_absolute] > rank[u_absolute]) {
            parent[u_absolute] = v_absolute;
        }
        else {
            parent[v_absolute] = u_absolute;
            rank[u_absolute]++;
        }
    }

    public static void main(String[] args) {
        KruskalAlgorithmPriorityQueue ka = new KruskalAlgorithmPriorityQueue(9,14);

        ka.addEdge(0,1,4);
        ka.addEdge(0,7,8);
        ka.addEdge(1,7,11);
        ka.addEdge(1,2,8);
        ka.addEdge(2,8,2);
        ka.addEdge(7,8,7);
        ka.addEdge(7,6,1);
        ka.addEdge(8,6,6);
        ka.addEdge(2,3,7);
        ka.addEdge(2,5,4);
        ka.addEdge(3,5,14);
        ka.addEdge(6,5,2);
        ka.addEdge(3,4,9);
        ka.addEdge(5,4,10);

        ka.minimumSpanningTree();

        System.out.println();

        PriorityQueue pq = new PriorityQueue(5);
        pq.insert(10);
        pq.insert(20);
        pq.insert(5);
        pq.insert(25);
        pq.insert(15);

        System.out.println("Priority queue is: ");
        for(int i = 0; i<pq.size; i++) {
            System.out.print(pq.heap[i] + " ");
        }
        System.out.println();
        System.out.println();

        System.out.println("Top of priority queue is: " + pq.extractMin());
        System.out.println("Updated priority queue is: ");
        for(int i = 0; i<pq.size; i++) {
            System.out.print(pq.heap[i] + " ");
        }
        System.out.println();
    }
}

/*
Output:
000000080
0080000110
080700000
0070014000
0000010000
00014100000
000000006
8110000007
000000670

Priority queue: 
5 15 10 25 20 

Top of priority queue is: 5
Updated priority queue is: 
10 15 20 25 0 
*/

