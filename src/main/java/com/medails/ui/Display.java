package com.medails.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.toedter.calendar.JDateChooser;

    /*********************************************************** 
                             MISE EN FORME 
    ***********************************************************/

public class Display
{    
    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Display()
    {   
        fenPosition();
        pan1Position();
        pan2Position();
        pan3Position();
        pan4Position();
    }

    /************************************************************ 
                            VARIABLES
    *************************************************************/

    /************************* Variables d'instance **************************/
    // Principaux utilitaires graphiques
    public JFrame fen;
    public JPanel pan1;
    public JPanel pan2;
    public JPanel pan3;
    public JPanel pan4;
    public JScrollPane scroll1;
    public JScrollPane scroll2;
    public JScrollPane scroll3;
    public JScrollPane scroll4;
    public JTabbedPane tabMain;
    public JTabbedPane tabGraph;    
    public JTabbedPane tabDeduction;    
    public JTabbedPane tabChomage;    
    public GridBagConstraints gbc;

    /*********** Onglet 1 ***************/
    public JComboBox<String>     boxYears;               { boxYears             = new JComboBox<String> ();}                                // A1 - Année
    public JComboBox<String>     boxMonths;              { boxMonths            = new JComboBox<String> ();}                                // A2 - Mois
    public JDateChooser          datePay;                { datePay              = new JDateChooser      ();}                                // A3 - Date de paiement
    public JTextField            txtDays;                { txtDays              = new JTextField        ();}                                // B1 - Jours travaillés
    public JTextField            txtTJM;                 { txtTJM               = new JTextField        ();}                                // B2 - TJM
    public JButton               butTVA;                 { butTVA               = new JButton           ("Calculer");}                 // B3 - Calcule TVA (BP)
    public JTextField            txtTTC;                 { txtTTC               = new JTextField        ();}                                // C1 - TTC
    public JTextField            txtHT;                  { txtHT                = new JTextField        ();}                                // C2 - HT
    public JTextField            txtTVA;                 { txtTVA               = new JTextField        ();}                                // C3 - TVA  
    public JTextField            txtTaxe;                { txtTaxe              = new JTextField        ();}                                // D1 - Taxe URSSAF
    public JTextField            txtBenefit;             { txtBenefit           = new JTextField        ();}                                // D2 - Bénéfices
    public JButton               butOpenFacture;         { butOpenFacture       = new JButton           ("Ouvrir");}                   // E1 - Ouvrir facture
    public JButton               butSearchFacture;       { butSearchFacture     = new JButton           ("Parcourir");}                // E2 - Parcourir facture
    public JComboBox<String>     boxRep1;                { boxRep1              = new JComboBox<String> ();}                                // F1 - Barre de recherche Facture (Réperoitre)
    public JComboBox<String>     boxPDF1;                { boxPDF1              = new JComboBox<String> ();}                                // G1 - Barre de recherche Facture (Nom du PDF) 
    public JButton               butOpenDecla;           { butOpenDecla         = new JButton           ("Ouvrir");}                   // H1 - Ouvrir déclaration
    public JButton               butSearchDecla;         { butSearchDecla       = new JButton           ("Parcourir");}                // H2 - Parcourir déclaration
    public JComboBox<String>     boxRep2;                { boxRep2              = new JComboBox<String> ();}                                // I1 - Barre de recherche Déclaration (Réperoitre)
    public JComboBox<String>     boxPDF2;                { boxPDF2              = new JComboBox<String> ();}                                // J1 - Barre de recherche Déclaration (Nom du PDF)
    public JButton 				 butDelete;				 { butDelete			= new JButton			("Supprimer");}			    // K1 - Supprimer
    public JButton               butSave;                { butSave              = new JButton           ("Enregistrer");}              // K2 - Enregistrer
    public JButton               butReset1;              { butReset1            = new JButton           ("RAZ");}                      // K3 - RAZ

