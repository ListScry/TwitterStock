package Filtration;

import SearchTwitter.TweetData;

import java.util.ArrayList;

/**
 * Created by Stefan Anders Mellem on 5/9/14.
 */
public class NoFilter extends Filter {

    public void filterTweets(ArrayList<TweetData> tweets) {
        return;
    }

    public String toString() {
        return "NoFilter";
    }
}
