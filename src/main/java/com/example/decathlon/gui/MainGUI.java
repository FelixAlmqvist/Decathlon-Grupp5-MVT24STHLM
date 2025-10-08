package com.example.decathlon.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.Arrays;
import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;
public class MainGUI {

    private JTextField nameField;
    private JTextField resultField;
    private JComboBox<String> disciplineBox;
    private JTextArea outputArea;
    private JComboBox<String> modeBox;

    private static final String[] DEC_EVENTS = {
            "100m","Long Jump","Shot Put","High Jump","400m","110m Hurdles","Discus Throw","Pole Vault","Javelin Throw","1500m"
    };
    private static final String[] HEP_EVENTS = {
            "100m Hurdles","High Jump","Shot Put","200m","Long Jump","Javelin Throw","800m"
    };

    public static void main(String[] args) {
        new MainGUI().createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Track and Field Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modePanel.add(new JLabel("Competition:"));
        modeBox = new JComboBox<>(new String[]{"Decathlon", "Heptathlon"});
        modeBox.addActionListener(e -> rebuildDisciplineBox());
        modePanel.add(modeBox);
        panel.add(modePanel);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Enter Competitor's Name:"));
        nameField = new JTextField(20);
        namePanel.add(nameField);
        panel.add(namePanel);

        JPanel disciplinePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        disciplinePanel.add(new JLabel("Select Discipline:"));
        disciplineBox = new JComboBox<>(DEC_EVENTS);
        disciplinePanel.add(disciplineBox);
        panel.add(disciplinePanel);

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        resultPanel.add(new JLabel("Enter Result:"));
        resultField = new JTextField(10);
        resultPanel.add(resultField);
        panel.add(resultPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton calculateButton = new JButton("Calculate Score");
        calculateButton.addActionListener(new CalculateButtonListener());
        buttonPanel.add(calculateButton);
        panel.add(buttonPanel);

        outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);

    }

    private void rebuildDisciplineBox() {
        String m = (String) modeBox.getSelectedItem();
        disciplineBox.removeAllItems();
        String[] src = "Heptathlon".equals(m) ? HEP_EVENTS : DEC_EVENTS;
        Arrays.stream(src).forEach(disciplineBox::addItem);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String discipline = (String) disciplineBox.getSelectedItem();
            String resultText = resultField.getText();
            String comp = (String) modeBox.getSelectedItem();

            try {
                double result = Double.parseDouble(resultText);
                int score = 0;
                if ("Decathlon".equals(comp)) {
                    switch (discipline) {
                        case "100m": score = new Deca100M().calculateResult(result); break;
                        case "400m": score = new Deca400M().calculateResult(result); break;
                        case "1500m": score = new Deca1500M().calculateResult(result); break;
                        case "110m Hurdles": score = new Deca110MHurdles().calculateResult(result); break;
                        case "Long Jump": score = new DecaLongJump().calculateResult(result); break;
                        case "High Jump": score = new DecaHighJump().calculateResult(result); break;
                        case "Pole Vault": score = new DecaPoleVault().calculateResult(result); break;
                        case "Discus Throw": score = new DecaDiscusThrow().calculateResult(result); break;
                        case "Javelin Throw": score = new DecaJavelinThrow().calculateResult(result); break;
                        case "Shot Put": score = new com.example.decathlon.deca.DecaShotPut().calculateResult(result); break;
                    }
                } else {
                    switch (discipline) {
                        case "100m Hurdles": score = new Hep100MHurdles().calculateResult(result); break;
                        case "High Jump": score = new HeptHightJump().calculateResult(result); break;
                        case "Shot Put": score = new HeptShotPut().calculateResult(result); break;
                        case "200m": score = new Hep200M().calculateResult(result); break;
                        case "Long Jump": score = new HeptLongJump().calculateResult(result); break;
                        case "Javelin Throw": score = new HeptJavelinThrow().calculateResult(result); break;
                        case "800m": score = new Hep800M().calculateResult(result); break;
                    }
                }

                outputArea.append("Competition: " + comp + "\n");
                outputArea.append("Competitor: " + name + "\n");
                outputArea.append("Discipline: " + discipline + "\n");
                outputArea.append("Result: " + result + "\n");
                outputArea.append("Score: " + score + "\n\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the result.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
