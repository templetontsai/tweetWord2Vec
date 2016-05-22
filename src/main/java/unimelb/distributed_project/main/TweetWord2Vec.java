/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */
package unimelb.distributed_project.main;

import org.apache.log4j.Logger;
import unimelb.distributed_project.gui.MainFrameGUI;


/**
 * This class is the panel for running Jacard Similarity Measurement. It has an python
 * visualization functionality built-in.
 *
 * @author Templeton Tsai
 */
public class TweetWord2Vec {
    final static Logger log = Logger.getLogger(TweetWord2Vec.class);

    public static void main(String[] args) throws Exception {
        log.debug("Init TweetWord2Vec");
        MainFrameGUI mainFrameGUI = new MainFrameGUI();
    }


}
