/**
 * @author zhangming
 * @date 2022-07-18 21:43:34
 */
class Printf1Test {

    public static void main(String[] args) {
        // count=8 返回打印的字符长度
        int count = Printf1.print(8, 4, 3.14);
        // cout+=8
        count += Printf1.print(8, 4, count);
        System.out.println();
        for (int i = 0; i < count; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
