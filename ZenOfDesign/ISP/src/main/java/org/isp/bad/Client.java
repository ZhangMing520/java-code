package org.isp.bad;

public class Client {

    public static void main(String[] args) {
        PrettyGirl girl = new PrettyGirl("林青霞");
        AbstractSearcher searcher = new Searcher(girl);
        searcher.show();
    }
}
