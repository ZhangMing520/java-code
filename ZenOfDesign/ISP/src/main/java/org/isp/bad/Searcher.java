package org.isp.bad;

public class Searcher extends AbstractSearcher {
    public Searcher(IPrettyGirl prettyGirl) {
        super(prettyGirl);
    }

    @Override
    public void show() {
        System.out.println("------美女的信息如下：------");
        prettyGirl.goodLooking();
        prettyGirl.niceFigure();
        prettyGirl.greatTemperament();
    }
}