    /*********** Onglet 2 ***************/
    public final int MINRANGE_DECADEPAN2     = 30000;     public final int MAXRANGE_DECADEPAN2     = 100000;
    public final int MINRANGE_YEARMONTHPAN2  = 10000;     public final int MAXRANGE_YEARMONTHPAN2  = 20000; 
    public JSlider               sliDecadePan2;          { sliDecadePan2        = new JSlider           (JSlider.HORIZONTAL, MINRANGE_DECADEPAN2, MAXRANGE_DECADEPAN2, MINRANGE_DECADEPAN2);}   
    public JSlider               sliYearMonthPan2;       { sliYearMonthPan2     = new JSlider           (JSlider.HORIZONTAL, MINRANGE_YEARMONTHPAN2, MAXRANGE_YEARMONTHPAN2, MINRANGE_YEARMONTHPAN2);}  
    public JToggleButton         togTotal;               { togTotal             = new JToggleButton     ();}                                // A1 - Déduction TVA 
    public JComboBox<String>     boxYearsTotal;          { boxYearsTotal        = new JComboBox<String> ();}                                // A2 - Choix Année          
    public JCheckBox             cckTTCPan2;             { cckTTCPan2           = new JCheckBox         ("", true);}    		// B1 - TTC
    public JCheckBox             cckTVAPan2;             { cckTVAPan2           = new JCheckBox         ("", true);}    		// B2 - TVA
    public JCheckBox             cckHTPan2;              { cckHTPan2            = new JCheckBox         ("", true);}    		// B3 - HT
    public JCheckBox             cckTaxePan2  ;          { cckTaxePan2          = new JCheckBox         ("", true);}    		// B4 - Urssaf
    public JCheckBox             cckBenefitPan2;         { cckBenefitPan2       = new JCheckBox         ("", true);}    		// B5 - Bénéfices

    /*********** Onglet 3 ***************/ 
    public final int MINRANGE_DECADEPAN3     = 10000;     public final int MAXRANGE_DECADEPAN3     = 25000;
    public final int MINRANGE_YEARMONTHPAN3  = 5000;      public final int MAXRANGE_YEARMONTHPAN3  = 10000; 
    public JSlider               sliDecadePan3;          { sliDecadePan3        = new JSlider           (JSlider.HORIZONTAL, MINRANGE_DECADEPAN3, MAXRANGE_DECADEPAN3, MINRANGE_DECADEPAN3);}   
    public JSlider               sliYearMonthPan3;       { sliYearMonthPan3     = new JSlider           (JSlider.HORIZONTAL, MINRANGE_YEARMONTHPAN3, MAXRANGE_YEARMONTHPAN3, MINRANGE_YEARMONTHPAN3);}                    
    public JComboBox<String>     boxYearsDeduction;      { boxYearsDeduction    = new JComboBox<String> ();}                                // A1 - Choix Année
    public JDateChooser          dateDeduction;          { dateDeduction        = new JDateChooser      ();}                                // B1 - Date d'achat 
    public JTextField            txtTTCPan3;             { txtTTCPan3           = new JTextField        ();}                                // C1 - TTC
    public JTextField            txtHTPan3;              { txtHTPan3            = new JTextField        ();}                                // C2 - HT 
    public JTextField            txtTVAPan3;             { txtTVAPan3           = new JTextField        ();}                                // C3 - TVA
    public JButton               butOpenDeduction;       { butOpenDeduction     = new JButton           ("Ouvrir");}                   // D1 - Ouvrir Déduction
    public JButton               butSearchDeduction;     { butSearchDeduction   = new JButton           ("Parcourir");}                // D2 - Parcourir Déduction
    public JComboBox<String>     boxRepDeduction;        { boxRepDeduction      = new JComboBox<String> ();}                                // E1 - Barre de recherche Déduction (Réperoitre)
    public JComboBox<String>     boxPDFDeduction;        { boxPDFDeduction      = new JComboBox<String> ();}                                // F1 - Barre de recherche Déduction (Nom du PDF)
    public JButton				 butDeleteDeduction;     { butDeleteDeduction   = new JButton		    ("Supprimer");}			    // G1 - Supprimer
    public JButton               butSaveDeduction;       { butSaveDeduction     = new JButton           ("Enregistrer");}              // G2 - Enregistrer
    public JButton               butReset3;              { butReset3            = new JButton           ("RAZ");}                      // G3 - RAZ

