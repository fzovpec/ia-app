package views; /**
 * This is a 9th example of an "Application Window".
 * It generates a window with 6 components : a label, a text field, 3 button, and a list.
 * There are also 2 panels = a right panel and a left panel.
 * The list is added to the left panel.
 * All other components are added to the right panel.
 * 
 * NEW : This class adds a menu bar to the window
 *         
 * @author DL 
 * @version 01/12/2020
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.*;
import javax.swing.table.DefaultTableModel;

// Libs required by MigLayout
import controllers.*;
import models.ReportCardModel;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.ArrayUtils;

// Libs required to manage events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.util.Arrays;


public class GUI extends JFrame {
    
    // instance variables
    JPanel contentPanel;
    JPanel filteringPanel;
    JSplitPane splSPLIT;
    
    JLabel labONE;
    JButton doFilteringButton;
    JButton butREAD;
    JButton butSAVE;
    JButton butUPDATE;
    FilteringController filteringController;
    ReportCard[] reportCards;
    
    DefaultListModel model1;
    JList lstFOUR;
    
    JMenuBar menuBar;
    
    JToolBar toolBar;
    JButton butOpen;
    JButton butSave;
    JButton butFind;
    JButton butPreferences;
    
    String FILEPATH;
    
    /* MAIN method
    *  
    */
    public static void main( String args[] ){
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI myWindow = new GUI();
            }
        
        });
    }
    
    
    /**
     * Constructor for objects of class MainWindow08
     */
    public GUI(){
        
        // 1. -- Frame title and size
        this.setTitle("Ecole");
        this.setPreferredSize(new Dimension(1200,600));  
        this.getContentPane().setLayout(new MigLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 2. -- Prepare the panels AND a splitPanel (which allows for resizing)
        contentPanel = new JPanel();
        contentPanel.setLayout(new MigLayout("center, wrap, width 100%", "left", "top"));

        filteringPanel = new JPanel();
        filteringPanel.setBackground(new Color(250,250,250));
        filteringPanel.setLayout(new MigLayout("width 100%", "left", "top"));
       
        splSPLIT = new JSplitPane(SwingConstants.VERTICAL, contentPanel, filteringPanel);
        splSPLIT.setOrientation(SwingConstants.VERTICAL);
        splSPLIT.setResizeWeight(0.9);
            
        // 3. -- Prepare the label
        labONE = new JLabel();
        labONE.setFont(new Font("sansserif",0,14));
        labONE.setText("Filter:");
        
        
        // 8. -- Prepare the list MODEL and the list
        //       The list MODEL is a structure that contains the DATA
        //       displayed through the list
        DefaultTableModel tableModel = new DefaultTableModel();
        ReportCardModel model = new ReportCardModel();

        reportCards = model.getReportsData();
        Object[][] tableContent = GUI.getTheDataForTheTable(reportCards);
        Object[] columnTitles = new Object[]{"First Name", "Second Name", "bin1", "average", "bin2", "coef", "Comment"};
        tableModel.setDataVector(tableContent, columnTitles);

        JTable table = new JTable(tableModel){
            public Class<?> getColumnClass(int column){
                return getValueAt(0, column).getClass();
            }
            public boolean isCellEditable(int row, int col){
                if (col==2 || col == 4 || col==6){
                    return true;
                }
                return false;
            }
        };

        JScrollPane scroll = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        
        // 9. -- Add the menubar to the window
        generateMenu();
        setJMenuBar(menuBar);

        // 6. -- Prepare the READ button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("width 100%", "left", "top"));

        butREAD = new JButton();
        butREAD.setFont(new Font("sansserif",0,14));
        butREAD.setText("Read File");
        butREAD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectFile('r');
                readFile();

            }
        });

        // 7. -- Prepare the SAVE button
        butSAVE = new JButton();
        butSAVE.setFont(new Font("sansserif",0,14));
        butSAVE.setText("Export File");
        butSAVE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectFile('s');
                saveFile();

            }
        });

        butUPDATE = new JButton();
        butUPDATE.setFont(new Font("sansserif",0,14));
        butUPDATE.setText("Update");
        butUPDATE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < table.getRowCount(); i++) {
                    reportCards[i].bins[0] = Integer.parseInt(table.getValueAt(i, 2).toString());
                    reportCards[i].bins[1] = Integer.parseInt(table.getValueAt(i, 4).toString());
                    reportCards[i].comment = (String) table.getValueAt(i, 6);

                    model.updateReportCard(reportCards[i]);
                }
            }
        });

        buttonPanel.add(butREAD,"right, bottom");
        buttonPanel.add(butSAVE,"right, bottom");
        buttonPanel.add(butUPDATE, "right bottom");

        String[] yearsString = ArrayUtils.addAll(new String[]{"All"});
        String[] termsString = ArrayUtils.addAll(new String[]{"All"});
        String[] courses = ArrayUtils.addAll(new String[]{"All"});
        String[] sections = ArrayUtils.addAll(new String[]{"All"});
        // Combo boxes
        try {
            filteringController = new FilteringController();

            int[] years = filteringController.getYears(reportCards);
            Arrays.sort(years);
            yearsString = ArrayUtils.addAll(new String[]{"All"}, Arrays.toString(years).split("[\\[\\]]")[1].split(", "));

            int[] terms = filteringController.getTerms(reportCards);
            Arrays.sort(years);
            termsString = ArrayUtils.addAll(new String[]{"All"}, Arrays.toString(years).split("[\\[\\]]")[1].split(", "));

            courses = ArrayUtils.addAll(new String[]{"All"}, filteringController.getCourses(reportCards));
            sections = ArrayUtils.addAll(new String[]{"All"}, filteringController.getSections(reportCards));
        } catch(ArrayIndexOutOfBoundsException e){}

        JComboBox yearsCombo = new JComboBox(yearsString);
        JComboBox courseCombo = new JComboBox(courses);
        JComboBox sectionCombo = new JComboBox(sections);
        JComboBox termsCombo = new JComboBox(termsString);

        // 5. -- Prepare the ADD WORD button
        doFilteringButton = new JButton();
        doFilteringButton.setFont(new Font("sansserif",0,14));
        doFilteringButton.setText("Filter");
        doFilteringButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reportCards = filteringController.filterReportCards(yearsCombo.getSelectedItem().toString(),
                        termsCombo.getSelectedItem().toString(), courseCombo.getSelectedItem().toString(), sectionCombo.getSelectedItem().toString(), reportCards);

                contentPanel.removeAll();
                pack();

                Object[][] tableContent = GUI.getTheDataForTheTable(reportCards);
                Object[] columnTitles = new Object[]{"First Name", "Second Name", "bin1", "average", "bin2", "coef", "Comment"};
                tableModel.setDataVector(tableContent, columnTitles);
                JTable table = new JTable(tableModel);
                JScrollPane scroll = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
                table.getColumnModel().getColumn(0).setPreferredWidth(100);

                contentPanel.add(scroll, "width 100%, height 90%");
                contentPanel.add(buttonPanel);
                pack();
            }

        });
        
        
        // 10. -- Add all components to the panels, and the panels to the window   
        contentPanel.add(scroll,"width 100%, height 90%");
        contentPanel.add(buttonPanel);

        filteringPanel.add(labONE,"width 100:100:100, height 20");
        filteringPanel.add(yearsCombo);
        filteringPanel.add(courseCombo);
        filteringPanel.add(sectionCombo);
        filteringPanel.add(termsCombo);
        filteringPanel.add(doFilteringButton,"right, width 50, span, wrap 15");
        
        add(splSPLIT,"width 100%, height 100%");
        getRootPane().setDefaultButton(doFilteringButton);
        
        // 11. -- Set some of the frame parameters, then close operation        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setResizable(true);
        pack();
        setVisible(true);        
        
    }

    /**
     * This method will open the FileChooser dialogbox and
     * select a file to read.
     */
    private void selectFile(char sel){
        int result;
        
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "text");
        fileChooser.setFileFilter(filter); 
        if (sel == 's'){
            XmlExporter exporter = new XmlExporter();
            ReportCard[] reportCards = new ReportCardModel().getReportsData();
            String filePath = exporter.chooseTheFile(this);
            exporter.exportDataToXml(reportCards, filePath);

        }else{
            ImportDataController importDataController = new ImportDataController();
            String filePath = importDataController.chooseTheFile(this);
            if (importDataController.verifyFile(filePath)){
                XmlReader.readXML(filePath);
            }
            else{
                JOptionPane.showMessageDialog(this, "Unfortunately it's not possible to read this file");
            }
        }

    } 

    
    /**
     * This method reads the content of a text file using a BufferedReader object
     */
    private void readFile(){
        try (BufferedReader MYFILE = new BufferedReader(new FileReader(FILEPATH))){
            String LINE = null;
            
            model1.clear();
             
            while ((LINE = MYFILE.readLine()) != null){
                model1.addElement(LINE);
     
            }
            
            MYFILE.close();

        }catch(Exception e){
            System.out.println(e);
            return;
        }                
    }   
    

    /**
     * This method saves the content of the listModel 'model1' in a text file using a BufferedReader object
     */
    private void saveFile(){
        try (BufferedWriter MYFILE = new BufferedWriter(new FileWriter(FILEPATH))){
            for(int C=0; C<model1.getSize(); C++){
                if(C>0) MYFILE.newLine();
                MYFILE.write((String)model1.getElementAt(C));     
                
            }
            
            MYFILE.close();

        }catch(Exception e){
            System.out.println(e);
            return;
        }        
    }    
    
    
    /**  
     * OK/CANCEL confirmation popup dialogbox
     * returns 0 if OK
     */
    public int askConfirmation(String theTitle, String theMessage) {
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage, theTitle, JOptionPane.OK_CANCEL_OPTION);
        return result;
    }
    
    
    /** 
    * DISPLAY the ABOUT message
    */
    private void displayAboutMessage(){
        JOptionPane.showMessageDialog(this,"My Application\nVersion 1.0","About...",JOptionPane.INFORMATION_MESSAGE);
        
    } 
    
    
    /**  
    * CLOSE FRAME
    */    
    private void exit(String theTitle, String theMessage){
        int i = askConfirmation(theTitle, theMessage);
        if (i == 0){ 
            dispose();
        }
    }    
    
    
    /** 
     * MENU GENERATOR
     */
    private void generateMenu(){
        menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");
        JMenu menuSearch = new JMenu("Search");        
        JMenu menuHelp = new JMenu("Help");
        
        ImageIcon iOpen = new ImageIcon("images/open_s.png");        
        ImageIcon iSave = new ImageIcon("images/save_s.png"); 
        ImageIcon iExit = new ImageIcon("images/cross_s.png");        
        ImageIcon iCut = new ImageIcon("images/cut_s.png");  
        ImageIcon iCopy = new ImageIcon("images/copy_s.png");  
        ImageIcon iPaste = new ImageIcon("images/paste_s.png");           
        ImageIcon iFind = new ImageIcon("images/find_s.png");
        ImageIcon iPrefs = new ImageIcon("images/preferences_s.png");       
        ImageIcon iAbout = new ImageIcon("images/about_s.png"); 
        
        JMenuItem comOpen = new JMenuItem("Open",iOpen);
        JMenuItem comSave = new JMenuItem("Export",iSave);
        JMenuItem comExit = new JMenuItem("Exit",iExit);
        JMenuItem comCut = new JMenuItem("Cut",iCut);
        JMenuItem comCopy = new JMenuItem("Copy",iCopy);
        JMenuItem comPaste = new JMenuItem("Paste",iPaste);
        JMenuItem comSearch = new JMenuItem("Search...",iFind);
        JMenuItem comPrefs = new JMenuItem("Preferences...",iPrefs);   
        JMenuItem comAbout = new JMenuItem("About...",iAbout);

        // add action listener to the Open menu item
        comOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                selectFile('s');
                readFile();
            }
        });

        // add action listener to the Save menu item
        comSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                selectFile('s');
                saveFile();                
            }
        });        
        
        // add action listener to the Exit menu item
        comExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                exit("Confirm","Do you really want to quit?");
            }
        });
        
        // add action listener to the ABOUT menu item
        comAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                displayAboutMessage();
            }
        });    
        
        menuFile.add(comOpen);
        menuFile.add(comSave);
        menuFile.addSeparator(); 
        menuFile.add(comExit);
        
        menuEdit.add(comCut);
        menuEdit.add(comCopy);
        menuEdit.add(comPaste);
        
        menuSearch.add(comSearch);

        menuHelp.add(comPrefs);
        menuHelp.addSeparator();                
        menuHelp.add(comAbout);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuSearch);      
        menuBar.add(menuHelp);
    }

    private static Object[][] getTheDataForTheTable(ReportCard[] reportCards){

        Object[][] tableData = new Object[reportCards.length][];

        for(int i = 0; i < reportCards.length; i++){
            ReportCard reportCard = reportCards[i];
            float average = (float) ((reportCard.bins[0] + reportCard.bins[1]) / 2.0);
            Object[] reportCardData = new Object[]{
                    reportCard.studentFirstName, reportCard.studentLastName, reportCard.bins[0], average, reportCard.bins[1], reportCard.coef, reportCard.comment
            };
            tableData[i] = reportCardData;
        }

        return tableData;
    }
    
}    
    
   
   