import sys
import unittest

from leetcode.walls_and_gates_286.walls_and_gates import Solution


class TestWallsAndGates(unittest.TestCase):

    def test_walls_and_gates(self):
        self.assertEqual(
            [
                [3, Solution.WALL, Solution.GATE, 1],
                [2, 2, 1, Solution.WALL],
                [1, Solution.WALL, 2, Solution.WALL],
                [Solution.GATE, Solution.WALL, 3, 4],
            ],
            Solution().wallsAndGets([
                [sys.maxsize, Solution.WALL, Solution.GATE, sys.maxsize],
                [sys.maxsize, sys.maxsize, sys.maxsize, Solution.WALL],
                [sys.maxsize, Solution.WALL, sys.maxsize, Solution.WALL],
                [Solution.GATE, Solution.WALL, sys.maxsize, sys.maxsize],
            ])
        )
