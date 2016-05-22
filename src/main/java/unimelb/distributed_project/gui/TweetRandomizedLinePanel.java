

package unimelb.distributed_project.gui;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.log4j.Logger;
import unimelb.distributed_project.utils.mongodb.TweetSpotterUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Templeton Tsai
 */
public class TweetRandomizedLinePanel extends JPanel {

    private JLabel label1;
    private JTextField srcTweetFileField;
    private JButton browseSrc;
    private JLabel label2;
    private JTextField dstTweetFileField;
    private JButton browseDst;
    private JButton reshuffleLine;

    final static Logger log = Logger.getLogger(TweetRandomizedLinePanel.class);
    private JFrame mainFrame = null;
    private String tweetDataSrcFilePath = null;
    private String tweetDataDstFilePath = null;

    public TweetRandomizedLinePanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void browseSrcActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            tweetDataSrcFilePath = selectedFile.getPath();
            srcTweetFileField.setText("");
            srcTweetFileField.setText(tweetDataSrcFilePath);

            log.debug("Selected file: " + tweetDataSrcFilePath);
        } else if (!srcTweetFileField.equals("")) {
            log.debug("Selected file: " + tweetDataSrcFilePath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the original tweet text file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void browseDstActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            tweetDataDstFilePath = selectedFile.getPath();
            dstTweetFileField.setText("");
            dstTweetFileField.setText(tweetDataDstFilePath);

            log.debug("Selected file: " + tweetDataDstFilePath);
        } else if (!dstTweetFileField.equals("")) {
            log.debug("Selected file: " + tweetDataDstFilePath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where to store the reshuffled tweet text file",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void reshuffleLineActionPerformed(ActionEvent e) {
        if (tweetDataSrcFilePath != null && !tweetDataSrcFilePath.equals("") && tweetDataDstFilePath != null && !tweetDataDstFilePath.equals("")) {
            tweetDataSrcFilePath = srcTweetFileField.getText().trim();
            tweetDataDstFilePath = dstTweetFileField.getText().trim();
            Thread tweetFileReshuffle = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (TweetSpotterUtils.reshuffleLines(tweetDataSrcFilePath,
                                tweetDataDstFilePath)) {
                            JOptionPane.showMessageDialog(mainFrame,
                                    "Reshuffle is done",
                                    "Reshuffle Done",
                                    JOptionPane.INFORMATION_MESSAGE);
                            reshuffleLine.setEnabled(true);
                            browseSrc.setEnabled(true);
                            browseDst.setEnabled(true);
                        }

                    } catch (Exception e1) {
                        log.debug("reshuffleLineActionPerformed reshuffle tweet exception");
                        e1.printStackTrace();
                    }
                }
            });
            tweetFileReshuffle.start();
            reshuffleLine.setEnabled(false);
            browseSrc.setEnabled(false);
            browseDst.setEnabled(false);

        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where to store the reshuffled tweet text file or where the " +
                            "source file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);
        }


    }


    private void initComponents() {

        label1 = new JLabel();
        srcTweetFileField = new JTextField();
        browseSrc = new JButton();
        label2 = new JLabel();
        dstTweetFileField = new JTextField();
        browseDst = new JButton();
        reshuffleLine = new JButton();


        setLayout(new FormLayout(
                "3*(default, $lcgap), 76dlu, $lcgap, 71dlu, 5*($lcgap, default)",
                "4*(default, $lgap), default"));

        //---- label1 ----
        label1.setText("Src Tweet File");
        add(label1, CC.xy(5, 3));
        add(srcTweetFileField, CC.xy(7, 3));

        //---- browseSrc ----
        browseSrc.setText("Browse");
        browseSrc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseSrcActionPerformed(e);
            }
        });
        add(browseSrc, CC.xy(9, 3));

        //---- label2 ----
        label2.setText("Dst Tweet File");
        add(label2, CC.xy(5, 5));
        add(dstTweetFileField, CC.xy(7, 5));

        //---- browseDst ----
        browseDst.setText("Browse");
        browseDst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                browseDstActionPerformed(e);
            }
        });
        add(browseDst, CC.xy(9, 5));

        //---- reshuffleLine ----
        reshuffleLine.setText("Reshuffle");
        reshuffleLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reshuffleLineActionPerformed(e);
            }
        });
        add(reshuffleLine, CC.xy(9, 9));


    }



}
