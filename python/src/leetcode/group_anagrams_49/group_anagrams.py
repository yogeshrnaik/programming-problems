from typing import List


class Solution:
    def groupAnagrams(self, words: List[str]) -> List[List[str]]:
        result = {}
        for word in words:
            freq = {}
            for c in word:
                freq[c] = 1 + freq.get(c, 0)

            freqKey = self.asKey(freq)
            anagrams = result.get(freqKey, [])
            anagrams.append(word)
            result[freqKey] = anagrams

        return list(result.values())

    def asKey(self, freq):
        key = ""
        for c in 'abcdefghijklmnopqrstuvwxyz':
            key += c + ":" + str(freq.get(c, 0)) + ","

        return key
