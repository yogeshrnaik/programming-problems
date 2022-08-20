from typing import List


class Solution:
    def topKFrequent(self, nums: List[int], k: int) -> List[int]:
        freqs = [[] for i in range(len(nums) + 1)]
        count = {}

        for n in nums:
            count[n] = 1 + count.get(n, 0)

        for n, c in count.items():
            freqs[c].append(n)

        result = []
        for i in range(len(freqs) - 1, 0, -1):
            if freqs[i]:
                for n in freqs[i]:
                    result.append(n)
                    if len(result) == k:
                        return result

        return result
