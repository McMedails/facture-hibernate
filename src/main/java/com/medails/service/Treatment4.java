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

import com.medails.entity.Chomage;
import com.medails.service.Generic;

    /************************************************************ 
                        TRAITEMENT DE DONNEES
    *************************************************************/

public class Treatment4
{
    /************* Déclarations Classes ****************/
    private Display dp;
    private Graphic gr;
    private Generic gn;

    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Treatment4(Display dp, Graphic gr, Generic gn)
    {
        this.dp = dp;
        this.gr = gr;    
        this.gn = gn; 

        /*********** Appels Méthodes ***************/
        actionJElements();
        graphDecenal();
        graphYearMonth();   
        gr.updateDatasets(graphYearMonth, gr.GRAPHMONTHS, gr.CHOMAGE, gr.dataYearsPan4, gr.dataMonthsPan4);                     
    }


    /************************************************************ 
                            VARIABLES
    *************************************************************/
    
    /************************* Variables de classe **************************/
    // Données pour création graphique
    public static Double[] graphDecenal = new Double[10]; 
    public static Double[] graphYearMonth = new Double[12];      

    /************************* Variables d'instance **************************/
    // Répertoire Facture 
    private final String DIRECTORY_CHOMAGE = "M:\\Multimédia\\Bureau\\Social\\Social - Pc Bureau\\00 - Gouvernement\\Pole Emploi\\Actualisation";

    /************************************************************ 
                              METHODES
    *************************************************************/

