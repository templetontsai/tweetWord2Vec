/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */

package unimelb.distributed_project.gui;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import unimelb.distributed_project.word2vec.TweetWord2VecUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the panel for running Jacard Similarity Measurement. It has an python
 * visualization functionality built-in.
 *
 * @author Templeton Tsai
 */

public class JacardSimilarityMeasurePanel extends JPanel {
    final static Logger log = Logger.getLogger(JacardSimilarityMeasurePanel.class);
    private JLabel label1 = null;
    private JTextField word2vecFilePath1TextField = null;
    private JButton browseButton1 = null;
    private JLabel label2 = null;
    private JTextField word2vecFilePath2TextField = null;
    private JButton browseButton2 = null;
    private JLabel label3 = null;
    private JLabel label4 = null;
    private JTextField topNearestWordtextField = null;
    private JScrollPane scrollPane2 = null;
    private JList wordList = null;
    private JLabel label5 = null;
    private JTextField simOutputPathTextField = null;
    private JButton browseButton3 = null;
    private JButton loadListButton = null;
    private JButton measureButton = null;
    private JButton visualizedButton = null;
    private JFrame mainFrame = null;
    private String word2vecFilePath1 = null;
    private String word2vecFilePath2 = null;
    private String listOfwordsFilePath = null;
    private String jcardSimFilePath = null;
    //default value if the field is not given any value
    private int topNearestWords = 10;

    /**
     * The constructor takes the parameter of JFrame to perform various of UI updates and
     * operations.
     *
     * @param mainFrame JFrame object
     */
    public JacardSimilarityMeasurePanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    /**
     * This function is to initialize the Swing components in this panel.
     */
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
        label5 = new JLabel();
        simOutputPathTextField = new JTextField();
        browseButton3 = new JButton();
        loadListButton = new JButton();
        measureButton = new JButton();
        visualizedButton = new JButton();


