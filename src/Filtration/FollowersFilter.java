package Filtration;

import SearchTwitter.TweetData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Stefan Mellem on 5/7/14.
 */
public class FollowersFilter extends Filter {
    private int threshold;

    public FollowersFilter(){
        this.threshold = 100;
    }

    public FollowersFilter(int threshold){
        this.threshold=threshold;
    }

    public void filterTweets(ArrayList<TweetData> tweets){
        for (Iterator<TweetData> i = tweets.iterator(); i.hasNext();){
            TweetData tweet = i.next();

            if (tweet.Followers < threshold)
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
        return "FollowersFilter"+threshold;
    }
}
