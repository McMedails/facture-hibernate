package com.medails.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import com.medails.ui.Display;
import com.medails.ui.Graphic;

import com.medails.entity.Deduction;
import com.medails.service.Generic;

    /************************************************************ 
                        TRAITEMENT DE DONNEES
    *************************************************************/

public class Treatment3
{
    /************* Déclarations Classes ****************/
    private final Display dp;
    private final Graphic gr;
    private final Generic gn;

    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Treatment3(Display dp, Graphic gr, Generic gn)
    {   
        this.dp = dp;
        this.gr = gr;   
        this.gn = gn;  

        /*********** Appels Méthodes ***************/
        actionJElements();
        calculListener();
        graphDecenal();
        graphYearMonth();   
        gr.updateDatasets(graphYearMonth, gr.GRAPHMONTHS, gr.SHORTCATEGORIES, gr.dataYearsPan3, gr.dataMonthsPan3);                     
    }


    /************************************************************ 
                            VARIABLES
    *************************************************************/
    
    /************************* Variables de classe **************************/
    // Données pour création graphique
    public static Double[][][] graphDecenal = new Double[5][5][5]; 
    public static Double[][][] graphYearMonth = new Double[12][12][12];      

    /************************* Variables d'instance **************************/
    // Répertoire Facture 
    private final String DIRECTORY_DEDUCTION = "M://Multimédia/Bureau/Social/Social - Pc Bureau/01 - Professionnelle/Achats";

    /************************************************************ 
                              METHODES
    *************************************************************/

    private void actionJElements()
    {
        /*********** Internes ***************/
        dp.butSaveDeduction        .addActionListener (e -> saveDeductionDataListener());
        dp.txtTTCPan3              .addActionListener (e -> calculListener());
        dp.boxYearsDeduction       .addActionListener (e -> graphYearMonth());
        dp.butReset3               .addActionListener (e -> clearListener()); 

        /*********** Partie Graphique ***************/
        dp.sliDecadePan3           .addChangeListener (e -> graphDecenal());
        dp.sliYearMonthPan3        .addChangeListener (e -> graphYearMonth());

        /*********** Génériques ***************/
        dp.butOpenDeduction        .addActionListener (e -> gn.openPDF(dp.boxRepDeduction, dp.boxPDFDeduction));
        dp.butSearchDeduction      .addActionListener (e -> gn.searchDirectory(dp.boxRepDeduction, dp.boxPDFDeduction, DIRECTORY_DEDUCTION));
        dp.butDeleteDeduction      .addActionListener (e -> gn.deleteInBDD(dp.boxPDFDeduction, gn::findByNameDeduction, gn::deleteDeduction)); 
                                 gn.popupListener (dp.boxRepDeduction, dp.boxPDFDeduction, () -> gn.getDirectoryRepDeduction(),
                                                                                           () -> gn.getPDFNameDeduction());
         

        // Solution Lambda pour mettre à jour les champs
        gn.boxPDFListener(dp.boxPDFDeduction,   f -> {},  // Lambda pour Facture (inutile ici)
                                                d ->      // Lambda pour Deduction
        {
            int         annee  =  d.getDeductionAnnee();
            String      mois   =  d.getDeductionMois();
            int         jour   =  d.getDeductionJour();
            dp.dateDeduction.setDate(Date.from(LocalDate.of(annee, gn.convertMonth(mois), jour)
                                                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));

            dp.txtTTCPan3   .setText     (String.valueOf    (d.getTTC()));
            dp.txtHTPan3    .setText     (String.valueOf    (d.getHT()));
            dp.txtTVAPan3   .setText     (String.valueOf    (d.getTVA())); 
        },
                                                c -> {}  // Lambda pour Chomage (inutile ici)
        );
    }


    /*********************************************************** 
                              PANEL 3 
    ***********************************************************/

