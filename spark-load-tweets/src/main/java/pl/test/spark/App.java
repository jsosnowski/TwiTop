package pl.test.spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import twitter4j.Status;

import java.util.Date;

public class App {

    static {
        Logger.getLogger("org.apache.spark").setLevel(Level.ERROR);
    }

    public static void main(String[] args) {
        System.out.println( "Hello World! Now is: " + new Date());

        SparkConf conf = new SparkConf().setAppName("Tweets Loader");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));

        JavaReceiverInputDStream<Status> tweets = TwitterUtils.createStream(jssc);
        JavaDStream<Status> plTweets = tweets.filter(s -> s.getLang().contains("pl") || s.getLang().contains("PL") || s.getLang().contains("Pl"));
        // Print tweets:
        plTweets.print(100);

        jssc.start();
        jssc.awaitTermination();

    }
}
