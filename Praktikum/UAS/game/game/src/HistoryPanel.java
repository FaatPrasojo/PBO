import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class HistoryPanel extends JPanel {
    private BattleGameGUI mainGUI;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea logArea;
    private JComboBox<String> filterCombo;
    
    public HistoryPanel(BattleGameGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // --- Title ---
        JLabel titleLabel = new JLabel("Battle History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.add(new JLabel("Filter by Winner:"));
        filterCombo = new JComboBox<>(new String[]{"All", "Heroes", "Enemies", "Draw"});
        filterCombo.addActionListener(e -> refreshTable());
        filterPanel.add(filterCombo);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        filterPanel.add(refreshButton);
        
        add(filterPanel, BorderLayout.NORTH);
        
        // --- Main Content (Split Pane) ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left: Table
        String[] columns = {"ID", "Hero 1", "Hero 2", "Enemy 1", "Enemy 2", "Winner", "Turns", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> showBattleLog());
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(600, 300));
        
        // Right: Battle Log
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setPreferredSize(new Dimension(400, 300));
        
        splitPane.setLeftComponent(tableScroll);
        splitPane.setRightComponent(logScroll);
        splitPane.setDividerLocation(0.6);
        
        add(splitPane, BorderLayout.CENTER);
        
        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> deleteHistory());
        
        JButton clearButton = new JButton("Clear All History");
        clearButton.addActionListener(e -> clearAllHistory());
        
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        
        String filter = (String) filterCombo.getSelectedItem();
        List<BattleHistory> battles;
        
        if (filter.equals("All")) {
            battles = DatabaseManager.getAllBattles();
        } else {
            battles = DatabaseManager.getBattlesByWinner(filter);
        }
        
        for (BattleHistory battle : battles) {
            Object[] row = {
                battle.getId(),
                battle.getHero1(),
                battle.getHero2() != null && !battle.getHero2().isEmpty() ? battle.getHero2() : "-",
                battle.getEnemy1(),
                battle.getEnemy2() != null && !battle.getEnemy2().isEmpty() ? battle.getEnemy2() : "-",
                battle.getWinner(),
                battle.getTotalTurns(),
                battle.getFormattedDate()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showBattleLog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            logArea.setText("");
            return;
        }
        
        int battleId = (int) tableModel.getValueAt(selectedRow, 0);
        BattleHistory battle = DatabaseManager.getBattleById(battleId);
        
        if (battle != null) {
            logArea.setText(battle.getBattleLog());
            logArea.setCaretPosition(0);
        }
    }
    
    private void deleteHistory() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a battle to delete!",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int battleId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this battle history?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            DatabaseManager.deleteBattle(battleId);
            refreshTable();
        }
    }
    
    private void clearAllHistory() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete ALL battle history? This cannot be undone!",
            "Confirm Clear All", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            DatabaseManager.clearAllHistory();
            refreshTable();
            logArea.setText("");
        }
    }
}
