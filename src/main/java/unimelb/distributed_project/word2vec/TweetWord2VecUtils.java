/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */
package unimelb.distributed_project.word2vec;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import info.debatty.java.stringsimilarity.Jaccard;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import unimelb.distributed_project.main.TweetWord2Vec;
import unimelb.distributed_project.utils.mongodb.DBInterface;
import unimelb.distributed_project.utils.mongodb.MongoDBImpl;


import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class the untility function for all the features perform in this application and
 * incorporated with word2vec library
 *
 * @author Templeton Tsai
 */
public class TweetWord2VecUtils {

    final static Logger log = Logger.getLogger(TweetWord2Vec.class);


    /**
     * The main funtion to run word2vec in word2vec library
     *
     * @param fileName           the origin file for training
     * @param saveFile           where to save after the words are vectorized
     * @param word2VecParameters Word2VecParameters object to specify the training parameters for
     *                           word2vec library
     * @return boolean to indicate is running done
     * @throws Exception the exceptions while running word2vec
     */
    public static boolean trainWordToVector(String fileName, String saveFile, Word2VecParameters
            word2VecParameters) throws
            Exception {

        boolean isDone;
        log.info("Load & Vectorize Sentences....");
        //regex out url
        SentenceIterator iter = new LineSentenceIterator(new File(fileName));


        iter.setPreProcessor(new SentencePreProcessor() {

            public String preProcess(String sentence) {
                sentence = sentence.trim();
                String pattern = "(@[A-Za-z0-9]+)|([^0-9A-Za-z \\t])|(\\w+:\\/\\/\\S+)";

                sentence = sentence.replaceAll(pattern, " ");


                return sentence.toLowerCase();
            }


        });


        TokenizerFactory t = new DefaultTokenizerFactory();


        log.info("Building model....");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(word2VecParameters.getMinWordFeq())
                .iterations(word2VecParameters.getNumIteration())
                .layerSize(word2VecParameters.getLayerSize())
                .learningRate(word2VecParameters.getLearningRate())
                .minLearningRate(1e-3) // learning rate decays wrt # words. floor learning
                .seed(word2VecParameters.getSeed())
                .windowSize(word2VecParameters.getWindowSize())
                .iterate(iter)
                //   .tokenizerFactory(t)
                .build();

        log.info("Fitting Word2Vec model....");
        vec.fit();

        log.info("Writing word vectors to text file....");

        // Write word vectors
        WordVectorSerializer.writeWordVectors(vec, saveFile);

        log.info("done training");
        isDone = true;

        return isDone;

    }

    /**
     * This function performs reading from MongoDB collection to a text file
     *
     * @param fileName the destination of where to save the transformed file
     * @return boolean to indicate is running done
     * @throws Exception the exceptions while transforming db data to text file
     */
    public static boolean saveDBTweetToFile(String fileName) throws Exception {
        DBInterface dbConn = new MongoDBImpl();

        String tweetStore = "EU_Refugee";
        FindIterable<Document> iterableDocs = dbConn.getDocs(tweetStore);

        File f = new File(fileName);
        f.delete();
        f.createNewFile();
        final BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

        iterableDocs.forEach(new Block<Document>() {

            public void apply(final Document doc) {
                String text = doc.getString("text");
                text = text.trim();
                text = text.replaceAll("(\\r|\\n)", " ");


                try {
                    bw.write(text);
                    bw.newLine();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                log.debug(text);

            }


        });

        dbConn.closeConn();
        bw.close();


        return true;

    }

    /**
     * This function performs the reshuffling lines in the given text file
     *
     * @param srcFileName source file
     * @param dstFileName reshuffled file
     * @return boolean to indicate is running done
     * @throws Exception the exceptions while transforming db data to text file
     */
    public static boolean reshuffleLines(String srcFileName, String dstFileName) throws Exception {

        boolean isDone;

        BufferedReader br = new BufferedReader(new FileReader(srcFileName));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            // process the line.
            lines.add(line);
        }
        Collections.shuffle(lines);
        BufferedWriter bw = new BufferedWriter(new FileWriter(dstFileName));


        int count = 0;
        for (String l : lines) {

            bw.append(l + "\n");

            count++;
        }
        log.debug("Count = " + count);
        bw.close();
        isDone = true;

        return isDone;


    }

    /**
     * This function finds out the n-nearest distance with the given term.
     *
     * @param fileName   word2vec file
     * @param term       word term
     * @param nearestNum indicates n-nearest term
     * @return boolean to indicate is running done
     */
    public static String runModelNearest(String fileName, String term, int nearestNum) {
        StringBuilder sb = new StringBuilder();
        try {
            WordVectors vec = WordVectorSerializer.loadTxtVectors(new File(fileName));
            Collection<String> lst = vec.wordsNearest(term, nearestNum);
            log.info("Closest Words:" + lst);

            List<String> list = new ArrayList<String>();
            for (String s : lst) {
                list.add(s);
            }
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);

            for (String s : list) {
                sb.append(s + " ");
            }
            System.out.println(sb);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * This is the function to perform Jacard Similarity. It is called to a third party library.
     *
     * @param a the string to compare
     * @param b the sring to compare
     * @return the similarity score in double
     * @see <a href="https://github.com/tdebatty/java-string-similarity</a>
     */
    public static double jcardSim(String a, String b) {
        Jaccard j = new Jaccard();

        return j.similarity(a, b);

    }

}
