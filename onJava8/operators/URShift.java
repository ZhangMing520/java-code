/**
 * 左移运算符 << 能将其左边的运算对象向左移动右侧指定的位数，低位补0；
 * 右移运算符有正、负值：若值为正，则在高位插入0；若值为负，则在高位插入1
 * 
 * >>>，使用了“零扩展”：无论正负，都在高位插入0。
 * 
 * 移动char、byte、short，移动发生之前将其提升为int，结果为int
 */
public class URShift {
    public static void main(String[] args) {
        int i = -1;
        System.out.println(Integer.toBinaryString(i));
        i >>>= 10;
        System.out.println(Integer.toBinaryString(i));

        long l = -1;
        System.out.println(Long.toBinaryString(l));
        l >>>= 10;
        System.out.println(Long.toBinaryString(l));

        short s = -1;
        System.out.println(Integer.toBinaryString(s));
        s >>>= 10;
        System.out.println(Integer.toBinaryString(s));

        byte b = -1;
        System.out.println(Integer.toBinaryString(b));
        b >>>= 10;
        System.out.println(Integer.toBinaryString(b));
        b = -1;
        System.out.println(Integer.toBinaryString(b));
        System.out.println(Integer.toBinaryString(b >>> 10));

    }
}