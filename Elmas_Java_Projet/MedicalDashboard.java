import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;

public class MedicalDashboard extends JFrame {
    private static final Color LIGHT_GRAY = new Color(211, 211, 211);
    private static final Color DARK_GRAY = new Color(80, 80, 80);
    
    // Identité du malade fields
    private JTextField txtFiche, txtPrenom, txtNom, txtPostnom, txtTelephone;
    private JFormattedTextField txtDateNaissance;
    private JRadioButton rbSexeM, rbSexeF;
    
    // Signes vitaux fields
    private JTextField txtTension, txtTemperature, txtPoids;
    
    // Symptômes checkboxes
    private JCheckBox[] chkSymptomes;
    
    // Examens de Laboratoire checkboxes
    private JCheckBox[] chkExamens;
    
    // Imagerie checkboxes
    private JCheckBox[] chkImagerie;
    
    // Résultats des examens radio buttons
    private JRadioButton[] rbResultats;
    
    // Interprétation fields
    private JTextField[] txtInterpretation;
    
    // Diagnostic dropdowns
    private JComboBox<String>[] comboDiagnostics;
    
    // Photo upload
    private JLabel photoLabel;
    private File selectedPhotoFile;
    
    // Prescription table
    private JTable medicinTable;
    private JComboBox<String> comboMedicines;

