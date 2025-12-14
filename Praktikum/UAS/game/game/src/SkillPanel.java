import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.ListSelectionModel; 

class SkillPanel extends JPanel {
    private BattleGameGUI mainGUI;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public SkillPanel(BattleGameGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Skill Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        String[] columns = {"Skill Name", "Type", "Value"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("Add Skill");
        addButton.addActionListener(e -> showAddDialog());
        
        JButton deleteButton = new JButton("Delete Skill");
        deleteButton.addActionListener(e -> deleteSkill());
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        
        for (Skill skill : mainGUI.getAvailableSkills()) {
            String type = "";
            String value = "";
            
            if (skill instanceof HealSkill) {
                type = "Heal";
                String[] parts = skill.name().split("[()]");
                if (parts.length > 1) {
                    value = parts[1];
                }
            } else if (skill instanceof PiercingStrike) {
                type = "Piercing Strike";
                String[] parts = skill.name().split("[x()]");
                if (parts.length > 1) {
                    value = "x" + parts[1];
                }
            }
            
            Object[] row = {
                skill.name(),
                type,
                value
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddDialog() {
        SkillDialog dialog = new SkillDialog(mainGUI);
        dialog.setVisible(true);
    }
    
    private void deleteSkill() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a skill to delete!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this skill?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            mainGUI.removeSkill(selectedRow);
        }
    }
}