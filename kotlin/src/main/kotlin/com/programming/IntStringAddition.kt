package com.programming

/**
 * Create a function to add together all combinations of adjacent integers in a string of integers:
 * This task required breaking down a string of numbers into individual characters and finding adjacent pairs.
 *
 * int_str = '12'
 * Output -> 1 + 2 + 12 = 15
 *
 * int_str = '123'
 * Output -> 1 + 2 + 3 + 12 + 23 + 123 = 164
 */
fun intStrAddition(intStr: String): Int {
    val length = intStr.length
    var sum = 0

    // Iterate over all possible starting positions of substrings
    for (startIndex in intStr.indices) {
        // Iterate over all possible ending positions of substrings
        for (endIndex in startIndex until length) {
            // Extract the substring and convert it to an integer
            val subNumber = intStr.substring(startIndex, endIndex + 1).toInt()
            sum += subNumber
        }
    }
    return sum
}

fun main() {
    var intStr = "12"
    var result = intStrAddition(intStr)
    println("Sum of all combinations of adjacent integers in '$intStr' -> $result")

    intStr = "123"
    result = intStrAddition(intStr)
    println("Sum of all combinations of adjacent integers in '$intStr' -> $result")
}