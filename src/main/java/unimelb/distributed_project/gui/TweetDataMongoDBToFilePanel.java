

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
public class TweetDataMongoDBToFilePanel extends JPanel {

    final static Logger log = Logger.getLogger(TweetDataMongoDBToFilePanel.class);
    private JLabel label1;
    private JTextField tweetDataFilePathTextField;
    private JButton browseButton;
    private JButton tweetDataDBToFile;
    private String tweetDataFilePath = null;
    private JFrame mainFrame = null;

    public TweetDataMongoDBToFilePanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void tweetDataDBToFileActionPerformed(ActionEvent e) {


            if(tweetDataFilePath != null && !tweetDataFilePath.equals("")) {
                tweetDataFilePath = tweetDataFilePathTextField.getText().trim();

                Thread tweetDBToFile = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(TweetSpotterUtils.saveDBTweetToFile(tweetDataFilePath)) {
                                JOptionPane.showMessageDialog(mainFrame,
                                        "Transform Done",
                                        "Transform Done",
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

            }  else {
                JOptionPane.showMessageDialog(this.mainFrame,
                        "Please suggest where to store tweet text file",
                        "file is not selected",
                        JOptionPane.ERROR_MESSAGE);
            }


    }

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
        } else if(!tweetDataFilePathTextField.equals("")) {
            log.debug("Selected file: " + tweetDataFilePath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where to store tweet text file",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void initComponents() {

        label1 = new JLabel();
        tweetDataFilePathTextField = new JTextField();
        browseButton = new JButton();
        tweetDataDBToFile = new JButton();



        setLayout(new FormLayout(
                "8*(default, $lcgap), default",
                "7*(default, $lgap), default"));


        label1.setText("Save Data To");
        add(label1, CC.xy(3, 3));
        add(tweetDataFilePathTextField, CC.xywh(5, 3, 9, 1));


        browseButton.setText("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(e);
            }
        });
        add(browseButton, CC.xy(15, 3));


        tweetDataDBToFile.setText("Transform");
        tweetDataDBToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tweetDataDBToFileActionPerformed(e);

            }
        });
        add(tweetDataDBToFile, CC.xy(13, 5));

    }



}
