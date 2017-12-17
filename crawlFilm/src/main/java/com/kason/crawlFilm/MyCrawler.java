package com.kason.crawlFilm;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class MyCrawler extends WebCrawler {
    private Logger logger = LoggerFactory.getLogger(MyCrawler.class);

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith(CrawlProperties.SEEDURL);
               // && href.startsWith("http://www.ics.uci.edu/");
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            Map<String, String> metaTags = htmlParseData.getMetaTags();
            for (Map.Entry<String, String> entry : metaTags.entrySet()){

                logger.info("metaTags {} {}", entry.getKey(), entry.getValue());

            }
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            //logger.info("[text content] {}", text);
            logger.info("[html content] {}", html);
            //Set<WebURL> links = htmlParseData.getOutgoingUrls();

            //System.out.println("Text length: " + text.length());
           // System.out.println("Html length: " + html.length());
            //System.out.println("Number of outgoing links: " + links.size());
        }
    }
}
