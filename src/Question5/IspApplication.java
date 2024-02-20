package Question5;

import java.util.ArrayList;
import java.util.List;

/*
Strategy: The task requires identifying devices affected by a power outage in a connected network. Initially, 
a matrix termed 'network' is established to represent the connections among various devices. Subsequently, 
devices with direct connections to the affected device are pinpointed. Following this identification, a 
depth-first search (DFS) is conducted for each directly connected device to ascertain their linkage to the primary 
affected device. Should any of these devices lack a pathway to the primary device, they, along with any devices 
encountered during the DFS, are cataloged as impacted. Furthermore, a supplementary function aids in gathering 
devices that are either directly or indirectly linked to the primary device, facilitating their inclusion in 
the DFS evaluation.
*/

public class IspApplication {
    // Number of devices in the network
    int v;
    // Adjacent matrix
    int network[][];

    IspApplication(int v) {
        this.v = v;
        network = new int[v][v];
    }

    // Function to add edge between connected devices
    // undirected graph
    public void addEdge(int source, int destination){
        network[source][destination]=1;
        network[destination][source] = 1;
    }

    public void printNetwork() {
        for(int i = 0; i< network.length; i++) {
            for(int j = 0; j<network[i].length; j++) {
                System.out.print(network[i][j]);
            }
            System.out.println();
        }
    }

    // Function to find the devices directly connected to the target or source
    List<Integer> getConnectedDevices(int root){
        List<Integer> connectedDevices=new ArrayList<>();
        for(int j=0; j<v;j++){
            if(network[root][j]!=0 || network[j][root]!=0){
                connectedDevices.add(j);
            }
        }
        return connectedDevices;
    }

    // Function to find the impacted devices
    public List<Integer> findImpactedDevices(int targetDevice, int source) {
        List<Integer> connectedDevicesToTarget = getConnectedDevices(targetDevice);
        List<Integer> connectedDevicesToSource = getConnectedDevices(source);
        List<Integer> impactedDevices = new ArrayList<>();
        boolean visited[] = new boolean[v];

        // The source and its direct connections are marked as true to avoid them being added to the impactedDevice list
        visited[source] = true;
        for (int device : connectedDevicesToSource) {
            visited[device] = true;
        }

        // Traverse the connected devices excluding source and its direct connections
        for (int device : connectedDevicesToTarget) {
            if (!visited[device]) {
                dfsCheckSource(device, visited, impactedDevices, source, targetDevice);
            }
        }

        return impactedDevices;
    }

    private void dfsCheckSource(int node, boolean visited[], List<Integer> impactedDevices, int source, int targetDevice) {
        visited[node] = true;

        // If the node happens to be the targetDevice, which is not an impacted device
        if (node == targetDevice) {
            return;
        }

        // To check direct/indirect connections to the source
        boolean connectedToSource = false;

        for (int i = 0; i < v; i++) {
            if ((network[node][i] != 0 || network[i][node] != 0) && !visited[i]) {
                // DFS function called recursively
                dfsCheckSource(i, visited, impactedDevices, source, targetDevice);
                // If the iteration variable i is connected to source, directly or undirectly, connectedToSource becomes true
                if (isConnectedToSource(i, source, visited)) {
                    connectedToSource = true;
                }
            }
        }

        // If the node is not directly or indirectly connected to the source, it is added to the impactedDevices list
        if (!connectedToSource) {
            impactedDevices.add(node);
        }
    }

    // Helper method to check if a node is indirectly connected to the source
    private boolean isConnectedToSource(int node, int source, boolean visited[]) {
        visited[node] = true;

        // If the node itself is the source or directly connected to the source, return true
        if (node == source) {
            return true;
        }

        // Check if the node is indirectly connected to the source through another node
        for (int i = 0; i < v; i++) {
            if ((network[node][i] != 0 || network[i][node] != 0) && !visited[i]) {
                if (isConnectedToSource(i, source, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int target = 4;
        int powerSource = 0;

        IspApplication isp = new IspApplication(8);

        isp.addEdge(0, 1);
        isp.addEdge(0, 2);
        isp.addEdge(1, 3);
        isp.addEdge(1, 6);
        isp.addEdge(2, 4);
        isp.addEdge(4, 6);
        isp.addEdge(4, 5);
        isp.addEdge(5, 7);

        isp.printNetwork();

        List<Integer> impDevice = isp.findImpactedDevices(target, powerSource);

        System.out.println("Impacted Device List: " + impDevice);
    }
}

/*
Example:
Network matrix:
0 -> 1
0 -> 2
1 -> 3
1 -> 6
2 -> 4
4 -> 6
4 -> 5
5 -> 7

Target device: 4
Power source - 0

Since 4 is connected to 5 only and 7 is connected to 5, when the power is cut off at 4,
5 and 7 become the impacted devices

Output: [7, 5]
 */


