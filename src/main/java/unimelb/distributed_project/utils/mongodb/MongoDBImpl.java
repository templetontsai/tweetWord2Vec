/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */
package unimelb.distributed_project.utils.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.mongodb.client.model.Filters.*;

/**
 * This class is ported from Sharon project to perform mongodb access
 *
 * @author Templeton Tsai
 */
public class MongoDBImpl implements DBInterface {

    //public static final String TWEET_STORE = "tweetCollection";
    public static final String TWEET_STORE = "tweetBotSpotterCollection";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private PropertiesConfiguration config;
    private Log log = LogFactory.getLog(MongoDBImpl.class);

    public MongoDBImpl() {
        String hostname;
        int port;
        String databaseName;
        String databaseUser;
        String databasePassword;

        config = Properties.getConfig();
        if (config != null) {
            hostname = config.getString("database.hostname");
            port = config.getInt("database.port");
            databaseName = config.getString("database.name");
            databaseUser = config.getString("database.user");
            databasePassword = config.getString("database.password");
        } else {
            log.warn("could not read sharon.properties, using: localhost:27017, sharonDB, sharon:sharon123");
            hostname = "localhost";
            port = 27017;
            databaseName = "sharonDB";
            databaseUser = "sharon";
            databasePassword = "sharon123";
        }
        MongoCredential mc = MongoCredential.createCredential(databaseUser, "admin", databasePassword.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(hostname, port), java.util.Arrays.asList(mc));
        database = mongoClient.getDatabase(databaseName);
    }

    public MongoDBImpl(String databaseInfo) {
        String hostname;
        int port;
        String databaseName;
        String databaseUser;
        String databasePassword;
        StringTokenizer st = new StringTokenizer(databaseInfo, ":");

        ArrayList<String> info = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            info.add(st.nextToken());
        }

        hostname = info.get(0);
        port = Integer.parseInt(info.get(1));
        databaseName = info.get(2);
        databaseUser = info.get(3);
        databasePassword = info.get(4);

        MongoCredential mc = MongoCredential.createCredential(databaseUser, "admin", databasePassword.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(hostname, port), java.util.Arrays.asList(mc));
        database = mongoClient.getDatabase(databaseName);
    }


    public void insertDoc(String collectionName, Document doc) {
        database.getCollection(collectionName).insertOne(doc);
    }


    public boolean containsDoc(String collectionName, String keyName, long value) {
        if (database.getCollection(collectionName).find(eq(keyName, value)).first() != null) {
            return true;
        } else {
            return false;
        }
    }


    public boolean containsDoc(String collectionName, String keyName, String value) {
        if (database.getCollection(collectionName).find(eq(keyName, value)).first() != null) {
            return true;
        } else {
            return false;
        }
    }


    public Document getDoc(String collectionName, String keyName, long value) {
        return (Document) database.getCollection(collectionName).find(eq(keyName, value)).first();
    }


    public FindIterable<Document> getDocs(String collectionName, String keyName, long value, Filter option) {
        FindIterable<Document> docs = null;
        switch (option) {
            case EQ:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(eq(keyName, value));
                break;
            case GT:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(gt(keyName, value));
                break;
            case GTE:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(gte(keyName, value));
                break;
            case LT:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(lt(keyName, value));
                break;
            case LTE:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(lte(keyName, value));
                break;
            default:
                log.warn("Invalid option, returning null document list");
                break;

        }
        return docs;
    }


    public FindIterable<Document> getDocs(String collectionName, String keyName, String value, Filter option) {
        FindIterable<Document> docs = null;
        switch (option) {
            case EQ:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(eq(keyName, value));
                break;
            case GT:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(gt(keyName, value));
                break;
            case GTE:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(gte(keyName, value));
                break;
            case LT:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(lt(keyName, value));
                break;
            case LTE:
                docs = (FindIterable<Document>) database.getCollection(collectionName).find(lte(keyName, value));
                break;
            default:
                log.warn("Invalid option, returning null document list");
                break;

        }
        return docs;
    }


    public Document getDoc(String collectionName, String keyName, String value) {
        return (Document) database.getCollection(collectionName).find(eq(keyName, value)).first();
    }


    public void deleteDoc(String collectionName, String keyName, long value) {
        database.getCollection(collectionName).deleteOne(eq(keyName, value));
    }


    public void deleteDoc(String collectionName, String keyName, String value) {
        database.getCollection(collectionName).deleteOne(eq(keyName, value));

    }

    public FindIterable<Document> getDocs(String collectionName, Document query) {
        return (FindIterable<Document>) database.getCollection(collectionName).find(query);
    }


    public FindIterable<Document> getDocs(String collectionName) {
        return (FindIterable<Document>) database.getCollection(collectionName).find();
    }


    public void updateDoc(String collectionName, String keyName, int value, String updateKeyName, Object newValue) {
        if (containsDoc(collectionName, keyName, value)) {
            // update the field
            database.getCollection(collectionName).updateOne(new Document(keyName, value), new Document("$set", new Document(updateKeyName, newValue)));
        } else {
            log.warn("Attempt to update a document that is no in the database.");
        }

    }


    public void updateDoc(String collectionName, String keyName, String value, String updateKeyName, Object newValue) {
        if (containsDoc(collectionName, keyName, value)) {
            // update the field
            database.getCollection(collectionName).updateOne(new Document(keyName, value), new Document("$set", new Document(updateKeyName, newValue)));
        } else {
            log.warn("Attempt to update a document that is no in the database.");
        }

    }


    public void updateDocs(String collectionName, String updateKeyName, Object newValue) {
        database.getCollection(collectionName).updateMany(new Document(), new Document("$set", new Document(updateKeyName, newValue)));
    }


    public void closeConn() {
        mongoClient.close();
    }


    public void dropCollection(String collectionName) {

        try {
            database.getCollection(collectionName).drop();
        } catch (MongoException e) {
            log.info("Failed to drop collection" + collectionName);
        }

    }
}
