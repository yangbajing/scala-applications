/**
 * Created by Yang Jing (yangbajing@gmail.com) on 2016-05-30.
 */
public class JavaExample {

    public static long factorial(int n, long acc) {
        if (n <= 0)
            return acc;
        else
            return factorial(n - 1, n * acc);
    }

    public static void main(String[] args) {
        factorial(20, 1L);
        int i = 0;
        long result = 0L;
        long begin = System.nanoTime();
        while (i < 10) {
            result = factorial(20, 1L);
            i += 1;
        }
        long end = System.nanoTime();
        System.out.println(String.format("result: %d, cost time: %d ns", result, end - begin));
    }

}
