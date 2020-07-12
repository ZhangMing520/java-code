import com.generate.demo._MyServiceDisp;

/**
 * @author zhangming
 * @date 2020/7/11 10:30
 */
public class MyServiceImpl extends _MyServiceDisp {

    @Override
    public String hellow(Ice.Current __current) {
        return "Hello world!";
    }
}
