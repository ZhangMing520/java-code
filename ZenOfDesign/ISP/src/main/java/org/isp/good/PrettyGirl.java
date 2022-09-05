package org.isp.good;

/**
 * @author zhangming
 * <p>
 * 不管是外形美女还是气质美女都可以通过 {@link PrettyGirl}定义
 */
public class PrettyGirl implements IGoodBodyGirl, IGreatTemperamentGirl {

    private String name;

    public PrettyGirl(String name) {
        this.name = name;
    }

    @Override
    public void goodLooking() {
        System.out.println(this.name + "--脸蛋很漂亮！");
    }

    @Override
    public void niceFigure() {
        System.out.println(this.name + "--身材非常棒！");
    }

    @Override
    public void greatTemperament() {
        System.out.println(this.name + "--气质非常好！");
    }
}
