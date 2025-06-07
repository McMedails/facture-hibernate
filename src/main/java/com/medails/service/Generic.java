package com.medails.service;

import java.awt.Desktop;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.JFreeChart;

import com.medails.dao.ChomageRepository;
import com.medails.dao.DeductionRepository;
import com.medails.dao.FactureRepository;
import com.medails.ui.Display;
import com.medails.ui.Graphic;

import com.medails.entity.Chomage;
import com.medails.entity.Deduction;
import com.medails.entity.Facture;

public class Generic
{
    /************* Déclarations Classes ****************/
    private final Display dp;
    private final Graphic gr;

    /************* Déclarations Interfaces *************/
    private final FactureRepository factureRepository;
    private final DeductionRepository deductionRepository;
    private final ChomageRepository chomageRepository;

    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Generic(Display dp, 
                    Graphic gr,
                     FactureRepository factureRepository,
                      DeductionRepository deductionRepository,
                       ChomageRepository chomageRepository)
    {
        this.dp = dp;
        this.gr = gr;
        this.factureRepository   = factureRepository;
        this.deductionRepository = deductionRepository;
        this.chomageRepository   = chomageRepository;
    }


    /************************************************************ 
                          METHODES GENERIQUES
    *************************************************************/

    // Méthode générique pour ouvrir un PDF
    public void openPDF(JComboBox<String> boxRep, JComboBox<String> boxPDF)
    {
        String selectedRep = (String) boxRep.getSelectedItem();
        String selectedPDF = (String) boxPDF.getSelectedItem();

        if (selectedRep == null || selectedPDF == null || selectedRep.isEmpty() || selectedPDF.isEmpty())
        {
            JOptionPane.showMessageDialog(dp.fen, "Veuillez sélectionner un fichier PDF dans l'onglet lien",
                                                  "Erreur", 
                                                  JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Nettoyage du nom de fichier pour supprimer le préfixe numérique
        String cleanedPDFName = cleanPrefix(selectedPDF);
        File file = new File(selectedRep + File.separator + cleanedPDFName);

        try
        {
            if (file.exists())  {   Desktop.getDesktop().open(file);    }
            else {   throw new IOException("Fichier introuvable");   }
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(dp.fen, "Le fichier PDF : " + file.getAbsolutePath() + "est introuvable",
                                                   "Erreur", 
                                                   JOptionPane.WARNING_MESSAGE);
        }
    }
        

    // Méthode générique pour ouvrir un répertoire
    public void searchDirectory(JComboBox<String> boxRep, JComboBox<String> boxPDF, String directory)
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(directory));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Type : .PDF", "pdf"));

        int result = fileChooser.showOpenDialog(dp.fen);
        File selectedRep = fileChooser.getSelectedFile();
        
        if (result == JFileChooser.APPROVE_OPTION)
        {
            if (selectedRep != null)
            {
                // MAJ des répertoires
                String parentDirectory = selectedRep.getParent();
                boxRep.removeAllItems();
                boxRep.addItem(parentDirectory);

                // MAJ des fichiers PDF
                String namePDF = selectedRep.getName();
                boxPDF.removeAllItems();
                boxPDF.addItem(namePDF);
            }
        }
    }


    // Méthode générique les onglets répertoires
    public void popupListener(JComboBox<String> boxRep, JComboBox<String> boxPDF, 
                               Supplier<List<String>> supplierRep,
                                Supplier<List<String>> supplierPDF)