        setLayout(new FormLayout(
                "6*(default, $lcgap), 63dlu, 5*($lcgap, default)",
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
        add(browseButton1, CC.xy(19, 3));

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
        add(browseButton2, CC.xy(19, 5));

        //---- label3 ----
        label3.setText("List of words");
        add(label3, CC.xywh(1, 7, 5, 1));

        //---- label4 ----
        label4.setText("Nearest words:");
        add(label4, CC.xy(11, 7));
        add(topNearestWordtextField, CC.xywh(13, 7, 5, 1));

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(wordList);
        }
        add(scrollPane2, CC.xywh(1, 9, 9, 1));

        //---- label5 ----
        label5.setText("Output file:");
        add(label5, CC.xy(11, 9));
        add(simOutputPathTextField, CC.xywh(13, 9, 5, 1));

        //---- browseButton3 ----
        browseButton3.setText("Browse");
        browseButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseButton3ActionPerformed(e);
            }
        });
        add(browseButton3, CC.xy(19, 9));

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
        add(measureButton, CC.xy(11, 11));

        //---- visualizedButton ----
        visualizedButton.setText("Visualized");
        visualizedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizedButtonActionPerformed(e);
            }
        });
        add(visualizedButton, CC.xywh(13, 11, 2, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    /**
     * Browse button action perform function for word2vecFilePath1.
     *
     * @param e ActionEvent object
     */
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
        } else if (!word2vecFilePath1TextField.getText().equals("")) {
            log.debug("Selected file: " + word2vecFilePath2);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the word2vec text file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Browse button action perform function for word2vecFilePath2.
     *
     * @param e ActionEvent object
     */
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
        } else if (!word2vecFilePath2TextField.getText().equals("")) {
            log.debug("Selected file: " + word2vecFilePath2);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the word2vec text file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Browse button action perform function for jcardSimFilePath.
     *
     * @param e ActionEvent object
     */
    private void browseButton3ActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            jcardSimFilePath = selectedFile.getPath();
            simOutputPathTextField.setText("");
            simOutputPathTextField.setText(jcardSimFilePath);

            log.debug("Selected file: " + jcardSimFilePath);
        } else if (!simOutputPathTextField.getText().equals("")) {
            log.debug("Selected file: " + jcardSimFilePath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the similarity output file path is not selected",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Measure button action perform function for triggering the thread to perform Jacard
     * Similarity measurement. It takes topNearestWordtextField's text as an input parameter to
     * execute the distance measurement in word2vec library.
     *
     * @param e ActionEvent object
     */
    private void measureButtonActionPerformed(ActionEvent e) {

        if (!word2vecFilePath1.equals("") && word2vecFilePath1 != null && !word2vecFilePath2.equals
                ("") && word2vecFilePath2 != null && wordList != null) {
            word2vecFilePath1 = word2vecFilePath1TextField.getText();
            word2vecFilePath2 = word2vecFilePath2TextField.getText();
            jcardSimFilePath = simOutputPathTextField.getText();
            if (!topNearestWordtextField.getText().equals(""))
                topNearestWords = new Integer(topNearestWordtextField.getText());
            Thread measureThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ListModel model = wordList.getModel();

                    try {
                        if (jcardSimFilePath.equals(""))
                            jcardSimFilePath = "./jcard_sim.txt";
                        BufferedWriter bw = new BufferedWriter(new FileWriter(jcardSimFilePath));
                        for (int i = 0; i < model.getSize(); i++) {
                            StringBuilder output = new StringBuilder();
                            output.append(model.getElementAt(i) + ",");
                            log.debug(model.getElementAt(i));
                            double jcardSimScore = TweetWord2VecUtils.jcardSim(TweetWord2VecUtils
                                            .runModelNearest
                                                    (word2vecFilePath1,
                                                            (String) model.getElementAt(i), topNearestWords),
                                    TweetWord2VecUtils.runModelNearest(word2vecFilePath2, (String) model.getElementAt(i), topNearestWords));
                            output.append(jcardSimScore + "\n");
                            bw.write(output.toString());
                            log.debug(i + "." + jcardSimScore);

                        }

                        bw.close();
                    } catch (IOException ioe) {
                        log.debug("create buffer writer fails");

                    }


                    log.debug("done JcardSimilarity Measure, enable all the button                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              ");
                    browseButton1.setEnabled(true);
                    browseButton2.setEnabled(true);
                    loadListButton.setEnabled(true);
                    measureButton.setEnabled(true);
                    browseButton3.setEnabled(true);
                    visualizedButton.setEnabled(true);
                    JOptionPane.showMessageDialog(mainFrame,
                            "JacardSimilarity Measurement is done",
                            "JacardSimilarity Measurement",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            measureThread.start();
            browseButton1.setEnabled(false);
            browseButton2.setEnabled(false);
            loadListButton.setEnabled(false);
            measureButton.setEnabled(false);
            browseButton3.setEnabled(false);
            visualizedButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest files and load wordOfList",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Load button's actionPerformed function to load the list of words to find nearest words
     * against all the words in the list loaded.
     *
     * @param e ActionEvent object
     */
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

                }
            } catch (IOException e1) {
                log.debug("creating/loading file exception");
                e1.printStackTrace();
            }


            log.debug("Selected file: " + listOfwordsFilePath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the listOfWords.txt text file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }


    }

    /**
     * This function performs the action of visualization of button. It uses external program,
     * python, to visualize plot for the result of Jacard similarity measurement between two
     * training sets.
     *
     * @param e ActionEvent object
     */
    private void visualizedButtonActionPerformed(ActionEvent e) {
        jcardSimFilePath = simOutputPathTextField.getText();
        if (jcardSimFilePath != null && !jcardSimFilePath.equals("")) {

            File f = new File(jcardSimFilePath);
            //Visualize it with Python
            try {
                if (f.exists()) {
                    String ployPythonFile = f.getParent();

                    ProcessBuilder processBuilder = new ProcessBuilder(
                            "/usr/bin/python", ployPythonFile + "/plot.py", jcardSimFilePath);
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    //TODO add error handling when the plot.py is not in place
                    IOUtils.copy(process.getInputStream(), System.out);


                } else {
                    log.debug("similarity file doesn't exist");
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


}
