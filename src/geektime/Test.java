package geektime;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Test {
    public static void main(String [] args) {
        for(int i = 60; i<200;i++){
            System.out.println(i + ": " +fibTailRec(i));
        }

    }
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
    public static Long fibCycle(int n){
        long tmp = 0;
        long pre = 1;
        long res = 1;
        for(int i = 3;i <= n;i++){
            tmp = pre;
            pre = res;
            res = tmp + pre;
        }
        return n <= 0 ? 0 : res;
    }

    public static long fibRec(int n){
        if(0 == n || 1 == n){
            return n;
        }
        return fibRec(n - 1) + fibRec(n - 2);
    }

    public  static long fibRoot(long first, long second, int n){
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

    public static long fibTailRec(int n){
        return fibRoot(1L, 1L, n);
    }

}
