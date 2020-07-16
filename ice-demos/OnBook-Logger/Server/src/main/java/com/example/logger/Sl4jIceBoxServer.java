package com.example.logger;

import Ice.InitializationData;
import Ice.Util;
import IceBox.Server;

/**
 * @author zhangming
 * @date 2020/7/14 23:43
 */
public class Sl4jIceBoxServer {

    public static void main(String[] args) {
        InitializationData initData = new InitializationData();
        initData.properties = Util.createProperties();
        initData.properties.setProperty("Ice.Admin.DelayCreation", "1");
        /**
         * 关键一步
         *
         * 其他与 {@link IceBox.Server} 一致
         *
         * 问题 ThreadPool Network 日志还是没有修改
         */
        initData.logger = new Sl4JLoggerI("system");

        Server server = new Server();
        System.exit(server.main("IceBox.Server", args, initData));
    }

}
