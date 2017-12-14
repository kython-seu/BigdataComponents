package com.kason.film;

public class Bigdata {

    public static void main(String[] args) throws Exception{
        String url = "http://www.dytt8.net";
        System.out.println("开始爬取网页");
        MyCrawler crawler = new MyCrawler(url);
        crawler.crawling(new String[] { url },url);
    }
}
