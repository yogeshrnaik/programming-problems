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

        return dfs(0, 0)
