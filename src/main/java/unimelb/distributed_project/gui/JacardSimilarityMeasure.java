
package unimelb.distributed_project.gui;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.log4j.Logger;
import unimelb.distributed_project.utils.mongodb.TweetSpotterUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Templeton Tsai
 */
public class JacardSimilarityMeasure extends JPanel {
    final static Logger log = Logger.getLogger(JacardSimilarityMeasure.class);
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Templeton Tsai
    private JLabel label1;
    private JTextField word2vecFilePath1TextField;
    private JButton browseButton1;
    private JLabel label2;
    private JTextField word2vecFilePath2TextField;
    private JButton browseButton2;
    private JLabel label3;
    private JLabel label4;
    private JTextField topNearestWordtextField;
    private JScrollPane scrollPane2;
    private JList wordList;
    private JButton loadListButton;
    private JButton measureButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private JFrame mainFrame;
    private String word2vecFilePath1;
    private String word2vecFilePath2;
    private String listOfwordsFilePath;
    private int topNearestWords = 10;

    public JacardSimilarityMeasure(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void browseButton1ActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            word2vecFilePath1 = selectedFile.getPath();
            word2vecFilePath1TextField.setText("");
            word2vecFilePath1TextField.setText(word2vecFilePath1);

            log.debug("Selected file: " + word2vecFilePath1);
        } else if(!word2vecFilePath1TextField.getText().equals("")) {
            log.debug("Selected file: " + word2vecFilePath2);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the word2vec text file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void browseButton2ActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            word2vecFilePath2 = selectedFile.getPath();
            word2vecFilePath2TextField.setText("");
            word2vecFilePath2TextField.setText(word2vecFilePath2);

            log.debug("Selected file: " + word2vecFilePath2);
        } else if(!word2vecFilePath2TextField.getText().equals("")) {
            log.debug("Selected file: " + word2vecFilePath2);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the word2vec text file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void measureButtonActionPerformed(ActionEvent e) {

        if (!word2vecFilePath1.equals("") && word2vecFilePath1 != null && !word2vecFilePath2.equals
                ("") && word2vecFilePath2 != null && wordList != null) {
            word2vecFilePath1 = word2vecFilePath1TextField.getText();
            word2vecFilePath2 = word2vecFilePath2TextField.getText();
            if(!topNearestWordtextField.getText().equals(""))
                topNearestWords = new Integer(topNearestWordtextField.getText());
            Thread measureThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ListModel model = wordList.getModel();

                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter("./jcard_sim.txt"));
                        for (int i = 0; i < model.getSize(); i++) {
                            StringBuilder output = new StringBuilder();
                            output.append(model.getElementAt(i) + ",");
                            log.debug(model.getElementAt(i));
                            double jcardSimScore = TweetSpotterUtils.jcardSim(TweetSpotterUtils
                                            .runModelNearest
                                                    (word2vecFilePath1,
                                                            (String) model.getElementAt(i), topNearestWords),
                                    TweetSpotterUtils.runModelNearest(word2vecFilePath2, (String) model.getElementAt(i), topNearestWords));
                            output.append(jcardSimScore + "\n");
                            bw.write(output.toString());
                            log.debug(i + "." + jcardSimScore);

                        }

                        bw.close();
                    } catch(IOException ioe) {
                        log.debug("create buffer writer fails");

                    }


                    log.debug("done JcardSimilarity Measure, enable all the button                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              ");
                    browseButton1.setEnabled(true);
                    browseButton2.setEnabled(true);
                    loadListButton.setEnabled(true);
                    measureButton.setEnabled(true);
                }
            });
            measureThread.start();
            browseButton1.setEnabled(false);
            browseButton2.setEnabled(false);
            loadListButton.setEnabled(false);
            measureButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest files and load wordOfList",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void loadListButtonActionPerformed(ActionEvent e) {

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            listOfwordsFilePath = selectedFile.getPath();

            File f = new File(listOfwordsFilePath);

            try {
                if (f.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader(listOfwordsFilePath));
                    String line;
                    List<String> lines = new ArrayList<String>();
                    DefaultListModel model = new DefaultListModel();
                    while ((line = br.readLine()) != null) {
                        // process the line.
                        lines.add(line);
                        model.addElement(line);
                        log.debug(line);

                    }

                    wordList.setModel(model);
                    wordList.updateUI();

                } else {
                    log.debug("Creating file, wordOfList doesn't exist");
                    //f.createNewFile();

                }
            } catch (IOException e1) {
                log.debug("creating/loading file exception");
                e1.printStackTrace();
            }


            log.debug("Selected file: " + listOfwordsFilePath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the listOfwords.txt text file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }


    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Templeton Tsai
        label1 = new JLabel();
        word2vecFilePath1TextField = new JTextField();
        browseButton1 = new JButton();
        label2 = new JLabel();
        word2vecFilePath2TextField = new JTextField();
        browseButton2 = new JButton();
        label3 = new JLabel();
        label4 = new JLabel();
        topNearestWordtextField = new JTextField();
        scrollPane2 = new JScrollPane();
        wordList = new JList();
        loadListButton = new JButton();
        measureButton = new JButton();

        //======== this ========


        setLayout(new FormLayout(
            "11*(default, $lcgap), default",
            "6*(default, $lgap), default"));

        //---- label1 ----
        label1.setText("word2vec File 1:");
        add(label1, CC.xy(1, 3));
        add(word2vecFilePath1TextField, CC.xywh(3, 3, 15, 1));

        //---- browseButton1 ----
        browseButton1.setText("Browse");
        browseButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseButton1ActionPerformed(e);

            }
        });
        add(browseButton1, CC.xy(23, 3));

        //---- label2 ----
        label2.setText("word2vec File 2:");
        add(label2, CC.xy(1, 5));
        add(word2vecFilePath2TextField, CC.xywh(3, 5, 15, 1));

        //---- browseButton2 ----
        browseButton2.setText("Browse");
        browseButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseButton2ActionPerformed(e);
            }
        });
        add(browseButton2, CC.xy(23, 5));

        //---- label3 ----
        label3.setText("List of words");
        add(label3, CC.xywh(1, 7, 5, 1));

        //---- label4 ----
        label4.setText("Top words");
        add(label4, CC.xy(11, 7));
        add(topNearestWordtextField, CC.xywh(13, 7, 5, 1));

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(wordList);
        }
        add(scrollPane2, CC.xywh(1, 9, 9, 1));

        //---- loadListButton ----
        loadListButton.setText("Load ");
        loadListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadListButtonActionPerformed(e);
            }
        });
        add(loadListButton, CC.xywh(1, 11, 9, 1));

        //---- measureButton ----
        measureButton.setText("Measure");
        measureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                measureButtonActionPerformed(e);

            }
        });
        add(measureButton, CC.xy(17, 11));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}
