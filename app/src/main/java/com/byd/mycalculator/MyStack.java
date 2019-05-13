package com.byd.mycalculator;

public class MyStack<T> {
    private int top = -1;   //栈顶指针
    private int maxSize;    //栈容量
    private Object[] data = null;

    MyStack() {
        data = new Object[100];
    }

    MyStack(int maxSize) {
        this.maxSize = maxSize;
        data = new Object[this.maxSize];
        top = -1;
    }

    public boolean isEmpty() {
        if (top == -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean push(T t) {
        if (top == maxSize - 1) {
            return false;
        } else {
            data[++top] = t;
            return true;
        }
    }

    public T pop() {
        if (top == -1) {
            throw new RuntimeException("栈为空!");
        } else {
            return (T)data[top--];
        }
    }

    public T peek(){
        if (top == -1) {
            throw new RuntimeException("栈为空!");
        } else {
            return (T)data[top];
        }
    }

    public int search(T t){
        int i = top;
        while (top != -1){
            if(peek() != t){
                top--;
            }else {
                break;
            }
        }
        int result = top;
        top = i;
        return result;
    }


}
