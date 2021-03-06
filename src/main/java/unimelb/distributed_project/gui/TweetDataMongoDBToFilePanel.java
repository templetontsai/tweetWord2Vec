/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */


package unimelb.distributed_project.gui;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.log4j.Logger;
import unimelb.distributed_project.word2vec.TweetWord2VecUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This class is the panel for the panel to transform data from a collection in MongoDB to a text
 * file for statically training of word2vec
 *
 * @author Templeton Tsai
 */
public class TweetDataMongoDBToFilePanel extends JPanel {

    final static Logger log = Logger.getLogger(TweetDataMongoDBToFilePanel.class);
    private JLabel label1 = null;
    private JTextField tweetDataFilePathTextField = null;
    private JButton browseButton = null;
    private JLabel label2 = null;
    private JTextField DBCollectiontextField = null;
    private String DBCollection = null;
    private JButton tweetDataDBToFile = null;
    private String tweetDataFilePath = null;
    private JFrame mainFrame = null;

    /**
     * The constructor takes the parameter of JFrame to perform various of UI updates and
     * operations.
     *
     * @param mainFrame JFrame object
     */
    public TweetDataMongoDBToFilePanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    /**
     * This function is to initialize the Swing components in this panel.
     */
    private void initComponents() {

        label1 = new JLabel();
        tweetDataFilePathTextField = new JTextField();
        browseButton = new JButton();
        label2 = new JLabel();
        DBCollectiontextField = new JTextField();
        tweetDataDBToFile = new JButton();
        setLayout(new FormLayout(
            "46dlu, $lcgap, 121dlu, 5dlu, default",
            "7*(default, $lgap), default"));

        //---- label1 ----
        label1.setText("Save Data To");
        add(label1, CC.xy(1, 3));
        add(tweetDataFilePathTextField, CC.xy(3, 3));

        //---- browseButton ----
        browseButton.setText("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(e);
            }
        });
        add(browseButton, CC.xy(5, 3));

        //---- label2 ----
        label2.setText("DB Collection");
        add(label2, CC.xy(1, 5));
        add(DBCollectiontextField, CC.xy(3, 5));

        //---- tweetDataDBToFile ----
        tweetDataDBToFile.setText("Transform");
        tweetDataDBToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tweetDataDBToFileActionPerformed(e);
            }
        });
        add(tweetDataDBToFile, CC.xy(5, 5));


    }

    /**
     * Transform button action perform function for reading data from MongoDB to text file
     *
     * @param e ActionEvent object
     */
    private void tweetDataDBToFileActionPerformed(ActionEvent e) {

        DBCollection = DBCollectiontextField.getText();
        if (tweetDataFilePath != null && !tweetDataFilePath.equals("") && !DBCollection.equals
                ("")) {
            tweetDataFilePath = tweetDataFilePathTextField.getText().trim();

            Thread tweetDBToFile = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (TweetWord2VecUtils.saveDBTweetToFile(tweetDataFilePath, DBCollection)) {
                            JOptionPane.showMessageDialog(mainFrame,
                                    "Transformation is one",
                                    "Transformation Done",
                                    JOptionPane.INFORMATION_MESSAGE);
                            tweetDataDBToFile.setEnabled(true);
                            browseButton.setEnabled(true);
                        }

                    } catch (Exception e1) {
                        log.debug("tweetDataDBToFileActionPerformed save tweet exception");
                        e1.printStackTrace();
                    }
                }
            });

            tweetDBToFile.start();
            tweetDataDBToFile.setEnabled(false);
            browseButton.setEnabled(false);

        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where to store tweet text file",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);
        }


    }

    /**
     * Browse button action perform function for tweetDataFilePath.
     *
     * @param e ActionEvent object
     */
    private void browseButtonActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            tweetDataFilePath = selectedFile.getPath();
            tweetDataFilePathTextField.setText("");
            tweetDataFilePathTextField.setText(tweetDataFilePath);

            log.debug("Selected file: " + tweetDataFilePath);
        } else if (!tweetDataFilePathTextField.equals("")) {
            log.debug("Selected file: " + tweetDataFilePath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where to store tweet text file",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

}
