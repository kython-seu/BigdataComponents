package com.kason.film;

public class Bigdata {

    public static void main(String[] args) throws Exception{
        String url = "http://www.dytt8.net";
        System.out.println("开始爬取网页");
        /*MyCrawler_back crawler = new MyCrawler_back(url);
        crawler.crawling(new String[] { url },url);*/

        int threadNUm = 5;

        CrawlWork crawlWork = new CrawlWork(new String[] { url });
        CrawlWork.filterUrl = url;

        for (int i=0; i< threadNUm; i++){
            Thread thread = new Thread(crawlWork);
            thread.setName("Thread-" + i);
            System.out.println("Thread-" + i + "start");
            thread.start();
        }
    }
}
