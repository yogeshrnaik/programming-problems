# Problem: https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-string-balanced/

class Solution:
    def minSwaps(self, s: str) -> int:
        extraClose = 0
        maxClose = 0

        for c in s:
            if c == ']':
                extraClose += 1
            elif c == '[':
                extraClose -= 1

            maxClose = max(maxClose, extraClose)



        return (1 + maxClose) // 2