import unittest

from leetcode.top_k_frequent_elements_347.top_k_frequent_elements import Solution


class TestTopKFrequent(unittest.TestCase):

    def test_top_k_frequents(self):
        self.assertListEqual([1, 2], Solution().topKFrequent(nums=[1, 1, 1, 2, 2, 3], k=2))
        self.assertListEqual([1], Solution().topKFrequent(nums=[1], k=1))
