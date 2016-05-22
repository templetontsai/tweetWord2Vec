/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */


package unimelb.distributed_project.gui;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.log4j.Logger;
import unimelb.distributed_project.word2vec.TweetWord2VecUtils;
import unimelb.distributed_project.word2vec.Word2VecParameters;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This class is the panel for training word2vec files
 *
 * @author Templeton Tsai
 */
public final class TrainWord2VecPanel extends JPanel {


    final static Logger log = Logger.getLogger(TrainWord2VecPanel.class);
    private Word2VecParameters word2VecParameters = null;
    private JLabel label1 = null;
    private JTextField windowSizeTextField = null;
    private JLabel label2 = null;
    private JTextField minFeqTextField = null;
    private JLabel label3 = null;
    private JTextField iterationTextField = null;
    private JLabel label4 = null;
    private JTextField layerSizeTextField = null;
    private JLabel label5 = null;
    private JTextField seedTextField = null;
    private JLabel label6 = null;
    private JTextField learningRateTextField = null;
    private JLabel trainingFilePath = null;
    private JTextField trainingPathTextField = null;
    private JLabel word2vecPath = null;
    private JTextField word2vecPathTextField = null;
    private JButton button2 = null;
    private JButton button1 = null;
    private JButton saveToButton = null;
    private JFrame mainFrame = null;
    private String trainFilePath = null;
    //default value of word2vec.txt if it is not given by word2vecPathTextField
    private String saveWord2VecPath = "./word2vec.txt";

