/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */
package unimelb.distributed_project.utils.mongodb;

import com.mongodb.client.FindIterable;
import org.bson.Document;

/**
 * This class is ported from Sharon project to perform mongodb access
 *
 * @author Templeton Tsai
 */
public interface DBInterface {

    public static final String TWEET_STORE = "tweetCollection";
    public static final String TWEET_BOT_SPOTTER_STORE = "tweetBotSpotterCollection";
    public static final String DISCUSSION_STORE = "discussionCollection";
    public static final String TOPIC_KEYWORD_STORE = "topicKeyCollection";
    public static final String COMMUNITY_STORE = "communityCollection";
    public static final String WINDOWED_COMMUNITY_STORE = "windowedCommunityCollection";
    public static final String USER_STORE = "userCollection";
    public static final String USER_ACTIVITY_LOG = "userActivityLog";
    public static final String PRODUCT_KEY_STORE = "productKeyCollection";

    // generic query
    public FindIterable<Document> getDocs(String collectionName, Document query);

    ;

    public void insertDoc(String collectionName, Document doc);

    public boolean containsDoc(String collectionName, String keyName, long value);

    public boolean containsDoc(String collectionName, String keyName, String value);

    public Document getDoc(String collectionName, String keyName, long value);

    public Document getDoc(String collectionName, String keyName, String value);

    public void deleteDoc(String collectionName, String keyName, long value);

    public void deleteDoc(String collectionName, String keyName, String value);

    public FindIterable<Document> getDocs(String collectionName, String keyName, long value, Filter option);

    public FindIterable<Document> getDocs(String collectionName, String keyName, String value, Filter option);

    public FindIterable<Document> getDocs(String collectionName);

    public void updateDoc(String collectionName, String keyName, int value, String updateKeyName, Object newValue);

    public void updateDoc(String collectionName, String keyName, String value, String updateKeyName, Object newValue);

    public void updateDocs(String collectionName, String updateKeyName, Object newValue);

    public void dropCollection(String collectionName);

    public void closeConn();

    public static enum Filter {EQ, GT, LT, GTE, LTE}

}
