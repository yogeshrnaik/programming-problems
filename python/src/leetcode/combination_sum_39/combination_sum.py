# Problem: https://leetcode.com/problems/combination-sum/
from typing import List


# Problem: https://leetcode.com/problems/combination-sum/

class Solution:

    def combinationSum(self, candidates: List[int], target: int) -> List[List[int]]:
        result = []

        def dfs(candidates: List[int], start: int, curr: List[int], target: int, result):
            if target == 0:
                result.append(curr.copy())
                return

            if target < 0:
                return

            for i in range(start, len(candidates)):
                c = candidates[i]
                curr.append(c)
                dfs(candidates, i, curr, target - c, result)
                curr.pop()

        dfs(candidates, 0, [], target, result)
        return result
