package com.kason.film;

import java.awt.SystemColor;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import com.kason.film.Testchartset;

public class HtmlParserTool {
    // 获取一个网站上的链接,filter 用来过滤链接
    public static Set<String> extracLinks(String url, LinkFilter filter, String url2) {

        Set<String> links = new HashSet<String>();
        try {
            Parser parser = new Parser(url);
            parser.setEncoding(Testchartset.dectedCode(url));
            //parser.setEncoding("gb2312");
   /*NodeFilter filtercode = new TagNameFilter("META");
   NodeFilter filtercode1 = new TagNameFilter("meta");
   OrFilter linkFilter1 = new OrFilter(filtercode, filtercode1);
   NodeList node = parser.extractAllNodesThatMatch(linkFilter1);
   Node matanode = (Node) node.elementAt(0);
   String code = matanode.getText();
   int start1 = code.indexOf("charset=");
   code = code.substring(start1);
   //int end1 = code.indexOf(" ");
   //if (end1 == -1)
      //end1 = code.indexOf(">");
     String encode = code.substring(8, code.length() - 1);
     System.out.println(encode);
   parser.setEncoding(encode);*/


            // 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
            NodeFilter frameFilter = new NodeFilter() {
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src=")) {
                        //System.out.println("true");
                        return true;
                    } else {
                        //System.out.println("flase");
                        return false;
                    }
                }
            };
            // OrFilter 来设置过滤 <a> 标签，和 <frame> 标签
            OrFilter linkFilter = new OrFilter(new NodeClassFilter(
                    LinkTag.class), frameFilter);
            // 得到所有经过过滤的标签
            NodeList list = parser.extractAllNodesThatMatch(linkFilter);
            for (int i = 0; i < list.size(); i++) {
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag)// <a> 标签
                {
                    //System.out.println(tag.getText());
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink();// url
                    if (linkUrl.startsWith(url2)) {

                    } else {
                        if (linkUrl.startsWith("ftp")) {
                            linkUrl = null;
                        } else {
                            linkUrl = url2 + linkUrl;
                        }
                    }
                    //System.out.println(linkUrl);
                    //System.out.println(linkUrl);
                    //if (filter.accept(linkUrl))
                    //System.out.println(linkUrl);
                    if (linkUrl != null)
                        links.add(linkUrl);
                } else// <frame> 标签
                {
                    // 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
                    String frame = tag.getText();
                    //System.out.println(frame);
                    int start = frame.indexOf("src=");
                    //System.out.println(start);
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if (end == -1)
                        end = frame.indexOf(">");
                    String frameUrl = frame.substring(5, end - 1);
                    if (filter.accept(frameUrl))
                        links.add(frameUrl);
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return links;
    }

    public static byte[] paerserhtml(String url, LinkFilter filter) {
        //byte[] content = null;
        String context = null;
        Parser parser;
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");

        try {
            parser = new Parser(url);
            //parser.setEncoding("gb2312");
            parser.setEncoding(Testchartset.dectedCode(url));
            NodeFilter filter1 = new TagNameFilter("title");
            //NodeList nodes = parser.extractAllNodesThatMatch(filter1);
            NodeFilter filter2 = new TagNameFilter("div");
            OrFilter linkFilter = new OrFilter(filter1, filter2);
            NodeList nodes = parser.extractAllNodesThatMatch(linkFilter);
            if (nodes != null) {
                for (int i = 0; i < nodes.size(); i++) {
                    Node textnode = (Node) nodes.elementAt(i);
                    String line = textnode.getText();
                    //System.out.println(line);
                    String text = textnode.toPlainTextString();
                    //System.out.println(text);
                    if (line.contains("Zoom")) {
                        //System.out.println(line.replaceAll(" ", "").replaceAll("\n", ""));
                        Matcher m = p.matcher(text);
                        String t = m.replaceAll("");
                        context += t;
                        context += "\n";
                    }/*else{
            		   context += text;
            	   }*/

                    // if(textnode.getText()
                    //context += textnode.toPlainTextString();
                    //context += textnode.getText();
                }
                //System.out.println(context);
            }
	   /*NodeFilter filter2 = new TagNameFilter ("div");
	   NodeFilter filter3 = new TagNameFilter ("img");
	   OrFilter linkFilter = new OrFilter(filter3, filter2);
       NodeList nodes_context = parser.extractAllNodesThatMatch(linkFilter);
       if(nodes_context!=null) {
           for (int i = 0; i < nodes_context.size(); i++) {
               Node textnode_context = (Node) nodes_context.elementAt(i);
               context += textnode_context.toPlainTextString();
               context += textnode_context.getText();
               }
           }
       else{
    	   System.out.println("null");
    	   }*/

        } catch (ParserException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } finally {
        }
        if (context == null) {
            context = "页面找不到了";
            return context.getBytes();
        } else {
            return context.getBytes();
        }
    }

}

