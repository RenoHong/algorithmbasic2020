using System;
using System.Linq ;
using System.Collections ;
using System.Collections.Generic ; 

public class DictionarySortedList
{
    public static void Main1()
    {
        Dictionary<int, string> dict = new Dictionary<int, string>() ;
        Random rand = new Random(); 
        for (int i = 900; i < 920; i++)
        {
            dict.TryAdd(i * rand.Next(), i.GetHashCode().ToString()) ;
            
        }

        foreach(var keyValue in dict)
        {
            System.Console.WriteLine("{0} -> {1}", keyValue.Key, keyValue.Value);
        }

        System.Console.WriteLine("==========SortedList============");
        SortedList<int, string> sortedList = new SortedList<int, string>(); 
        for (int i = 900; i < 920; i++)
        {
            sortedList.TryAdd(i * rand.Next(), i.GetHashCode().ToString()) ;
        }
        foreach( var kv in sortedList)
        {
            System.Console.WriteLine("{0} -> {1}", kv.Key, kv.Value);
        }
    }
}