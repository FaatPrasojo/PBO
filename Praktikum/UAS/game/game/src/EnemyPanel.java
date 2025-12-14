import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.ListSelectionModel;

class EnemyPanel extends JPanel {
    private BattleGameGUI mainGUI;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public EnemyPanel(BattleGameGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Enemy Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        String[] columns = {"Type", "Name", "HP", "Attack Power", "Threat Level", "Strategy"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("Add Enemy");
        addButton.addActionListener(e -> showAddDialog());
        
        JButton editButton = new JButton("Edit Enemy");
        editButton.addActionListener(e -> showEditDialog());
        
        JButton deleteButton = new JButton("Delete Enemy");
        deleteButton.addActionListener(e -> deleteEnemy());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        
        for (Enemy enemy : mainGUI.getEnemies()) {
            String type = enemy instanceof BossMonster ? "Boss" : "Monster";
            
            Object[] row = {
                type,
                enemy.getName(),
                enemy.getMaxHealth(),
                enemy.getAttackPower(),
                enemy.getThreatLevel(),
                "FixedStrategy" // Hardcoded, seharusnya ambil dari object
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddDialog() {
        EnemyDialog dialog = new EnemyDialog(mainGUI, null, -1);
        dialog.setVisible(true);
    }
    
    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an enemy to edit!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Enemy enemy = mainGUI.getEnemies().get(selectedRow);
        EnemyDialog dialog = new EnemyDialog(mainGUI, enemy, selectedRow);
        dialog.setVisible(true);
    }
    
    private void deleteEnemy() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an enemy to delete!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this enemy?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            mainGUI.removeEnemy(selectedRow);
        }
    }
}