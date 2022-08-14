from typing import List


# Problem: https://leetcode.com/problems/min-cost-climbing-stairs/
class Solution:

    def minCostClimbingStairs(self, cost: List[int]) -> int:
        return self.recursive(cost)

    def recursive(self, cost: List[int]) -> int:
        dp = {}
        n = len(cost)

        def minCost(i):
            if i < 0:
                return 0

            if i == 0 or i == 1:
                return cost[i]
            if i in dp:
                return dp[i]

            dp[i] = cost[i] + min(minCost(i - 1), minCost(i - 2))
            return dp[i]

        return min(minCost(n - 1), minCost(n - 2))

    def iterative(self, cost: List[int]) -> int:
        cost.append(0)
        for i in range(len(cost) - 3, -1, -1):
            cost[i] += min(cost[i + 1], cost[i + 2])
            i -= 1

        return min(cost[0], cost[1])
