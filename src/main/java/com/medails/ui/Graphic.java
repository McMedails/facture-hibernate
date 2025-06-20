package com.medails.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
        
/************************************************************ 
                        GRAPHIQUE
*************************************************************/

public class Graphic
{
    /************* Déclarations Classes ****************/
    private final Display dp;
    
    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/
    
    public Graphic (Display dp)
    {
        this.dp = dp;

        /*********** Panel 2 ***************/
        chartDecadePan2 = createChart(dataDecadePan2, dp.MINRANGE_YEARMONTHPAN2);
        chartYearsPan2  = createChart(dataYearsPan2,  dp.MINRANGE_YEARMONTHPAN2);
        chartMonthsPan2 = createChart(dataMonthsPan2, dp.MINRANGE_YEARMONTHPAN2);

        createGraphic(chartDecadePan2, WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabGraph, "Décénie", dp.pan2, 0, 2, 5);
        createGraphic(chartYearsPan2,  WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabGraph, "Annuel",  dp.pan2, 0, 2, 5);
        createGraphic(chartMonthsPan2, WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabGraph, "Mensuel", dp.pan2, 0, 2, 5);

        /*********** Panel 3 ***************/
        chartDecadePan3 = createChart(dataDecadePan3, dp.MINRANGE_YEARMONTHPAN3);
        chartYearsPan3  = createChart(dataYearsPan3,  dp.MINRANGE_YEARMONTHPAN3);
        chartMonthsPan3 = createChart(dataMonthsPan3, dp.MINRANGE_YEARMONTHPAN3);

        createGraphic(chartDecadePan3, WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabDeduction, "Décénie", dp.pan3, 0, 4, 4);
        createGraphic(chartYearsPan3,  WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabDeduction, "Annuel",  dp.pan3, 0, 4, 4);
        createGraphic(chartMonthsPan3, WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabDeduction, "Mensuel", dp.pan3, 0, 4, 4);

        /*********** Panel 4 ***************/
        chartDecadePan4 = createChart(dataDecadePan4, dp.MINRANGE_YEARMONTHPAN4);
        chartYearsPan4  = createChart(dataYearsPan4,  dp.MINRANGE_YEARMONTHPAN4);
        chartMonthsPan4 = createChart(dataMonthsPan4, dp.MINRANGE_YEARMONTHPAN4);

        createGraphic(chartDecadePan4, WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabChomage, "Décénie", dp.pan4, 0, 4, 4);
        createGraphic(chartYearsPan4,  WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabChomage, "Annuel",  dp.pan4, 0, 4, 4);
        createGraphic(chartMonthsPan4, WIDTH_GRAPHIC, HEIGHT_GRAPHIC, dp.tabChomage, "Mensuel", dp.pan4, 0, 4, 4);
    }

    
    /************************************************************ 
                            VARIABLES
    *************************************************************/

    /************************* Variables d'instance **************************/
    public final String[] GRAPHMONTHS = {"janvier", "février", "mars", "avril", 
                                            "mai", "juin", "juillet", "août", "septembre", 
                                                "octobre", "novembre", "décembre"};

    public final String[] GRAPHYEARS = {"2024", "2025", "2026", "2027", "2028"};                                     
    public final String[] LONGRAPHYEARS = {"2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028"};                                     

    public final String[] CATEGORIES = {"TTC", "TVA", "HT", "URSSAF", "Bénéfices"};
    public final String[] SHORTCATEGORIES = {"TTC", "TVA", "HT"};
    public final String[] CHOMAGE = {"MONTANT"};

    public final int WIDTH_GRAPHIC = 340;
    public final int HEIGHT_GRAPHIC = 400;

    /*********** Panel 2 ***************/
    public DefaultCategoryDataset dataDecadePan2 = new DefaultCategoryDataset();
    public DefaultCategoryDataset dataYearsPan2  = new DefaultCategoryDataset();
    public DefaultCategoryDataset dataMonthsPan2 = new DefaultCategoryDataset();

    public JFreeChart chartDecadePan2;
    public JFreeChart chartYearsPan2;
    public JFreeChart chartMonthsPan2;

    public ChartPanel chartPanelDecadePan2;
    public ChartPanel chartPanelYearsPan2;
    public ChartPanel chartPanelMonthsPan2;

    /*********** Panel 3 ***************/
    public DefaultCategoryDataset dataDecadePan3 = new DefaultCategoryDataset();
    public DefaultCategoryDataset dataYearsPan3  = new DefaultCategoryDataset();
    public DefaultCategoryDataset dataMonthsPan3 = new DefaultCategoryDataset();

