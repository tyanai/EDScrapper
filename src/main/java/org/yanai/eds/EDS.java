package org.yanai.eds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class EDS {

    private final String ticker;
    private final String earningDate;

    public EDS(String ticker, String earningDate) {
        this.ticker = ticker;
        this.earningDate = earningDate;
    }

    public String getTicker() {
        return ticker;
    }

    public String getEarningDate() {
        return earningDate;
    }
    
    public static String findTickerEarningDate(String ticker) {
        String url = "https://finance.yahoo.com/quote/" + ticker + "?p=" + ticker;

       

        java.net.URL formedURL = null;
        try {
            formedURL = new java.net.URL(url);
        } catch (Exception e) {
            return e.getMessage();
        }
        
        String earningDate = null;
        try {
            String htmlString = callURL(formedURL.toString());
            Document doc = Jsoup.parse(htmlString);

            String body = doc.body().text();

            int locationOfString = body.indexOf("Earnings Date");
            earningDate = body.substring(locationOfString + 14, locationOfString + 26);
            
            if (earningDate.indexOf("No matching") > -1){
                earningDate = "";
            }
            
            

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return earningDate;
    }

    public static String callURL(String myURL) throws IOException {
        
               
        System.out.println("Requeted URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
      
        
        InputStreamReader in = null;
        try {
            
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null) {
                urlConn.setReadTimeout(60 * 1000);
            }
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            
           
        } catch (java.net.ConnectException  e) {  
            return "CR";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        } finally {
            // Release the connection.
            if (in != null){
                in.close();
            }
        } 

        return sb.toString();
        
    }
}
