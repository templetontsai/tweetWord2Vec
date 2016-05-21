package unimelb.distributed_project.utils.mongodb;

import org.bson.Document;

/**
 * Created by templeton on 2016/4/20.
 */
public class TwitterAuth {

    // Class to keep Twitter authentication details. This is an immutable class.

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String screenName;


    public TwitterAuth(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        super();
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        this.screenName = null;
    }

    public TwitterAuth() {
        this(null, null, null, null);
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screen_name) {
        this.screenName = screen_name;
    }

    public Document toDoc() {
        Document d = new Document();
        d.append("twitter_consumerKey", this.consumerKey);
        d.append("twitter_consumerSecret", this.consumerSecret);
        d.append("twitter_accessToken", this.accessToken);
        d.append("twitter_accessTokenSecret", this.accessTokenSecret);


        return d;
    }
}
