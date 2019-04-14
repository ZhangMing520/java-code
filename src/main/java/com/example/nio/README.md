1. channels buffers selectors
- 数据总是从 channel 中读取到 buffer中，或者从 buffer 中写入 channel
- selector 可以监控多个 channels 的事件(例如: connection opened, data arrived etc.) 

2. channel 种类
- FileChannel
- DatagramChannel
- SocketChannel
- ServerSocketChannel

3. channel 和 stream 比较
- channel 是可读可写的，Stream 只能读或者只能写
- channel 读写操作可以是非阻塞的
- channel 总是读取到 buffer 中，或者从 buffer 写入


4. buffer 读写4步
- 写入数据到 buffer 中
- 调用 buffer.flip()
- 从 buffer 中读取数据
- 调用 buffer.clear() 或者 buffer.compact()

5. flip() clear() compact() rewind()  mark() reset()
- flip() 将 buffer 从写模式转换为读模式

- clear() 使 buffer 可以再次写入，清空 buffer

- compact() 使 buffer 可以再次写入，只清除 buffer 中已经被读取的内容，没有被读取的内容移动到 buffer 开头，新写入的内容在其之后

- rewind() 重置 position 为0，可以重新读取 buffer 中的所有数据

- mark() 标记 position 位置，通过 reset() 重置 position 到刚刚标记的值，mark() 和 reset() 之间读取数据，实现重复读取部分数据

6. buffer 的3个属性 capacity，position，limit
> position，limit依赖于 buffer 的模式变化，capacity 一直不变
- capacity 就是 buffer 容量

- position 写入时候，初始化是0，position 指向下一个要写入数据的单元格，最大值是 capacity-1；
读取时候，flip()调用之后，position 重置为0，此时 position 指向下一个要读取的数据单元格

- limit 写入时候，limit 等于 capacity，表示能够写入 buffer 的数据量；
读取时候，表示能够从 buffer 中读取的数据量，limit 等于（flip()之前）写模式的 position，写入多少数据就能读取多少数据，limit 表示写入的数据量


7. scatter gather 
> scatter 从 channel 读取数据到多个 buffer 中；gather 将多个 buffer 中数据写入到 channel

8. selector 监听的状态
> selector 只能监听非阻塞模式的 channel，FileChannel不能设置为 not-blocking，Socket channel 可以。
- Connect
- Accept
- Read
- Write

9. SelectionKey 包含的属性
- 监听的事件集合（一个或者多个并集）
- 准备就绪的事件集合
- channel
- selector 
- 附加对象（可选）

```
// 监听的事件
int interestSet = selectionKey.interestOps();
boolean isInterestedInAccept  = interestSet & SelectionKey.OP_ACCEPT;
boolean isInterestedInConnect = interestSet & SelectionKey.OP_CONNECT;
boolean isInterestedInRead    = interestSet & SelectionKey.OP_READ;
boolean isInterestedInWrite   = interestSet & SelectionKey.OP_WRITE; 

// 准备就绪的事件
int readySet = selectionKey.readyOps();
selectionKey.isAcceptable();
selectionKey.isConnectable();
selectionKey.isReadable();
selectionKey.isWritable();

Channel  channel  = selectionKey.channel();

Selector selector = selectionKey.selector(); 

selectionKey.attach(theObject);

Object attachedObj = selectionKey.attachment();
```

10. Selector select() 阻塞直到监听的事件准备就绪
> int 返回值表示就绪 channel 的个数
- int select()
- int select(long timeout)
- int selectNow()  不阻塞，立即返回，不管 channel 是否就绪

```
// SelectionKey 代表 Channel 注册到 Selector 的信息
Set<SelectionKey> selectedKeys = selector.selectedKeys();

Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

while(keyIterator.hasNext()) {
    
    SelectionKey key = keyIterator.next();

    if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.

    } else if (key.isConnectable()) {
        // a connection was established with a remote server.

    } else if (key.isReadable()) {
        // a channel is ready for reading

    } else if (key.isWritable()) {
        // a channel is ready for writing
    }

    // Channel 处理完成，根据业务决定是否移除
    keyIterator.remove();
}
```

11. wakeUp()
> 一个线程调用 select() 阻塞，另一个线程可以在同一个 Selector 上调用 wakeUp()，那么阻塞线程就会立即返回。如果在调用 wakeUp() 时候，没有线程被 select() 阻塞，那么下一个线程调用 select() 时候会立即返回。


#### 注意
- selector 适用于应用有很多连接，但是每一个连接处理时间不是很长，否则会阻塞其他 channel 