from typing import List


class Solution:

    def letterCombinations(self, digits: str) -> List[str]:
        numToChars = {
            '2': ['a', 'b', 'c'],
            '3': ['d', 'e', 'f'],
            '4': ['g', 'h', 'i'],
            '5': ['j', 'k', 'l'],
            '6': ['m', 'n', 'o'],
            '7': ['p', 'q', 'r', 's'],
            '8': ['t', 'u', 'v'],
            '9': ['w', 'x', 'y', 'z']
        }

        charsList = []
        result = []
        for d in digits:
            charsList.append(numToChars[d])

        self.generate(charsList, result, 0, "")
        return result

    def generate(self, charsList, result, start, startChar):
        if start == len(charsList) - 1:
            for c in charsList[start]:
                result.append(startChar + c)
        elif start < len(charsList) - 1:
            for c in charsList[start]:
                self.generate(charsList, result, start + 1, startChar + c)
