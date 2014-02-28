package SentimentAnalysis;

import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * Created by Shekhar Gulati
 */

public class SentimentAnalyzer {

    static Properties props;
    static StanfordCoreNLP pipeline;

    public static float findSentiment(String line) {
        float totalSentiment = 0.0f;
        int count = 0;
        if (line != null && line.length() > 0) {
            Annotation annotation = pipeline.process(line);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                //System.out.println(" " + tree.toString());

                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                //System.out.println("  " + sentiment);

                totalSentiment += sentiment;
                count++;
            }
        }
        return totalSentiment/count;

    }

    public static void init(){
        props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public static void main(String[] args) {
        SentimentAnalyzer.init();
        float sent = SentimentAnalyzer.findSentiment("meh! this tweet is really positive!");
        System.out.println(sent);
    }
}