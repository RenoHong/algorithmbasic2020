using System ;
using System.Collections ;
using System.Collections.Generic; 
public class TwoStacksImplementQueue<T>{
    Stack<T> stackIn = new Stack<T>() ;
    Stack<T> stackOut = new Stack<T>(); 

    public void Enqueue(T value)
    {
        stackIn.Push(value) ;
    }   

    private void InToOut()
    {
        if(!stackOut.Any()){
            while(stackIn.Any())
                stackOut.Push(stackIn.Pop()) ;
        }
    } 

    public T Dequeue()
    {
        InToOut();
        if(stackOut.Any()) return stackOut.Pop();
        throw new InvalidDataException("No data");
    }

    public bool Any()
    {   
        return stackIn.Any() || stackOut.Any();
    }

    public T Peek()
    {
        if (!stackOut.Any())
        {
            InToOut();
        }
        if(!stackOut.Any()) throw new InvalidDataException("No data");
        return stackOut.Peek();
    }


}
// public class Test{
//     public static void Main()
//     {
//         TwoStacksImplementQueue<int> queue = new TwoStacksImplementQueue<int>();
//         for (int i = 0; i < 10; i++)
//         {
//             queue.Enqueue(i) ;
//         }
//         while (queue.Any())
//         {
//             System.Console.WriteLine(queue.Dequeue());
//         }
        
//     }
// }