    /**
     * The constructor takes the parameter of JFrame to perform various of UI updates and
     * operations.
     *
     * @param mainFrame JFrame object
     */
    public TrainWord2VecPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    /**
     * This function is to initialize the Swing components in this panel.
     */
    private void initComponents() {

        label1 = new JLabel();
        windowSizeTextField = new JTextField();
        label2 = new JLabel();
        minFeqTextField = new JTextField();
        label3 = new JLabel();
        iterationTextField = new JTextField();
        label4 = new JLabel();
        layerSizeTextField = new JTextField();
        label5 = new JLabel();
        seedTextField = new JTextField();
        label6 = new JLabel();
        learningRateTextField = new JTextField();
        trainingFilePath = new JLabel();
        trainingPathTextField = new JTextField();
        word2vecPath = new JLabel();
        word2vecPathTextField = new JTextField();
        button2 = new JButton();
        button1 = new JButton();
        saveToButton = new JButton();


        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });

        setLayout(new FormLayout(
                "6*(default, $lcgap), default",
                "9*(default, $lgap), default"));


        label1.setText("WindowSize");
        add(label1, CC.xy(5, 3));
        add(windowSizeTextField, CC.xy(7, 3));


        label2.setText("MinFrequency");
        add(label2, CC.xy(5, 5));
        add(minFeqTextField, CC.xy(7, 5));


        label3.setText("Iteration");
        add(label3, CC.xy(5, 7));
        add(iterationTextField, CC.xy(7, 7));

        label4.setText("LayerSize");
        add(label4, CC.xy(5, 9));
        add(layerSizeTextField, CC.xy(7, 9));


        label5.setText("Seed");
        add(label5, CC.xy(5, 11));
        add(seedTextField, CC.xy(7, 11));

        label6.setText("Learning Rate");
        add(label6, CC.xy(5, 13));
        add(learningRateTextField, CC.xy(7, 13));


        trainingFilePath.setText("TraningFilePath");
        trainingFilePath.setLabelFor(trainingPathTextField);
        add(trainingFilePath, CC.xy(5, 15));
        add(trainingPathTextField, CC.xywh(7, 15, 5, 1));


        button2.setText("Browse");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseActionPerformed(e);

            }
        });
        add(button2, CC.xy(13, 15));


        word2vecPath.setText("Word2VecFilePath");
        word2vecPath.setLabelFor(word2vecPathTextField);
        add(word2vecPath, CC.xy(5, 17));
        add(word2vecPathTextField, CC.xywh(7, 17, 5, 1));

        //---- button2 ----
        saveToButton.setText("Save to");
        saveToButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SaveToActionPerformed(e);

            }
        });
        add(saveToButton, CC.xy(13, 17));


        button1.setText("Train");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trainActionPerformed(e);

            }
        });

        add(button1, CC.xy(7, 19));

    }

    /**
     * Save button action perform function for where the word2vec file should be saved.
     *
     * @param e ActionEvent object
     */
    private void SaveToActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            saveWord2VecPath = selectedFile.getPath();
            word2vecPathTextField.setText("");
            word2vecPathTextField.setText(saveWord2VecPath);

            log.debug("Selected file: " + saveWord2VecPath);
        } else if (!word2vecPathTextField.getText().equals("")) {
            log.debug("Selected file: " + saveWord2VecPath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "word2vec file is not selected\nword2vec.txt will be generated under where the program is run",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);
            word2vecPathTextField.setText("");
            word2vecPathTextField.setText(saveWord2VecPath);
        }
    }

    /**
     * Browse button action perform function for trainFilePath.
     *
     * @param e ActionEvent object
     */
    private void browseActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/home"));
        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            log.debug("Selected file: " + selectedFile.getAbsolutePath());
            trainFilePath = selectedFile.getPath();
            trainingPathTextField.setText("");
            trainingPathTextField.setText(trainFilePath);

            log.debug("Selected file: " + trainFilePath);

        } else if (!trainingPathTextField.getText().equals("")) {
            log.debug("Selected file: " + trainFilePath);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Train file is not selected",
                    "file not found",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This function performs the word2vec training by calling the utility function in
     * TweetWord2Vec. It builds an object Word2VecParameters to pass it to utility function
     *
     * @param e ActionEvent object
     */
    private void trainActionPerformed(ActionEvent e) {

        word2VecParameters = new Word2VecParameters.Word2VecParametersBuilder()
                .windowSize(windowSizeTextField.getText())
                .minWordFeq(minFeqTextField.getText())
                .numIteration(iterationTextField.getText())
                .layerSize(layerSizeTextField.getText())
                .learningRate(learningRateTextField.getText())
                .seed(seedTextField.getText())
                .build();


        if (trainFilePath == null || trainFilePath.equals("")) {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Train file is not selected",
                    "file not found",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (saveWord2VecPath.equals("")) {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "word2vec output file is not selected, the word2vec file will be placed under" +
                            " the same folder where the program is run",
                    "folder not found",
                    JOptionPane.ERROR_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "WindowSize = " + word2VecParameters.getWindowSize() + "\n" + "MinFrequency =" +

                            word2VecParameters.getMinWordFeq() + "\n" + "Iteration = " +
                            word2VecParameters.getNumIteration() + "\n" + "LayerSize = " +
                            word2VecParameters.getLayerSize() + "\n" + "Seed = " +
                            word2VecParameters.getSeed() + "\n" + "LearningRate = " +
                            word2VecParameters.getLearningRate()
                    ,
                    "Training Parameters",
                    JOptionPane.INFORMATION_MESSAGE);
            Thread trainingThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        saveWord2VecPath = word2vecPathTextField.getText().trim();
                        trainFilePath = trainingPathTextField.getText().trim();
                        if (TweetWord2VecUtils.trainWordToVector(trainFilePath, saveWord2VecPath,
                                word2VecParameters)) {
                            JOptionPane.showMessageDialog(mainFrame,
                                    "Training is done",
                                    "Transform Done",
                                    JOptionPane.INFORMATION_MESSAGE);
                            button1.setEnabled(true);
                            button2.setEnabled(true);
                            saveToButton.setEnabled(true);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        log.debug("Exception while running training");
                    }
                }
            });
            trainingThread.start();
            button1.setEnabled(false);
            button2.setEnabled(false);
            saveToButton.setEnabled(false);
        }


    }
}



