package org.isp.bad;

/**
 * @author zhangming
 * <p>
 * 美女的定义在发生变化，但是我们的接口却定义了美女必须要是三者都具备，按照这个标准，气质型美女就不能算美女了。
 * IPrettyGirl接口需要拆分为两个接口，一种是外形美，另外一种是气质美
 */
public interface IPrettyGirl {
    void goodLooking();

    void niceFigure();

    void greatTemperament();
}
