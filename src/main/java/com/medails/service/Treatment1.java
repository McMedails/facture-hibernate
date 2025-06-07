package com.medails.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javax.swing.JOptionPane;

import com.medails.ui.Display;

import com.medails.entity.Facture;
import com.medails.service.Generic;

    /************************************************************ 
                        TRAITEMENT DE DONNEES
    *************************************************************/

public class Treatment1
{
    /************* Déclarations Classes ****************/
    private final Display dp;
    private final Generic gn;

    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Treatment1(Display dp, Generic gn)
    {
        this.dp = dp;
        this.gn = gn;

        /*********** Appels Méthodes ***************/
        actionJElements();                                                     
    }

    /************************************************************ 
                              VARIABLES
    *************************************************************/

    /************************* Variables d'instance **************************/
    // Taux de taxe
    public final Double ACRE2024 = ((2.2 + 11.6 + 0.2) / 100);  // Année 2024 (ACRE)
    public final Double ACRE2025 = ((2.2 + 12.3 + 0.2) / 100);  // Année 2025 (ACRE)
    public final Double SANS2025 = ((2.2 + 24.6 + 0.2) / 100);  // Année 2025 (sans ACRE)
    public final Double SANS20XX = ((2.2 + 26.1 + 0.2) / 100);  // Année 2026 ou plus
    public final Double TVA = 1.2;

    // Répertoire Facture 
    private final String DIRECTORY_FACTURE = "M://Multimédia/Bureau/Social/Social - Pc Bureau/01 - Professionnelle/Factures";

    // Répertoire Déclaration
    private final String DIRECTORY_DECLA = "M://Multimédia/Bureau/Social/Social - Pc Bureau/00 - Gouvernement/URSSAF/Déclarations";
     
    // Variables pour calcules
    private double currentTTC     = 0.0;
    private double currentHT      = 0.0;
    private double currentTVA     = 0.0;
    private double currentTaxe    = 0.0;
    private double currentBenefit = 0.0;

    /************************************************************ 
                              METHODES
    *************************************************************/

    private void actionJElements()
    {
        /*********** Internes ***************/
        dp.butSave                 .addActionListener (e -> saveFactureDataListener()); 
        dp.butTVA                  .addActionListener (e -> calculListener());
        dp.butReset1               .addActionListener (e -> clearListener());

        /*********** Génériques ***************/
        dp.butOpenFacture          .addActionListener (e -> gn.openPDF(dp.boxRep1, dp.boxPDF1));
        dp.butOpenDecla            .addActionListener (e -> gn.openPDF(dp.boxRep2, dp.boxPDF2));

        dp.butSearchFacture        .addActionListener (e -> gn.searchDirectory(dp.boxRep1, dp.boxPDF1, DIRECTORY_FACTURE));
        dp.butSearchDecla          .addActionListener (e -> gn.searchDirectory(dp.boxRep2, dp.boxPDF2, DIRECTORY_DECLA)); 
        dp.butDelete               .addActionListener (e -> gn.deleteInBDD(dp.boxPDF1, gn::findByNameFacture, gn::deleteFacture));
        						 gn.popupListener     (dp.boxRep1, dp.boxPDF1, () -> gn.getDirectoryRepFacture(),
                                                                               () -> gn.getPDFNameFacture());
                                 gn.popupListener     (dp.boxRep2, dp.boxPDF2, () -> gn.getDirectoryRepDecla(),
                                                                               () -> gn.getPDFNameDecla());


        // Solution Lambda pour mettre à jour les champs
        gn.boxPDFListener(dp.boxPDF1,    f ->   // Lambda pour Facture
        {
            dp.boxYears.setSelectedItem                     (f.getFactureAnnee());
            dp.boxMonths.setSelectedItem                    (f.getFactureMois());

            int       annee  =  f.getVersementAnnee();
            String    mois   =  f.getVersementMois();
            int       jour   =  f.getVersementJour();
            dp.datePay.setDate(Date.from(LocalDate.of(annee, gn.convertMonth(mois), jour)
                                            .atStartOfDay(ZoneId.systemDefault()).toInstant()));

            dp.txtDays      .setText    (String.valueOf     (f.getJours()));   
            dp.txtTJM       .setText    (String.valueOf     (f.getTJM())); 
            dp.txtTTC       .setText    (String.valueOf     (f.getTTC()));   
            dp.txtHT        .setText    (String.valueOf     (f.getHT()));   
            dp.txtTVA       .setText    (String.valueOf     (f.getTVA()));    
            dp.txtTaxe      .setText    (String.valueOf     (f.getTaxes()));  
            dp.txtBenefit   .setText    (String.valueOf     (f.getBenefices())); 
        },
                                        d -> {},  // Lambda pour Deduction (inutile ici)
                                        c -> {}   // Lambda pour Chomage (inutile ici)
        );
    }

