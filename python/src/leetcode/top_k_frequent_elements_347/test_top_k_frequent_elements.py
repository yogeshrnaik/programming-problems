import unittest

from leetcode.top_k_frequent_elements_347.top_k_frequent_elements import Solution


class TestUniquePathsWithObstacles(unittest.TestCase):

    def test_union_paths(self):
        self.assertListEqual([1, 2], Solution().topKFrequent(nums=[1, 1, 1, 2, 2, 3], k=2))
        self.assertListEqual([1], Solution().topKFrequent(nums=[1], k=1))
