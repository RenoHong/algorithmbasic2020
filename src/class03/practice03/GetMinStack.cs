using System ;
using System.Collections.Generic ;
using System.Collections;
using System.Linq ;
public class GetMinStack
{

    public class Stack2
    {
        Stack<int> _sMin = new Stack<int>() ;
        Stack<int> _sData = new Stack<int>() ;

        public int GetMin()
        {
            if(_sMin.Any()) return _sMin.Peek(); 
            else throw new InvalidOperationException("No data") ;
        }

        public void Push(int n)
        {
            _sData.Push(n) ; 
            if(!_sMin.Any()) _sMin.Push(n) ;
            else 
            {
                if(n <= GetMin())
                    _sMin.Push(n) ;
            }
            
        }

        public int Pop()
        {
            if(!_sData.Any()) throw new InvalidOperationException("No data");
            int data = _sData.Pop() ;
            if(data == GetMin())
            {
                _sMin.Pop() ;
            }
            return data ;
        }

        public int Peek()
        {
            if(!_sData.Any()) throw new InvalidOperationException("No data");
            return _sData.Peek();
        }
    }
    public class Stack1
    {
        Stack<int> stackData = new Stack<int>(); 
        Stack<int> stackMin = new Stack<int>();

        public int GetMin()
        {
            if(stackMin.Any()) return stackMin.Peek();
            else throw new InvalidOperationException("No elements");
        }

        public void Push(int n)
        {
            stackData.Push(n);
            if(!stackMin.Any())
            {
                stackMin.Push(n);  
            }
            else
            {
                if(stackMin.Peek() >= n)
                {
                    stackMin.Push(n) ;
                }
                else
                {
                    stackMin.Push(stackMin.Peek());
                }
            }
        }

        public int Pop()
        {
            if (!stackData.Any())
            {
                throw new InvalidOperationException("No more data");
            }
            stackMin.Pop();
            return  stackData.Pop() ;
        } 

        public int Peek()
        {
            if (stackData.Any())
            {
                return stackData.Peek();
            }
            else
            {
                throw new InvalidOperationException("No more data");
            }
        }
 
    }
 
}