from typing import List


# Problem: https://leetcode.com/problems/target-sum/
class Solution:

    def findTargetSumWays(self, nums: List[int], target: int) -> int:
        dp = {}

        def dfs(index, curr):
            if index == len(nums):
                return 1 if curr == target else 0

            if (index, curr) in dp:
                return dp[(index, curr)]

            dp[(index, curr)] = dfs(index + 1, curr + nums[index]) + dfs(index + 1, curr - nums[index])
            return dp[(index, curr)]

        return dfs(0, 0)
