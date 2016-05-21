package unimelb.distributed_project.main;

import org.apache.log4j.Logger;
import unimelb.distributed_project.gui.MainFrameGUI;
import unimelb.distributed_project.gui.TweetDataMongoDBToFilePanel;
import unimelb.distributed_project.utils.mongodb.TweetSpotterUtils;


/**
 * @author Templeton Tsai
 */
public class TweetWord2Vec {
    final static Logger log = Logger.getLogger(TweetWord2Vec.class);
    public static void main(String[] args) throws Exception {

        if(args[0].equals("gui")) {
            MainFrameGUI mainFrameGUI = new MainFrameGUI();
        } else if(args[0].equals("command")) {

            TweetSpotterUtils t = new TweetSpotterUtils();


            //t.saveTweetTofile("/home/templeton/Codebase_DCP/WordVerctorTweetSpotter/src/main/resources/tweet_text.txt");
            //t.reshuffleLines("/home/templeton/Codebase_DCP/WordVerctorTweetSpotter/src/main/resources/tweet_text_small.txt_reshuffle");


            //t.trainWordToVector("/home/templeton/Codebase_DCP/WordVerctorTweetSpotter/src/main/resources/tweet_text.txt.normal",
            //      "/home/templeton/Codebase_DCP/WordVerctorTweetSpotter/wordVectors.txt.normal.iter100.w5");
            //t.trainWordToVector("/home/templeton/Codebase_DCP/WordVerctorTweetSpotter/src/main/resources/tweet_text.txt.reshuffleLines",
            //     "/home/templeton/Codebase_DCP/WordVerctorTweetSpotter/wordVectors.txt.reshuffle_lines.iter100.w5");
            //t.runModelNearest("wordVectors.txt.normal","climate", 20);
            //t.runModelNearest("wordVectors.txt.reshuffle_lines","climate", 20);
            //t.runModelNearest("wordVectors.txt.1000.reshuffle_lines","climate", 20);
             System.out.println(t.jcardSim(t.runModelNearest
                     ("/home/templeton/Codebase_DCP/WordVerctorTweetSpotter/analysis" +
                     "/word2vec_eurefugee.txt",
                  "refugees", 10),
                t.runModelNearest("/home/templeton/Codebase_DCP/WordVerctorTweetSpotter/analysis" +
                         "/word2vec_eurefugee_shuffle.txt", "refugees", 10)));
        }


    }


}
