/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */


package unimelb.distributed_project.gui;

import javax.swing.*;

/**
 * This is the main panel to accommodate the tabPaned panel for all the features in this
 * application
 *
 * @author Templeton Tsai
 */
public final class MainPanel extends JPanel {

    private JTabbedPane tabbedPane = null;
    private TrainWord2VecPanel trainPanel = null;
    private TweetDataMongoDBToFilePanel tweetDataMongoDBToFilePanel = null;
    private TweetRandomizedLinePanel tweetRandomizedLinePanel = null;
    private JacardSimilarityMeasurePanel jacardSimilarityMeasurePanel = null;
    private JFrame mainFrame = null;

    /**
     * The constructor takes the parameter of JFrame to pass it for initialization of other panels.
     *
     * @param mainFrame JFrame object
     */
    public MainPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }


    /**
     * This function is to initialize the Swing components in this panel.
     */
    private void initComponents() {

        trainPanel = new TrainWord2VecPanel(this.mainFrame);
        tweetDataMongoDBToFilePanel = new TweetDataMongoDBToFilePanel(this.mainFrame);
        tweetRandomizedLinePanel = new TweetRandomizedLinePanel(this.mainFrame);
        jacardSimilarityMeasurePanel = new JacardSimilarityMeasurePanel(this.mainFrame);
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("TrainWord2Vec", trainPanel);
        tabbedPane.addTab("TweetDBToFile", tweetDataMongoDBToFilePanel);
        tabbedPane.addTab("TweetReshuffle", tweetRandomizedLinePanel);
        tabbedPane.addTab("Similarity", jacardSimilarityMeasurePanel);
        this.add(tabbedPane);


    }


}
