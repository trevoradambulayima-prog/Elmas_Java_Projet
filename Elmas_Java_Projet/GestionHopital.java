import javax.swing.*;
import java.awt.*;

public class GestionHopital extends JFrame {
    private JProgressBar progressBar;
    private JTextField userField;
    private JPasswordField passField;
    // Professional Gray Day Theme
    private static final Color DARK_GRAY = new Color(60, 65, 75);
    private static final Color MEDIUM_GRAY = new Color(100, 110, 120);
    private static final Color LIGHT_GRAY = new Color(240, 242, 245);
    private static final Color ACCENT_GRAY = new Color(80, 90, 105);
    private static final Color TEXT_DARK = new Color(45, 50, 60);

    public GestionHopital() {
        setTitle("Accueil - Gestion des Malades");
        
        // Set window to fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Splash / Progress Bar Section
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(DARK_GRAY);
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitle = new JLabel("Gestion Hospitalière - Connexion", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        northPanel.add(lblTitle, BorderLayout.NORTH);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setBackground(LIGHT_GRAY);
        progressBar.setForeground(ACCENT_GRAY);
        progressBar.setPreferredSize(new Dimension(0, 8));
        
        JLabel lblChargement = new JLabel("Initialisation...", JLabel.CENTER);
        lblChargement.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblChargement.setForeground(Color.WHITE);
        
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBackground(DARK_GRAY);
        progressPanel.add(lblChargement, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        northPanel.add(progressPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        // Login Section
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(LIGHT_GRAY);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username Field
        JLabel userLabel = new JLabel("Nom d'utilisateur:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(TEXT_DARK);
        gbc.gridy = 0;
        centerPanel.add(userLabel, gbc);
        
        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        userField.setBackground(Color.WHITE);
        userField.setPreferredSize(new Dimension(250, 38));
        gbc.gridy = 1;
        centerPanel.add(userField, gbc);
        
        // Password Field
        JLabel passLabel = new JLabel("Mot de passe:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(TEXT_DARK);
        gbc.gridy = 2;
        centerPanel.add(passLabel, gbc);
        
        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        passField.setBackground(Color.WHITE);
        passField.setPreferredSize(new Dimension(250, 38));
        gbc.gridy = 3;
        centerPanel.add(passField, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.setBackground(LIGHT_GRAY);
        
        JButton btnConnexion = new JButton("Connexion");
        btnConnexion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnConnexion.setBackground(ACCENT_GRAY);
        btnConnexion.setForeground(Color.WHITE);
        btnConnexion.setFocusPainted(false);
        btnConnexion.setBorder(BorderFactory.createEmptyBorder(8, 30, 8, 30));
        btnConnexion.setPreferredSize(new Dimension(130, 40));
        btnConnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnQuitter.setBackground(new Color(150, 100, 100));
        btnQuitter.setForeground(Color.WHITE);
        btnQuitter.setFocusPainted(false);
        btnQuitter.setBorder(BorderFactory.createEmptyBorder(8, 30, 8, 30));
        btnQuitter.setPreferredSize(new Dimension(130, 40));
        btnQuitter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnPanel.add(btnConnexion);
        btnPanel.add(btnQuitter);
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 0, 0, 0);
        centerPanel.add(btnPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Login Logic
        btnConnexion.addActionListener(e -> verifyLogin());
        btnQuitter.addActionListener(e -> System.exit(0));

        setVisible(true);
        simulateProgress();
    }

    private void simulateProgress() {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try { Thread.sleep(20); progressBar.setValue(i); } catch (Exception e) {}
            }
        }).start();
    }

    private void verifyLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        // Credential check
        boolean auth = (user.equals("Etudiant") && pass.equals("123")) || 
                       (user.equals("Parent") && pass.equals("456")) || 
                       (user.equals("Bob OMARI") && pass.equals("java2026"));

        if (auth) {
            new MenuPrincipal().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Accès refusé!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionHopital::new);
    }
}
