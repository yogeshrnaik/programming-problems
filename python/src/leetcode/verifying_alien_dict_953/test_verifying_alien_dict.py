import unittest

from leetcode.verifying_alien_dict_953.verifying_alien_dict import Solution


class TestVerifyAlienDict(unittest.TestCase):

    def test_verifying_alien_dict(self):
        self.assertTrue(Solution().isAlienSorted(
            words=["hello", "leetcode"],
            order="hlabcdefgijkmnopqrstuvwxyz")
        )
        self.assertFalse(Solution().isAlienSorted(
            words=["word", "world", "row"], order="worldabcefghijkmnpqstuvxyz")
        )
        self.assertFalse(Solution().isAlienSorted(
            words=["apple", "app"], order="abcdefghijklmnopqrstuvwxyz")
        )
