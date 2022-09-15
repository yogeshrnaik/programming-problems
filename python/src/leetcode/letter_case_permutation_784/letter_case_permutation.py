from typing import List

# https://leetcode.com/problems/letter-case-permutation/
class Solution:
    def letterCasePermutation(self, s: str) -> List[str]:
        result = []

        subset = []

        def dfs(index):
            if index == len(s):
                result.append("".join(subset))
                return

            subset.append(s[index])
            dfs(index + 1)
            subset.pop()

            if s[index] != s[index].upper():
                # if the character at index is lowercase, we need to use its uppercase counterpart
                subset.append(s[index].upper())
                dfs(index + 1)
                subset.pop()

            if s[index] != s[index].lower():
                # if the character at index is uppercase, we need to use its lowercase counterpart
                subset.append(s[index].lower())
                dfs(index + 1)
                subset.pop()

        dfs(0)

        return result
