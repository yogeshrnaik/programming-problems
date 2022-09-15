from typing import List


class Solution:
    def subsetsWithDup(self, nums: List[int]) -> List[List[int]]:
        nums.sort()
        result = []

        subset = []

        def dfs(index):
            if len(nums) == index:
                result.append(subset.copy())
                return

            subset.append(nums[index])
            dfs(index + 1)
            subset.pop()

            while index + 1 < len(nums) and nums[index] == nums[index + 1]:
                index += 1

            dfs(index + 1)

        dfs(0)

        return result
