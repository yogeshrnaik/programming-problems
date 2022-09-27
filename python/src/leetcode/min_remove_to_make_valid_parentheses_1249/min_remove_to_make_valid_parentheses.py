# Problem: https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/

class Solution:
    def minRemoveToMakeValid(self, s: str) -> str:
        indicesToRemove = set([])
        stack = []
        for i in range(0, len(s)):
            if s[i] == '(':
                stack.append(i)
            elif s[i] == ')':
                if stack:
                    stack.pop()
                else:
                    indicesToRemove.add(i)

        while stack:
            indicesToRemove.add(stack.pop())

        result = ""
        for i in range(0, len(s)):
            if i not in indicesToRemove:
                result += s[i]

        return result
