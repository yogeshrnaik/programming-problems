from typing import List


class Solution:
    def generateParenthesis(self, n: int) -> List[str]:
        result = []

        def generate(openCount, closeCount, curr):
            if openCount == closeCount and openCount == n:
                result.append(curr)
                return

            if openCount < n:
                generate(openCount + 1, closeCount, curr + "(")

            if closeCount < openCount:
                generate(openCount, closeCount + 1, curr + ")")

        generate(0, 0, "")
        return result
