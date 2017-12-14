package com.kason.film;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.Html;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

public class Testchartset{
    private static final String oriEncode = "UTF-8,utf-8,gb2312,gbk,iso-8859-1";

    public static void main(String [] args){


        String url="http://www.dytt8.net/";
        String decode=dectedCode(url);
        System.out.println(decode);

    }
    /**
     * 检测字符级
     * @param url
     * @return
     */
    /*public static String dectedEncode(String url) {
        String[] encodes = oriEncode.split(",");
        for (int i = 0; i < encodes.length; i++) {
            if (dectedCode(url, encodes[i])) {
                return encodes[i];
            }
        }
        return null;
    }*/

    public static String dectedCode(String url) {
        /*try {
            Parser parser = new Parser(url);
            //parser.setEncoding(encode);
            NodeFilter filtercode = new TagNameFilter("META");
     	   NodeFilter filtercode1 = new TagNameFilter("meta");
     	   OrFilter linkFilter = new OrFilter(filtercode, filtercode1);
     	   NodeList node = parser.extractAllNodesThatMatch(linkFilter);
     	   Node matanode = (Node) node.elementAt(0);
     	   String code = matanode.getText();
     	   //System.out.println(code);
     	   int start = code.indexOf("charset=");
     	   code = code.substring(start);
     	   //System.out.println(code);
     	   int end = code.indexOf(" ");
     	   //System.out.println(end);
     	   if(end==-1){
     		   end=code.length();
     	   }
     	   String encode1 = code.substring(8, end-1);
     	   String encode2 = code.substring(9,end-1);
     	   //System.out.println(encode1+"chkc"+encode2);
     	  String[] encodes = oriEncode.split(",");
     	   if(encode1.equals(encodes[0])||encode2.equals(encodes[0])||encode1.equals(encodes[1])||encode2.equals(encodes[1]))
                    return "utf-8";
     	   if(encode1.equals(encodes[2])||encode2.equals(encodes[2]))
     		   return "gb2312";
     	  if(encode1.equals(encodes[3])||encode2.equals(encodes[3]))
    		   return "gbk";
     	 if(encode1.equals(encodes[4])||encode2.equals(encodes[4]))
   		   return "iso-8859-1";
        } catch (Exception e) {

        }*/

        return "gb2312";
    }
}
