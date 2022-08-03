/**
 * @author zhangming
 * @date 7/25/22 12:30 AM
 * <p>
 * this application demonstrates the bytecode verifier of the virtual machine.
 * If you use a hex editor to modify the class file, then the virtual machine should detect the tampering
 * <p>
 * 1. javac VerifierTest.java
 * 2. javap -c VerifierTest.class
 * 3. sudo apt install ghex
 * 4. 修改 class 04 3B 05 3C 1A 1B 60 3D 1C AC , 将 3C 修改为 3B  (m倍初始化了2次，n未被初始化)
 * 5. java VerifierTest
 * 6. java -noverify VerifierTest      结果是随机值
 */
public class VerifierTest {

    public static void main(String[] args) {
        System.out.println("1 + 2 == " + fun());
    }

    public static int fun() {
        int m;
        int n;
        m = 1;
        n = 2;
//        use hex editor to change to "m = 2" in class file
        int r = m + n;
        return r;
    }
}
