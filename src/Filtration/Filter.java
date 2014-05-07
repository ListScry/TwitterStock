package Filtration;

import SearchTwitter.TweetData;

import java.util.ArrayList;

/**
 * Created by Stefan Mellem on 5/7/14.
 */
public abstract class Filter {
    public abstract void filterTweets(ArrayList<TweetData> tweets);
    public abstract String toString();
}
