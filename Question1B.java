import java.util.PriorityQueue;

class Question1B {
    public int minTimeToBuildAllEngines(int[] inputEngines, int splitCost) {
        // Create a priority queue to store engine build times.
        PriorityQueue<Integer> engines = new PriorityQueue<>();
      
        // Add each engine's build time to the queue.
        for (int engine : inputEngines) {
            engines.offer(engine);
        }
      
        // While there are at least two input in the queue:
        while (engines.size() > 1) {
            // Remove the smallest build time (the top element).
            engines.poll();
            
            // Get the second smallest build time.
            int secondSmallestEngine = engines.poll();
          
            // Combine the second smallest build time with the split cost
            // and add it back to the queue.
            engines.offer(secondSmallestEngine + splitCost);
        }
      
        // The final result is the build time of the last remaining engine.
        return engines.poll();
    }
    
    public static void main(String[] args) {
        int engines[]= {1, 2, 3};
        int splitCost=1;
        Question1B object= new Question1B();
        System.out.println("Minimum time cost to complete all engine builds: "+object.minTimeToBuildAllEngines(engines, splitCost));
    }
}
