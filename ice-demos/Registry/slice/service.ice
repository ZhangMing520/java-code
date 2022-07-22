[["java:package:com.hp.tel.ice"]]
module book{
    struct Message{
        string name;
        // 类型 纸质书、电子书等
        int type;
//        若目前没有库存，这也预定成功
        bool valid;
        double price;
//        存放客户定制要求
        string content;
    };

    interface OnlineBook{

        Message bookTick(Message msg);
    };
};