    /*********** Onglet 4 ***************/
    public final int MINRANGE_DECADEPAN4     = 18000;     public final int MAXRANGE_DECADEPAN4     = 30000;
    public final int MINRANGE_YEARMONTHPAN4  = 2000;      public final int MAXRANGE_YEARMONTHPAN4  = 5000; 
    public JSlider               sliDecadePan4;          { sliDecadePan4        = new JSlider           (JSlider.HORIZONTAL, MINRANGE_DECADEPAN4, MAXRANGE_DECADEPAN4, MINRANGE_DECADEPAN4);}   
    public JSlider               sliYearMonthPan4;       { sliYearMonthPan4     = new JSlider           (JSlider.HORIZONTAL, MINRANGE_YEARMONTHPAN4, MAXRANGE_YEARMONTHPAN4, MINRANGE_YEARMONTHPAN4);}                    
    public JComboBox<String>     boxYearsChomage;        { boxYearsChomage      = new JComboBox<String> ();}                                  // A1 - Choix Année
    public JDateChooser          dateChomage;            { dateChomage          = new JDateChooser      ();}                                  // B1 - Date versement 
    public JComboBox<String>     boxMonthsChomage;       { boxMonthsChomage     = new JComboBox<String> ();}                                  // B2 - Mois actualisation
    public JTextField            txtDayChomage;          { txtDayChomage        = new JTextField        ();}                                  // C1 - Nombre de jours dans le mois
    public JTextField            txtQChomage;            { txtQChomage          = new JTextField        ();}                                  // C2 - Quotient de l'ARE 
    public JTextField            txtAREChomage;          { txtAREChomage        = new JTextField        ();}                                  // C3 - Montant de l'ARE 
    public JButton               butOpenChomage;         { butOpenChomage       = new JButton           ("Ouvrir");}                     // D1 - Ouvrir Chomage
    public JButton               butSearchChomage;       { butSearchChomage     = new JButton           ("Parcourir");}                  // D2 - Parcourir Chomage
    public JComboBox<String>     boxRepChomage;          { boxRepChomage        = new JComboBox<String> ();}                                  // E1 - Barre de recherche Chomage (Réperoitre)
    public JComboBox<String>     boxPDFChomage;          { boxPDFChomage        = new JComboBox<String> ();}                                  // F1 - Barre de recherche Chomage (Nom du PDF)
    public JButton				 butDeleteChomage;       { butDeleteChomage     = new JButton		    ("Supprimer");}			      // G1 - Supprimer
    public JButton               butSaveChomage;         { butSaveChomage       = new JButton           ("Enregistrer");}                // G2 - Enregistrer
    public JButton               butReset4;              { butReset4            = new JButton           ("RAZ");}                        // G3 - RAZ

    // Année et mois
    private String years[] = {"", "2024", "2025", "2026", "2027", "2028"};
    private String yearsChomage[] = {"", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028"}; 

    private String months[] = {"", "Janvier", "Février", "Mars", "Avril", 
                                      "Mai", "Juin", "Juillet", "Août", "Septembre", 
                                      "Octobre", "Novembre", "Décembre"};
        
                                      
    /************************************************************ 
                              METHODES
    *************************************************************/

    public void fenPosition()
    {
        // Création Fenetre/Panel
        fen = new JFrame();
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        pan4 = new JPanel();

        // Configuration Fenetre/Panel
        fen.setTitle("Gestionnaie de facture");
        fen.setSize(410, 640);
        fen.setMinimumSize(new Dimension(410, 640));
        fen.setLocationRelativeTo(null);
        fen.setResizable(true);
        pan1.setBackground(Color.LIGHT_GRAY);
        pan2.setBackground(Color.LIGHT_GRAY);
        pan3.setBackground(Color.LIGHT_GRAY);
        pan4.setBackground(Color.LIGHT_GRAY);
        pan1.setLayout(new GridBagLayout());   
        pan2.setLayout(new GridBagLayout());   
        pan3.setLayout(new GridBagLayout());   
        pan4.setLayout(new GridBagLayout());   

        // Ajout du scroll aux panels
        scroll1 = new JScrollPane(pan1);
        scroll2 = new JScrollPane(pan2);
        scroll3 = new JScrollPane(pan3);
        scroll4 = new JScrollPane(pan4);
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Onglets - utilisation les JScrollPane au lieu des JPanel
        tabMain = new JTabbedPane();
        tabMain.add("Enregistrement", scroll1);
        tabMain.add("Facture", scroll2);
        tabMain.add("Déducttion", scroll3);
        tabMain.add("Chomage", scroll4);
        tabGraph = new JTabbedPane();
        tabDeduction = new JTabbedPane();
        tabChomage = new JTabbedPane();

        // Ajout des onglets dans fenetre
        fen.add(tabMain, BorderLayout.CENTER);

        // Placement des composants
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 0);

        // Fermeture de la fenetre
        fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fen.setVisible(true); 
    }

