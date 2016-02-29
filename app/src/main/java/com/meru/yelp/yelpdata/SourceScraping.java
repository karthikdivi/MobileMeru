package com.meru.yelp.yelpdata;


import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceScraping {


    public static Entity scrapeYelp(Document doc){
        Entity entity = new Entity();
        entity.setName(doc.select("[itemprop=name]").text());
        entity.setStreetAddress(doc.select("[itemprop=streetAddress]").text());
        entity.setLocality(doc.select("[itemprop=addressLocality]").text());
        entity.setRegion(doc.select("[itemprop=addressRegion]").text());
        entity.setPostalCode(doc.select("[itemprop=postalCode]").text());
        entity.setPhone(doc.select("[itemprop=telephone]").text());
        entity.webUrl = doc.select(".biz-website a").text();
        if(entity.webUrl!=null && !entity.webUrl.startsWith("http")){
            entity.webUrl = "http://"+entity.webUrl;
        }
        return entity;
    }


    public static Entity scrapeOpentable(Document doc){
        String pattern = "([^,]+),\\s+([A-Z]{2})\\s+(\\w+)";
        Entity entity = new Entity();
        entity.setName(doc.select("h1[itemprop=name]").text());
        String addressLine = doc.select("[itemprop=streetAddress]").text() ;
        int index = addressLine.lastIndexOf("\n");
        if(index != -1){
            if(addressLine.substring(0,index)!=null){
                entity.streetAddress = addressLine.substring(0,index).trim();
            }
            addressLine = addressLine.substring(index+1);
        }
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(addressLine);
        if(m.find()){
            entity.locality = m.group(1);
            entity.region = m.group(2);
            entity.postalCode = m.group(3);
        }
        entity.setWebUrl(doc.select(".restaurant-website").text());
        entity.setPhone(doc.select("span[itemprop=telephone]").text());
        return entity;
    }

}

