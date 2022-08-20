import unittest

from leetcode.interleaving_string_97.interleaving_string import Solution


class TestInterleavingString(unittest.TestCase):

    def test_interleaving_string(self):
        self.assertTrue(Solution().isInterleave(s1="", s2="", s3=""))
        self.assertTrue(Solution().isInterleave(s1="aabcc", s2="dbbca", s3="aadbbcbcac"))
        self.assertFalse(Solution().isInterleave(s1="aabcc", s2="dbbca", s3="aadbbbaccc"))
        self.assertTrue(Solution().isInterleave(s1="a", s2="b", s3="ab"))
        self.assertTrue(Solution().isInterleave(s1="a", s2="", s3="a"))
        self.assertTrue(Solution().isInterleave(s1="", s2="a", s3="a"))