    // Calcule HT/TVA
    private void calculListener()
    {
        // Vérification cellule non-vide
        if (!dp.txtTTCPan3.getText().isEmpty())
        {
            try
            {
                double TTC = Double.parseDouble(dp.txtTTCPan3.getText()); 
                double HT  = TTC / 1.2;
                double TVA = TTC - HT;
                                
                // Report -> TTC
                String repportTTC = String.format(Locale.US, "%.2f", TTC);
                dp.txtTTCPan3.setText(repportTTC);

                // Report -> HT
                String repportHT = String.format(Locale.US, "%.2f",  HT);
                dp.txtHTPan3.setText(repportHT);
                
                // Report -> TVA
                String repportTVA = String.format(Locale.US, "%.2f", TVA);
                dp.txtTVAPan3.setText(repportTVA);
            }
            catch (NumberFormatException e)
            {
                // Si le text n'est pas un nombre valide
                dp.txtTTCPan3.setText("");
                dp.txtHTPan3.setText("");
                dp.txtTVAPan3.setText("");
            }
        }
        else
        {
            // Efface si le champ TTC est vide
            dp.txtHTPan3.setText("");
            dp.txtTVAPan3.setText("");           
        }         
    }

    /*********************************************************** 
                           Onglet Décénie
    ***********************************************************/

    public void graphDecenal()
    {
        // Mise à jour des graphiques avec la nouvelle échelle
        gn.slideRange(gr.chartDecadePan3, dp.sliDecadePan3); 

        // Réinitialise les datasets du graphique
        gr.dataDecadePan3.clear();

        // Récupération de toutes les déductions
        List<Deduction> deductions = gn.getAllDeduction();

        // Map pour cumuler TTC, TVA et HT par année
        Map<String, double[]> cumulParAnnee = new HashMap<>();

        for (Deduction deduction : deductions)
        {
            String currentYear = String.valueOf(deduction.getDeductionAnnee());
            double currentTTC  = deduction.getTTC();
            double currentTVA  = deduction.getTVA();
            double currentHT   = deduction.getHT();

            // Si l'année n'existe pas encore dans la map, on initialise le tableau [TTC, TVA, HT]
            cumulParAnnee.putIfAbsent(currentYear, new double[3]);

            double[] valeurs = cumulParAnnee.get(currentYear);
            valeurs[0] += currentTTC;
            valeurs[1] += currentTVA;
            valeurs[2] += currentHT;
        }

        // Injection dans le tableau graphDecenal
        for (int ii = 0; ii < gr.GRAPHYEARS.length; ii++)
        {
            String annee = gr.GRAPHYEARS[ii];
            double[] valeurs = cumulParAnnee.getOrDefault(annee, new double[] {0.0, 0.0, 0.0});

            graphDecenal[ii][ii][0] = valeurs[0]; // TTC
            graphDecenal[ii][ii][1] = valeurs[1]; // TVA
            graphDecenal[ii][ii][2] = valeurs[2]; // HT
        }

        // Mise à jour du graphique avec les nouvelles données
        gr.updateDatasets(graphDecenal, gr.GRAPHYEARS, gr.SHORTCATEGORIES, gr.dataDecadePan3); 
    }

    /*********************************************************** 
                    Onglet Annuel / Mensuel
    ***********************************************************/

    public void graphYearMonth()
    {
        // Mise à jour des graphiques avec la nouvelle échelle
        gn.slideRange(gr.chartYearsPan3, dp.sliYearMonthPan3);
        gn.slideRange(gr.chartMonthsPan3, dp.sliYearMonthPan3);  

        // Initialisation des données graphiques
        gr.dataYearsPan3.clear();
        gr.dataMonthsPan3.clear();

        // Initialisation des données
        String      lastYear        = null;
        boolean     refreshYear     = false;

        // Récupère l'année sélectionnée dans la ComboBox
        String selectedYear = dp.boxYearsDeduction.getSelectedItem().toString();

        // Récupère les données de la DB
        List<Deduction> deductions = gn.getAllDeduction();

        // Réinitialiation du tableau de données
        for (int ii = 0; ii < gr.GRAPHMONTHS.length; ii++)
        {
            for (int jj = 0; jj < gr.SHORTCATEGORIES.length; jj++)
            {
                graphYearMonth[ii][ii][jj] = null;
            }
        }

        for (Deduction deduction : deductions)
        {
            String   currentYear    = String.valueOf(deduction.getDeductionAnnee());

            // Ne traiter que les données de l'année sélectionnée
            if (!currentYear.equals(selectedYear))
            {   continue;   }

            String   currentMonth   = (String) deduction.getDeductionMois();
            double   currentTTC     = (double) deduction.getTTC();
            double   currentTVA     = (double) deduction.getTVA();
            double   currentHT      = (double) deduction.getHT();

            // Vérifie changement d'année
            if (refreshYear == false)
            {
                lastYear = currentYear;
                refreshYear = true;
            }

            // Réinitialise si changement d'année
            if (!lastYear.equals(currentYear))
            {
                refreshYear = false;
            }

            /************************* GRAPHIQUE **************************/  

            if (lastYear.equals(currentYear))
            {
                // Trouve le mois correspondant et stocke la valeur dans le tableau
                for (int ii = 0; ii < gr.GRAPHMONTHS.length; ii++)
                {
                    if (gr.GRAPHMONTHS[ii].equals(currentMonth))
                    {
                        graphYearMonth[ii][ii][0] = currentTTC;   
                        graphYearMonth[ii][ii][1] = currentTVA;        
                        graphYearMonth[ii][ii][2] = currentHT; 
                    }
                }
            }

            // Renvoie des données calculées vers le graphique
            gr.updateDatasets(graphYearMonth, gr.GRAPHMONTHS, gr.SHORTCATEGORIES, gr.dataYearsPan3, gr.dataMonthsPan3); 
        }
    }

