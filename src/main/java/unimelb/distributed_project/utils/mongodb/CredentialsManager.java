/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */
package unimelb.distributed_project.utils.mongodb;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is ported from Sharon project to perform mongodb access
 *
 * @author Templeton Tsai
 */
public class CredentialsManager {

    public static final String TWITTER_CREDENTIALS_DIR = "TwitterCredentials";
    private static Log log = LogFactory.getLog(CredentialsManager.class);

    @Deprecated
    public static PropertiesConfiguration getConfiguration() throws ConfigurationException {
        return new PropertiesConfiguration("sharon.properties");
    }

    public static int getNumTwitterCredentials() {
        int numCredentials = 0;

        if (!Properties.isUsingDefaults()) {
            numCredentials = new File(Properties.getFileBase() + "/" + TWITTER_CREDENTIALS_DIR).listFiles().length;
        } else {
            // there's no accessible configuration file
        }
        log.debug("Number of credential Files: " + numCredentials);

        if (numCredentials == 0) {
            numCredentials = 1;
        }

        return numCredentials;
    }

    /**
     * This method returns the credentials from  the appropriate file based on the specified index.
     * File names are sorted alphabetically.
     */
    public static TwitterAuth getTwitterCredentials(int indx) {
        String fileName;
        if (!Properties.isUsingDefaults()) {
            File dir = new File(Properties.getFileBase() + "/" + TWITTER_CREDENTIALS_DIR);
            File[] filesList = dir.listFiles();
            ArrayList<String> fileNames = new ArrayList<String>();
            if (filesList != null) {
                for (File file : filesList) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }

            // spout and bolt indices start at 0
            if (indx >= fileNames.size()) {
                log.warn("Spout index is larger than the number of credentials. Using defaults.");
                fileName = null;
            } else {
                Collections.sort(fileNames);
                fileName = fileNames.get(indx);
            }
        } else {
            log.warn("There are no credentials available to the system.");
            fileName = null;
        }

        return getTwitterCredentials(fileName);
    }

    /* returns file names */
    public static ArrayList<String> getCredentialList() {
        ArrayList<String> fileNames = new ArrayList<String>();
        if (!Properties.isUsingDefaults()) {
            File dir = new File(Properties.getFileBase() + "/" + TWITTER_CREDENTIALS_DIR);
            File[] filesList = dir.listFiles();
            if (filesList != null) {
                for (File file : filesList) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
                Collections.sort(fileNames);
            }
        } else {
            log.warn("There are no credentials available to the system.");
        }
        return fileNames;
    }

    /* either return a credential from a filename or returns a default credential */
    public static TwitterAuth getTwitterCredentials(String fname) {

        String filename = Properties.getFileBase() + "/" + TWITTER_CREDENTIALS_DIR + "/" + fname;

        PropertiesConfiguration config = null;
        TwitterAuth twitterAuth;

        try {
            config = new PropertiesConfiguration(filename);
            log.info("Obtained credentails from file: " + filename);
            twitterAuth = new TwitterAuth(config.getString("consumerKey"), config.getString("consumerSecret"),
                    config.getString("accessToken"), config.getString("accessTokenSecret"));
        } catch (ConfigurationException e) {
            log.error("Failed to obtained credentails from file: " + filename + ", using defaults.");
            twitterAuth = new TwitterAuth("hOqRkP13vsD3yeMhdy7sQ2lfD", "wQ8penRQQIgtYoa25yfpFyhiKhVIV3CO9H3p3KeNRF85cgFatM",
                    "169057458-r0B8jswxWTLf4PKWaZzxuF6fZPE6nSytN0lDbeKg", "ItT7Qyk2SYysg4ZnVaqud8GON6Sj6CsnBlki0TVI7EYY5");
        }

        return twitterAuth;

    }

    /* write credentials to a file */
    /* returns true on success */
    public static boolean putTwitterCredentials(String filename, TwitterAuth cred) {
        if (!Properties.isUsingDefaults()) {
            PropertiesConfiguration config = new PropertiesConfiguration();
            config.addProperty("consumerKey", cred.getConsumerKey());
            config.addProperty("consumerSecret", cred.getConsumerSecret());
            config.addProperty("accessToken", cred.getAccessToken());
            config.addProperty("accessTokenSecret", cred.getAccessTokenSecret());
            try {
                config.save(Properties.getFileBase() + "/" + TWITTER_CREDENTIALS_DIR + "/" + filename);
                return true;
            } catch (ConfigurationException e) {
                log.error("could not save credential: " + filename);
                return false;
            }
        } else {
            log.error("There is no known location to save the supplied credentials file: " + filename);
            return false;
        }
    }

    /* delete a credential file */
    public static boolean deleteTwitterCredential(String filename) {
        if (!Properties.isUsingDefaults()) {
            String pathname = Properties.getFileBase() + "/" + TWITTER_CREDENTIALS_DIR + "/" + filename;
            try {
                Files.deleteIfExists(new File(pathname).toPath());
                return true;
            } catch (NoSuchFileException x) {
                log.warn("credential did not exist: " + pathname);
            } catch (IOException x) {
                log.warn("exception deleting credential: " + pathname);
            }
        } else {
            log.warn("Location of the credentials file is unknown: " + filename);
        }
        return false;
    }
}
