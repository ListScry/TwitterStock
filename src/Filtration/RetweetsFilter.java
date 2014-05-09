package Filtration;

import SearchTwitter.TweetData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Stefan Mellem on 5/7/14.
 */
public class RetweetsFilter extends Filter {
    private int threshold;

    public RetweetsFilter(){
        this.threshold = 100;
    }

    public RetweetsFilter(int threshold){
        this.threshold=threshold;
    }

    public void filterTweets(ArrayList<TweetData> tweets){
        for (Iterator<TweetData> i = tweets.iterator(); i.hasNext();){
            TweetData tweet = i.next();

            if (tweet.Retweets < threshold)
                i.remove();
        }
    }

    public void setThreshold(int threshold){
        this.threshold = threshold;
    }

    public int getThreshold(){
        return threshold;
    }

    public String toString(){
        return "RetweetsFilter with threshold: "+threshold;
    }
}
