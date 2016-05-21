package unimelb.distributed_project.gui;

import javax.swing.*;

/**
 * @author Templeton Tsai
 */
public final class MainFrameGUI extends JFrame {

    private MainPanel mainPanel = null;

    public MainFrameGUI() {
        super("TweetWord2Vec");
        initComponents();
        this.pack();
        this.setVisible(true);

    }


    private void initComponents() {
        mainPanel = new MainPanel(this);
        JPanel p = new JPanel();
        p.add(new JLabel("dd"));

        this.setContentPane(mainPanel);
        //this.setContentPane(p);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


}
