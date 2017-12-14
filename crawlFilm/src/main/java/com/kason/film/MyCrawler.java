package com.kason.film;

import java.util.Set;

import org.htmlparser.util.ParserException;

public class MyCrawler {


    public static void main(String[] args) {
        MyCrawler myCrawler = new MyCrawler();
        //myCrawler.crawling(new String[]{""});
    }

    public MyCrawler() {
    }

    /**
     * 使用种子初始化 URL 队列
     *
     * @return
     * @param seeds
     *            种子URL
     */
    private	String url1 = null;
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++)
            LinkQueue.addUnvisitedUrl(seeds[i]);
    }
    public MyCrawler(String url){//构造函数
        this.url1 = url.replaceAll("[\\?/:*|<>\"]", "_");
    }
    /**
     * 抓取过程
     *
     * @return
     * @param seeds
     * @throws ParserException
     */
    public void crawling(String[] seeds,final String url2) throws ParserException { // 定义过滤器，提取以http://www.lietu.com开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith(url2))
                    return true;
                else
                    return false;
            }
        };
        // 初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        // 循环条件：待抓取的链接不空且抓取的网页不多于1000
        while (!LinkQueue.unVisitedUrlsEmpty()
                && LinkQueue.getVisitedUrlNum() <= 10000000) {
            // 队头URL出队列
            String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
            byte[] context = HtmlParserTool.paerserhtml(visitUrl, filter);
            System.out.println(LinkQueue.getVisitedUrlNum());
            if (visitUrl == null)
                continue;
            DownLoadFile downLoader = new DownLoadFile();
            // 下载网页

            //System.out.println(context.toString());
            downLoader.downloadfile(visitUrl,context,url1);
            // 该 url 放入到已访问的 URL 中
            LinkQueue.addVisitedUrl(visitUrl);
            // 提取出下载网页中的 URL
            Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter,url2);
            // 新的未访问的 URL 入队
            for (String link : links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }
}