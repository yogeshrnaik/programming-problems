import sys
from typing import List


# Problem: https://leetcode.com/problems/coin-change-ii/
class Solution:
    def change(self, amount: int, coins: List[int]) -> int:

        def dfs(index, curr_amount, memo={}):
            if curr_amount > amount: return 0
            if curr_amount == amount: return 1
            if index == len(coins): return 0
            if (index, curr_amount) in memo: return memo[(index, curr_amount)]

            memo[(index, curr_amount)] = dfs(index, curr_amount + coins[index]) + dfs(index + 1, curr_amount)
            return memo[(index, curr_amount)]

        result = dfs(0, 0)

        self.dynamic_programming(amount, coins)

        return self.change2(amount, coins)

    def dynamic_programming(self, amount: int, coins: List[int]):
        dp = [[0] * (len(coins) + 1) for i in range(amount + 1)]
        dp[0] = [1] * (len(coins) + 1)

        for a in range(1, amount + 1):
            for i in range(len(coins) - 1, -1, -1):
                dp[a][i] = dp[a][i + 1]
                if a - coins[i] >= 0:
                    dp[a][i] += dp[a - coins[i]][i]

        self.print(dp)

    def print(self, dp):
        for i in range(len(dp)):
            for j in range(len(dp[i])):
                print(dp[i][j], "\t", end="")
            print("\n")

    def change2(self, amount: int, coins: List[int]) -> int:
        cache = [0] * (amount + 1)
        cache[0] = 1
        for coin in coins:
            for index in range(1, amount + 1):
                if index - coin >= 0:
                    cache[index] += cache[index - coin]
        return cache[amount]