    /************************** Factorisation **************************/

    private void addComposant (JPanel panel, JComponent component, 
                               int gridx, int gridy, int gridwidth)
    {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        panel.add(component, gbc);
    }

    private JTextField createTextField (int width, int height)
    {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(width, height));
        textField.setEnabled(true);
        return textField;
    }

    private JComboBox<String> createJComboBox (int width, int height)
    {
       JComboBox<String> comboBox = new JComboBox<>();
       comboBox.setPreferredSize(new Dimension(width, height));
       comboBox.setEnabled(true);
       return comboBox;
    }   
    
    // Surcharge 
    private JComboBox<String> createJComboBox (int width, int height, String[] element)
    {
       JComboBox<String> comboBox = new JComboBox<>(element);
       comboBox.setPreferredSize(new Dimension(width, height));
       comboBox.setEnabled(true);
       return comboBox;
    }

    private Font styleFont1 = new Font("Arial", Font.BOLD, 18);
    private Font styleFont2 = new Font("Arial", Font.BOLD, 16);
    
    /*********************************************************** 
                            POSITIONNEMENT 
    ***********************************************************/

    public void pan1Position()
    {
        /************************** Facture **************************/
        JLabel labFacture = new JLabel("<html><u>Facture</u></html>");
        labFacture.setFont(styleFont1);
        addComposant(pan1, labFacture, 0, 1, 1);
        
        // A1 - Années
        JLabel labYears = new JLabel("Année");
        boxYears = createJComboBox(60, 18, years);
        addComposant(pan1, labYears, 0, 2, 1);
        addComposant(pan1, boxYears, 0, 4, 1);
 
        // A2 - Mois
        JLabel labMonths = new JLabel("Mois");
        boxMonths = createJComboBox(100, 18, months);
        addComposant(pan1, labMonths, 1, 2, 1);
        addComposant(pan1, boxMonths, 1, 4, 1);

        // A3 - Date de paiement
        JLabel labPay = new JLabel("Date de Paiement");
        datePay.setPreferredSize(new Dimension(100, 18));
        addComposant(pan1, labPay, 2, 2, 1);
        addComposant(pan1, datePay, 2, 4, 1);
        
        // B1 - Jours travaillés
        JLabel labDays = new JLabel("Jours travaillés");        
        txtDays = createTextField(60, 18);           
        addComposant(pan1, labDays, 0, 6, 1);         
        addComposant(pan1, txtDays, 0, 8, 1);

        // B2 - TJM
        JLabel labTJM = new JLabel("TJM");
        txtTJM = createTextField(60, 18);
        addComposant(pan1, labTJM, 1, 6, 1);
        addComposant(pan1, txtTJM, 1, 8, 1);

        // B3 - Calcule TVA (BP)
        addComposant(pan1, butTVA, 2, 8, 1);

        // C1 - TTC
        JLabel labTTC = new JLabel("TTC");
        txtTTC = createTextField(60, 18);
        addComposant(pan1, labTTC, 0, 10, 1);
        addComposant(pan1, txtTTC, 0, 12, 1);

        // C2 - HT
        JLabel labHT = new JLabel("HT");
        txtHT = createTextField(60, 18);
        addComposant(pan1, labHT, 1, 10, 1);
        addComposant(pan1, txtHT, 1, 12, 1);

        // C3 - Différence TVA
        JLabel labTVA = new JLabel("TVA");
        txtTVA = createTextField(60, 18);
        addComposant(pan1, labTVA, 2, 10, 1);
        addComposant(pan1, txtTVA, 2, 12, 1);
 
        // /************************* URSSAF *************************/
        JLabel labUrssaf = new JLabel("<html><u>URSSAF</u></html>");
        labUrssaf.setFont(styleFont2);
        addComposant(pan1, labUrssaf, 0, 14, 1);
        
        // D1 - Taxe URSSAF
        JLabel labTaxe = new JLabel("Taxes");
        txtTaxe = createTextField(60, 18);
        addComposant(pan1, labTaxe, 0, 16, 1);
        addComposant(pan1, txtTaxe, 0, 18, 1);

        // D2 - Différence Taxe
        JLabel labBenefit = new JLabel("Bénéfices");
        txtBenefit = createTextField(60, 18);
        addComposant(pan1, labBenefit, 1, 16, 1);
        addComposant(pan1, txtBenefit, 1, 18, 1);

        // /************************** Liens **************************/
        JLabel labLiens = new JLabel("<html><u>Liens</u></html>");
        labLiens.setFont(styleFont2);
        addComposant(pan1, labLiens, 0, 20, 1);
        
        // Liens vers facture
        JLabel labLienFacture = new JLabel("Facture");
        addComposant(pan1, labLienFacture, 0, 22, 1);
        
        // E1 - Ouvrir facture
        gbc.insets = new Insets(0, 65, 10, 0);
        addComposant(pan1, butOpenFacture, 1, 22, 1);
    
        // E2 - Parcourir facture
        gbc.insets = new Insets(0, 0, 10, 0);
        addComposant(pan1, butSearchFacture, 2, 22, 1);
        
        // F1 - Barre de recherche Facture (Réperoitre)
        boxRep1 = createJComboBox(330, 18);
        addComposant(pan1, boxRep1, 0, 24, 3);
        
        // G1 - Barre de recherche Facture (Nom du PDF)   
        boxPDF1 = createJComboBox(330, 18);
        addComposant(pan1, boxPDF1, 0, 26, 3);
  
        // Liens vers déclaration
        JLabel labLienDecla = new JLabel("Déclaration");
        addComposant(pan1, labLienDecla, 0, 28, 1);

        // H1 - Ouvrir déclaration
        gbc.insets = new Insets(0, 65, 10, 0);
        addComposant(pan1, butOpenDecla, 1, 28, 1);
        
        // H2 - Parcourir déclaration
        gbc.insets = new Insets(0, 0, 10, 0);
        addComposant(pan1, butSearchDecla, 2, 28, 1);
        
        // I1 - Barre de recherche Déclaration (Réperoitre)
        gbc.insets = new Insets(0, 0, 10, 0);
        boxRep2 = createJComboBox(330, 18);
        addComposant(pan1, boxRep2, 0, 30, 3);
        
        // J1 - Barre de recherche Déclaration (Nom du PDF)
        boxPDF2 = createJComboBox( 330, 18);
        addComposant(pan1, boxPDF2, 0, 32, 3);

        // /************************ Boutons ************************/
        // K1 - Supprimer
        gbc.insets = new Insets(0, 0, 10, 90);
        addComposant(pan1, butDelete, 0, 34, 2);
        
        // K2 - Enregistrer
        gbc.insets = new Insets(0, 0, 10, 55);
        addComposant(pan1, butSave, 1, 34, 2);

        // K3 - RAZ
        gbc.insets = new Insets(0, 0, 10, 0);
        addComposant(pan1, butReset1, 2, 34, 2);
    }

    /*********************************************************** 
                              PANEL 2 
    ***********************************************************/

    public void pan2Position()
    { 
        /************************* Facture **************************/

        // XX - Slide
        gbc.insets = new Insets(10, 0, 10, 0);
        JLabel labSliDecadePan2 = new JLabel("Décénie : ");
        JLabel labSliYearMonthPan2 = new JLabel("Annuel/Mensuel : ");
        addComposant(pan2, labSliDecadePan2, 0, 0, 1);
        addComposant(pan2, labSliYearMonthPan2, 0, 1, 1);
        addComposant(pan2, sliDecadePan2, 1, 0, 4); 
        addComposant(pan2, sliYearMonthPan2, 1, 1, 4); 

        // A1 Déduction TVA
        gbc.insets = new Insets(400, 85, 0, 0);
        addComposant(pan2, togTotal, 2, 2, 2);  

        // A2 - Choix Année
        gbc.insets = new Insets(400, 30, 0, 0);
        boxYearsTotal = createJComboBox(60, 18, years);
        addComposant(pan2, boxYearsTotal, 4, 2, 2);

        // B1 - TTC
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel labcckTTCPan2 = new JLabel("TTC");
        cckTTCPan2.setBackground(Color.LIGHT_GRAY);
        addComposant(pan2, labcckTTCPan2, 0, 4, 1);
        addComposant(pan2, cckTTCPan2, 0, 6, 1);

        // B2 - TVA
        gbc.insets = new Insets(0, 0, 10, 15);
        JLabel labcckTVAPan2 = new JLabel("TVA");
        cckTVAPan2.setBackground(Color.LIGHT_GRAY);
        addComposant(pan2, labcckTVAPan2, 1, 4, 1);
        addComposant(pan2, cckTVAPan2, 1, 6, 1);

        // B3 - HT
        gbc.insets = new Insets(0, 0, 10, 20);
        JLabel labcckHTPan2 = new JLabel("HT");
        cckHTPan2.setBackground(Color.LIGHT_GRAY);
        addComposant(pan2, labcckHTPan2, 2, 4, 1);
        addComposant(pan2, cckHTPan2, 2, 6, 1);

        // B4 - URSSAF
        gbc.insets = new Insets(0, 0, 10, 25);
        JLabel labcckTaxePan2 = new JLabel("URSSAF");
        cckTaxePan2.setBackground(Color.LIGHT_GRAY);
        addComposant(pan2, labcckTaxePan2, 3, 4, 1);
        addComposant(pan2, cckTaxePan2, 3, 6, 1);

        // B5 - Bénéfices
        gbc.insets = new Insets(0, 0, 10, 30);
        JLabel labcckBenefitPan2 = new JLabel("Bénéfices");
        cckBenefitPan2.setBackground(Color.LIGHT_GRAY);
        addComposant(pan2, labcckBenefitPan2, 4, 4, 1);
        addComposant(pan2, cckBenefitPan2, 4, 6, 1);
    }

    /*********************************************************** 
                              PANEL 3 
    ***********************************************************/

    public void pan3Position()
    {
        /********************** Déduction ***********************/
    
        // XX - Slide
        gbc.insets = new Insets(10, 0, 10, 0);
        JLabel labSliDecadePan3 = new JLabel("Décénie : ");
        JLabel labSliYearMonthPan3 = new JLabel("Annuel/Mensuel : ");
        addComposant(pan3, labSliDecadePan3, 0, 0, 1);
        addComposant(pan3, labSliYearMonthPan3, 0, 1, 1);
        addComposant(pan3, sliDecadePan3, 1, 0, 3); 
        addComposant(pan3, sliYearMonthPan3, 1, 1, 3); 

        // A1 - Choix Année
        gbc.insets = new Insets(400, 30, 0, 0);
        boxYearsDeduction = createJComboBox(60, 18, years);
        addComposant(pan3, boxYearsDeduction, 2, 4, 1); 

        // B1 - Date d'achat
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel labDateDeduction = new JLabel("Date d'achat");
        dateDeduction.setPreferredSize(new Dimension(100, 18));
        addComposant(pan3, labDateDeduction, 0, 6, 1);
        addComposant(pan3, dateDeduction, 0, 8, 1);

        // C1 - TTC
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel labTTCPan3 = new JLabel("TTC");
        txtTTCPan3 = createTextField(60, 18); 
        addComposant(pan3, labTTCPan3, 0, 10, 1);
        addComposant(pan3, txtTTCPan3, 0, 12, 1);

        // C2 - HT
        JLabel labHTPan3 = new JLabel("HT");
        txtHTPan3 = createTextField(60, 18); 
        addComposant(pan3, labHTPan3, 1, 10, 1);
        addComposant(pan3, txtHTPan3, 1, 12, 1);

        // C3 - TVA
        JLabel labTVAPan3 = new JLabel("TVA");
        txtTVAPan3 = createTextField(60, 18); 
        addComposant(pan3, labTVAPan3, 2, 10, 1);
        addComposant(pan3, txtTVAPan3, 2, 12, 1);

        JLabel labDeduction = new JLabel("<html><u>Déduction</u></html>");
        labDeduction.setFont(styleFont2);
        addComposant(pan3, labDeduction, 0, 14, 1);

        // D1 - Ouvrir Déduction
        gbc.insets = new Insets(0, 65, 10, 10);
        addComposant(pan3, butOpenDeduction, 1, 16, 1);

        // D2 - Parcourir Déduction
        gbc.insets = new Insets(0, 0, 10, 0);
        addComposant(pan3, butSearchDeduction, 2, 16, 1);

        // E1 - Barre de recherche Déduction (Réperoitre)
        boxRepDeduction = createJComboBox(330, 18);
        addComposant(pan3, boxRepDeduction, 0, 18, 3);

        // F1 - Barre de recherche Déduction (Nom du PDF)
        boxPDFDeduction = createJComboBox(330, 18);
        addComposant(pan3, boxPDFDeduction, 0, 20, 3);
    
        // /************************ Boutons ************************/
        // G1 - Supprimer
        gbc.insets = new Insets(0, 0, 10, 110);
        addComposant(pan3, butDeleteDeduction, 0, 22, 2);
        
        // G2 - Enregistrer
        gbc.insets = new Insets(0, 0, 10, 55);
        addComposant(pan3, butSaveDeduction, 1, 22, 2);

        // G3 - RAZ
        gbc.insets = new Insets(0, 0, 10, 0);
        addComposant(pan3, butReset3, 2, 22, 2);
    }


    /*********************************************************** 
                              PANEL 4 
    ***********************************************************/

    public void pan4Position()
    {
        /********************** Chomage ***********************/
    
        // XX - Slide
        gbc.insets = new Insets(10, 0, 10, 0);
        JLabel labSliDecadePan4 = new JLabel("Décénie : ");
        JLabel labSliYearMonthPan4 = new JLabel("Annuel/Mensuel : ");
        addComposant(pan4, labSliDecadePan4, 0, 0, 1);
        addComposant(pan4, labSliYearMonthPan4, 0, 1, 1);
        addComposant(pan4, sliDecadePan4, 1, 0, 3); 
        addComposant(pan4, sliYearMonthPan4, 1, 1, 3); 

        // A1 - Choix Année
        gbc.insets = new Insets(400, 30, 0, 0);
        boxYearsChomage = createJComboBox(60, 18, yearsChomage);
        addComposant(pan4, boxYearsChomage, 2, 4, 1); 

        // B1 - Date versement
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel labDateChomage = new JLabel("Date versement");
        dateChomage.setPreferredSize(new Dimension(100, 18));
        addComposant(pan4, labDateChomage, 0, 6, 1);
        addComposant(pan4, dateChomage, 0, 8, 1);

        // B2 - Mois actualisation
        JLabel labMonthsChomage = new JLabel("Mois actualisation");
        boxMonthsChomage = createJComboBox(100, 18, months);
        addComposant(pan4, labMonthsChomage, 1, 6, 1);
        addComposant(pan4, boxMonthsChomage, 1, 8, 1);

        // C1 - Nombre de jours dans le mois
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel labDaysPan4 = new JLabel("Jours");
        txtDayChomage = createTextField(60, 18); 
        addComposant(pan4, labDaysPan4, 0, 10, 1);
        addComposant(pan4, txtDayChomage, 0, 12, 1);

        // C2 - Quotient de l'ARE 
        JLabel labQPan4 = new JLabel("%");
        txtQChomage = createTextField(60, 18); 
        addComposant(pan4, labQPan4, 1, 10, 1);
        addComposant(pan4, txtQChomage, 1, 12, 1);

        // C3 - Montant de l'ARE 
        JLabel labAREPan4 = new JLabel("Montant");
        txtAREChomage = createTextField(60, 18); 
        addComposant(pan4, labAREPan4, 2, 10, 1);
        addComposant(pan4, txtAREChomage, 2, 12, 1);

        JLabel labChomage = new JLabel("<html><u>Chomage</u></html>");
        labChomage.setFont(styleFont2);
        addComposant(pan4, labChomage, 0, 14, 1);

        // D1 - Ouvrir Chomage
        gbc.insets = new Insets(0, 65, 10, 10);
        addComposant(pan4, butOpenChomage, 1, 16, 1);

        // D2 - Parcourir Chomage
        gbc.insets = new Insets(0, 0, 10, 0);
        addComposant(pan4, butSearchChomage, 2, 16, 1);

        // E1 - Barre de recherche Chomage (Réperoitre)
        boxRepChomage = createJComboBox(330, 18);
        addComposant(pan4, boxRepChomage, 0, 18, 3);

        // F1 - Barre de recherche Chomage (Nom du PDF)
        boxPDFChomage = createJComboBox(330, 18);
        addComposant(pan4, boxPDFChomage, 0, 20, 3);
    
        // /************************ Boutons ************************/
        // G1 - Supprimer
        gbc.insets = new Insets(0, 0, 10, 110);
        addComposant(pan4, butDeleteChomage, 0, 22, 2);
        
        // G2 - Enregistrer
        gbc.insets = new Insets(0, 0, 10, 55);
        addComposant(pan4, butSaveChomage, 1, 22, 2);

        // G3 - RAZ
        gbc.insets = new Insets(0, 0, 10, 0);
        addComposant(pan4, butReset4, 2, 22, 2);
    }
}
                        