    /*********************************************************** 
                              PANEL 1 
    ***********************************************************/
    
    // B3 -> Calculer
    public void calculListener() 
    {
        try
        {
            // Récupération des valeurs inscrites par l'utilisateur
            String boxYear  = (String) dp.boxYears.getSelectedItem();    
            String boxMonth = (String) dp.boxMonths.getSelectedItem();     
            double txtDays  = Double.parseDouble(dp.txtDays.getText());   
            double txtTJM   = Double.parseDouble(dp.txtTJM.getText());    

            // Vérification cellules non-vide
            if ((boxYear == "") || (boxMonth == "")) 
            {
                JOptionPane.showMessageDialog(dp.fen, "Veuillez remplir les champs : \n - Année \n - Mois",
                                                      "Champs de saisie vides", 
                                                      JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Caclule des valeurs 
            currentHT   =  txtDays     *  txtTJM;
            currentTTC  =  currentHT   *  TVA;
            currentTVA  =  currentTTC  -  currentHT;

            /************************* Calcule URSSAF *************************/

            // Année 2024 (ACRE)
            if ("2024".equals(boxYear))
            {
                currentTaxe = currentHT * ACRE2024;
            }

            // Année 2025 (ACRE)
            else if (("2025".equals(boxYear)) && 
                        ("Janvier".equals(boxMonth) ||
                          "Février".equals(boxMonth) ||
                           "Mars".equals(boxMonth) ||
                            "Avril".equals(boxMonth)))
            {
                currentTaxe = currentHT * ACRE2025;
            }

            // Année 2025 (sans ACRE)
            else if (("2025".equals(boxYear)) && 
                        ("Mai".equals(boxMonth) ||
                          "Juin".equals(boxMonth) ||
                           "Juillet".equals(boxMonth) ||
                            "Août".equals(boxMonth) ||
                             "Septembre".equals(boxMonth) ||
                              "Novembre".equals(boxMonth) ||
                               "Décembre".equals(boxMonth)))              
            {
                currentTaxe = currentHT * SANS2025;
            }

            // Année 2026 ou plus
            else
            {
                currentTaxe = currentHT * SANS20XX;
            }              

            /************************* Report *************************/

            // Report -> TTC
            String reportTTC = String.format(Locale.US, "%.2f", currentTTC);
            dp.txtTTC.setText(reportTTC);

            // Report -> HT
            String reportHT = String.format(Locale.US ,"%.2f", currentHT);
            dp.txtHT.setText(reportHT);           

            // Report -> TVA
            String reportTVA = String.format(Locale.US ,"%.2f", currentTVA);
            dp.txtTVA.setText(reportTVA);

            // Report -> URSSAF
            String reportTaxe = String.format(Locale.US, "%.2f", currentTaxe);
            dp.txtTaxe.setText(reportTaxe);

            // Report -> Bénéfice
            currentBenefit = currentHT - currentTaxe;
            String reportBenefit = String.format(Locale.US, "%.2f", currentBenefit);
            dp.txtBenefit.setText(reportBenefit);
        }
        catch (NumberFormatException ex)
        {   
            // Vérification présence chiffre
            JOptionPane.showMessageDialog(dp.fen, "Veuillez entrez des nombres valides dans les champs : \n - Jours travaillés \n - TJM",
                                                    "Format non-respecté", 
                                                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /*********************************************************** 
                             HIBERNATE 
    ***********************************************************/

    // K2 -> Enrengistrer
    public void saveFactureDataListener()
    {
        // Vérification cellules non-vide
        if (dp.boxYears.getSelectedItem() == null ||
             dp.boxMonths.getSelectedItem() == null ||
              dp.datePay.getDate() == null ||
               dp.txtDays.getText().isEmpty() ||
                dp.txtTJM.getText().isEmpty() ||
                 dp.txtTTC.getText().isEmpty() ||
                  dp.txtHT.getText().isEmpty() ||
                   dp.txtTVA.getText().isEmpty() ||
                    dp.txtTaxe.getText().isEmpty() ||
                     dp.txtBenefit.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(dp.fen, "Tous les champs doivent être renseignés",
                                                    "Champs manquants", 
                                                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            Facture facture = extractFactureFromUI();

            // Vérification des doublons
	        boolean exists = gn.getAllFacture().stream().anyMatch(f -> 
                                f.getNameFacture().equals(facture.getNameFacture()) ||
                                f.getNameDecla().equals(facture.getNameDecla()) 
	        );
	
	        if (exists)
	        {
	            JOptionPane.showMessageDialog(dp.fen, "Une facture pour ce mois existe déjà",
	                                                  "Doublon",
	                                                  JOptionPane.WARNING_MESSAGE);
	            return;
	        }

            // Enregistrement
            gn.saveFacture(facture);
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


    private Facture extractFactureFromUI() 
    {
        Date getPay = dp.datePay.getDate();
        SimpleDateFormat sdfYear  = new SimpleDateFormat("yyyy", Locale.FRENCH);
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM", Locale.FRENCH);
        SimpleDateFormat sdfDay   = new SimpleDateFormat("dd"  , Locale.FRENCH);

        Facture facture = new Facture();

        /* A1 */  facture.setFactureAnnee        (Integer.parseInt       ((String) dp.boxYears.getSelectedItem()));
        /* A2 */  facture.setFactureMois         (                       (String) dp.boxMonths.getSelectedItem());
        /* A3 */  facture.setVersementAnnee      (Integer.parseInt       ((sdfYear.format(getPay))));
        /* A3 */  facture.setVersementMois       (sdfMonth.format        (getPay));
        /* A3 */  facture.setVersementJour       (Integer.parseInt       (sdfDay.format(getPay)));
        /* B1 */  facture.setJours               (Double.parseDouble     (dp.txtDays.getText()));
        /* B2 */  facture.setTJM                 (Double.parseDouble     (dp.txtTJM.getText()));
        /* C1 */  facture.setTTC                 (Double.parseDouble     (dp.txtTTC.getText()));
        /* C2 */  facture.setHT                  (Double.parseDouble     (dp.txtHT.getText()));
        /* C3 */  facture.setTVA                 (Double.parseDouble     (dp.txtTVA.getText()));
        /* D1 */  facture.setTaxes               (Double.parseDouble     (dp.txtTaxe.getText()));
        /* D2 */  facture.setBenefices           (Double.parseDouble     (dp.txtBenefit.getText()));
        /* F1 */  facture.setRepFacture          (                       (String) dp.boxRep1.getSelectedItem());
        /* G1 */  facture.setRepDecla            (                       (String) dp.boxRep2.getSelectedItem());
        /* I1 */  facture.setNameFacture         (                       (String) dp.boxPDF1.getSelectedItem());
        /* J1 */  facture.setNameDecla           (                       (String) dp.boxPDF2.getSelectedItem());

        return facture;
    }


    public void clearListener()
    {
        /* A1 */ dp.boxYears.setSelectedItem("");
        /* A2 */ dp.boxMonths.setSelectedItem("");
        /* A3 */ dp.datePay.setDate(null); 
        /* B1 */ dp.txtDays.setText("");  
        /* B2 */ dp.txtTJM.setText("");
        /* C1 */ dp.txtTTC.setText("");
        /* C2 */ dp.txtHT.setText("");
        /* C3 */ dp.txtTVA.setText("");
        /* D1 */ dp.txtTaxe.setText("");
        /* D2 */ dp.txtBenefit.setText("");
        /* F1 */ dp.boxRep1.removeAllItems();
        /* G1 */ dp.boxPDF1.removeAllItems();
        /* I1 */ dp.boxRep2.removeAllItems();
        /* J1 */ dp.boxPDF2.removeAllItems(); 
    } 
}