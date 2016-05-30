/**
 * Created by Yang Jing (yangbajing@gmail.com) on 2016-05-30.
 */
public class JavaExample {
    public static int test(int n, int acc) {
        if (n <= 0)
            return acc;
        else
            return test(n - 1, n * acc);
    }

    public static void main(String[] args) {
        test(100, 1);
        long begin = System.nanoTime();
        int result = test(200, 1);
        long end = System.nanoTime();
        System.out.println(String.format("result: %s, cost time: %d ns", result, end - begin));
    }
}
