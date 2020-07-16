import Ice.Current;
import Ice.Logger;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book._OnlineBookDisp;

/**
 * @author zhangming
 * @date 2020/7/15 23:59
 */
public class OnlineBookI extends _OnlineBookDisp {

    @Override
    public Message bookTick(Message msg, Current __current) {
        Logger logger = __current.adapter.getCommunicator().getLogger();
        logger.print("bookTick to call ." + logger.getClass().getName());

        logger.print("bookTick called " + msg.content);
        return msg;
    }

}
