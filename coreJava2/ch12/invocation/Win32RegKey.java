import java.util.Enumeration;

/**
 * @author zhangming
 * @date 7/22/22 9:52 PM
 * <p>
 *  cl -I $JAVA_HOME\include -I $JAVA_HOME\include\win32 -LD 
 */
public class Win32RegKey {

    public static final int HKEY_CLASSES_ROOT = 0x80000000;
    public static final int HKEY_CURRENT_USER = 0x80000001;
    public static final int HKEY_LOCAL_MACHINE = 0x80000002;
    public static final int HKEY_USERS = 0x80000003;
    public static final int HKEY_CURRENT_CONFIG = 0x80000005;
    public static final int HKEY_DYN_DATA = 0x80000006;

    private int root;
    private String path;

    public Win32RegKey(int root, String path) {
        this.root = root;
        this.path = path;
    }

    /***
     *  getValue
     * 1. 打开注册表键。为了读取它们的值，注册表API要求这些键是开放的
     * 2. 查询与名字关联的值的类型和大小
     * 3. 把数据读到缓存
     * 4. 如果类型是 REG_SZ（字符串），调用 NewStringUTF，用该值来创建一个新的字符串
     * 5. 如果类型是 REG_DWORD（32位整数），调用 Integer 构造器
     * 6. 如果类型是 REG_BINARY，调用 NewByteArray 来创建一个新的字节数组，并调用 SetByteArrayRegion，把值数据复制到该字节数组中。
     * 7. 如果不是以上类型或调用 API 函数时出现错误，那就抛出异常，并小心地释放到此为止所获得的所有资源
     * 8. 关闭键，并返回创建的对象（String Integer byte[]）
     *
     * @param name
     * @return
     */
    public native Object getValue(String name);

    /***
     * setValue
     * 1. 打开注册表键以便写入
     * 2. 找出要写入的值的类型
     * 3. 如果类型是 String，调用 GetStringUTFChars 获取一个指向这些字符的指针
     * 4. 如果类型是 Integer,调用 intValue 方法获取该包装对象中存储的整数
     * 5. 如果类型是 byte[], 调用 GetByteArrayElements 获取指向这些字节的指针
     * 6. 把数据和长度传递给注册表
     * 7. 关闭键值。
     * 8. 如果类型是 String 和 byte[], 那么还要释放指向数据的指针
     *
     * */
    public native void setValue(String name, Object value);

    public Enumeration<String> names() {
        return new Win32RegKeyNameEnumeration(root, path);
    }
}

/***
 * 当枚举过程开始时候，我们必须打开键。因此在枚举过程中，我们必须保持该键的句柄。也就是说，该键的句柄必须与枚举对象存放在一起。
 * 键的句柄是 DWORD 类型的，它是一个 32 位数，所以可以存放在一个 Java 的整数中。它被存放在枚举类的 hkey 域中
 * */
class Win32RegKeyNameEnumeration implements Enumeration<String> {

    private int root;
    private String path;
    private int index = -1;
    private int hkey = 0;
    // 名/值对的个数和最长名字的长度
    private int maxsize;
    private int count;

    public Win32RegKeyNameEnumeration(int root, String path) {
        this.root = root;
        this.path = path;
    }

    /**
     * 1. 获取 index 和 count 域
     * 2. 如果 index 是 -1 ，调用 startNameEnumeration 函数打开键，查询数量和最大长度，初始化 hkey count maxsize index
     * 3. 如果 index 小于 count，则返回 JNI_TRUE，否则返回 JNI_FALSE
     */
    public native boolean hasMoreElements();

    /***
     * 1. 获取 index 和 count 域
     * 2. 如果 index 是 -1 ，调用 startNameEnumeration 函数打开键，查询数量和最大长度，初始化 hkey count maxsize index
     * 3. 如果 index 等于 count，抛出一个 NoSuchElementException 异常
     * 4. 从注册表中读取下一个名字。
     * 5. 递增 index
     * 6. 如果 index 等于 count，则关闭键
     * */
    public native String nextElement();

}

class Win32RegKeyException extends RuntimeException {
    public Win32RegKeyException() {
    }

    public Win32RegKeyException(String message) {
        super(message);
    }

}