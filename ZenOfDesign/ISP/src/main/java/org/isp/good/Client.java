package org.isp.good;


public class Client {

    public static void main(String[] args) {
        IGoodBodyGirl goodBodyGirl = new PrettyGirl("林青霞");
        IGreatTemperamentGirl greatTemperamentGirl = new PrettyGirl("林青霞");
        AbstractSearcher searcher = new Searcher(greatTemperamentGirl);
        searcher.show();
    }
}
