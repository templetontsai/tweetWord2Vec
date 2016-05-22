/*
 * Created by JFormDesigner on Fri May 20 15:37:25 AEST 2016
 */

package unimelb.distributed_project.gui;

import javax.swing.*;

/**
 * @author Templeton Tsai
 */
public final class MainPanel extends JPanel {

    private JTabbedPane tabbedPane = null;
    private TrainWord2VecPanel trainPanel = null;
    private TweetDataMongoDBToFilePanel tweetDataMongoDBToFilePanel = null;
    private TweetRandomizedLinePanel tweetRandomizedLinePanel = null;
    private JacardSimilarityMeasurePanel jacardSimilarityMeasurePanel = null;
    private JFrame mainFrame = null;

    public MainPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }


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
