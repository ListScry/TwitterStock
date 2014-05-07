package Filtration;

import SearchTwitter.TweetData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Stefan Mellem on 5/7/14.
 */
public class SpamFilter extends Filter {

    public SpamFilter(){}

    public void filterTweets(ArrayList<TweetData> tweets){
        for (Iterator<TweetData> i = tweets.iterator(); i.hasNext();){
            TweetData tweet = i.next();

            if (tweet.Text.contains("http:") || tweet.Text.contains("www."))
                i.remove();
        }
    }

    public String toString(){
        return "SpamFilter";
    }
}
