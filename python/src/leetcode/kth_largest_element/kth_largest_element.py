import random
from typing import List


class Solution:
    def findKthLargest(self, nums: List[int], k: int) -> int:
        pivot = random.choice(nums)
        left = [x for x in nums if x > pivot]
        mid = [x for x in nums if x == pivot]
        right = [x for x in nums if x < pivot]
        leftCount, midCount = len(left), len(mid)

        if k <= leftCount:
            return self.findKthLargest(left, k)
        elif k > (leftCount + midCount):
            return self.findKthLargest(right, k - (leftCount + midCount))
        else:
            return mid[0]
