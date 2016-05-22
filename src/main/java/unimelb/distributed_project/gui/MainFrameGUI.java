/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */
package unimelb.distributed_project.gui;

import javax.swing.*;

/**
 * This is the class for main fram in UI
 *
 * @author Templeton Tsai
 */
public final class MainFrameGUI extends JFrame {

    private MainPanel mainPanel = null;

    /**
     * The constructor takes no parameter and call super function to initialize the name of the
     * frame
     */
    public MainFrameGUI() {
        super("TweetWord2Vec");
        initComponents();
        this.pack();
        this.setVisible(true);

    }


    /**
     * This function is to initialize the Swing components in this panel.
     */
    private void initComponents() {
        mainPanel = new MainPanel(this);
        JPanel p = new JPanel();
        p.add(new JLabel("dd"));

        this.setContentPane(mainPanel);
        this.setSize(600, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


}
