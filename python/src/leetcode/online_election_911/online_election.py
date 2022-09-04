from collections import defaultdict


class TopVotedCandidate(object):

    def __init__(self, persons, times):
        """
        :type persons: List[int]
        :type times: List[int]
        """
        self.persons = persons
        self.times = times
        self.leaders = []
        res = defaultdict(int)
        leader = 0
        for index, person in enumerate(self.persons):
            res[person] += 1
            if res[person] >= res[leader]:
                leader = person
            self.leaders.append(leader)

    def q(self, t):
        """
        :type t: int
        :rtype: int
        """
        t_index = self.bs(t)
        # print t_index
        return self.leaders[t_index]

    def bs(self, t):
        l = 0
        r = len(self.times) - 1
        ans = -1
        while l <= r:
            m = (l + r) / 2
            c = self.times[m]

            if c == t:
                return m
            elif c < t:
                ans = max(ans, m)
                l = m + 1
            else:
                r = m - 1
        # print ans, len(self.times)-1
        return ans
