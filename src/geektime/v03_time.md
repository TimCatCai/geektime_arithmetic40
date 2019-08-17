# type
o(1): Constant Complexity  
o(log n): Logarithmic Complexity   
o(n): Linear Complexity  
o(n^2): N square Complexity  
o(n^3): N cube Complexity  
o(2^n): Exponential Growth  
o(n!): Factorial   
# 递归的时间复杂度运算
## 斐波拉切数列的例子
这里需要特别注意精度溢出的问题，若用int存储，只能执行到46(18亿)(int 21亿)  
若用long能执行到：92(7.5\*10^18)(long: 9\*10^18), 对于矩阵快速法，要考虑  
到尾数部分有52位，指数11位，符号位１位，所以到超过76(76: 3416454622906707 77: 5527939700884757)(2^52=4.503599627×10¹⁵)时就开始产生误差。
但移位的舎入方法不祥。  
误差的产生原因是浮点数的尾数部分产生了假溢出。
### 指数递归方法　
斐波拉切数列递归实现时间复杂度: 2^n  
计算方法: 差分方程： (1 + 根5 / 2) ^ 2
```java
public class  Recursion{
   long fib(int n){
        if(0 == n || 1 == n){
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
```


```kotlin
fun fib(n: Long): Long =
    if(0L == n || 1L == n){
       n
    }
    else{
        fib(n - 1) + fib(n - 2)
    }
```

### 尾递归实现方法：  
尾递归的定义： 在一个程序中，执行最后一条语句是对自己的调用，且没有其他的运算  
尾递归的实现：　在编译器优化的条件下实现的。递归的第一次调用会开辟一份空间，  
此后的递归调用会在该开辟的空间上执行，而不会重新开辟。   
本质： 与循环法没有任何区别，也就这种尾递归的方法完全可以转化为循环来实现。　用这种的方法不好了解，
失去了递归的应有的简介性，推荐使用循环实现。　　
时间复杂度：　O(n-2) = O(n)   
空间复杂度：　O(n)(编译器不优化)　O(1)(编译器优化) 


```java
public class TailRecursion{
    public long fibRoot(long first, long second, int n){
        if(n == 3){
            return first + second;
        }
        if(n <= 2 && n >=0){
            return n;
        }
        if(n < 0){
            return 0L;
        }
        
        return fibRoot(second, first + second, n - 1);
    }
    
    public int fib(int n){
        return fibRoot(1L, 1L, n);
    }   
}
```
```kotlin
fun fibRoot(first: Long, second: Long, n: Int): Long = when{
    n == 3        -> first + second
    in range(0,3) -> 1L
    n < 0         -> 0L 
    else          -> fibRoot(second, first + second, n - 1)
}

fun fib(n: Int) = fibRoot(1L, 1L, n)
```

### 循环实现方法
时间复杂度：　O(n-2) = O(n)   
空间复杂度：　O(1) 

```java
public class cycleFib{
    public static long fib(int n){
        long tmp = 0L;
        long pre = 1L;
        long res = 1L;
        for(int i = 3;i <= n;i++){
            tmp = pre;
            pre = res;
            res = tmp + pre;
        }
        return n <= 0 ? 0L : res;
    }
}
```

```kotlin
fun fib(n: Int):Long {
    var tmp = 0L
    var pre = 1L
    var res = 1L
    for(i in 3..n+1){
        tmp = pre
        pre = res
        res = tmp + pre
    }

    return if(n <= 0) 0L else res 
}
```

### 矩阵实现

| fn+1  fn  | = | fn   fn-1|*|1 1|...= |f2 f1|*(|1 1|)^n-1 = (|1 1|)^n 
| fn    fn-1| = | fn-1 fn-2|*|1 0|...= |f1 f0|*(|1 0|)     = (|1 0|)

令
Q = |1 1|
  = |1 0|
则
| fn+1  fn  | = Q^n  
| fn    fn-1| =
则
fn = (Q^n)0,1　(坐标从(0,0)开始)

矩阵快速幂：
a^n = a^010...00(2) = a^(2^k\*0 + 2^k-1 \*1...+2^1\*0+2^0\*0)  
= (a^2^k)^0\*(a^2^k-1)^1....(a^2^1)^0\*(a^2^0)^0  　
a^2^k = a^2^k-1 * a^2^k-1

time = O(log n)
```java
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Test{
    public static double fib(int n){
        // 若传入的参数<=0，则直接返回0
        if(n <= 0){
            return 0;
        }
        // 斐波拉切Ｑ－矩阵的数组数据
        double [][] QData = {{1, 1},
                             {1, 0}};
        // 斐波拉切Ｑ－矩阵
        RealMatrix Q = new Array2DRowRealMatrix(QData);

        // Fn = (Q^n)0,1
        //　将结果矩阵初始化为单位阵
        double [][] resultMatrixData = {{1, 0},
                                        {0, 1}};
        RealMatrix resultMatrix = new Array2DRowRealMatrix(resultMatrixData);
        while(0 != n){
            //判断最低位是否为１，１则乘上Q矩阵
            if((n & 1) == 1){
                resultMatrix = resultMatrix.multiply(Q);
            }
            //　n左移一位
            n >>= 1;
            //　Q 矩阵倍增
            Q = Q.multiply(Q);
        }

        double fn = resultMatrix.getEntry(0, 1);
        return fn;
        }
}       
```