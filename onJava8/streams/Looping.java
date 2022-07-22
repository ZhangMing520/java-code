/**
 * @author zhangming
 * @date 7/17/22 9:29 PM
 */
public class Looping {

    static void hi() {
        System.out.println("Hi!");
    }

    public static void main(String[] args) {
        Repeat.repeat(3, () -> System.out.println("Looping!"));
        Repeat.repeat(2, Looping::hi);
    }

}
