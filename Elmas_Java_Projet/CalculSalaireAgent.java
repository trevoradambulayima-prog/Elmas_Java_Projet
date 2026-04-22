import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;

public class CalculSalaireAgent extends JFrame {
    private static final Color LIGHT_GRAY = new Color(211, 211, 211);
    private static final Color DARK_GRAY = new Color(80, 80, 80);

    // Identité fields
    private JTextField txtMatricule, txtPrenom, txtNom, txtPostnom, txtLieuNaissance;
    private JTextField txtPereNoms, txtMereNoms, txtAdressePhysique, txtAdresseEmail, txtTelephone;
    private JComboBox<String> comboSex;
    private JFormattedTextField txtDateNaissance;
    
    // Origine fields
    private JTextField txtVillage, txtTerritoire, txtDistrict, txtProvince;
    private JComboBox<String> comboPays;
    
    // Études fields
    private JTextField txtUniversitaires, txtSecondaires, txtAutresFormations;
    
    // Photo & Student Info fields
    private JTextField txtNomEtudiant, txtPromotion, txtFiliere;
    private JLabel photoLabel;
    private File selectedPhotoFile;
    
    // Languages checkboxes
    private JCheckBox[] chkLangues;
    
    // Nationalité radio buttons
    private JRadioButton[] rbNationalites;
    
    // Salaire sections
    private JTextField txtSalaireBase, txtPrimeRendement, txtPrimeAnciennete, txtPrimeRisque;
    private JTextField txtHeuresSup, txtParticipationBenef, txtGratification13;
    private JTextField txtTransport, txtLogement, txtRepas, txtCongé;
    private JComboBox<String> comboAnnee, comboMois;
    private JComboBox<String> comboDirection, comboDivision, comboBureau, comboCellule;
    
    // Retenues fields
    private JTextField txtIPR, txtCNSS, txtINPP, txtTotalPrimes, txtTotalRetenues;
    private JTextField txtBrut, txtNet;