    public JFreeChart chartDecadePan3;
    public JFreeChart chartYearsPan3;
    public JFreeChart chartMonthsPan3;

    public ChartPanel chartPanelDecadePan3;
    public ChartPanel chartPanelYearsPan3;
    public ChartPanel chartPanelMonthsPan3;

    /*********** Panel 4 ***************/
    public DefaultCategoryDataset dataDecadePan4 = new DefaultCategoryDataset();
    public DefaultCategoryDataset dataYearsPan4  = new DefaultCategoryDataset();
    public DefaultCategoryDataset dataMonthsPan4 = new DefaultCategoryDataset();

    public JFreeChart chartDecadePan4;
    public JFreeChart chartYearsPan4;
    public JFreeChart chartMonthsPan4;

    public ChartPanel chartPanelDecadePan4;
    public ChartPanel chartPanelYearsPan4;
    public ChartPanel chartPanelMonthsPan4;

    /************************************************************ 
                              METHODES
    *************************************************************/

    // Met à jour la plage de l'axe des ordonnées d'un graphique
    public void updatChartRange(JFreeChart chart, int newRange)
    {
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, newRange);
    }

    public JFreeChart createChart (DefaultCategoryDataset dataset, Integer maxRange)
    {
        // Création du graphique à barres (Annuel)
        JFreeChart chart = ChartFactory.createBarChart(null,     /* Titre du graphique */
                                                       null,     /* Axe des abscisses */ 
                                                       null,     /* Axe des ordonnées */  
                  dataset, PlotOrientation.HORIZONTAL, true,     /*Légende */ 
                                                       true,     /*Info tooltips */ 
                                                      false);    /* URL */

        // Personnalisation du graphique 
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        // Définir la plage de l'axe des ordonnées
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, maxRange);
        return chart;
    }

    public ChartPanel createGraphic (JFreeChart freeChart, int width, int height, JTabbedPane jTabbedPane, 
                                     String txtTab, JPanel jPanel, int gridX, int gridY, int gridWidth)
    {
        ChartPanel chartPanel = new ChartPanel(freeChart);
        chartPanel.setPreferredSize(new Dimension(width, height));
        jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        jTabbedPane.addTab(txtTab, chartPanel);
        dp.gbc.gridx = gridX;
        dp.gbc.gridy = gridY;
        dp.gbc.gridwidth = gridWidth;
        jPanel.add(jTabbedPane, dp.gbc);
        return chartPanel;
    }

    /*********************************************************** 
                        Onglet Décénie (Pan2)
    ***********************************************************/

    public void updateDatasets(Double[][][][][] data, String[] graph, String[] categories, 
                               DefaultCategoryDataset categoryDataset) 
    {
        for (int ii = 0; ii < graph.length; ii++) 
        {   
            for (int jj = 0; jj < categories.length; jj++) 
            {   
                // Récupération de la valeur
                Double value = data[ii][ii][ii][ii][jj]; 
    
                if (value != null) 
                {
                    categoryDataset.addValue(value, categories[jj], graph[ii]);
                }
                else 
                {
                    // Vérifier si la clé existe déjà avant d'appeler getValue()
                    boolean keyExists = categoryDataset.getColumnIndex(graph[ii]) >= 0 &&
                                            categoryDataset.getRowIndex(categories[jj]) >= 0;
    
                    // Si la clé n'existe pas, ajouter 0
                    if (!keyExists)  
                    {
                        categoryDataset.addValue(0.0, categories[jj], graph[ii]);
                    }
                }
            }
        }
    }

    /*********************************************************** 
                   Onglet Annuel / Mensuel (Pan2)
    ***********************************************************/

    public void updateDatasets(Double[][][][][] data, String[] graph, String[] categories, 
                               DefaultCategoryDataset categoryDataset1, DefaultCategoryDataset categoryDataset2) 
    {
        for (int ii = 0; ii < graph.length; ii++) 
        {   
            for (int jj = 0; jj < categories.length; jj++) 
            {   
                // Récupération de la valeur
                Double value = data[ii][ii][ii][ii][jj]; 
    
                if (value != null) 
                {
                    categoryDataset1.addValue(value, categories[jj], graph[ii]);
                    categoryDataset2.addValue(value, categories[jj], graph[ii]);
                }
                else 
                {
                    // Vérifier si la clé existe déjà avant d'appeler getValue()
                    boolean keyExists = categoryDataset1.getColumnIndex(graph[ii]) >= 0 &&
                                            categoryDataset1.getRowIndex(categories[jj]) >= 0;
    
                    // Si la clé n'existe pas, ajouter 0
                    if (!keyExists)  
                    {
                        categoryDataset1.addValue(0.0, categories[jj], graph[ii]);
                    }
                }
            }
        }
    }

    /*********************************************************** 
                        Onglet Décénie (Pan3)
    ***********************************************************/

    public void updateDatasets(Double[][][] data, String[] graph, String[] categories, 
                               DefaultCategoryDataset categoryDataset) 
    {
        for (int ii = 0; ii < graph.length; ii++) 
        {   
            for (int jj = 0; jj < categories.length; jj++) 
            {   
                // Récupération de la valeur
                Double value = data[ii][ii][jj]; 
    
                if (value != null) 
                {
                    categoryDataset.addValue(value, categories[jj], graph[ii]);
                }
                else 
                {
                    // Vérifier si la clé existe déjà avant d'appeler getValue()
                    boolean keyExists = categoryDataset.getColumnIndex(graph[ii]) >= 0 &&
                                            categoryDataset.getRowIndex(categories[jj]) >= 0;
    
                    // Si la clé n'existe pas, ajouter 0
                    if (!keyExists)  
                    {
                        categoryDataset.addValue(0.0, categories[jj], graph[ii]);
                    }
                }
            }
        }
    }

    /*********************************************************** 
                   Onglet Annuel / Mensuel (Pan3)
    ***********************************************************/

    public void updateDatasets(Double[][][] data, String[] graph, String[] categories, 
                               DefaultCategoryDataset categoryDataset1, DefaultCategoryDataset categoryDataset2) 
    {
        for (int ii = 0; ii < graph.length; ii++) 
        {   
            for (int jj = 0; jj < categories.length; jj++) 
            {   
                // Récupération de la valeur
                Double value = data[ii][ii][jj]; 
    
                if (value != null) 
                {
                    categoryDataset1.addValue(value, categories[jj], graph[ii]);
                    categoryDataset2.addValue(value, categories[jj], graph[ii]);
                }
                else 
                {
                    // Vérifier si la clé existe déjà avant d'appeler getValue()
                    boolean keyExists = categoryDataset1.getColumnIndex(graph[ii]) >= 0 &&
                                            categoryDataset1.getRowIndex(categories[jj]) >= 0;
    
                    // Si la clé n'existe pas, ajouter 0
                    if (!keyExists)  
                    {
                        categoryDataset1.addValue(0.0, categories[jj], graph[ii]);
                    }
                }
            }
        }
    }

    /*********************************************************** 
                        Onglet Décénie (Pan4)
    ***********************************************************/

    public void updateDatasets(Double[] data, String[] graph, String[] categories, 
                               DefaultCategoryDataset categoryDataset) 
    {
        for (int ii = 0; ii < graph.length; ii++) 
        {   
            for (int jj = 0; jj < categories.length; jj++) 
            {   
                // Récupération de la valeur
                Double value = data[ii]; 
    
                if (value != null) 
                {
                    categoryDataset.addValue(value, categories[jj], graph[ii]);
                }
                else 
                {
                    // Vérifier si la clé existe déjà avant d'appeler getValue()
                    boolean keyExists = categoryDataset.getColumnIndex(graph[ii]) >= 0 &&
                                            categoryDataset.getRowIndex(categories[jj]) >= 0;
    
                    // Si la clé n'existe pas, ajouter 0
                    if (!keyExists)  
                    {
                        categoryDataset.addValue(0.0, categories[jj], graph[ii]);
                    }
                }
            }
        }
    }

    /*********************************************************** 
                   Onglet Annuel / Mensuel (Pan4)
    ***********************************************************/

    public void updateDatasets(Double[] data, String[] graph, String[] categories, 
                               DefaultCategoryDataset categoryDataset1, DefaultCategoryDataset categoryDataset2) 
    {
        for (int ii = 0; ii < graph.length; ii++) 
        {   
            for (int jj = 0; jj < categories.length; jj++) 
            {   
                // Récupération de la valeur
                Double value = data[ii]; 
    
                if (value != null) 
                {
                    categoryDataset1.addValue(value, categories[jj], graph[ii]);
                    categoryDataset2.addValue(value, categories[jj], graph[ii]);
                }
                else 
                {
                    // Vérifier si la clé existe déjà avant d'appeler getValue()
                    boolean keyExists = categoryDataset1.getColumnIndex(graph[ii]) >= 0 &&
                                            categoryDataset1.getRowIndex(categories[jj]) >= 0;
    
                    // Si la clé n'existe pas, ajouter 0
                    if (!keyExists)  
                    {
                        categoryDataset1.addValue(0.0, categories[jj], graph[ii]);
                    }
                }
            }
        }
    }
}