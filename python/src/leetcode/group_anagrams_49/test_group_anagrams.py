import unittest

from leetcode.group_anagrams_49.group_anagrams import Solution


class TestGroupAnagrams(unittest.TestCase):

    def test_group_anagrams(self):
        self.assertListEqual([['eat', 'tea', 'ate'], ['tan', 'nat'], ["bat"]],
                             Solution().groupAnagrams(words=["eat", "tea", "tan", "ate", "nat", "bat"]))
        self.assertListEqual([[""]], Solution().groupAnagrams(words=[""]))
        self.assertListEqual([["a"]], Solution().groupAnagrams(words=["a"]))
