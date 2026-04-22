import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private static final Color LIGHT_GRAY = new Color(211, 211, 211);
    private static final Color DARK_GRAY = new Color(80, 80, 80);

    public MenuPrincipal() {
        setTitle("Menu Principal - Gestion Hospitalière");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel with Title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(DARK_GRAY);
        JLabel title = new JLabel("MENU PRINCIPAL - GESTION HOSPITALIÈRE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        topPanel.add(title);
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(topPanel, BorderLayout.NORTH);

        // Center Panel with Buttons
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        centerPanel.setBackground(LIGHT_GRAY);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JButton btnConnexion = new JButton("Connexion");
        styleButton(btnConnexion);
        JButton btnMalades = new JButton("Gestion des Malades");
        styleButton(btnMalades);
        JButton btnSalaire = new JButton("Calcul de Salaire des agents");
        styleButton(btnSalaire);
        JButton btnQuitter = new JButton("Fin de Traitement");
        styleButton(btnQuitter, new Color(150, 50, 50));

        // Link to the other classes
        btnConnexion.addActionListener(e -> {
            new GestionHopital().setVisible(true);
            this.dispose();
        });
        btnMalades.addActionListener(e -> {
            new MedicalDashboard().setVisible(true);
            this.dispose();
        });
        btnSalaire.addActionListener(e -> {
            new CalculSalaireAgent().setVisible(true);
            this.dispose();
        });
        btnQuitter.addActionListener(e -> System.exit(0));

        centerPanel.add(btnConnexion);
        centerPanel.add(btnMalades);
        centerPanel.add(btnSalaire);
        centerPanel.add(btnQuitter);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton btn) {
        styleButton(btn, DARK_GRAY);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}