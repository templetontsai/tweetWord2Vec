/*
 * Created by JFormDesigner on Sat May 21 01:50:08 AEST 2016
 */

package unimelb.distributed_project.gui;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private JScrollPane scrollPane2;
    private JList wordList;
    private JButton loadListButton;
    private JButton measureButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private JFrame mainFrame;
    private String word2vecFilePath1;
    private String word2vecFilePath2;
    private String listOfwordsFilePath;

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
        } else {
            JOptionPane.showMessageDialog(this.mainFrame,
                    "Please suggest where the word2vec text file is",
                    "file is not selected",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void measureButtonActionPerformed(ActionEvent e) {

        Thread measureThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ListModel model = wordList.getModel();
                for(int i = 0; i < model.getSize(); i++) {



                }
            }
        });
        measureThread.start();



    }

    private void loadListButtonActionPerformed(ActionEvent e) {
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

                }
                wordList = new JList(model);
            } else {

                f.createNewFile();

            }
        } catch (IOException e1) {
            log.debug("creating/loading file exception");
            e1.printStackTrace();
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
                measureButtonActionPerformed(e);
                measureButtonActionPerformed(e);
                measureButtonActionPerformed(e);
                measureButtonActionPerformed(e);
                measureButtonActionPerformed(e);
                measureButtonActionPerformed(e);
                measureButtonActionPerformed(e);
            }
        });
        add(measureButton, CC.xy(17, 11));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}
