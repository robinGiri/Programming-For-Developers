package Question3;

/*
The ScoreTracker class efficiently manages a dynamically updating list of scores, maintaining them in sorted order
for precise median calculations. It initializes a private list to store scores and incorporates a binary search
algorithm within the addScore method to find the correct insertion index, ensuring the list remains sorted. The median 
is calculated through the getMedianScore method, which adeptly handles both even and odd numbers of scores by either 
averaging the two middle scores or selecting the middle score directly. Exception handling is implemented for cases 
with no scores, and the system's functionality is demonstrated through a main method that tests the score addition and 
median calculation processes. This approach highlights a blend of efficient data management, algorithmic precision, 
and robust handling of dynamic data sets.
 */

 import java.util.ArrayList;
 import java.util.List;
 
 public class ScoreTracker {
 
     // List for storing the scores in sorted order
     private List<Double> scores;
 
     // Constructor for initializing the list
     public ScoreTracker() {
         scores = new ArrayList<>();
     }
 
     // Method for adding a new score to the sorted list
     public void addScore(double score) {
         // Finding the index where the score should be inserted
         int index = findInsertionIndex(score);
         // Inserting the score at the calculated index to maintain sorted order
         scores.add(index, score);
     }
 
     // Helper method for finding the insertion index using binary search
     private int findInsertionIndex(double score) {
         int low = 0;
         int high = scores.size() - 1;
 
         // Binary search loop
         while (low <= high) {
             int mid = low + (high - low) / 2;
             double midScore = scores.get(mid);
 
             if (midScore == score) {
                 return mid;
             } else if (midScore < score) {
                 low = mid + 1;
             } else {
                 high = mid - 1;
             }
         }
         return low;
     }
 
     // Method for calculating and return the median score
     public double getMedianScore() {
         int size = scores.size();
         if (size == 0) {
             throw new IllegalStateException("No scores available.");
         }
 
         // Calculating the middle index
         int middleIndex = size / 2;
         if (size % 2 == 0) {
             double lowerMedian = scores.get(middleIndex - 1);
             double upperMedian = scores.get(middleIndex);
             return (lowerMedian + upperMedian) / 2.0;
         } else {
             return scores.get(middleIndex);
         }
     }
 
     // Main method for testing the score tracking functionality
     public static void main(String[] args) {
         // Creating an instance of the score tracker
         ScoreTracker scoreTracker = new ScoreTracker();
 
         // Adding scores to the tracker
         scoreTracker.addScore(85.5);
         scoreTracker.addScore(92.3);
         scoreTracker.addScore(77.8);
         scoreTracker.addScore(90.1);
 
         // Getting and printing the median after the first set of scores
         double median1 = scoreTracker.getMedianScore();
         System.out.println("Median score 1: " + median1);
 
         // Adding more scores to the tracker
         scoreTracker.addScore(81.2);
         scoreTracker.addScore(88.7);
 
         // Getting and printing the median after the second set of scores
         double median2 = scoreTracker.getMedianScore();
         System.out.println("Median score 2: " + median2);
     }
 }
 
 /*
Example:
Output : Median score 1: 87.8 and Median score 2: 87.1
Time complexity : O(log n) + O(n)
*/
