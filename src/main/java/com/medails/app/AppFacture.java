package com.medails.app;

import javax.swing.SwingUtilities;

import com.medails.dao.ChomageRepository;
import com.medails.dao.DeductionRepository;
import com.medails.dao.FactureRepository;
import com.medails.impl.ChomageRepositoryImpl;
import com.medails.impl.DeductionRepositoryImpl;
import com.medails.impl.FactureRepositoryImpl;
import com.medails.service.Generic;
import com.medails.service.Treatment1;
import com.medails.service.Treatment2;
import com.medails.service.Treatment3;
import com.medails.service.Treatment4;
import com.medails.ui.Display;
import com.medails.ui.Graphic;


    /*********************************************************** 
                          DEMARAGE PROGRAMME
    ***********************************************************/

public class AppFacture
{                  
    public static void main (String[]args)
    {
        // Swing Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                // Interface Graphique
                Display display = new Display();
                Graphic graphic = new Graphic(display);

                // DAO Hibernate
                FactureRepository   factureRepository   = new FactureRepositoryImpl();
                DeductionRepository deductionRepository = new DeductionRepositoryImpl();
                ChomageRepository   chomageRepository   = new ChomageRepositoryImpl();

                // Méthodes génériques
                Generic generic = new Generic(display, graphic, factureRepository, deductionRepository, chomageRepository);

                // Injection des DAO dans les traitements
                Treatment1 treatment1 = new Treatment1(display, generic);  
                Treatment2 treatment2 = new Treatment2(display, graphic, generic); 
                Treatment3 treatment3 = new Treatment3(display, graphic, generic); 
                Treatment4 treatment4 = new Treatment4(display, graphic, generic); 
            }
        });
    }
}

/*             ____________________________________________________________       
              || Enregistrement|Facture|Déduction|Chomage                 ||                                   
              ||                                                          ||
              || Facture                                                  ||
              ||                                                          ||
              ||      Année             Mois         Date de paiement     ||
              ||     [_(A1)_]>        [_(A2)_]>   [_______(A3)________]>  ||                     
              ||                                                          ||
              ||                                                          ||
              || Jours travaillées      TJM                               ||
              ||     [_(B1)_]         [_(B2)_]        [Calculer](B3)      ||
              ||                                                          ||
              ||                                                          ||
              ||       TTC               HT              TVA              ||
              ||     [_(C1)_]         [_(C2)_]         [_(C3)_]           ||
              ||                                                          ||
              ||                                                          ||
              || URSSAF                                                   ||
              ||                                                          ||
              ||   Montant taxe       Bénéfices                           || 
              ||     [_(D1)_]         [_(D2)_]                            ||               
              ||                                                          ||
              ||                                                          ||
              || Liens                                                    ||
              ||                                                          ||
              ||      Facture            [Ouvrir](E1)   [Parcourir](E2)   ||
              ||     [____________________(F1)_____________________]>     ||
              ||     [____________________(G1)_____________________]>     ||
              ||                                                          ||
              ||      Déclaration        [Ouvrir](H1)   [Parcourir](H2)   ||
              ||     [____________________(I1)____________________]>      ||
              |      [____________________(J1)____________________]>      ||
              ||                                                          ||
              ||    [Supprimer](K1)	   [Enrengistrer](K2)    [RAZ](K3)    ||
              ||__________________________________________________________||

               ____________________________________________________________  
              || Enregistrement|Facture|Déduction|Chomage                 ||
              ||                                                          ||
              ||     Décénie          <>-----------------------------     || 
              ||     Déductible       <>-----------------------------     || 
              ||   ___________________________________________________    ||
              ||  |                                                   |   ||
              ||  |                                                   |   || 
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   || 
              ||  |                                                   |   || 
              ||  |                                                   |   ||
              ||  |___________________________________________________|   ||
              ||   Décénie|Annuel|Mensuel    [Déduction](A1)  [_(A2)_]>   ||
              ||                                                          ||
              ||      TTC     TVA     HT      URSSAF      Bénéfices       ||  
              ||     ■(B1)    ■(B2)  ■(B3)     ■(B4)        ■(B5)         ||
              ||                                                          ||
              ||                                                          ||
              ||                                                          ||
              ||                                                          ||
              ||__________________________________________________________||    
              
               ____________________________________________________________  
              || Enregistrement|Facture|Déduction|Chomage                 || 
              ||                                                          ||
              ||     Décénie          <>-----------------------------     || 
              ||     Déductible       <>-----------------------------     || 
              ||   ___________________________________________________    ||
              ||  |                                                   |   ||
              ||  |                                                   |   || 
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   || 
              ||  |                                                   |   || 
              ||  |                                                   |   ||
              ||  |___________________________________________________|   ||
              ||   Décénie|Annuel|Mensuel                   [_(A1)_]>     ||
              ||                                                          ||
              ||    Date d'achat                                          ||
              ||  [_____(B1)_____]>                                       ||  
              ||                                                          ||
              ||                                                          ||
              ||      TTC              HT                     TVA         ||
              ||    [_(C1)_]        [_(C2)_]                [_(C3)_]      ||
              ||                                                          ||
              ||    Déduction                                             ||
              ||                                                          ||
              ||                         [Ouvrir](D1)   [Parcourir](D2)   ||
              ||     [____________________(E1)_____________________]>     ||
              ||     [____________________(F1)_____________________]>     ||
              ||                                                          ||
              ||    [Supprimer](G1)	   [Enrengistrer](G2)    [RAZ](G3)    ||
              ||__________________________________________________________||

               ____________________________________________________________  
              || Enregistrement|Facture|Déduction|Chomage                 || 
              ||                                                          ||
              ||     Décénie          <>-----------------------------     || 
              ||     Déductible       <>-----------------------------     || 
              ||   ___________________________________________________    ||
              ||  |                                                   |   ||
              ||  |                                                   |   || 
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   ||
              ||  |                                                   |   || 
              ||  |                                                   |   || 
              ||  |                                                   |   ||
              ||  |___________________________________________________|   ||
              ||   Décénie|Annuel|Mensuel                   [_(A1)_]>     ||
              ||                                                          ||
              ||   Date versement   Mois actualisation                    ||
              ||  [_____(B1)_____]>  [_____B2)_____]>                     ||  
              ||                                                          ||
              ||                                                          ||
              ||      TTC              HT                     TVA         ||
              ||    [_(C1)_]        [_(C2)_]                [_(C3)_]      ||
              ||                                                          ||
              ||    Déduction                                             ||
              ||                                                          ||
              ||                         [Ouvrir](D1)   [Parcourir](D2)   ||
              ||     [____________________(E1)_____________________]>     ||
              ||     [____________________(F1)_____________________]>     ||
              ||                                                          ||
              ||    [Supprimer](G1)	   [Enrengistrer](G2)    [RAZ](G3)    ||
              ||__________________________________________________________||
 */