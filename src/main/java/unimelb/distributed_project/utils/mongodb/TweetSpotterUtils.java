package unimelb.distributed_project.utils.mongodb;

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
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.EndingPreProcessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import unimelb.distributed_project.main.TweetWord2Vec;
import unimelb.distributed_project.word2vec.Word2VecParameters;

import java.io.*;
import java.util.*;

/**
 * @author Templeton Tsai
 */
public class TweetSpotterUtils {

    final static Logger log = Logger.getLogger(TweetWord2Vec.class);



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

        // Split on white spaces in the line to get words
        TokenizerFactory t = new DefaultTokenizerFactory();
        final EndingPreProcessor preProcessor = new EndingPreProcessor();
        t.setTokenPreProcessor(new CommonPreprocessor());
        /*t.setTokenPreProcessor(new TokenPreProcess() {

            public String preProcess(String token) {
                token = token.toLowerCase();
                String base = preProcessor.preProcess(token);
                //Get Rid of any url link in the text
                return base;
            }
        });*/

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
                .tokenizerFactory(t)
                .build();

        log.info("Fitting Word2Vec model....");
        vec.fit();

        log.info("Writing word vectors to text file....");

        // Write word vectors
        WordVectorSerializer.writeWordVectors(vec, saveFile);

        log.info("done training");
        isDone = true;

        return isDone;



        //UiServer server = UiServer.getInstance();
        //log.debug("Started on port " + server.getPort());
    }

    public static boolean saveDBTweetTofile(String fileName) throws Exception {
        DBInterface dbConn = new MongoDBImpl();

        String tweetStore = "EU_Refugee";
        FindIterable<Document> iterableDocs = dbConn.getDocs(tweetStore);

        File f = new File(fileName);
        f.delete();
        f.createNewFile();
        final BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        List<String> lines = new ArrayList<String>();
        iterableDocs.forEach(new Block<Document>() {

            public void apply(final Document doc) {
                String text = doc.getString("text");
                text = text.trim();


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

    public static double jcardSim(String a, String b) {
        Jaccard j = new Jaccard();

        return j.similarity(a, b);

    }

}
