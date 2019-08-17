package geektime

import java.util.*
import java.util.stream.IntStream.range

fun main(args: Array<String>){
    println(fib(78))
}


fun fib(n: Int): Long {
    var tmp = 0L
    var pre = 1L
    var res = 1L
    for(i in 3..n){
        tmp = pre
        pre = res
        res = tmp + pre
    }

    return if(n <= 0) 0 else res
}