    public CalculSalaireAgent() {
        setTitle("Calcul de Salaire des Agents");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL: STUDENT IDENTIFICATION ---
        JPanel studentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studentPanel.setBackground(DARK_GRAY);
        studentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel lblStudent = new JLabel("Étudiant: ELMAS MASONGA | L2 Génie Logiciel");
        lblStudent.setFont(new Font("Arial", Font.BOLD, 16));
        lblStudent.setForeground(Color.WHITE);
        studentPanel.add(lblStudent);
        add(studentPanel, BorderLayout.NORTH);

        // --- CENTER PANEL: SCROLLABLE FORM ---
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(LIGHT_GRAY);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 1. IDENTITÉ SECTION
        mainPanel.add(createSection("IDENTITÉ DE L'AGENT", createIdentityPanel()));
        
        // 2. ORIGINE SECTION
        mainPanel.add(createSection("ORIGINE", createOriginPanel()));
        
        // 3. ÉTUDES SECTION
        mainPanel.add(createSection("ÉTUDES FAITES", createStudiesPanel()));
        
        // 4. PHOTO & STUDENT INFO
        mainPanel.add(createSection("PHOTO & INFORMATIONS ÉTUDIANT", createPhotoPanel()));
        
        // 5. LANGUES PARLÉES
        mainPanel.add(createSection("LANGUES PARLÉES", createLanguagesPanel()));
        
        // 6. NATIONALITÉ
        mainPanel.add(createSection("NATIONALITÉ", createNationalityPanel()));
        
        // 7. SALAIRE FIXE & PRIMES
        mainPanel.add(createSection("SALAIRE FIXE & PRIMES", createSalaryPanel()));
        
        // 8. INDEMNITÉS
        mainPanel.add(createSection("INDEMNITÉS", createAllowancesPanel()));
        
        // 9. PÉRIODE & SERVICE
        mainPanel.add(createSection("PÉRIODE DE PAIE & SERVICE", createPeriodServicePanel()));
        
        // 10. RETENUES & RÉSULTATS
        mainPanel.add(createSection("RETENUES & RÉSULTATS", createDeductionsPanel()));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(LIGHT_GRAY);
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM PANEL: BUTTONS ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setBackground(DARK_GRAY);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JButton btnAnnul = createStyledButton("Annuler", new Color(150, 50, 50));
        JButton btnModif = createStyledButton("Modifier", DARK_GRAY);
        JButton btnCalc = createStyledButton("Calculer", DARK_GRAY);
        JButton btnSave = createStyledButton("Enregistrer", DARK_GRAY);
        JButton btnPrint = createStyledButton("Imprimer", DARK_GRAY);
        JButton btnBulletin = createStyledButton("Bulletin de Paie", DARK_GRAY);
        JButton btnListing = createStyledButton("Listing de Paie", DARK_GRAY);
        JButton btnAutreAgent = createStyledButton("Autre Agent", new Color(60, 120, 60));
        JButton btnRetourMenu = createStyledButton("Retour au Menu", new Color(60, 120, 60));
        
        btnPanel.add(btnAnnul);
        btnPanel.add(btnModif);
        btnPanel.add(btnCalc);
        btnPanel.add(btnSave);
        btnPanel.add(btnPrint);
        btnPanel.add(btnBulletin);
        btnPanel.add(btnListing);
        btnPanel.add(btnAutreAgent);
        btnPanel.add(btnRetourMenu);
        add(btnPanel, BorderLayout.SOUTH);

        // --- BUTTON ACTIONS ---
        btnCalc.addActionListener(e -> calculateSalary());
        
        btnAnnul.addActionListener(e -> {
            resetForm();
        });
        
        btnModif.addActionListener(e -> {
            if (txtMatricule.getText().trim().isEmpty() || txtMatricule.getText().equals("0")) {
                JOptionPane.showMessageDialog(this, "Erreur: Veuillez d'abord remplir le Matricule!", "Champ obligatoire", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Mode modification activé!", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        btnSave.addActionListener(e -> {
            if (txtMatricule.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Erreur: Le champ Matricule ne doit pas être vide!", "Champ obligatoire", JOptionPane.WARNING_MESSAGE);
            } else if (txtSalaireBase.getText().trim().isEmpty() || txtSalaireBase.getText().equals("0")) {
                JOptionPane.showMessageDialog(this, "Erreur: Le champ Salaire de Base ne doit pas être vide!", "Champ obligatoire", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Données enregistrées avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                // Clear photo after saving for next entry
                photoLabel.setIcon(null);
                photoLabel.setText("Cliquez pour\najouter une photo");
                selectedPhotoFile = null;
            }
        });
        
        btnPrint.addActionListener(e -> {
            if (txtMatricule.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Erreur: Veuillez d'abord remplir le Matricule!", "Champ obligatoire", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Feuille complète de l'agent générée!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        btnBulletin.addActionListener(e -> {
            if (txtMatricule.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Erreur: Veuillez d'abord remplir le Matricule!", "Champ obligatoire", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Bulletin de paie généré!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        btnListing.addActionListener(e -> JOptionPane.showMessageDialog(this, "Listing de paie généré!", "Succès", JOptionPane.INFORMATION_MESSAGE));
        
        btnAutreAgent.addActionListener(e -> {
            resetForm();
            JOptionPane.showMessageDialog(this, "Formulaire réinitialisé pour un nouvel agent!", "Information", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnRetourMenu.addActionListener(e -> {
            this.dispose();
            new MenuPrincipal().setVisible(true);
        });

        setVisible(true);
    }
    
    private JPanel createSection(String title, JPanel content) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(DARK_GRAY, 2),
            title,
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13),
            DARK_GRAY
        ));
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, content.getPreferredSize().height + 40));
        section.add(content, BorderLayout.CENTER);
        return section;
    }
    
    private JPanel createIdentityPanel() {
        JPanel p = new JPanel(new GridLayout(13, 2, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtMatricule = new JTextField("0");
        txtPrenom = new JTextField("0");
        txtNom = new JTextField("0");
        txtPostnom = new JTextField("0");
        txtDateNaissance = new JFormattedTextField("JJ/MM/AAAA");
        txtLieuNaissance = new JTextField("0");
        comboSex = new JComboBox<>(new String[]{"Sélectionner", "Masculin", "Féminin"});
        txtPereNoms = new JTextField("0");
        txtMereNoms = new JTextField("0");
        txtAdressePhysique = new JTextField("0");
        txtAdresseEmail = new JTextField("0");
        txtTelephone = new JTextField("0");
        
        for(JTextField tf : new JTextField[]{txtMatricule, txtPrenom, txtNom, txtPostnom, txtLieuNaissance, 
                                              txtPereNoms, txtMereNoms, txtAdressePhysique, txtAdresseEmail, txtTelephone}) {
            styleTextField(tf);
        }
        styleTextField(txtDateNaissance);
        
        p.add(createLabel("Matricule:")); p.add(txtMatricule);
        p.add(createLabel("Prénom:")); p.add(txtPrenom);
        p.add(createLabel("Nom:")); p.add(txtNom);
        p.add(createLabel("Postnom:")); p.add(txtPostnom);
        
        // Date avec calendrier
        JPanel datePanel = new JPanel(new BorderLayout(5, 0));
        datePanel.setBackground(Color.WHITE);
        JButton btnCalendarDate = new JButton("📅");
        btnCalendarDate.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCalendarDate.setMargin(new Insets(5, 10, 5, 10));
        btnCalendarDate.setBackground(DARK_GRAY);
        btnCalendarDate.setForeground(Color.WHITE);
        btnCalendarDate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCalendarDate.addActionListener(e -> showDatePicker());
        datePanel.add(txtDateNaissance, BorderLayout.CENTER);
        datePanel.add(btnCalendarDate, BorderLayout.EAST);
        
        p.add(createLabel("Date de naissance:")); p.add(datePanel);
        p.add(createLabel("Lieu de naissance:")); p.add(txtLieuNaissance);
        p.add(createLabel("Sexe:")); p.add(comboSex);
        p.add(createLabel("Noms du père:")); p.add(txtPereNoms);
        p.add(createLabel("Noms de la mère:")); p.add(txtMereNoms);
        p.add(createLabel("Adresse physique:")); p.add(txtAdressePhysique);
        p.add(createLabel("Adresse électronique:")); p.add(txtAdresseEmail);
        p.add(createLabel("Téléphone:")); p.add(txtTelephone);
        
        return p;
    }
    
    private JPanel createOriginPanel() {
        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtVillage = new JTextField("0");
        txtTerritoire = new JTextField("0");
        txtDistrict = new JTextField("0");
        txtProvince = new JTextField("0");
        comboPays = new JComboBox<>(get50Countries());
        
        styleTextField(txtVillage);
        styleTextField(txtTerritoire);
        styleTextField(txtDistrict);
        styleTextField(txtProvince);
        
        p.add(createLabel("Village:")); p.add(txtVillage);
        p.add(createLabel("Territoire:")); p.add(txtTerritoire);
        p.add(createLabel("District:")); p.add(txtDistrict);
        p.add(createLabel("Province:")); p.add(txtProvince);
        p.add(createLabel("Pays:")); p.add(comboPays);
        
        return p;
    }
    
    private JPanel createStudiesPanel() {
        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtUniversitaires = new JTextField("0");
        txtSecondaires = new JTextField("0");
        txtAutresFormations = new JTextField("0");
        
        styleTextField(txtUniversitaires);
        styleTextField(txtSecondaires);
        styleTextField(txtAutresFormations);
        
        p.add(createLabel("Études Universitaires:")); p.add(txtUniversitaires);
        p.add(createLabel("Études Secondaires:")); p.add(txtSecondaires);
        p.add(createLabel("Autres formations:")); p.add(txtAutresFormations);
        
        return p;
    }
    
    private JPanel createPhotoPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Left side: Photo display box
        JPanel photoSection = new JPanel(new BorderLayout());
        photoSection.setBackground(Color.WHITE);
        photoSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(DARK_GRAY, 2),
            "Photo de l'Agent",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 11),
            DARK_GRAY
        ));
        
        photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(JLabel.CENTER);
        photoLabel.setVerticalAlignment(JLabel.CENTER);
        photoLabel.setPreferredSize(new Dimension(150, 180));
        photoLabel.setBackground(new Color(240, 240, 240));
        photoLabel.setOpaque(true);
        photoLabel.setBorder(BorderFactory.createLineBorder(LIGHT_GRAY, 2));
        photoLabel.setText("Cliquez pour\najouter une photo");
        photoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        photoLabel.setForeground(DARK_GRAY);
        photoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        photoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openPhotoChooser();
            }
        });
        
        photoSection.add(photoLabel, BorderLayout.CENTER);
        
        // Right side: Student info fields
        JPanel infoSection = new JPanel(new GridLayout(3, 2, 5, 5));
        infoSection.setBackground(Color.WHITE);
        
        txtNomEtudiant = new JTextField("");
        txtPromotion = new JTextField("");
        txtFiliere = new JTextField("");
        
        styleTextField(txtNomEtudiant);
        styleTextField(txtPromotion);
        styleTextField(txtFiliere);
        
        infoSection.add(createLabel("Noms de l'Étudiant:")); infoSection.add(txtNomEtudiant);
        infoSection.add(createLabel("Promotion:")); infoSection.add(txtPromotion);
        infoSection.add(createLabel("Filière:")); infoSection.add(txtFiliere);
        
        p.add(photoSection, BorderLayout.WEST);
        p.add(infoSection, BorderLayout.CENTER);
        
        return p;
    }
    
    private void openPhotoChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionner une photo");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        // Image file filter
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
            "Fichiers image (JPG, PNG, GIF, BMP, etc.)", 
            "jpg", "jpeg", "png", "gif", "bmp", "tiff", "webp", "ico"
        );
        fileChooser.addChoosableFileFilter(imageFilter);
        fileChooser.setFileFilter(imageFilter);
        
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedPhotoFile = fileChooser.getSelectedFile();
            displayPhoto(selectedPhotoFile);
        }
    }
    
    private void displayPhoto(File photoFile) {
        try {
            BufferedImage img = ImageIO.read(photoFile);
            if (img != null) {
                // Scale image to fit the label
                int maxWidth = 150;
                int maxHeight = 180;
                
                int width = img.getWidth();
                int height = img.getHeight();
                
                double scaleX = (double) maxWidth / width;
                double scaleY = (double) maxHeight / height;
                double scale = Math.min(scaleX, scaleY);
                
                int newWidth = (int) (width * scale);
                int newHeight = (int) (height * scale);
                
                BufferedImage scaledImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                java.awt.Graphics2D g2d = scaledImg.createGraphics();
                g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
                g2d.dispose();
                
                photoLabel.setIcon(new ImageIcon(scaledImg));
                photoLabel.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'image: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JPanel createLanguagesPanel() {
        JPanel p = new JPanel(new GridLayout(3, 5, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] langs = {"Français", "Portugais", "Espagnol", "Allemand", "Mandarin", 
                         "Anglais", "Néerlandais", "Japonais", "Coréen", "Autres",
                         "Lingala", "Swahili", "Tshiluuba", "Kikongo"};
        chkLangues = new JCheckBox[langs.length];
        
        for (int i = 0; i < langs.length; i++) {
            chkLangues[i] = new JCheckBox(langs[i]);
            chkLangues[i].setBackground(Color.WHITE);
            p.add(chkLangues[i]);
        }
        
        return p;
    }
    
    private JPanel createNationalityPanel() {
        JPanel p = new JPanel(new GridLayout(3, 5, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] nationalities = {"Congolaise", "Congo/Brazza", "Angolaise", "Tunisienne", "Marocaine",
                                 "Algérienne", "Libanaise", "Indienne", "Française", "Allemande",
                                 "Chinoise", "Anglaise", "Coréenne", "Américaine", "Autres"};
        rbNationalites = new JRadioButton[nationalities.length];
        ButtonGroup bgNat = new ButtonGroup();
        
        for (int i = 0; i < nationalities.length; i++) {
            rbNationalites[i] = new JRadioButton(nationalities[i]);
            rbNationalites[i].setBackground(Color.WHITE);
            bgNat.add(rbNationalites[i]);
            p.add(rbNationalites[i]);
        }
        
        return p;
    }
    
    private JPanel createSalaryPanel() {
        JPanel p = new JPanel(new GridLayout(7, 2, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtSalaireBase = new JTextField("0");
        txtPrimeRendement = new JTextField("0");
        txtPrimeAnciennete = new JTextField("0");
        txtPrimeRisque = new JTextField("0");
        txtHeuresSup = new JTextField("0");
        txtParticipationBenef = new JTextField("0");
        txtGratification13 = new JTextField("0");
        
        for(JTextField tf : new JTextField[]{txtSalaireBase, txtPrimeRendement, txtPrimeAnciennete, 
                                              txtPrimeRisque, txtHeuresSup, txtParticipationBenef, txtGratification13}) {
            styleTextField(tf);
        }
        
        p.add(createLabel("Salaire de Base:")); p.add(txtSalaireBase);
        p.add(createLabel("Prime de rendement:")); p.add(txtPrimeRendement);
        p.add(createLabel("Prime d'ancienneté:")); p.add(txtPrimeAnciennete);
        p.add(createLabel("Prime de risque:")); p.add(txtPrimeRisque);
        p.add(createLabel("Heures supplémentaires:")); p.add(txtHeuresSup);
        p.add(createLabel("Participation aux bénéfices:")); p.add(txtParticipationBenef);
        p.add(createLabel("Gratification 13ème mois:")); p.add(txtGratification13);
        
        return p;
    }
    
    private JPanel createAllowancesPanel() {
        JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtTransport = new JTextField("0");
        txtLogement = new JTextField("0");
        txtRepas = new JTextField("0");
        txtCongé = new JTextField("0");
        
        for(JTextField tf : new JTextField[]{txtTransport, txtLogement, txtRepas, txtCongé}) {
            styleTextField(tf);
        }
        
        p.add(createLabel("Transport:")); p.add(txtTransport);
        p.add(createLabel("Logement:")); p.add(txtLogement);
        p.add(createLabel("Repas:")); p.add(txtRepas);
        p.add(createLabel("Congé:")); p.add(txtCongé);
        
        return p;
    }
    
    private JPanel createPeriodServicePanel() {
        JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        comboAnnee = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 100; i <= currentYear + 100; i++) comboAnnee.addItem("" + i);
        
        comboMois = new JComboBox<>(new String[]{"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                                                   "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"});
        
        comboDirection = new JComboBox<>(getDirections());
        comboDivision = new JComboBox<>(getDivisions());
        comboBureau = new JComboBox<>(getBureaux());
        comboCellule = new JComboBox<>(getCellules());
        
        p.add(createLabel("Année:")); p.add(comboAnnee);
        p.add(createLabel("Mois:")); p.add(comboMois);
        p.add(createLabel("Direction:")); p.add(comboDirection);
        p.add(createLabel("Division:")); p.add(comboDivision);
        p.add(createLabel("Bureau:")); p.add(comboBureau);
        p.add(createLabel("Cellule:")); p.add(comboCellule);
        
        return p;
    }
    
    private JPanel createDeductionsPanel() {
        JPanel p = new JPanel(new GridLayout(6, 2, 5, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtIPR = new JTextField("0"); txtIPR.setEditable(false);
        txtCNSS = new JTextField("0"); txtCNSS.setEditable(false);
        txtINPP = new JTextField("0"); txtINPP.setEditable(false);
        txtTotalPrimes = new JTextField("0"); txtTotalPrimes.setEditable(false);
        txtTotalRetenues = new JTextField("0"); txtTotalRetenues.setEditable(false);
        txtBrut = new JTextField("0"); txtBrut.setEditable(false);
        txtNet = new JTextField("0"); txtNet.setEditable(false);
        
        for(JTextField tf : new JTextField[]{txtIPR, txtCNSS, txtINPP, txtTotalPrimes, txtTotalRetenues, txtBrut, txtNet}) {
            styleTextField(tf);
        }
        
        p.add(createLabel("IPR (3%):")); p.add(txtIPR);
        p.add(createLabel("CNSS (4%):")); p.add(txtCNSS);
        p.add(createLabel("INPP:")); p.add(txtINPP);
        p.add(createLabel("Total Primes:")); p.add(txtTotalPrimes);
        p.add(createLabel("Total Retenues:")); p.add(txtTotalRetenues);
        p.add(createLabel("Salaire Brut (SB):")); p.add(txtBrut);
        p.add(createLabel("Salaire Net:")); p.add(txtNet);
        
        return p;
    }
    
    private void calculateSalary() {
        try {
            double base = Double.parseDouble(txtSalaireBase.getText());
            double primeRend = Double.parseDouble(txtPrimeRendement.getText());
            double primeAncien = Double.parseDouble(txtPrimeAnciennete.getText());
            double primeRisque = Double.parseDouble(txtPrimeRisque.getText());
            double heuresSup = Double.parseDouble(txtHeuresSup.getText());
            double particip = Double.parseDouble(txtParticipationBenef.getText());
            double gratif13 = Double.parseDouble(txtGratification13.getText());
            
            double transport = Double.parseDouble(txtTransport.getText());
            double logement = Double.parseDouble(txtLogement.getText());
            double repas = Double.parseDouble(txtRepas.getText());
            double conge = Double.parseDouble(txtCongé.getText());
            
            // Calculate totals
            double totalPrimes = primeRend + primeAncien + primeRisque + heuresSup + particip + gratif13;
            double totalIndemnites = transport + logement + repas + conge;
            
            // SB = Base + Primes + Indemnités
            double sb = base + totalPrimes + totalIndemnites;
            
            // Retenues
            double ipr = sb * 0.03;
            double cnss = sb * 0.04;
            double inpp = 0; // Not specified in document, default to 0
            double totalRetenues = ipr + cnss + inpp;
            
            double net = sb - totalRetenues;
            
            // Display results
            txtTotalPrimes.setText(String.format("%.2f", totalPrimes));
            txtTotalRetenues.setText(String.format("%.2f", totalRetenues));
            txtBrut.setText(String.format("%.2f", sb));
            txtIPR.setText(String.format("%.2f", ipr));
            txtCNSS.setText(String.format("%.2f", cnss));
            txtINPP.setText(String.format("%.2f", inpp));
            txtNet.setText(String.format("%.2f", net));
            
            JOptionPane.showMessageDialog(this, "Calcul effectué avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des chiffres valides dans tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetForm() {
        for(JTextField tf : new JTextField[]{txtMatricule, txtPrenom, txtNom, txtPostnom, txtLieuNaissance,
                                              txtPereNoms, txtMereNoms, txtAdressePhysique, txtAdresseEmail, txtTelephone,
                                              txtVillage, txtTerritoire, txtDistrict, txtProvince,
                                              txtUniversitaires, txtSecondaires, txtAutresFormations,
                                              txtSalaireBase, txtPrimeRendement, txtPrimeAnciennete, txtPrimeRisque,
                                              txtHeuresSup, txtParticipationBenef, txtGratification13,
                                              txtTransport, txtLogement, txtRepas, txtCongé,
                                              txtIPR, txtCNSS, txtINPP, txtTotalPrimes, txtTotalRetenues, txtBrut, txtNet}) {
            tf.setText("0");
        }
        // Clear photo
        photoLabel.setIcon(null);
        photoLabel.setText("Cliquez pour\najouter une photo");
        selectedPhotoFile = null;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 11));
        label.setForeground(DARK_GRAY);
        return label;
    }
    
    private void styleTextField(JTextField txt) {
        txt.setFont(new Font("Arial", Font.PLAIN, 11));
        txt.setBorder(BorderFactory.createLineBorder(LIGHT_GRAY, 1));
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btn.setMargin(new Insets(8, 12, 8, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private String[] get50Countries() {
        return new String[]{"Afghanistan", "Afrique du Sud", "Albanie", "Algérie", "Allemagne", "Andorre", "Angola", "Anguilla",
            "Antarctique", "Antigua-et-Barbuda", "Arabie Saoudite", "Argentine", "Arménie", "Aruba", "Australie",
            "Autriche", "Azerbaïdjan", "Bahamas", "Bahreïn", "Bangladesh", "Barbade", "Belgique", "Belize", "Bénin",
            "Bermudes", "Bhoutan", "Biélorussie", "Birmanie", "Birmanie", "Birmanie", "Birmanie", "Birmanie", "Birmanie",
            "Bolivie", "Bosnie-Herzégovine", "Botswana", "Brésil", "Brunei", "Bulgarie", "Burkina Faso", "Burundi",
            "Cambodge", "Cameroun", "Canada", "Cape-Vert", "Chili", "Chine", "Colombie", "Comores", "Corée du Nord", "Corée du Sud"};
    }
    
    private String[] getDirections() {
        return new String[]{"Direction Générale", "Direction RH", "Direction Finance", "Direction IT", "Direction Opérations"};
    }
    
    private String[] getDivisions() {
        return new String[]{"Division 1", "Division 2", "Division 3", "Division 4", "Division 5"};
    }
    
    private String[] getBureaux() {
        return new String[]{"Bureau A", "Bureau B", "Bureau C", "Bureau D"};
    }
    
    private String[] getCellules() {
        return new String[]{"Cellule 1", "Cellule 2", "Cellule 3", "Cellule 4"};
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
        btnPrevYear.setFont(new Font("Arial", Font.BOLD, 12));
        btnPrevYear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton btnNextYear = new JButton(">>");
        btnNextYear.setBackground(DARK_GRAY);
        btnNextYear.setForeground(Color.WHITE);
        btnNextYear.setFocusPainted(false);
        btnNextYear.setFont(new Font("Arial", Font.BOLD, 12));
        btnNextYear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
        
        // Refresh calendar display
        Runnable refreshCalendar = new Runnable() {
            public void run() {
                calendarPanel.removeAll();
                monthYearLabel.setText(getMonthName(month[0]) + " " + year[0]);
                
                // Days header
                String[] dayNames = {"L", "M", "M", "J", "V", "S", "D"};
                for (String day : dayNames) {
                    JLabel dayLabel = new JLabel(day);
                    dayLabel.setFont(new Font("Arial", Font.BOLD, 12));
                    dayLabel.setHorizontalAlignment(JLabel.CENTER);
                    dayLabel.setForeground(DARK_GRAY);
                    calendarPanel.add(dayLabel);
                }
                
                // Calculate first day of month and number of days
                cal.set(year[0], month[0], 1);
                int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
                if (firstDayOfWeek == 0) firstDayOfWeek = 6; else firstDayOfWeek--;
                
                int maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                
                // Empty cells before first day
                for (int i = 0; i < firstDayOfWeek; i++) {
                    calendarPanel.add(new JLabel(""));
                }
                
                // Days in month
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
            }
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

    public static void main(String[] args) {
        new CalculSalaireAgent();
    }
}
