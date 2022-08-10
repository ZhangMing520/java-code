import com.sun.tools.attach.VirtualMachine;

// Attach部署方式需要JDK6以上版本，并且需要服务器中具备JDK环境（JRE不包含tools.jar和attach.so）
public class AttachTest {

    public static void main(String[] args) throws Exception {
        if (args.length <= 1) {
            System.out.println("Usage: java AttachTest /PATH/TO/AGENT.jar");
            return;
        }

        VirtualMachine vm = VirtualMachine.attach(args[0]);
        vm.loadAgent(args[1]);
    }

}
