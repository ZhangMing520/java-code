public class FloatZero {
    public static void main(String[] args) {
        float zeroP = +0.0f;
        float zeroN = -0.0f;
        System.out.println(zeroP == zeroN);
//        即 -0.0F 可通过 Float.intBitsToFloat(0x8000000) 求得
        System.out.println(Float.intBitsToFloat(0x8000000));
    }

}
