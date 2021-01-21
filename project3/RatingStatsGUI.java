package project3;

import java.util.Set;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Provides functionality of a GUI for RatingStatsApp
 * 
 * @author Himaja
 * @author Daniel
 * @author Scott
 *
 */

public class RatingStatsGUI extends JFrame {
    
    // I grabbed this template from zybooks and found a similar one from another website as well
    private JLabel promptLabel;
    private JButton proceedButton;
    private JComboBox<String> dropDown;
    private int selectedIndex;
    private String newDataID;

    // This sets up the GUI Frame (constructor)
    RatingStatsGUI() {

        setTitle("RatingStats");

        // Creates a label with an option prompt "Select an option"
        promptLabel = new JLabel("Select an option:");
        add(promptLabel);

        // List of options for user to select from
        String[] selectOptions = new String[] {"Display computed statistics for specific dataID" + DataAnalysis.LINE_SEP
        , "Add new collection and compute statistics" + DataAnalysis.LINE_SEP
        , "Exit program" + DataAnalysis.LINE_SEP};

        // Create Dropdown Menu
        dropDown = new JComboBox<String>(selectOptions);
        setLayout(new FlowLayout());
        add(dropDown);

        // Create a proceed button
        proceedButton = new JButton();
        proceedButton.setText("Proceed");
        add(proceedButton);

        // Add event listener for dropDown menu
        dropDown.addActionListener(new ActionListener() {

        	/**
        	 * actionPerformed of type void for dropDown menu
        	 * 
        	 * @param event
        	 */
            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String selectedOption = (String) combo.getSelectedItem();

                DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) combo.getModel();
                selectedIndex = model.getIndexOf(selectedOption);
            }
        });

        // Add event listener for proceed button
        try {
            DatasetHandler dh = new DatasetHandler();
            Set<Dataset> datasets = dh.getDataSets();
            
            proceedButton.addActionListener(new ActionListener() {
            
                @Override
                /**
                 * actionPerformed of type void for proceedButton
                 * 
                 * @param event
                 */
                public void actionPerformed(ActionEvent event) {
                
                    boolean found = false;
                    if (selectedIndex == 0) {
                            if (datasets.size()<1){
                            String selection = (String) dropDown.getSelectedItem();
                            JOptionPane.showMessageDialog(RatingStatsGUI.this, "There is no data to select from, select another option");
                            }
                            else {
                            // printDB() from DatasetHandler
                            
                                String content = "";
                                for (final Dataset d : datasets) {
                                content = content + d.toString() + DataAnalysis.STAT_FILE_TEMPLATE.replace("<dataID>", 
                                d.getDataId())+ DataAnalysis.LINE_SEP;

                            }
                            JOptionPane.showMessageDialog(RatingStatsGUI.this, "dataID,RAW_FILE,RATINGS_NO,STAT_FILE" + 
                            DataAnalysis.LINE_SEP + content);
                    		
                            String selection = (String) dropDown.getSelectedItem();
                            newDataID = JOptionPane.showInputDialog(RatingStatsGUI.this, "Please enter dataID from the list" );
                            
                            if (!(dh.checkID(newDataID))) {
                                JOptionPane.showMessageDialog(RatingStatsGUI.this, "dataID not in the current database, select another option");
                            } 
                            else {
                            	found = true;
                            }
                        }
                    // end option 1
                    } else if (selectedIndex == 1) {
                        String selection = (String) dropDown.getSelectedItem();
                        newDataID = JOptionPane.showInputDialog(RatingStatsGUI.this,"Please enter new unique dataID");
                    
                        if (!(dh.checkID(newDataID))){
                            String fileName = JOptionPane.showInputDialog(RatingStatsGUI.this,
                            "For new " + newDataID + " collection, what is the source file name?");
                            boolean check = dh.addCollection(newDataID,fileName);
                            
                            if (check) {
                                JOptionPane.showMessageDialog(RatingStatsGUI.this, "Collection " + newDataID + " added");
                                found = true;
                            }
                            else {
                                JOptionPane.showMessageDialog(RatingStatsGUI.this, "File not found! Try again.");
                               
                            }
                        } 
                        else {
                            JOptionPane.showMessageDialog(RatingStatsGUI.this, 
                            newDataID + " is in the current database, displaying existing statistics.");
                        }
                    } // end option 2                 
    
                    
                    if (found) {
                        JOptionPane.showMessageDialog(RatingStatsGUI.this,
                                "statistics are already computed and saved");
                        String[] processStats = new String[] { "Choose one of the following functions",
                                "3. Use existing stat data", "4. Process statistics again, I have new data" };

                        // Create Dropdown Menu
                        dropDown = new JComboBox<String>(processStats);
                        setLayout(new FlowLayout());
                        add(dropDown);

                        // Create a proceed button
                        proceedButton = new JButton();
                        proceedButton.setText("Proceed");
                        add(proceedButton);
                        JOptionPane.showMessageDialog(RatingStatsGUI.this, dropDown);

                        Dataset d;
                        try {
                            d = dh.populateCollection(newDataID);
                            int stats = d.statsExist();
                            if (selectedIndex == 2 || (stats == 0)) {
                                d.computeStats();
                                // save stats if recomputed
                                dh.saveStats(newDataID);
                                dh.saveDB();
                            }

                            int k = 20;
                            String reportContent1 = dh.printReport(newDataID, k);

                            JFrame newFrame = new JFrame("Report");
                            newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                            newFrame.getContentPane().add(new JTextArea(reportContent1));
                            newFrame.setSize(500, 700);
                            newFrame.pack();
                            newFrame.setVisible(true);
                            
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    else if (selectedIndex == 2) {
                        String selection = (String) dropDown.getSelectedItem();
                        JOptionPane.showMessageDialog(RatingStatsGUI.this, "Goodbye!"); 
                        // I think we can just remove this option from the dropdown since the user can just exit with the X button...
                    } // end selection

                
                }
            });
    
    
        }catch(IOException e){
            System.out.println("Dataset path not found: " + e.getMessage());
            System.out.println("Please check the file and try again, exiting.");
        }
        
    }

    

    // Option indexes start from 0
    /**
     * get selected index from choices
     * 
     * @return selected index
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    public static void main(String [] args) {
		
        RatingStatsGUI frame = new RatingStatsGUI();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        int selection = frame.getSelectedIndex();
        System.out.println(selection);

    }
}