    /*********************************************************** 
                             HIBERNATE 
    ***********************************************************/

    // F2 -> Enrengistrer
    public void saveDeductionDataListener()
    {
        // Vérification cellules non-vide
        if (dp.dateDeduction.getDate() == null ||
             dp.txtTTCPan3.getText().isEmpty() ||
              dp.txtHTPan3.getText().isEmpty() ||
               dp.txtTVAPan3.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(dp.fen, "Tous les champs doivent être renseignés",
                                                    "Champs manquants", 
                                                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            Deduction deduction = extractDeductionFromUI();

            // Vérification des doublons
	        boolean exists = gn.getAllDeduction().stream().anyMatch(f -> 
                                f.getNameDeduction().equals(deduction.getNameDeduction())
	        );
	
	        if (exists)
	        {
	            JOptionPane.showMessageDialog(dp.fen, "Une facture pour ce mois existe déjà",
	                                                  "Doublon",
	                                                  JOptionPane.WARNING_MESSAGE);
	            return;
	        }

            // Enregistrement
            gn.saveDeduction(deduction);
	        JOptionPane.showMessageDialog(dp.fen, "Facture enregistrée avec succès",
	                                              "Enregistement réussi !",
	                                              JOptionPane.INFORMATION_MESSAGE);

        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(dp.fen, "Erreur de format numérique : " + e.getMessage(),
                                                  "Erreur",
                                                  JOptionPane.ERROR_MESSAGE);       	
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(dp.fen, "Erreur lors de l'enregistrement : " + e.getMessage(),
                                                    "Erreur",
                                                    JOptionPane.ERROR_MESSAGE);      	
        }
    }


    private Deduction extractDeductionFromUI() 
    {
        Date getDeduction = dp.dateDeduction.getDate();
        SimpleDateFormat sdfYear  = new SimpleDateFormat("yyyy", Locale.FRENCH);
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM", Locale.FRENCH);
        SimpleDateFormat sdfDay   = new SimpleDateFormat("dd"  , Locale.FRENCH);

        Deduction deduction = new Deduction();

        /* A1 */ deduction.setDeductionAnnee       (Integer.parseInt       (sdfYear.format((getDeduction))));
	    /* A1 */ deduction.setDeductionMois        (sdfMonth.format        (getDeduction));
	    /* A1 */ deduction.setDeductionJour        (Integer.parseInt       (sdfDay.format(getDeduction)));
	    /* B1 */ deduction.setTTC                  (Double.parseDouble     (dp.txtTTCPan3.getText()));
	    /* B2 */ deduction.setHT                   (Double.parseDouble     (dp.txtHTPan3.getText()));
	    /* B3 */ deduction.setTVA                  (Double.parseDouble     (dp.txtTVAPan3.getText()));
        /* D1 */ deduction.setRepDeduction         (                       (String) dp.boxRepDeduction.getSelectedItem());
		/* E1 */ deduction.setNameDeduction        (                       (String) dp.boxPDFDeduction.getSelectedItem());

        return deduction;
    }


    // F3 -> RAZ
    private void clearListener()
    {
        /* A1 */ dp.dateDeduction.setDate(null); 
        /* B1 */ dp.txtTTCPan3.setText(""); 
        /* B2 */ dp.txtHTPan3.setText(""); 
        /* B3 */ dp.txtTVAPan3.setText(""); 
        /* D1 */ dp.boxRepDeduction.removeAllItems();
        /* E1 */ dp.boxPDFDeduction.removeAllItems();
    } 
}