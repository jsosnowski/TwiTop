package pl.test.spark;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class App {

    public static void main(String[] args) {
        readTweetsUsingTwitter4j();
    }

    private static void readTweetsUsingTwitter4j() {
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                String rawJSON = TwitterObjectFactory.getRawJSON(status);
                System.out.println(rawJSON);
//                System.out.println(status.getUser().getName() + " : " + status.getText());
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("LIMIT: " + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setJSONStoreEnabled(true);
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        twitterStream.addListener(listener);
        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.sample("pl");
    }
}
