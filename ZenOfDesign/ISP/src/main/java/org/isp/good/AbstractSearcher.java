package org.isp.good;


public abstract class AbstractSearcher {
    protected IGoodBodyGirl goodBodyGirl;
    protected IGreatTemperamentGirl greatTemperamentGirl;

    public AbstractSearcher(IGoodBodyGirl prettyGirl) {
        this.goodBodyGirl = prettyGirl;
    }

    public AbstractSearcher(IGreatTemperamentGirl prettyGirl) {
        this.greatTemperamentGirl = prettyGirl;
    }

    public abstract void show();

}
