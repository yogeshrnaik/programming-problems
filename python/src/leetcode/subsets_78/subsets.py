from typing import List


class Solution:
    def subsets(self, nums: List[int]) -> List[List[int]]:
        result = []

        subset = []

        def dfs(index):
            if len(nums) == index:
                result.append(subset.copy())
                return

            subset.append(nums[index])
            dfs(index + 1)

            subset.pop()
            dfs(index + 1)

        dfs(0)

        return result
