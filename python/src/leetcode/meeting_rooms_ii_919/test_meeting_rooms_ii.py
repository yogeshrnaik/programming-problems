import unittest

from leetcode.meeting_rooms_ii_919.meeting_rooms_ii import Solution, Interval


class TestMeetingRoomsII(unittest.TestCase):

    def test_meeting_rooms_ii(self):
        self.assertEqual(2, Solution().min_meeting_rooms([Interval(0, 30), Interval(5, 10), Interval(15, 20)]))
        self.assertEqual(1, Solution().min_meeting_rooms([Interval(2, 7)]))
