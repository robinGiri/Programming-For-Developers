package Question4;

import java.util.*;

/*
Approach: The question asks us to find th minimum moves to find all keys.
A grid is defined a starting point S, paths P and walls W.
It also consists of uppercase letters representing locked doors and the key to those doors are represented by the corresponding lowercase letter
The concept of Breadth First Search has been used since it searches in all possible paths to get the keys.
So, the starting position is found from the grid and the queue is initialized from S.
The total number of keys is also calculated.
Then, until the queue is full, breadth first search is done via DIRECTIONS 2D array which contains co-ordinates for moving up, down, left and right.
According to those directions, we move through the maze from the starting position, picking up keys along the way.
With every key find, the move count is incremented.
In the end, once the collectedKeys = totalKeys, the loop is exited by returning the moves.
*/

public class MazeProblem {
    private static final int DIRECTIONS[][] = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static final char START_POINT = 'S';
    private static final char WALL = 'W';

    public static int findMinMoves(char grid[][]) {
        int m = grid.length;
        int n = grid[0].length;

        // Create a visited array to keep track of visited cells
        boolean visited[][] = new boolean[m][n];

        // Create a queue to perform BFS
        Queue<Position> queue = new LinkedList<>();

        // Start from the starting point
        Position startPosition = null;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == START_POINT) {
                    // If grid[i][j] is "S", then it made into the starting position using Position class
                    // here i and j represent row and column of that position
                    startPosition = new Position(i, j);
                    break;
                }
            }
        }
        // Starting point not found
        if (startPosition == null) {
            return -1;
        }

        // Mark the starting point as visited
        visited[startPosition.row][startPosition.col] = true;

        // Initialize the queue with the starting position
        queue.offer(startPosition);

        // Calculation of the number of keys in grid
        int totalKeys = 0;
        for (char row[] : grid) {
            for (char cell : row) {
                if (Character.isLowerCase(cell)) {
                    totalKeys++;
                }
            }
        }

        int keysCollected = 0;
        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                // The position at the top of the queue is returned and removed
                Position currentPosition = queue.poll();

                // Checking if we have reached the final position i.e. if all the keys have been calculated
                if (keysCollected == totalKeys) {
                    // Return the move count here
                    return moves;
                }

                // Check adjacent cells for keys
                // From the current posiiton i.e. current row and column, the position is changed via change in row and column using the for-each loop
                // According to the loop, the row could either stay in the same position, left or right
                // The column could either stay in the same posiiton, up or down.
                for (int direction[] : DIRECTIONS) {
                    int newRow = currentPosition.row + direction[0];
                    int newCol = currentPosition.col + direction[1];

                    // Check if the new cell is within the grid and not visited, and it's not a wall
                    if (isInBounds(m, n, newRow, newCol) && !visited[newRow][newCol] && grid[newRow][newCol] != WALL) {
                        // Mark the new cell as visited and add it to the queue
                        visited[newRow][newCol] = true;
                        if (Character.isLowerCase(grid[newRow][newCol])) {
                            keysCollected++;
                        }
                        queue.offer(new Position(newRow, newCol));
                    }
                }
            }
            // Increment the move count after processing all cells at this level
            moves++;
        }
        // Could not find the goal
        return -1;
    }

    // To check the validity of a cell's position via row and column
    private static boolean isInBounds(int m, int n, int row, int col) {
        return row >= 0 && row < m && col >= 0 && col < n;
    }

    private static class Position {
        int row;
        int col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) {
        char grid[][] = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };

        int minMoves = findMinMoves(grid);
        if (minMoves != -1) {
            System.out.println("The minimum number of moves required to collect all keys is: " + minMoves);
        }
        else {
            System.out.println("It is impossible to collect all the keys and reach the exit.");
        }
    }
}