    public MedicalDashboard() {
        setTitle("Gestion des Malades - Dossier Médical");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- HEADER PANEL ---
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // --- MAIN CONTENT (SCROLLABLE) ---
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(LIGHT_GRAY);
        
        mainContent.add(createIdentityPanel());
        mainContent.add(createVitalsPanel());
        mainContent.add(Box.createVerticalStrut(10));
        mainContent.add(createSymptomsPanel());
        mainContent.add(Box.createVerticalStrut(10));
        mainContent.add(createLabExamsPanel());
        mainContent.add(Box.createVerticalStrut(10));
        mainContent.add(createImagingPanel());
        mainContent.add(Box.createVerticalStrut(10));
        mainContent.add(createExamResultsPanel());
        mainContent.add(Box.createVerticalStrut(10));
        mainContent.add(createImagingInterpretationPanel());
        mainContent.add(Box.createVerticalStrut(10));
        mainContent.add(createDiagnosticPanel());
        mainContent.add(Box.createVerticalStrut(10));
        mainContent.add(createPrescriptionPanel());
        mainContent.add(Box.createVerticalStrut(20));
        
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBackground(LIGHT_GRAY);
        add(scrollPane, BorderLayout.CENTER);
        
        // --- BUTTON PANEL (BOTTOM) ---
        add(createButtonPanel(), BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_GRAY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Photo and Student Info (Left side)
        JPanel photoInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        photoInfoPanel.setBackground(DARK_GRAY);
        JLabel headerPhotoLabel = new JLabel();
        headerPhotoLabel.setPreferredSize(new Dimension(80, 100));
        headerPhotoLabel.setBackground(Color.WHITE);
        headerPhotoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        headerPhotoLabel.setOpaque(true);
        headerPhotoLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPhotoLabel.setVerticalAlignment(JLabel.CENTER);
        headerPhotoLabel.setText("Cliquez pour\najouter une photo");
        headerPhotoLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        headerPhotoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerPhotoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                openHeaderPhotoChooser(headerPhotoLabel);
            }
        });
        photoInfoPanel.add(headerPhotoLabel);
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(DARK_GRAY);
        
        JLabel lblNom = new JLabel("ELMAS MASONGA");
        lblNom.setFont(new Font("Arial", Font.BOLD, 16));
        lblNom.setForeground(Color.WHITE);
        infoPanel.add(lblNom);
        
        JLabel lblPromotion = new JLabel("Promotion: L2");
        lblPromotion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPromotion.setForeground(Color.WHITE);
        infoPanel.add(lblPromotion);
        
        JLabel lblFiliere = new JLabel("Filière: Génie Logiciel");
        lblFiliere.setFont(new Font("Arial", Font.PLAIN, 14));
        lblFiliere.setForeground(Color.WHITE);
        infoPanel.add(lblFiliere);
        
        photoInfoPanel.add(infoPanel);
        headerPanel.add(photoInfoPanel, BorderLayout.WEST);
        
        JLabel titleLabel = new JLabel("Gestion des Malades - Fiche Médicale");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }

    private JPanel createIdentityPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("IDENTITÉ DU MALADE");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        JPanel contentPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtFiche = new JTextField();
        txtPrenom = new JTextField();
        txtNom = new JTextField();
        txtPostnom = new JTextField();
        txtTelephone = new JTextField();
        txtDateNaissance = new JFormattedTextField();
        
        rbSexeM = new JRadioButton("M");
        rbSexeF = new JRadioButton("F");
        ButtonGroup sexGroup = new ButtonGroup();
        sexGroup.add(rbSexeM);
        sexGroup.add(rbSexeF);
        rbSexeM.setBackground(Color.WHITE);
        rbSexeF.setBackground(Color.WHITE);
        
        contentPanel.add(createLabel("N° Fiche:")); contentPanel.add(txtFiche);
        contentPanel.add(createLabel("Prénom:")); contentPanel.add(txtPrenom);
        contentPanel.add(createLabel("Nom:")); contentPanel.add(txtNom);
        contentPanel.add(createLabel("Postnom:")); contentPanel.add(txtPostnom);
        
        JPanel datePanel = new JPanel(new BorderLayout());
        datePanel.setBackground(Color.WHITE);
        datePanel.add(txtDateNaissance, BorderLayout.CENTER);
        JButton btnCalendar = new JButton("📅");
        btnCalendar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCalendar.setMargin(new Insets(2, 5, 2, 5));
        btnCalendar.setBackground(DARK_GRAY);
        btnCalendar.setForeground(Color.WHITE);
        btnCalendar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCalendar.addActionListener(e -> showDatePicker());
        datePanel.add(btnCalendar, BorderLayout.EAST);
        
        contentPanel.add(createLabel("Date de naissance:")); contentPanel.add(datePanel);
        
        JPanel sexPanel = new JPanel();
        sexPanel.setBackground(Color.WHITE);
        sexPanel.add(rbSexeM);
        sexPanel.add(rbSexeF);
        contentPanel.add(createLabel("Sexe:")); contentPanel.add(sexPanel);
        
        contentPanel.add(createLabel("Téléphone:")); contentPanel.add(txtTelephone);
        contentPanel.add(new JLabel(""));
        contentPanel.add(new JLabel(""));
        
        p.add(contentPanel);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        return p;
    }

    private JPanel createVitalsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("SIGNES VITAUX");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        JPanel contentPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtTension = new JTextField();
        txtTemperature = new JTextField();
        txtPoids = new JTextField();
        
        contentPanel.add(createLabel("Tension artérielle:")); contentPanel.add(txtTension);
        contentPanel.add(createLabel("Température corporelle:")); contentPanel.add(txtTemperature);
        contentPanel.add(createLabel("Poids corporel:")); contentPanel.add(txtPoids);
        
        photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(150, 180));
        photoLabel.setBackground(LIGHT_GRAY);
        photoLabel.setBorder(BorderFactory.createLineBorder(DARK_GRAY, 2));
        photoLabel.setOpaque(true);
        photoLabel.setHorizontalAlignment(JLabel.CENTER);
        photoLabel.setVerticalAlignment(JLabel.CENTER);
        photoLabel.setText("Cliquez pour\najouter une photo");
        photoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        photoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        photoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                openPhotoChooser();
            }
        });
        
        contentPanel.add(createLabel("Photo du malade:")); contentPanel.add(photoLabel);
        
        p.add(contentPanel);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return p;
    }

    private JPanel createSymptomsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("SYMPTÔMES");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        String[] symptoms = {"Fièvre", "Frissons", "Fatigue intense (asthénie)", "Douleurs musculaires (courbatures)", "Maux de tête (céphalées)",
                            "Sueurs", "Toux (sèche ou grasse)", "Mal de gorge", "Nez qui coule ou bouché (congestion)", "Eternuements",
                            "Essoufflement (dyspnée)", "Oppression thoracique", "Nausées", "Vomissements", "Diarrhée",
                            "Douleurs abdominales", "Perte d'appétit (anorexie)", "Reflux (pyrosis)", "Étourdissements", "Vertiges",
                            "Perte d'odorat (anosmie) ou de goût (agueusie)", "Troubles de la vision", "Confusion", "Perte de connaissance"};
        
        chkSymptomes = new JCheckBox[24];
        JPanel contentPanel = new JPanel(new GridLayout(0, 5, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int i = 0; i < 24; i++) {
            chkSymptomes[i] = new JCheckBox(symptoms[i]);
            chkSymptomes[i].setBackground(Color.WHITE);
            contentPanel.add(chkSymptomes[i]);
        }
        
        p.add(contentPanel);
        return p;
    }

    private JPanel createLabExamsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("EXAMENS DE LABORATOIRE");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        String[] exams = {"GS", "VS", "Selles", "Urine", "Glycémie", "Cholestérol", "Globules rouges", "G. Blancs", "Créatinine"};
        chkExamens = new JCheckBox[9];
        JPanel contentPanel = new JPanel(new GridLayout(0, 5, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int i = 0; i < exams.length; i++) {
            chkExamens[i] = new JCheckBox(exams[i]);
            chkExamens[i].setBackground(Color.WHITE);
            contentPanel.add(chkExamens[i]);
        }
        
        p.add(contentPanel);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return p;
    }

    private JPanel createImagingPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("IMAGERIE");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        String[] imaging = {"Radiographie", "Scanner", "Echographie", "Dialyse", "ECG", "EEG", "IRM"};
        chkImagerie = new JCheckBox[7];
        JPanel contentPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int i = 0; i < imaging.length; i++) {
            chkImagerie[i] = new JCheckBox(imaging[i]);
            chkImagerie[i].setBackground(Color.WHITE);
            contentPanel.add(chkImagerie[i]);
        }
        
        p.add(contentPanel);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        return p;
    }

    private JPanel createExamResultsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("RÉSULTATS DES EXAMENS");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        String[] results = {"GS", "VS", "Selles", "Urine", "Glycémie", "Cholestérol", "Globules rouges", "G. Blancs", "Créatinine", "Urée"};
        rbResultats = new JRadioButton[results.length * 2];
        JPanel contentPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        int idx = 0;
        for (int i = 0; i < results.length; i++) {
            JLabel lbl = new JLabel(results[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 12));
            contentPanel.add(lbl);
            
            JPanel resultPanel = new JPanel();
            resultPanel.setBackground(Color.WHITE);
            
            rbResultats[idx] = new JRadioButton("+");
            rbResultats[idx].setBackground(Color.WHITE);
            rbResultats[idx + 1] = new JRadioButton("--");
            rbResultats[idx + 1].setBackground(Color.WHITE);
            
            ButtonGroup group = new ButtonGroup();
            group.add(rbResultats[idx]);
            group.add(rbResultats[idx + 1]);
            
            resultPanel.add(rbResultats[idx]);
            resultPanel.add(rbResultats[idx + 1]);
            contentPanel.add(resultPanel);
            
            idx += 2;
        }
        
        p.add(contentPanel);
        return p;
    }

    private JPanel createImagingInterpretationPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("INTERPRÉTATION DES RÉSULTATS D'IMAGERIE");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        String[] imagingLabels = {"Radiographie", "Scanner", "Echographie", "Dialyse", "ECG", "EEG", "IRM"};
        txtInterpretation = new JTextField[imagingLabels.length];
        JPanel contentPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int i = 0; i < imagingLabels.length; i++) {
            contentPanel.add(createLabel(imagingLabels[i] + ":"));
            txtInterpretation[i] = new JTextField();
            contentPanel.add(txtInterpretation[i]);
        }
        
        p.add(contentPanel);
        return p;
    }

    @SuppressWarnings("unchecked")
    private JPanel createDiagnosticPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("DIAGNOSTIC");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        String[] diseases1 = {"Sélectionner", "Paludisme", "Grippe", "Typhoïde", "Anémie", "Diabète"};
        String[] diseases2 = {"Sélectionner", "Cholera", "Tuberculose", "Pneumonie", "Hypertension", "Asthme"};
        String[] diseases3 = {"Sélectionner", "Bronchite", "Sinusite", "Otite", "Gastrite", "Appendicite"};
        
        comboDiagnostics = new JComboBox[3];
        JPanel contentPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JComboBox<String>[] temp = (JComboBox<String>[]) comboDiagnostics;
        
        temp[0] = new JComboBox<>(diseases1);
        temp[1] = new JComboBox<>(diseases2);
        temp[2] = new JComboBox<>(diseases3);
        
        contentPanel.add(createLabel("1ère maladie:")); contentPanel.add(temp[0]);
        contentPanel.add(createLabel("2ème maladie:")); contentPanel.add(temp[1]);
        contentPanel.add(createLabel("3ème maladie:")); contentPanel.add(temp[2]);
        
        p.add(contentPanel);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        return p;
    }

    private JPanel createPrescriptionPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_GRAY);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_GRAY);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel title = new JLabel("PRESCRIPTION / ORDONNANCE");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        p.add(titlePanel);
        
        // Dropdown for medicines
        String[] medicines = new String[30];
        for (int i = 0; i < 30; i++) {
            medicines[i] = "Médicament " + (i + 1);
        }
        
        JPanel dropPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dropPanel.setBackground(Color.WHITE);
        dropPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        comboMedicines = new JComboBox<>(medicines);
        dropPanel.add(new JLabel("Médicaments:"));
        dropPanel.add(comboMedicines);
        p.add(dropPanel);
        
        // Table with 8 medicines
        String[] columns = {"Médicament", "Posologie", "Durée"};
        Object[][] data = new Object[8][3];
        for (int i = 0; i < 8; i++) {
            data[i][0] = "Médicament " + (i + 1);
            data[i][1] = "";
            data[i][2] = "";
        }
        
        medicinTable = new JTable(data, columns);
        medicinTable.setBackground(Color.WHITE);
        medicinTable.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(medicinTable);
        tableScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        p.add(tableScroll);
        
        p.add(Box.createVerticalStrut(10));
        return p;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(DARK_GRAY);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnAnnuler = createStyledButton("Annuler", new Color(150, 50, 50));
        JButton btnModifier = createStyledButton("Modifier", DARK_GRAY);
        JButton btnEnregistrer = createStyledButton("Enregistrer", DARK_GRAY);
        JButton btnRechercher = createStyledButton("Rechercher", DARK_GRAY);
        JButton btnImprimer = createStyledButton("Imprimer", DARK_GRAY);
        JButton btnOrdonnance = createStyledButton("Ordonnance", DARK_GRAY);
        JButton btnAutreMalade = createStyledButton("Autre malade", new Color(60, 120, 60));
        JButton btnRetour = createStyledButton("Retour au Menu", new Color(60, 120, 60));
        
        btnAnnuler.addActionListener(e -> resetForm());
        btnModifier.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mode modification activé"));
        btnEnregistrer.addActionListener(e -> JOptionPane.showMessageDialog(this, "Données enregistrées"));
        btnRechercher.addActionListener(e -> openSearchDialog());
        btnImprimer.addActionListener(e -> JOptionPane.showMessageDialog(this, "Impression de la fiche malade..."));
        btnOrdonnance.addActionListener(e -> JOptionPane.showMessageDialog(this, "Impression ordonnance..."));
        btnAutreMalade.addActionListener(e -> resetForm());
        btnRetour.addActionListener(e -> {
            new MenuPrincipal().setVisible(true);
            this.dispose();
        });
        
        buttonPanel.add(btnAnnuler);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnEnregistrer);
        buttonPanel.add(btnRechercher);
        buttonPanel.add(btnImprimer);
        buttonPanel.add(btnOrdonnance);
        buttonPanel.add(btnAutreMalade);
        buttonPanel.add(btnRetour);
        
        return buttonPanel;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        return lbl;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 35));
        return btn;
    }

    private void resetForm() {
        txtFiche.setText("");
        txtPrenom.setText("");
        txtNom.setText("");
        txtPostnom.setText("");
        txtTelephone.setText("");
        txtDateNaissance.setText("");
        txtTension.setText("");
        txtTemperature.setText("");
        txtPoids.setText("");
        
        rbSexeM.setSelected(false);
        rbSexeF.setSelected(false);
        
        for (JCheckBox chk : chkSymptomes) chk.setSelected(false);
        for (JCheckBox chk : chkExamens) chk.setSelected(false);
        for (JCheckBox chk : chkImagerie) chk.setSelected(false);
        for (JRadioButton rb : rbResultats) rb.setSelected(false);
        for (JTextField txt : txtInterpretation) txt.setText("");
        for (JComboBox<String> combo : comboDiagnostics) combo.setSelectedIndex(0);
        
        photoLabel.setIcon(null);
        photoLabel.setText("Cliquez pour\najouter une photo");
        selectedPhotoFile = null;
    }
    
    private void openPhotoChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionner une photo du patient");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
            "Fichiers image (JPG, PNG, GIF, BMP, TIFF, WebP, ICO)", 
            "jpg", "jpeg", "png", "gif", "bmp", "tiff", "webp", "ico"
        );
        fileChooser.setFileFilter(imageFilter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedPhotoFile = fileChooser.getSelectedFile();
            displayPhoto(selectedPhotoFile);
        }
    }
    
    private void displayPhoto(File photoFile) {
        try {
            BufferedImage originalImage = ImageIO.read(photoFile);
            if (originalImage == null) {
                JOptionPane.showMessageDialog(this, "Impossible de charger l'image. Format non supporté.");
                return;
            }
            
            // Scale image to fit in 150x180 without distortion
            int displayWidth = 150;
            int displayHeight = 180;
            BufferedImage scaledImage = new BufferedImage(displayWidth, displayHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            
            float scale = Math.min((float)displayWidth / originalImage.getWidth(), (float)displayHeight / originalImage.getHeight());
            int newWidth = (int)(originalImage.getWidth() * scale);
            int newHeight = (int)(originalImage.getHeight() * scale);
            
            int x = (displayWidth - newWidth) / 2;
            int y = (displayHeight - newHeight) / 2;
            
            g2d.fillRect(0, 0, displayWidth, displayHeight);
            g2d.drawImage(originalImage, x, y, newWidth, newHeight, null);
            g2d.dispose();
            
            ImageIcon icon = new ImageIcon(scaledImage);
            photoLabel.setIcon(icon);
            photoLabel.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la photo: " + e.getMessage());
        }
    }
    
    private void openHeaderPhotoChooser(JLabel headerPhotoLabel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionner une photo du patient");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
            "Fichiers image (JPG, PNG, GIF, BMP, TIFF, WebP, ICO)", 
            "jpg", "jpeg", "png", "gif", "bmp", "tiff", "webp", "ico"
        );
        fileChooser.setFileFilter(imageFilter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            displayHeaderPhoto(headerPhotoLabel, selectedFile);
            selectedPhotoFile = selectedFile;
            displayPhoto(selectedFile);
        }
    }
    
    private void displayHeaderPhoto(JLabel headerLabel, File photoFile) {
        try {
            BufferedImage originalImage = ImageIO.read(photoFile);
            if (originalImage == null) {
                JOptionPane.showMessageDialog(this, "Impossible de charger l'image. Format non supporté.");
                return;
            }
            
            // Scale image to fit in 80x100 without distortion for header
            int displayWidth = 80;
            int displayHeight = 100;
            BufferedImage scaledImage = new BufferedImage(displayWidth, displayHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            
            float scale = Math.min((float)displayWidth / originalImage.getWidth(), (float)displayHeight / originalImage.getHeight());
            int newWidth = (int)(originalImage.getWidth() * scale);
            int newHeight = (int)(originalImage.getHeight() * scale);
            
            int x = (displayWidth - newWidth) / 2;
            int y = (displayHeight - newHeight) / 2;
            
            g2d.fillRect(0, 0, displayWidth, displayHeight);
            g2d.drawImage(originalImage, x, y, newWidth, newHeight, null);
            g2d.dispose();
            
            ImageIcon icon = new ImageIcon(scaledImage);
            headerLabel.setIcon(icon);
            headerLabel.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la photo: " + e.getMessage());
        }
    }

    private void showDatePicker() {
        JDialog dateDialog = new JDialog(this, "Sélectionner une date", true);
        dateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dateDialog.setLayout(new BorderLayout(10, 10));
        dateDialog.setSize(400, 350);
        dateDialog.setLocationRelativeTo(this);
        
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        final int[] year = {cal.get(Calendar.YEAR)};
        final int[] month = {cal.get(Calendar.MONTH)};
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        
        // Month and Year selector with navigation
        JPanel monthYearPanel = new JPanel(new BorderLayout(5, 5));
        monthYearPanel.setBackground(DARK_GRAY);
        monthYearPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnPrevYear = new JButton("<<");
        btnPrevYear.setBackground(DARK_GRAY);
        btnPrevYear.setForeground(Color.WHITE);
        btnPrevYear.setFocusPainted(false);
        
        JButton btnNextYear = new JButton(">>");
        btnNextYear.setBackground(DARK_GRAY);
        btnNextYear.setForeground(Color.WHITE);
        btnNextYear.setFocusPainted(false);
        
        JLabel monthYearLabel = new JLabel(getMonthName(month[0]) + " " + year[0]);
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 14));
        monthYearLabel.setForeground(Color.WHITE);
        monthYearLabel.setHorizontalAlignment(JLabel.CENTER);
        
        monthYearPanel.add(btnPrevYear, BorderLayout.WEST);
        monthYearPanel.add(monthYearLabel, BorderLayout.CENTER);
        monthYearPanel.add(btnNextYear, BorderLayout.EAST);
        dateDialog.add(monthYearPanel, BorderLayout.NORTH);
        
        JPanel calendarPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Runnable refreshCalendar = () -> {
            calendarPanel.removeAll();
            monthYearLabel.setText(getMonthName(month[0]) + " " + year[0]);
            
            String[] dayNames = {"L", "M", "M", "J", "V", "S", "D"};
            for (String day : dayNames) {
                JLabel dayLabel = new JLabel(day);
                dayLabel.setFont(new Font("Arial", Font.BOLD, 12));
                dayLabel.setHorizontalAlignment(JLabel.CENTER);
                dayLabel.setForeground(DARK_GRAY);
                calendarPanel.add(dayLabel);
            }
            
            cal.set(year[0], month[0], 1);
            int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (firstDayOfWeek == 0) firstDayOfWeek = 6; else firstDayOfWeek--;
            
            int maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            for (int i = 0; i < firstDayOfWeek; i++) {
                calendarPanel.add(new JLabel(""));
            }
            
            for (int day = 1; day <= maxDaysInMonth; day++) {
                final int selectedDay = day;
                JButton dayButton = new JButton(String.valueOf(day));
                dayButton.setBackground(day == dayOfMonth && year[0] == currentYear && month[0] == cal.get(Calendar.MONTH) ? DARK_GRAY : Color.WHITE);
                dayButton.setForeground(day == dayOfMonth && year[0] == currentYear && month[0] == cal.get(Calendar.MONTH) ? Color.WHITE : DARK_GRAY);
                dayButton.setFont(new Font("Arial", Font.PLAIN, 11));
                dayButton.setFocusPainted(false);
                dayButton.setBorder(BorderFactory.createLineBorder(LIGHT_GRAY, 1));
                dayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                dayButton.addActionListener(e -> {
                    String dateStr = String.format("%02d/%02d/%04d", selectedDay, month[0] + 1, year[0]);
                    txtDateNaissance.setText(dateStr);
                    dateDialog.dispose();
                });
                
                calendarPanel.add(dayButton);
            }
            
            calendarPanel.revalidate();
            calendarPanel.repaint();
        };
        
        btnPrevYear.addActionListener(e -> {
            if (year[0] > currentYear - 100) year[0]--;
            refreshCalendar.run();
        });
        
        btnNextYear.addActionListener(e -> {
            if (year[0] < currentYear + 100) year[0]++;
            refreshCalendar.run();
        });
        
        refreshCalendar.run();
        dateDialog.add(calendarPanel, BorderLayout.CENTER);
        dateDialog.setVisible(true);
    }

    private String getMonthName(int month) {
        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                          "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        return months[month];
    }
    
    private void openSearchDialog() {
        // Check if a patient is already registered
        if (!isPatientRegistered()) {
            JOptionPane.showMessageDialog(this, 
                "Aucun patient enregistré!\nVeuillez d'abord enregistrer une personne avant de rechercher.",
                "Recherche - Aucune donnée", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create search dialog
        JDialog searchDialog = new JDialog(this, "Rechercher un patient", true);
        searchDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        searchDialog.setSize(500, 300);
        searchDialog.setLocationRelativeTo(this);
        searchDialog.setLayout(new BorderLayout(10, 10));
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblSearch = new JLabel("Entrez les détails à rechercher:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 13));
        searchPanel.add(lblSearch, BorderLayout.NORTH);
        
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createLineBorder(DARK_GRAY, 1));
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        // Results area
        JTextArea resultsArea = new JTextArea();
        resultsArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        resultsArea.setEditable(false);
        resultsArea.setBackground(new Color(245, 245, 245));
        resultsArea.setLineWrap(true);
        resultsArea.setWrapStyleWord(true);
        JScrollPane resultScroll = new JScrollPane(resultsArea);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnSearch = new JButton("Rechercher");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 12));
        btnSearch.setBackground(DARK_GRAY);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setPreferredSize(new Dimension(120, 35));
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton btnClose = new JButton("Fermer");
        btnClose.setFont(new Font("Arial", Font.BOLD, 12));
        btnClose.setBackground(new Color(150, 100, 100));
        btnClose.setForeground(Color.WHITE);
        btnClose.setPreferredSize(new Dimension(120, 35));
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnSearch.addActionListener(e -> {
            String searchTerm = searchField.getText().trim().toLowerCase();
            if (searchTerm.isEmpty()) {
                resultsArea.setText("Veuillez entrer un terme de recherche.");
                return;
            }
            String results = searchPatientDetails(searchTerm);
            resultsArea.setText(results);
        });
        
        btnClose.addActionListener(e -> searchDialog.dispose());
        
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnClose);
        
        searchDialog.add(searchPanel, BorderLayout.NORTH);
        searchDialog.add(resultScroll, BorderLayout.CENTER);
        searchDialog.add(buttonPanel, BorderLayout.SOUTH);
        searchDialog.setVisible(true);
    }
    
    private boolean isPatientRegistered() {
        // Check if at least the patient ID or name is filled
        return !txtFiche.getText().trim().isEmpty() || 
               !txtPrenom.getText().trim().isEmpty() || 
               !txtNom.getText().trim().isEmpty();
    }
    
    private String searchPatientDetails(String searchTerm) {
        StringBuilder results = new StringBuilder();
        results.append("RÉSULTATS DE LA RECHERCHE\n");
        results.append("=".repeat(50)).append("\n\n");
        
        boolean found = false;
        
        // Search in identity fields
        results.append("--- IDENTITÉ DU MALADE ---\n");
        if (txtFiche.getText().toLowerCase().contains(searchTerm)) {
            results.append("✓ N° Fiche: ").append(txtFiche.getText()).append("\n");
            found = true;
        }
        if (txtPrenom.getText().toLowerCase().contains(searchTerm)) {
            results.append("✓ Prénom: ").append(txtPrenom.getText()).append("\n");
            found = true;
        }
        if (txtNom.getText().toLowerCase().contains(searchTerm)) {
            results.append("✓ Nom: ").append(txtNom.getText()).append("\n");
            found = true;
        }
        if (txtPostnom.getText().toLowerCase().contains(searchTerm)) {
            results.append("✓ Postnom: ").append(txtPostnom.getText()).append("\n");
            found = true;
        }
        if (txtTelephone.getText().toLowerCase().contains(searchTerm)) {
            results.append("✓ Téléphone: ").append(txtTelephone.getText()).append("\n");
            found = true;
        }
        
        // Search in vitals
        results.append("\n--- SIGNES VITAUX ---\n");
        if (txtTension.getText().toLowerCase().contains(searchTerm)) {
            results.append("✓ Tension: ").append(txtTension.getText()).append("\n");
            found = true;
        }
        if (txtTemperature.getText().toLowerCase().contains(searchTerm)) {
            results.append("✓ Température: ").append(txtTemperature.getText()).append("\n");
            found = true;
        }
        if (txtPoids.getText().toLowerCase().contains(searchTerm)) {
            results.append("✓ Poids: ").append(txtPoids.getText()).append("\n");
            found = true;
        }
        
        // Search in symptoms
        results.append("\n--- SYMPTÔMES DÉTECTÉS ---\n");
        int symptomCount = 0;
        for (JCheckBox chk : chkSymptomes) {
            if (chk.isSelected() && chk.getText().toLowerCase().contains(searchTerm)) {
                results.append("✓ ").append(chk.getText()).append("\n");
                symptomCount++;
                found = true;
            }
        }
        if (symptomCount == 0 && !searchTerm.isEmpty()) {
            boolean hasAnySymptom = false;
            for (JCheckBox chk : chkSymptomes) {
                if (chk.isSelected()) {
                    if (hasAnySymptom == false) {
                        results.append("Symptômes enregistrés: ");
                        hasAnySymptom = true;
                    }
                }
            }
        }
        
        // Search in interpretation fields
        results.append("\n--- INTERPRÉTATION D'IMAGERIE ---\n");
        String[] imagingLabels = {"Radiographie", "Scanner", "Echographie", "Dialyse", "ECG", "EEG", "IRM"};
        for (int i = 0; i < txtInterpretation.length; i++) {
            if (!txtInterpretation[i].getText().isEmpty() && 
                txtInterpretation[i].getText().toLowerCase().contains(searchTerm)) {
                results.append("✓ ").append(imagingLabels[i]).append(": ")
                    .append(txtInterpretation[i].getText()).append("\n");
                found = true;
            }
        }
        
        if (!found) {
            results.append("\nAucun résultat trouvé pour: '").append(searchTerm).append("'");
        }
        
        return results.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MedicalDashboard());
    }
}