    private void actionJElements()
    {
        /*********** Internes ***************/
        dp.butSaveChomage           .addActionListener (e -> saveChomageDataListener());
        dp.txtDayChomage            .addActionListener (e -> calculListener());
        dp.txtAREChomage            .addActionListener (e -> calculListener());
        dp.boxYearsChomage          .addActionListener (e -> graphYearMonth());
        dp.butReset4                .addActionListener (e -> clearListener());  

        /*********** Partie Graphique ***************/
        dp.sliDecadePan4           .addChangeListener (e -> graphDecenal());
        dp.sliYearMonthPan4        .addChangeListener (e -> graphYearMonth());

        /*********** Génériques ***************/
        dp.butOpenChomage           .addActionListener (e -> gn.openPDF(dp.boxRepChomage, dp.boxPDFChomage));
        dp.butSearchChomage         .addActionListener (e -> gn.searchDirectory(dp.boxRepChomage, dp.boxPDFChomage, DIRECTORY_CHOMAGE));
        dp.butDeleteChomage         .addActionListener (e -> gn.deleteInBDD(dp.boxPDFChomage, gn::findByNameChomage, gn::deleteChomage)); 
                                  gn.popupListener (dp.boxRepChomage, dp.boxPDFChomage,  () -> gn.getDirectoryRepChomage(),
                                                                                         () -> gn.getPDFNameChomage());
        


        // Solution Lambda pour mettre à jour les champs
        gn.boxPDFListener(dp.boxPDFChomage,   f -> {},  // Lambda pour Facture (inutile ici)
                                              d -> {},  // Lambda pour Deduction (inutile ici)
                                              c ->      // Lambda pour Chomage
        {
            int         annee  =  c.getChomageAnnee();
            String      mois   =  c.getChomageMois();
            int         jour   =  c.getChomageJour();
            dp.dateChomage.setDate(Date.from(LocalDate.of(annee, gn.convertMonth(mois), jour)
                                                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

            dp.boxMonthsChomage     .setSelectedItem                       (c.getMoisActualisation());
            dp.txtDayChomage        .setText            (String.valueOf    (c.getJourParMois()));
            dp.txtQChomage          .setText            (String.valueOf    (c.getCoefficient()));
            dp.txtAREChomage        .setText            (String.valueOf    (c.getMontant()));   
        });
    }


    /*********************************************************** 
                              PANEL 4 
    ***********************************************************/

    // Calcule HT/TVA
    private void calculListener()
    {
        // Vérification cellule non-vide
        if (!dp.txtDayChomage.getText().isEmpty() && 
              !dp.txtAREChomage.getText().isEmpty())
        {
            try
            {
                double Days = Double.parseDouble(dp.txtDayChomage.getText());
                double ARE = Double.parseDouble(dp.txtAREChomage.getText());
                double Taux  = ARE / Days;
                                
                // Report -> TTC
                String repportTaux = String.format(Locale.US, "%.2f", Taux);
                dp.txtQChomage.setText(repportTaux);
   
            }
            catch (NumberFormatException e)
            {
                // Si le text n'est pas un nombre valide
                dp.txtDayChomage.setText("");
                dp.txtQChomage.setText("");
                dp.txtAREChomage.setText("");
            }
        }
        else
        {
            // Efface si le champ Jours/Montant sont vides
            dp.txtDayChomage.setText("");
            dp.txtAREChomage.setText("");           
        }         
    }

           
    /***********************************************************  
                           Onglet Décénie
    ***********************************************************/

    public void graphDecenal()
    {
        // Mise à jour des graphiques avec la nouvelle échelle
        gn.slideRange(gr.chartDecadePan4, dp.sliDecadePan4); 

        // Initialisation des données graphiques
        gr.dataDecadePan4.clear();

        // Récupère les données de la DB
        List<Chomage> chomages = gn.getAllChomage();

        // Map temporaire pour cumuler les ARE par année
        Map<String, Double> cumulParAnnee = new HashMap<>();

        for (Chomage chomage : chomages)
        {
            String   currentYear    = String.valueOf(chomage.getChomageAnnee());
            double   currentARE     = chomage.getMontant();

            // Cumule les montants par année
            cumulParAnnee.put(currentYear, cumulParAnnee.getOrDefault
                              (currentYear, 0.0) + currentARE);

            /************************* GRAPHIQUE **************************/ 
            // Trouve le mois correspondant et stocke la valeur dans le tableau
            for (int ii = 0; ii < gr.LONGRAPHYEARS.length; ii++)
            {
                String annee = gr.LONGRAPHYEARS[ii];
                graphDecenal[ii] = cumulParAnnee.getOrDefault(annee, 0.0);
            }
            
            // Renvoie des données calculées vers le graphique
            gr.updateDatasets(graphDecenal, gr.LONGRAPHYEARS, gr.CHOMAGE, gr.dataDecadePan4); 
        }
    }

    /*********************************************************** 
                    Onglet Annuel / Mensuel
    ***********************************************************/

    public void graphYearMonth()
    {
        // Mise à jour des graphiques avec la nouvelle échelle
        gn.slideRange(gr.chartYearsPan4, dp.sliYearMonthPan4);
        gn.slideRange(gr.chartMonthsPan4, dp.sliYearMonthPan4);  

        // Initialisation des données graphiques
        gr.dataYearsPan4.clear();
        gr.dataMonthsPan4.clear();

        // Initialisation des données
        String      lastYear        = null;
        boolean     refreshYear     = false;

        // Récupère l'année sélectionnée dans la ComboBox
        String selectedYear = dp.boxYearsChomage.getSelectedItem().toString();

        // Récupère les données de la DB
        List<Chomage> chomages = gn.getAllChomage();
        
        // Réinitialiation du tableau de données
        for (int ii = 0; ii < gr.GRAPHMONTHS.length; ii++)
        {
            for (int jj = 0; jj < gr.CHOMAGE.length; jj++)
            {
                graphYearMonth[ii] = null;
            }
        }

        for (Chomage chomage : chomages)
        {
            String   currentYear    = String.valueOf(chomage.getChomageAnnee());

            // Ne traiter que les données de l'année sélectionnée
            if (!currentYear.equals(selectedYear))
            {   continue;   }

            String   currentMonth   = (String) chomage.getChomageMois();
            double   currentARE     = (double) chomage.getMontant();

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
                        graphYearMonth[ii] = currentARE;   
                    }
                }
            }

            // Renvoie des données calculées vers le graphique
            gr.updateDatasets(graphYearMonth, gr.GRAPHMONTHS, gr.CHOMAGE, gr.dataYearsPan4, gr.dataMonthsPan4); 
        }
    }

    /*********************************************************** 
                             HIBERNATE 
    ***********************************************************/

    // G2 -> Enrengistrer
    public void saveChomageDataListener()
    {
        // Vérification cellules non-vide
        if (dp.dateChomage.getDate() == null ||
             dp.boxMonthsChomage.getSelectedItem() == null ||
              dp.txtDayChomage.getText().isEmpty() ||
               dp.txtQChomage.getText().isEmpty() ||
                dp.txtAREChomage.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(dp.fen, "Tous les champs doivent être renseignés",
                                                    "Champs manquants", 
                                                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try
        {
            Chomage chomage = extractChomageFromUI();

            // Vérification des doublons
            boolean exists = gn.getAllChomage().stream().anyMatch(f -> 
                                f.getNameChomage().equals(chomage.getNameChomage())
            );
    
            if (exists)
            {
                JOptionPane.showMessageDialog(dp.fen, "Une facture pour ce mois existe déjà",
                                                    "Doublon",
                                                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Enregistrement
            gn.saveChomage(chomage);
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


    private Chomage extractChomageFromUI() 
    {
        Date getChomage = dp.dateChomage.getDate();
        SimpleDateFormat sdfYear  = new SimpleDateFormat("yyyy", Locale.FRENCH);
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM", Locale.FRENCH);
        SimpleDateFormat sdfDay   = new SimpleDateFormat("dd"  , Locale.FRENCH);

        Chomage chomage = new Chomage();

        /* B1 */ chomage.setChomageAnnee         (Integer.parseInt       (sdfYear.format((getChomage))));
	    /* B1 */ chomage.setChomageMois          (sdfMonth.format        (getChomage));
	    /* B1 */ chomage.setChomageJour          (Integer.parseInt       (sdfDay.format(getChomage)));
        /* B2 */ chomage.setMoisActualisation    (                       (String) dp.boxMonthsChomage.getSelectedItem());
	    /* C1 */ chomage.setJourParMois          (Integer.parseInt       (dp.txtDayChomage.getText()));
	    /* C2 */ chomage.setCoefficient          (Double.parseDouble     (dp.txtQChomage.getText()));
	    /* C3 */ chomage.setMontant              (Double.parseDouble     (dp.txtAREChomage.getText()));
        /* D1 */ chomage.setRepChomage           (                       (String) dp.boxRepChomage.getSelectedItem());
		/* E1 */ chomage.setNameChomage          (                       (String) dp.boxPDFChomage.getSelectedItem());

        return chomage;
    }

 
    // F3 -> RAZ
    private void clearListener()
    {
        /* B1 */ dp.dateChomage.setDate(null); 
        /* B2 */ dp.boxMonthsChomage.setSelectedItem("");
        /* C1 */ dp.txtDayChomage.setText(""); 
        /* C2 */ dp.txtQChomage.setText(""); 
        /* C3 */ dp.txtAREChomage.setText(""); 
        /* D1 */ dp.boxRepChomage.removeAllItems();
        /* E1 */ dp.boxPDFChomage.removeAllItems();
    } 
}