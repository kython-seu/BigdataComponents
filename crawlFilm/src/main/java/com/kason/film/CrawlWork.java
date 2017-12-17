package com.kason.film;

import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class CrawlWork implements Runnable{
    private Logger logger = LoggerFactory.getLogger(CrawlWork.class);

    private static String crawl_name = "CRAWL_FILE";

    public static String filterUrl;

    private final Object object = new Object();

    // 已访问的 url 集合
    private Set<String> visitedUrl = new HashSet<String>();
    // 待访问的 url 集合
    private Queue<String> unVisitedUrl = new PriorityQueue<String>();

    // 获得URL队列
    private Queue getUnVisitedUrl() {
        return unVisitedUrl;
    }

    // 添加到访问过的URL队列中
    private void addVisitedUrl(String url) {
        visitedUrl.add(url);
    }

    // 移除访问过的URL
    private void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }

    // 未访问的URL出队列
    private Object unVisitedUrlDeQueue() {
        return unVisitedUrl.poll();
    }

    // 保证每个 url 只被访问一次
    private void addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("") && !visitedUrl.contains(url)
                && !unVisitedUrl.contains(url))
            unVisitedUrl.add(url);
    }

    // 获得已经访问的URL数目
    private int getVisitedUrlNum() {
        return visitedUrl.size();
    }

    // 判断未访问的URL队列中是否为空
    private boolean unVisitedUrlsEmpty() {
        return unVisitedUrl.isEmpty();
    }

    private volatile boolean stopFlag = false;
    private LinkFilter filter = new LinkFilter() {
        public boolean accept(String url) {
            if (url.startsWith(filterUrl))
                return true;
            else
                return false;
        }
    };

    private DownLoadFile downLoader;

    public CrawlWork(String[] seeds) {

        // 初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        downLoader = new DownLoadFile();
    }

    public void run() {

        try {
            crawling();
        } catch (ParserException e) {
            logger.error("Crawl Film happens errror");
            e.printStackTrace();
        }
    }

    private void initCrawlerWithSeeds(String[] seeds){
        for(String seed : seeds){
            this.addUnvisitedUrl(seed);
        }
    }
    /**
     * 抓取过程
     *
     * @return
     * @throws ParserException
     */
    public void crawling() throws ParserException { // 定义过滤器，提取以http://www.lietu.com开头的链接

            while (!stopFlag){
                synchronized (object) {
                    if (!this.unVisitedUrlsEmpty() && this.getVisitedUrlNum() <= 100000) {
                        // 队头URL出队列
                        String visitUrl = (String) this.unVisitedUrlDeQueue();

                        byte[] context = null;
                        String respondBody = HtmlParserTool.parseHtml(visitUrl);
                        /*if(respondBody.equals("")){
                            continue;
                        }else{
                            context = respondBody.getBytes();
                        }*/
                        context = respondBody.getBytes();
                        logger.info(Thread.currentThread().getName() + " has already crawl {}", this.getVisitedUrlNum());
                        if (visitUrl == null)
                            continue;

                        if(!respondBody.equals("")) {
                            // 下载网页
                            downLoader.downloadfile(visitUrl, context, crawl_name);
                            logger.info("crawl content {}", new String(context));
                        }

                        // 该 url 放入到已访问的 URL 中
                        this.addVisitedUrl(visitUrl);
                        // 提取出下载网页中的 URL
                        Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter, filterUrl);
                        // 新的未访问的 URL 入队
                        for (String link : links) {
                            this.addUnvisitedUrl(link);
                        }
                    } else {
                        stopFlag = true;
                    }
                    logger.info("release the lock");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        stopFlag = true;
                        logger.error("interupt stop");
                        //e.printStackTrace();
                    }
                }
            }

    }
}
