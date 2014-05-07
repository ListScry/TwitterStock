package Filtration;

import SearchTwitter.TweetData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Stefan Mellem on 5/7/14.
 */
public class FeelFilter extends Filter {

    public void filterTweets(ArrayList<TweetData> tweets){
        for (Iterator<TweetData> i = tweets.iterator(); i.hasNext();){
            TweetData tweet = i.next();

            String tweetTextLowerCase = tweet.Text.toLowerCase();

            if (!(tweetTextLowerCase.contains("i feel") || tweetTextLowerCase.contains("make me") ||
                    tweetTextLowerCase.contains("i dont feel") || tweetTextLowerCase.contains("i don't feel") ||
                    tweetTextLowerCase.contains("i'm") || tweet.Text.contains("i am")))
                i.remove();
        }
    }

    public String toString(){
        return "FeelFilter";
    }
}
