package Question1;

import java.util.PriorityQueue;

/*
Strategy: The methodology employed for addressing this problem incorporates the use of a priority queue. 
The rationale behind selecting a priority queue is its inherent characteristic of assigning higher precedence 
to smaller numbers, which ensures that the engine build times are organized in a sequential, ascending order. 
The essence of this strategy lies in the distribution of engineers in such a manner that maximizes their allocation 
across the construction of numerous engines. By initiating the process with the engine that demands the most time, 
the division strategy ultimately culminates with the engines that require the minimal amount of time. This allows for 
the commencement of the total time calculation with these two engines, progressively accumulating the time needed for 
the construction of subsequent engines with longer time requirements. The use of Math.max is critical in this context 
as it determines the juncture at which engineers have completed their work on specific engines, thereby enabling the 
allocation of split costs.
*/

public class SpaceshipEngine {
    // Function to calculate the minimum time needed to build all engines
    public static int minTimeBuildEngine(int engines[], int splitCost) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // The array engines[] contains time required to build a particular engine
        // Populating the priority queue with the time required for each engine
        for (int i = 0; i < engines.length; i++) {
            pq.add(engines[i]);
        }

        int totalTime = 0;

        // The first two engines are extracted from priority queue
        int engine1Time = pq.remove();
        int engine2Time = pq.remove();
        // Then, the total time is calculated by finding the maximum time from the two engines, to which split cost is added.
        totalTime = splitCost + Math.max(engine1Time, engine2Time);

        // After working on the first two engines, then the engineers move on to work on the next engines in the array
        // Thus, a loop is used
        for(int i = 0; i<=pq.size(); i++) {
            int engineTime = pq.remove();
            // For each engine extracted, the maximum time is calculated between that engine's time and present total time and split cost is added
            totalTime = splitCost + Math.max(totalTime, engineTime);
        }

        return totalTime;
    }

    public static void main(String[] args) {
        int engines[] = {2,1,3};
        int splitCost = 1;

        int result = minTimeBuildEngine(engines, splitCost);

        System.out.println("Minimum time required to build engines: " + result);
    }
}

/*
Example:
engines: [2,1,3] added to Priority Queue pq => [1,2,3]
splitCost = 1

engine1Time = 1
engine2Time = 2
totalTime = 1 + Math.max(1,2) = 3

for loop: (i = 0; i<=pq.size() = 1; i++)
engineTime = pq.remove() = 3
totalTime = 1 + Math.max(3,3) = 4
*/
