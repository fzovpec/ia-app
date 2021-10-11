package views;

import controllers.ReportCard;
import controllers.ScreenSwitcher;
import models.ReportCardModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContentPanel {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    ScreenSwitcher screenSwitcher = new ScreenSwitcher();
    ReportCardModel model = new ReportCardModel();
    ReportCard [] reportCards;
    JButton saveButton = new JButton("Save");

    DefaultTableModel tableModel = new DefaultTableModel();

    public ContentPanel(JFrame frame){
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        ReportCard[] reportCards = model.getReportsData();
        Object[][] tableContent = this.getTheDataForTheTable(reportCards);
        Object[] columnTitles = new Object[]{"First Name", "Second Name", "bin1", "bin2", "coef", "Comment"};
        tableModel.setDataVector(tableContent, columnTitles);

        JTable table = new JTable(tableModel);
        table.getColumn("Comment").setCellRenderer(new ButtonRenderer());
        table.getColumn("Comment").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scroll = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < reportCards.length; i++){
                    reportCards[i].bin1 = (String) table.getValueAt(i, 2);
                    reportCards[i].bin2 = (String) table.getValueAt(i, 3);
                    model.updateReportCard(reportCards[i]);
                }
            }
        });

        panel.add(scroll);
        panel.add(saveButton);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.setPreferredSize(new Dimension(600, 800));
    }

    public JPanel getPanel(){
        return panel;
    }

    public Object[][] getTheDataForTheTable(ReportCard[] reportCards){

        Object[][] tableData = new Object[reportCards.length][];

        for(int i = 0; i < reportCards.length; i++){
            ReportCard reportCard = reportCards[i];
            Object[] reportCardData = new Object[]{
                    reportCard.studentFirstName, reportCard.studentLastName, reportCard.bin1, reportCard.bin2, reportCard.coef, "Modify " + i
            };
            tableData[i] = reportCardData;
        }

        return tableData;
    }
}



class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        String btnLabel = label.split(" ")[0];
        int identifier = Integer.parseInt(label.split(" ")[1]);
        button.setText(btnLabel);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyMessage modifyMessage = new ModifyMessage(identifier);
            }
        });
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
