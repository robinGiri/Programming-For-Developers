package Question1;

/*
Methodology: The strategy employed to tackle this problem leverages the principles of dynamic programming.
Dynamic programming is instrumental in breaking down a complex problem into more manageable, smaller sub-problems.
The process begins by concentrating on these sub-problems to devise sub-solutions, progressively constructing a
comprehensive solution for the initial problem. Moreover, dynamic programming is pivotal in determining the most
efficient solution based on the sub-solutions derived from these sub-problems. In this instance, the objective is
to ascertain the least expensive method for decorating a series of venues with a specific theme chosen from a selection
of themes. The initial step involves setting up the dynamic programming (dp) table with the initial venue and theme.
Subsequently, the focus shifts to identifying the least expensive option for adorning the first two venues by 
evaluating the cost associated with each theme. This approach is then extended to the first three venues, the first 
four venues, and so forth, thereby ensuring that the optimal solution evolves through the formulation of optimal 
sub-solutions.
*/

public class MinimumCostDecoration {
    // Function to calculate minimum cost to decorate venues
    // 2D matrix costs is taken as parameter, which defines cost of each venue for each theme
    public static int minCostToDecorateVenues(int costs[][]) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        // n: number of venues
        int n = costs.length;
        //k: number of themes
        int k = costs[0].length;

        // In the case of there being only one venue
        // The themes of that venue are simply compared to each other to find the minimum costing theme
        if (n == 1) {
            int min = Integer.MAX_VALUE;
            for (int cost : costs[0]) {
                min = Math.min(min, cost);
            }
            return min;
        }

        int dp[][] = new int[n][k];

        // Initialize the dp table with the costs of the first venue i.e. venue 0
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }

        // Fill up the dp table with minimum cost for each venue with each theme
        //Loop i iterates over each venue, starting from venue 1
        for (int i = 1; i < n; i++) {
            // Loop j iterates over each theme for current venue i
            for (int j = 0; j < k; j++) {
                // Initialize the minimum cost to a large value
                int minCost = Integer.MAX_VALUE;

                // Here the minimum cost to decorate current venue with current theme is determined
                // in relation to the minimum cost of decorating the previous venue with theme prev_j
                for (int prev_j = 0; prev_j < k; prev_j++) {
                    // To check if the consecutive venues have same themes
                    if (prev_j != j) {
                        minCost = Math.min(minCost, dp[i - 1][prev_j] + costs[i][j]);
                    }
                }
                dp[i][j] = minCost;
            }
        }

        // Return the minimum cost from the last row
        // Only the last row is considered because the minimum cost that it holds for each theme has been calculated
        // by considering the minimum costs of previous venues
        int minCost = Integer.MAX_VALUE;
        for (int cost : dp[n - 1]) {
            minCost = Math.min(minCost, cost);
        }
        return minCost;
    }


    // Main method
    public static void main(String[] args) {
        int costs[][] = new int[3][3];
        costs[0][0] = 1;
        costs[0][1] = 3;
        costs[0][2] = 2;
        costs[1][0] = 4;
        costs[1][1] = 6;
        costs[1][2] = 8;
        costs[2][0] = 3;
        costs[2][1] = 1;
        costs[2][2] = 5;

        int minCost = minCostToDecorateVenues(costs);

        System.out.println("Minimum cost to decorate given venues: " + minCost);
    }
}

/*
Example:
theme 0 + venue 0 = 1
theme 0 + venue 1 = 3
theme 0 + venue 2 = 2
theme 1 + venue 0 = 4
theme 1 + venue 1 = 6
theme 1 + venue 2 = 8

First dp matrix initialized with cost of decorating venue 0 with all three themes
dp[0][0] = 1
dp[0][1] = 3
dp[0][2] = 2

minCost = infinity
when prev_j = 1 and j = 0 and i = 1,
minCost = Math.min(infinity, dp[0][1] + costs[1][0]) = infinity, 3+4 = 7
when prev_j = 2 and j = 0 and i = 1,
minCost = Math.min(7, dp[0][2] + costs[1][0]) = 7, 2+4 = 6
minCost for dp[1][0] = 6

and so on the dp table is populated with minimum cost of every venue with each theme, making sure that the previously chosen theme is not the same
Out of the last row of dp, the minimum cost is calculated, which is 7
*/
