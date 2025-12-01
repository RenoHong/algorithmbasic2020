using System;
using System.Collections;
using System.Collections.Generic;

public class MergeSort
{
    public static void Sort(int[] arr)
    {
        if (arr == null || arr.Length < 2) return;

        Process(arr, 0, arr.Length - 1);
    }

    private static void Process(int[] arr, int l, int r)
    {
        if (l >= r) return;
        int m = l + ((r - l) >> 1);
        Process(arr, l, m);
        Process(arr, m + 1, r);
        Merge(arr, l, m, r);
    }

    private static void Merge(int[] arr, int l, int m, int r)
    {
        int[] helper = new int[r - l + 1];
        int p1 = l, p2 = m + 1;
        int i = 0;
        while (p1 <= m && p2 <= r)
        {
            helper[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p2 <= r)
        {
            helper[i++] = arr[p2++];
        }
        while (p1 <= m)
        {
            helper[i++] = arr[p1++];
        }

        // System.Console.WriteLine(string.Join(",", helper));
        for (int j = 0; j < helper.Length; j++)
        {
            arr[l + j] = helper[j];
        }
    }

    public static void Main()
    {
        int[] arr = [5, 9, 3, 1, 3, 8, 7, 10, 0];
        Sort(arr);
        System.Console.WriteLine(string.Join(",", arr));
    }
}