import Ice.Communicator;
import Ice.ObjectPrx;
import Ice.Util;
import com.generate.demo.MyServicePrx;
import com.generate.demo.MyServicePrxHelper;

/**
 * @author zhangming
 * @date 2020/7/11 10:42
 */
public class MyClient {

    public static void main(String[] args) {
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args);
            // 传入远程服务单元的名称 网络协议 ip 端口 构造一个 proxy 对象
            ObjectPrx base = communicator.stringToProxy("MyService:default -p 10001");

            // 通过 checkedCast 向下转型，获取 MyService 接口的远程，并同时检测根据传入的名称获取服务单元是否 OnlineBook 的代理接口
            // 包含一次远程调用的过程
            MyServicePrx proxy = MyServicePrxHelper.checkedCast(base);
            if (proxy == null) {
                throw new Error("Invalid proxy");
            }
            String rt = proxy.hellow();
            System.out.println(rt);
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
    }

}
