import unittest

from leetcode.word_break_139.word_break import Solution


class TestWordBreak(unittest.TestCase):

    def test_word_break(self):
        self.assertTrue(Solution().wordBreak("leetcode", ["leet", "code"]))
        self.assertTrue(Solution().wordBreak("applepenapple", ["apple", "pen"]))
        self.assertFalse(Solution().wordBreak("catsandog", ["cats", "dog", "sand", "and", "cat"]))
        self.assertFalse(Solution().wordBreak("a", ["b"]))