    {
        boxRep.addPopupMenuListener(new PopupMenuListener() 
        {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) 
            {    
                // Récupération depuis MySQL
                List<String> arrayRep = supplierRep.get();
                List<String> arrayPDF = supplierPDF.get(); 
        
                // Mise à jour des ComboBox
                updateComboBox(boxRep, arrayRep);
                updateComboBox(boxPDF, arrayPDF);
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
    
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
    }

    public void updateComboBox (JComboBox<String> comboBox, List<String> allItems)
    {
        comboBox.removeAllItems();
        allItems.forEach(comboBox::addItem);
        comboBox.setSelectedIndex(-1);
    }


    /****************** Suppression dans BDD ****************/

    public <T> void deleteInBDD(JComboBox<String> box, 
                                 Finder<T> finder,
                                  Consumer<T> deleteFunction)
    {
        String fullName = (String) box.getSelectedItem();
        if (fullName == null || fullName.isEmpty())
        {
            JOptionPane.showMessageDialog(dp.fen, "Veuillez sélectionner un fichier à supprimer.",
                                             "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cleanedName = cleanPrefix(fullName);

        Optional<T> optional= finder.find(cleanedName);

        if (optional.isPresent())
        {
            deleteFunction.accept(optional.get());
            JOptionPane.showMessageDialog(dp.fen, "Fichier supprimé avec succès !",
                                                  "Suppresion reussie",
                                                  JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(dp.fen, "Aucun fichier trouvé avec ce nom.",
                                                    "Erreur",
                                                    JOptionPane.WARNING_MESSAGE);  
        }
    }


    /****************** Gestion prefixe ****************/

    // Méthode pour nettoyer le nom du PDF en supprimant le préfixe numérique
    public String cleanPrefix(String item) 
    {
        return item.replaceFirst("^\\d+-", "").trim();
    }


    public void boxPDFListener(JComboBox<String> box, 
                                Consumer<Facture>  updateFacture,
                                 Consumer<Deduction> updateDeduction,
                                  Consumer<Chomage>    updateChomage)
    {
        box.addActionListener(e -> 
        {
            String item = (String) box.getSelectedItem();

            if (item != null && !item.isEmpty()) 
            {
                String cleanedName = cleanPrefix(item);

                Optional<Facture> optFacture = factureRepository.findByNameFacture(cleanedName);
                optFacture.ifPresent(updateFacture);

                Optional<Deduction> optDeduction = deductionRepository.findByNameDeduction(cleanedName);
                optDeduction.ifPresent(updateDeduction);

                Optional<Chomage> optChomage = chomageRepository.findByNameChomage(cleanedName);
                optChomage.ifPresent(updateChomage);
            }
        });
    }
    

    /**************** Convertion mois ****************/

    // Reconversion du format Date 
    public int convertMonth(String mois)
    {
        Map<String, Integer> moisMap = Map.ofEntries
           (Map.entry("janvier"     , 1),
            Map.entry("février"     , 2),
            Map.entry("mars"        , 3),
            Map.entry("avril"       , 4),
            Map.entry("mai"         , 5),
            Map.entry("juin"        , 6),
            Map.entry("juillet"     , 7),
            Map.entry("août"        , 8),
            Map.entry("septembre"   , 9),
            Map.entry("octobre"     , 10),
            Map.entry("novembre"    , 11),
            Map.entry("décembre"    , 12));

        return moisMap.getOrDefault(mois, 1);
    }


    /**************** SlideRange ****************/

    // Mise à jour des graphiques avec la nouvelle échelle
    public void slideRange(JFreeChart chart, JSlider slide)
    {
        int range = slide.getValue();
        gr.updatChartRange(chart, range);
    }


    /**************** CheckBox ****************/

    // CheckBox Graphique
    public boolean filterGraph(JCheckBox checkBox)
    { return checkBox.isSelected(); }

    
    /************************************************************ 
                           GESTION D'APPELS
    *************************************************************/

                /************* Facture DAO *************/

    /* f-1a */
    public Optional<Facture> findByNameFacture(String nameFacture)
    {
        return factureRepository.findByNameFacture(nameFacture);
    }
    /* f-1b */
    public Optional<Facture> findByNameDecla(String nameDecla)
    {
        return factureRepository.findByNameDecla(nameDecla);
    }

    /* f-2 */
    public void saveFacture(Facture facture)
    {
        factureRepository.save(facture);
    }

    /* f-3 */
    public List<Facture> getAllFacture()
    {
        return factureRepository.findAll();
    }

    /* f-4 */
    public void getFacture(Long id)
    {
        factureRepository.findById(id);
    }

    /* f-5 */
    public void deleteFacture(Facture facture)
    {
        factureRepository.delete(facture);
    }

    /* f-6 */
    public List<String> getDirectoryRepFacture()
    {
        return factureRepository.getDirectoryRepFacture();
    }

    /* f-7 */
    public List<String> getDirectoryRepDecla()
    {
        return factureRepository.getDirectoryRepDecla();
    }

    /* f-8 */
    public List<String> getPDFNameFacture()
    {
        return factureRepository.getPDFNameFacture();
    }

    /* f-9 */
    public List<String> getPDFNameDecla()
    {
        return factureRepository.getPDFNameDecla();
    }


                /************* Deduction DAO *************/

    /* d-1 */
    public Optional<Deduction> findByNameDeduction(String nameDeduction)
    {
        return deductionRepository.findByNameDeduction(nameDeduction);
    }

    /* d-2 */
    public void saveDeduction(Deduction deduction)
    {
        deductionRepository.save(deduction);
    }

    /* d-3 */
    public List<Deduction> getAllDeduction()
    {
        return deductionRepository.findAll();
    }

    /* d-4 */
    public void getDeduction(Long id)
    {
        deductionRepository.findById(id);
    }

    /* d-5 */
    public void deleteDeduction(Deduction deduction)
    {
        deductionRepository.delete(deduction);
    }

    /* d-6 */
    public List<String> getDirectoryRepDeduction()
    {
        return deductionRepository.getDirectoryRepDeduction();
    }

    /* d-7 */
    public List<String> getPDFNameDeduction()
    {
        return deductionRepository.getPDFNameDeduction();
    }


                /************* Chomage DAO *************/

    /* c-1 */
    public Optional<Chomage> findByNameChomage(String nameChomage)
    {
        return chomageRepository.findByNameChomage(nameChomage);
    }

    /* c-2 */
    public void saveChomage(Chomage chomage)
    {
        chomageRepository.save(chomage);
    }

    /* c-3 */
    public List<Chomage> getAllChomage()
    {
        return chomageRepository.findAll();
    }

    /* c-4 */
    public void getChomage(Long id)
    {
        chomageRepository.findById(id);
    }

    /* c-5 */
    public void deleteChomage(Chomage chomage)
    {
        chomageRepository.delete(chomage);
    }

    /* c-6 */
    public List<String> getDirectoryRepChomage()
    {
        return chomageRepository.getDirectoryRepChomage();
    }

    /* c-7 */
    public List<String> getPDFNameChomage()
    {
        return chomageRepository.getPDFNameChomage();
    }
}