import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.ListSelectionModel; 

class HeroPanel extends JPanel {
    private BattleGameGUI mainGUI;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public HeroPanel(BattleGameGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Hero Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        String[] columns = {"Name", "HP", "Attack Power", "Level", "Strategy", "Skills"};
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
        
        JButton addButton = new JButton("Add Hero");
        addButton.addActionListener(e -> showAddDialog());
        
        JButton editButton = new JButton("Edit Hero");
        editButton.addActionListener(e -> showEditDialog());
        
        JButton deleteButton = new JButton("Delete Hero");
        deleteButton.addActionListener(e -> deleteHero());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        
        for (Player player : mainGUI.getPlayers()) {
            String skillNames = "";
            for (Skill skill : player.getSkills()) {
                skillNames += skill.name() + ", ";
            }
            if (skillNames.length() > 0) {
                skillNames = skillNames.substring(0, skillNames.length() - 2);
            }
            
            Object[] row = {
                player.getName(),
                player.getMaxHealth(),
                player.getAttackPower(),
                player.getLevel(),
                "LevelScaled", // Hardcoded, seharusnya ambil dari object player.attackStrategy.toString()
                skillNames
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddDialog() {
        HeroDialog dialog = new HeroDialog(mainGUI, null, -1);
        dialog.setVisible(true);
    }
    
    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hero to edit!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Player player = mainGUI.getPlayers().get(selectedRow);
        HeroDialog dialog = new HeroDialog(mainGUI, player, selectedRow);
        dialog.setVisible(true);
    }
    
    private void deleteHero() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hero to delete!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this hero?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            mainGUI.removePlayer(selectedRow);
        }
    }
}