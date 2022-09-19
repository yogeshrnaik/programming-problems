import unittest

from leetcode.meeting_rooms_920.meeting_rooms import Solution, Interval


class TestMeetingRooms(unittest.TestCase):

    def test_meeting_rooms(self):
        self.assertFalse(Solution().can_attend_meetings([Interval(0, 30), Interval(5, 10), Interval(15, 20)]))
        self.assertTrue(Solution().can_attend_meetings([Interval(5, 8), Interval(9, 15)]))
