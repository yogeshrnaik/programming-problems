# Problem: https://leetcode.com/problems/unique-paths/
# Solution memoization: https://www.youtube.com/watch?v=oBt53YbR9Kk&t=2320s
# Solution tabulation: https://youtu.be/oBt53YbR9Kk?t=12137

class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        return self.with_dynamic_programming(m, n)

    def union_path_with_memoization(self, m: int, n: int, memo={}) -> int:
        if m == 1 and n == 1:
            return 1

        if m == 0 or n == 0:
            return 0

        key = str(m) + "," + str(n)
        if key in memo:
            return memo[key]

        path_count = self.union_path_with_memoization(m - 1, n, memo) + self.union_path_with_memoization(m, n - 1, memo)
        memo[key] = path_count
        return memo[key]

    def with_dynamic_programming(self, row: int, col: int):
        dp = [[0 for i in range(col+1)] for j in range(row+1)]
        dp[1][1] = 1

        for i in range(row+1):
            for j in range(col+1):
                if j+1 <= col:
                    dp[i][j+1] += dp[i][j]
                if i+1 <= row:
                    dp[i+1][j] += dp[i][j]

        print(dp)
        return dp[row][col]
