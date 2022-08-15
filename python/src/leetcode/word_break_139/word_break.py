# Problem: https://leetcode.com/problems/word-break/
from typing import List


class Solution:

    def wordBreak(self, s: str, wordDict: List[str], memo={}) -> bool:
        if s == "":
            return True

        if s in memo:
            return memo[s]

        for w in wordDict:
            if s.startswith(w):
                sub = s[len(w):]
                if self.wordBreak(sub, wordDict, memo):
                    memo[s] = True
                    return True

        memo[s] = False
        return False
