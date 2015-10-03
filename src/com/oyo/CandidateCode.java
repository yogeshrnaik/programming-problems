package com.oyo;
import java.io.*;
public class CandidateCode
{

    public static int calculate_sum(int input1,int input2)
    {
        long total = 0;

        for (int i = input1; i <= input2; i++) {
            total += fibonacci(i);
        }

        return (int)(total % 1000000007);
    }

    private static long fibonacci(int number) {
        if (number == 0 || number == 1) {
            return number;
        }

        long result = 0;
        long num2 = 1;
        long fibonacci;
        for (int n = 0; n < number; n ++)
        {
            fibonacci = result + num2;
            result = num2;
            num2 = fibonacci;
        }


        return result;


        // return fibonacci(number-1) + fibonacci(number - 2);
    }
}