package com.example.concurrent.atomic;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;


/**
 * DynamicProxyTest 生成的 $Proxy0.class 反编译之后的文件内容
 */
final class Proxy0 extends Proxy
        implements DynamicProxyTest.IHello
{
    private static Method m1;
    private static Method m3;
    private static Method m2;
    private static Method m0;

    public Proxy0(InvocationHandler paramInvocationHandler)
    {
        super(paramInvocationHandler);
    }

    public final boolean equals(Object paramObject)

    {
        try
        {
            return ((Boolean)this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
        }
        catch (RuntimeException localRuntimeException)
        {
            throw localRuntimeException;
        }
        catch (Throwable localThrowable)
        {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final void sayHello()
    {
        try
        {
            this.h.invoke(this, m3, null);
            return;
        }
        catch (RuntimeException localRuntimeException)
        {
            throw localRuntimeException;
        }
        catch (Throwable localThrowable)
        {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final String toString()
    {
        try
        {
            return (String)this.h.invoke(this, m2, null);
        }
        catch (RuntimeException localRuntimeException)
        {
            throw localRuntimeException;
        }
        catch (Throwable localThrowable)
        {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final int hashCode()
    {
        try
        {
            return ((Integer)this.h.invoke(this, m0, null)).intValue();
        }
        catch (RuntimeException localRuntimeException)
        {
            throw localRuntimeException;
        }
        catch (Throwable localThrowable)
        {
            throw new UndeclaredThrowableException(localThrowable);
        }

    }

    static
    {
        try
        {
            m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
            m3 = Class.forName("com.example.concurrent.atomic.DynamicProxyTest$IHello").getMethod("sayHello", new Class[0]);
            m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
            m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);

        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
            throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
        throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
        }
    }
}