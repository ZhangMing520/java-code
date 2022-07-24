import java.util.Enumeration;

/**
 * @author zhangming
 * @date 2022/7/24 13:48
 * <p>
 * 因为写注册表需要管理员权限，所以以下命令在管理员 powershell 窗口执行
 * <p>
 * javac Win32RegKeyTest.java -encoding utf8
 * java  Win32RegKeyTest -Djava.library.path=.
 */
public class Win32RegKeyTest {

    public static void main(String[] args) {
        Win32RegKey key = new Win32RegKey(Win32RegKey.HKEY_LOCAL_MACHINE, "SOFTWARE\\JavaSoft\\Java Runtime Environment");

//        设置值
//        key.setValue("Default user", "Harry Hacker");
//        key.setValue("Lucky number", new Integer(13));
//        key.setValue("Small primes", new byte[]{2, 3, 5, 7, 11});

//        获取遍历值
        Enumeration<String> e = key.names();
        while (e.hasMoreElements()) {
            String name = e.nextElement();
            System.out.print(name + "=");

            Object value = key.getValue(name);
            if (value instanceof byte[]) {
                for (byte b : (byte[]) value) {
                    System.out.println((b & 0xFF) + " ");
                }
            } else {
                System.out.println(value);
            }
            System.out.println();
        }
    }
}
