import sys
from typing import List


# Problem: https://leetcode.com/problems/coin-change/
class Solution:
    def coinChange(self, coins: List[int], amount: int) -> int:
        memo = {}
        def minCoinChange(currAmount):
            shorted = None
            if currAmount == 0:
                return []

            if currAmount < 0:
                return None

            if currAmount in memo:
                return memo[currAmount]

            for coin in coins:
                reminderCombination = minCoinChange(currAmount - coin)
                if reminderCombination is not None:
                    combination = reminderCombination.copy()
                    combination.append(coin)
                    if not shorted or len(combination) < len(shorted):
                        shorted = combination

            memo[currAmount] = shorted
            return shorted

        result = minCoinChange(amount)
        return len(result) if result is not None else -1
