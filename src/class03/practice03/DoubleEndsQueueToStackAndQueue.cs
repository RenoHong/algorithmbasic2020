using System;
using System.Linq;
using System.Collections;
using System.Collections.Generic;

public class DoubleEndsQueueToStackAndQueue
{

 

}

public class DoubleNode<T>
{
    public DoubleNode<T> Last { get; set; }
    public DoubleNode<T> Next { get; set; }
    public T Data {get; set;}

    public DoubleNode(T value, DoubleNode<T>? last = null, DoubleNode<T>? next = null)
    {
        Data = value;
        Last = last;
        Next = next;
    }
}

public class DoubleEndQueue<T>
{
    private DoubleNode<T> head = null;
    private DoubleNode<T> tail = null;
    private int size = 0;

    public void AddFirst(T value)
    {
        DoubleNode<T> n = new DoubleNode<T>(value);
        if (!Any())
        {
            head = tail = n;
        }
        else
        {
            n.Next = head;
            head.Last = n;
            head = n;
        }

        size++;
    }

    public void AddLast(T value)
    {
        DoubleNode<T> n = new DoubleNode<T>(value);
        if (!Any())
        {
            head = tail = n;
        }
        else
        {
            tail.Next = n;
            n.Last = tail;
            tail = n;
        }
        size++;
    }

    public T RemoveFirst()
    {
        if (!Any()) throw new InvalidOperationException("No data");
        DoubleNode<T> n = head;
        if (head == tail)
        {
            head = tail = null;
        }
        else
        {
            head = head.Next;
            head.Last = null;
            n.Next = null;
        }
        size--;
        return n.Data;
    }

    public T RemoveLast()
    {
        if (!Any())
        {
            throw new InvalidOperationException("No data");
        }
        else
        {
            DoubleNode<T> n = tail; ;
            if (head == tail)
            { 
                head = tail = null; 
            }
            else
            { 
                tail = tail.Last;
                tail.Next = null;
                n.Last = null; 
            }
            size -- ;
            return n.Data;
        }
        
    }

    public void Clear()
    {
        head = tail = null ;
        size =0 ;
    }

    public void AddAfter(DoubleNode<T> node, DoubleNode<T> after)
    {
        if(node == null || head == null)
            throw new InvalidOperationException("No data") ;

        var current = head ; 
        while(current != null)
        {
            if(node == current)
                break ;
            current = current.Next ;
        }

        if(current != node) throw new InvalidOperationException("Give node not found!");
        if(node != tail)
        {
             after.Next = node.Next ;
        }
        else
        {
            tail = after ;
        }
        after.Last = node ;
        node.Next = after ;
        size ++ ;
    }

    public DoubleNode<T> Find(T data)
    {
        if(!Any()) return null ;
        var node = head ;
        while(node != null)
        {
            if(node.Data.Equals(data))
            {
                return node ;
            }
            node = node.Next ;
        }
        return null ;
    }


    public bool Any()
    {
        return size > 0;
    }

}