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
        long result = 0;
        long num2 = 1;
        long fibonacci;
        System.out.print(num2);
        for (int loop = 0; loop < number; loop ++)
        {
            fibonacci = result + num2;
            result = num2;
            num2 = fibonacci;
        }
        System.out.print(result);
        return result;
    }
}