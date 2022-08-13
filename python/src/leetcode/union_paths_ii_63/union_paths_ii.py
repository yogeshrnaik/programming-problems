from typing import List


class Solution:
    def uniquePathsWithObstacles(self, obstacleGrid: List[List[int]]) -> int:
        dp = [[-1] * len(obstacleGrid)] * len(obstacleGrid[0])

        return self.explore(obstacleGrid, 0, 0, dp)

    def explore(self, grid, r, c, dp):
        if r < 0 or c < 0 or r > len(grid) - 1 or c > len(grid[0]) - 1 or grid[r][c] == 1:
            return 0

        if r == len(grid) - 1 and c == len(grid[0]) - 1:
            return 1

        if dp[r][c] != -1:
            return dp[r][c]

        dp[r][c] = self.explore(grid, r + 1, c, dp) + self.explore(grid, r, c + 1, dp)
        return dp[r][c]
