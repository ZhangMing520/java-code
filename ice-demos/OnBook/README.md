1. _OnlineBookOperations 与 _OnlineBookOperationsNC不用
> 前者在方法签名中增加了ice的内部对象 Ice.Current，这个Current对象包括了当前调用网络连接等上下文信息

2. OnlineBookDisp 代码分析
```java 
public static Ice.DispatchStatus ___bookTick(OnlineBook __obj, IceInternal.Incoming __inS, Ice.Current __current)
{
    __checkMode(Ice.OperationMode.Normal, __current.mode);
    // IceInternal.Incoming 代表着当前 rpc 请求的网络通道
    // 从这个网络通道中读取 rpc 方法传入的参数
    IceInternal.BasicStream __is = __inS.startReadParams();

    // 构造具体的参数对象
    Message msg;
    msg = new Message();

    // 执行反序列化逻辑 即将网络字节流变为具体的Java对象属性
    msg.__read(__is);
    __inS.endReadParams();

    // 调用用户实现的 OnlineBook 的具体业务接口，即 OnlineBookOperationsNC 的实现方法
    Message __ret = __obj.bookTick(msg, __current);

    // 将结果协会到 rpc 请求的网络应答报文中
    IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);

    // 反序列化调用结果 写入网络通道 等待发送给客户端
    __ret.__write(__os);
    __inS.__endWriteParams(true);

    // 完成调用
    return Ice.DispatchStatus.DispatchOK;
}
```

3. Message对象的序列化和反序列化的具体实现代码
> Java里最基本的对象到字节流的转换逻辑（ObjectStream）,Java Buffer类可以指定大端/小端的编码方式，ByteBuffer.order(ByteOrder.LITTLE_ENDIAN) 方法实现
```java 
public void
__write(IceInternal.BasicStream __os)
{
    __os.writeString(name);
    __os.writeInt(type);
    __os.writeBool(valid);
    __os.writeDouble(price);
    __os.writeString(content);
}

public void
    __read(IceInternal.BasicStream __is)
    {
        name = __is.readString();
        type = __is.readInt();
        valid = __is.readBool();
        price = __is.readDouble();
        content = __is.readString();
    }
```

4. Ice Object 的 ID
```java
public static final String[] __ids =
{
      "::Ice::Object",   // 所有的 Ice Object 都有此ID
      "::book::OnlineBook"  // 这是 OnlineBook 这个Object的真正ID
};

 public static String ice_staticId()
    {
// 默认的ID    "::book::OnlineBook"
        return __ids[1];
    }

// 二分法 查找是否是某个远程对象
   public boolean ice_isA(String s)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

```

5. 客户端代理 _OnlineBookDel
```java
public interface _OnlineBookDel extends Ice._ObjectDel
{
    Message bookTick(Message msg, java.util.Map<String, String> __ctx, Ice.Instrumentation.InvocationObserver __obsv)
        throws IceInternal.LocalExceptionWrapper;
}
```
> 此代理接口有两个具体的实现类，并且为Final类，不可修改
- __OnlineBookDelD  本地优化模式的调用代理
- __OnlineBookDelM  远程网络调用代理

```java 
// ObjectPrxHelperBase 自动选择创建本地或者远程代理

_ObjectDel createDelegate(boolean async) {
    if (this._reference.getCollocationOptimized()) {
        // 具备优化条件，则创建本地直接调用代理对象 xxxDelD 
        ObjectAdapter adapter = this._reference.getInstance().objectAdapterFactory().findObjectAdapter(this);
        if (adapter != null) {
            _ObjectDelD d = this.__createDelegateD();
            d.setup(this._reference, adapter);
            return d;
        }
    }

    _ObjectDelM d = this.__createDelegateM();
    d.setup(this._reference, this, async);
    return d;
}
```

```java 
// __OnlineBookDelM  远程网络调用代理

public Message
    bookTick(Message msg, java.util.Map<String, String> __ctx, Ice.Instrumentation.InvocationObserver __observer)
        throws IceInternal.LocalExceptionWrapper
    {
        // 得到网络通信的输出通道
        IceInternal.Outgoing __og = __handler.getOutgoing("bookTick", Ice.OperationMode.Normal, __ctx, __observer);
        try
        {
            try
            {
                // 写 rpc 调用的参数， Message 对象序列化到输出流中
                IceInternal.BasicStream __os = __og.startWriteParams(Ice.FormatType.DefaultFormat);
                msg.__write(__os);
                __og.endWriteParams();
            }
            catch(Ice.LocalException __ex)
            {
                __og.abort(__ex);
            }
            // 发送数据并检查是否成功
            boolean __ok = __og.invoke();
            try
            {
                if(!__ok)
                {
                    try
                    {
                        __og.throwUserException();
                    }
                    catch(Ice.UserException __ex)
                    {
                        throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                    }
                }

// 等待服务端的响应结果消息，并反序列化为 Message 应答对象
                IceInternal.BasicStream __is = __og.startReadParams();
                Message __ret;
                __ret = new Message();
                __ret.__read(__is);
                __og.endReadParams();
                return __ret;
            }
            catch(Ice.LocalException __ex)
            {
                throw new IceInternal.LocalExceptionWrapper(__ex, false);
            }
        }
        finally
        {
    // 清理此次调用
            __handler.reclaimOutgoing(__og);
        }
    }
```

6. OnlineBookPrxHelper.checkedCast
> 从只包含基础的对象 ID、EndPoint等相关信息的基础 ObjectPrx 类中复制消息，并构造一个具体的 ObjectPrx 实现类
```java
 public static OnlineBookPrx checkedCast(Ice.ObjectPrx __obj)
    {
        OnlineBookPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof OnlineBookPrx)
            {
                __d = (OnlineBookPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId()))
                {
// 构造具体的 proxy 类
                    OnlineBookPrxHelper __h = new OnlineBookPrxHelper();
// 从基础 Proxy 中复制必要信息，包括连接等内部信息 
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }
```