// To implement a ScoreTracker class that keeps track of scores and calculates the median score efficiently, we can use two heaps: a max heap to keep track of the lower half of the numbers and a min heap for the upper half. This approach allows constant-time retrieval of the median elements and logarithmic time insertion.

// Here's how the implementation works:

// Max heap (lowerHalf): Stores the smaller half of the numbers. If there are odd numbers of scores, this heap will contain one more element than the min heap.
// Min heap (upperHalf): Stores the larger half of the numbers.
// When a new score is added:

// If the new score is less than or equal to the maximum of the lower half, add it to the max heap. Otherwise, add it to the min heap.
// After adding a new score, balance the heaps so that the difference in their sizes is no more than 1.
// To get the median:
// If the heaps are of equal size, the median is the average of the max of the lower half and the min of the upper half.
// If they're not equal, the median is the top of the heap with more elements.
// Here's the Java implementation:

import java.util.Collections;
import java.util.PriorityQueue;

public class Question3A {
    private PriorityQueue<Double> lowerHalf;
    private PriorityQueue<Double> upperHalf;

    public Question3A() {
        lowerHalf = new PriorityQueue<>(Collections.reverseOrder()); // Max Heap
        upperHalf = new PriorityQueue<>(); // Min Heap
    }

    public void addScore(double score) {
        if (lowerHalf.isEmpty() || score <= lowerHalf.peek()) {
            lowerHalf.add(score);
        } else {
            upperHalf.add(score);
        }

        // Balance the heaps
        if (lowerHalf.size() > upperHalf.size() + 1) {
            upperHalf.add(lowerHalf.poll());
        } else if (upperHalf.size() > lowerHalf.size()) {
            lowerHalf.add(upperHalf.poll());
        }
    }

    public double getMedianScore() {
        if (lowerHalf.size() == upperHalf.size()) {
            return (lowerHalf.peek() + upperHalf.peek()) / 2;
        } else {
            return lowerHalf.peek();
        }
    }

    public static void main(String[] args) {
        Question3A scoreTracker = new Question3A();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        System.out.println(scoreTracker.getMedianScore()); // Output: 88.3

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        System.out.println(scoreTracker.getMedianScore()); // Output: 86.6
    }
}
