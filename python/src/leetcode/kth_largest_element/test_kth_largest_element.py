import unittest

from leetcode.kth_largest_element.kth_largest_element import Solution


class TestKthLargestElement(unittest.TestCase):

    def test_kth_largest(self):
        self.assertEqual(5, Solution().findKthLargest(nums=[3, 2, 1, 5, 6, 4], k=2))
        self.assertEqual(4, Solution().findKthLargest(nums=[3, 2, 3, 1, 2, 4, 5, 5, 6], k=4))
