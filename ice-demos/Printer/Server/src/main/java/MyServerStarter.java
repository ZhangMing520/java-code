import Ice.Communicator;
import Ice.ObjectAdapter;
import Ice.Util;

/**
 * @author zhangming
 * @date 2020/7/11 10:32
 * <p>
 * gradlew :server:build
 */
public class MyServerStarter {

    public static void main(String[] args) {
        // A communicator starts a number of non-background threads.
        // Destroying the communicator terminates all these threads.

        /*
         * 初始化 Communicator 对象，args 可以传一些初始化参数，如连接超时，初始化客户端连接池的数量等
         */
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args);

            // 创建名为“MyServiceAdapter”的 ObjectAdapter，使用缺省的通信协议（tcp/ip端口为10000的请求）
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("MyServiceAdapter",
                    "default -p 10001");

            // 实例化一个 MyService 服务对象（servant）
            MyServiceImpl servant = new MyServiceImpl();

            // 将 servant 增加到 ObjectAdapter 对象中，并将 servant 关联到 ID 为 MyService的 ice object
            adapter.add(servant, Util.stringToIdentity("MyService"));

            // 激活 ObjectAdapter
            adapter.activate();

            System.out.println("server started");
            // 服务退出之前，一直持续对请求监听
            communicator.waitForShutdown();
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
    }
}
