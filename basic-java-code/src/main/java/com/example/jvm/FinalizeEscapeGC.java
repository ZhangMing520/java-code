package com.example.jvm;

/**
 * @author zhangming
 * @date 2019/3/10 21:26
 * <p>
 * 1. 对象可以在被 gc 时候自我拯救
 * 2. 这种自救的机会只有一次，因为一个对象的finalize()方法最多只会被系统调用一次
 *
 * 对象死亡至少要经历两次标记过程：对象在进行根搜索后发现没有与 GC Roots相连接的引用链，对象会被第一次标记并且进行一次筛选，
 * 筛选的条件是此对象是否有必要执行finalize()方法。
 * 对象没有覆盖 finalize() 方法或者 finalize()  已经被虚拟机调用过，虚拟机都视为“没有必要执行”。
 * 如果有必要执行，这个对象会被放置在一个 F-Queue的队列，并在稍后由一条虚拟机自动创建，低优先级的 Finalizer线程执行。但不承诺会等待它运行结束
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("i am still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method invoke");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();

        // 对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();
        // Finalizer 方法优先级很低，暂停 0.5 秒，等待
        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("i am dead");
        }

        // 拯救失败
        SAVE_HOOK = null;
        System.gc();
        // Finalizer 方法优先级很低，暂停 0.5 秒，等待
        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("i am dead");
        }
    }
}
