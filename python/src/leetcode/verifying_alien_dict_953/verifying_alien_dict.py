from typing import List


# Problem: https://leetcode.com/problems/verifying-an-alien-dictionary/

class Solution:
    def isAlienSorted(self, words: List[str], order: str) -> bool:
        orderMap = {}
        for i in range(0, len(order)):
            orderMap[order[i]] = i

        for i in range(0, len(words) - 1):
            for j in range(1, len(words)):
                w1 = words[i]
                w2 = words[j]
                minLen = min(len(w1), len(w2))
                for k in range(0, minLen):
                    w1order = orderMap.get(w1[k])
                    w2order = orderMap.get(w2[k])
                    if w1order > w2order:
                        return False
                    elif w1order < w2order:
                        break
                    elif k == minLen - 1 and len(w1) > len(w2):
                        return False

        return True
