package org.isp.good;


public class Searcher extends AbstractSearcher {

    public Searcher(IGoodBodyGirl prettyGirl) {
        super(prettyGirl);
    }

    public Searcher(IGreatTemperamentGirl prettyGirl) {
        super(prettyGirl);
    }

    @Override
    public void show() {
        System.out.println("------美女的信息如下：------");
        if (goodBodyGirl != null) {
            goodBodyGirl.goodLooking();
            goodBodyGirl.niceFigure();
        }
        if (greatTemperamentGirl != null) {
            greatTemperamentGirl.greatTemperament();
        }
    }
}
