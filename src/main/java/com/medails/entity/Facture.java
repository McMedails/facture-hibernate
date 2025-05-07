package com.medails.entity;

import jakarta.persistence.*;

/*
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>6.4.4.Final</version> 
</dependency>

<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>3.1.0</version>
</dependency>
*/


@Entity
@Table(name = "facture")
public class Facture
{
    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Facture() {}

    /************************************************************ 
                            VARIABLES
    *************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long    id;

    @Column(name = "FactureAnnee")
    private int     FactureAnnee;

    @Column(name = "FactureMois")
    private String  FactureMois;

    @Column(name = "VersementAnnee")
    private int     VersementAnnee;

    @Column(name = "VersementMois")
    private String  VersementMois;

    @Column(name = "VersementJour")
    private int     VersementJour;

    @Column(name = "Jours")
    private double  Jours;

    @Column(name = "TJM")
    private double  TJM;

    @Column(name = "TTC")
    private double  TTC;

    @Column(name = "HT")
    private double  HT;

    @Column(name = "TVA")
    private double  TVA;

    @Column(name = "Taxes")
    private double  Taxes;

    @Column(name = "Benefices")
    private double  Benefices;

    @Column(name = "RepFacture")
    private String  RepFacture;

    @Column(name = "RepDecla")
    private String  RepDecla;

    @Column(name = "NameFacture")
    private String  NameFacture;

    @Column(name = "NameDecla")
    private String  NameDecla;


    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Facture( int     FactureAnnee,
                    String  FactureMois,
                    int     VersementAnnee,
                    String  VersementMois,
                    int     VersementJour,
                    double  Jours,
                    double  TJM,
                    double  TTC,
                    double  HT,
                    double  TVA,
                    double  Taxes,
                    double  Benefices,
                    String  RepFacture,
                    String  RepDecla,
                    String  NameFacture,
                    String  NameDecla )
    {
                    this.FactureAnnee    =  FactureAnnee;
                    this.FactureMois     =  FactureMois; 
                    this.VersementAnnee  =  VersementAnnee;
                    this.VersementMois   =  VersementMois; 
                    this.VersementJour   =  VersementJour; 
                    this.Jours           =  Jours;         
                    this.TJM             =  TJM;           
                    this.TTC             =  TTC;           
                    this.HT              =  HT;            
                    this.TVA             =  TVA;           
                    this.Taxes           =  Taxes;         
                    this.Benefices       =  Benefices;     
                    this.RepFacture      =  RepFacture;    
                    this.RepDecla        =  RepDecla;      
                    this.NameFacture     =  NameFacture;   
                    this.NameDecla       =  NameDecla;     
    }   
 

    /************************************************************ 
                          GETTERS / SETTERS
    *************************************************************/

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getFactureAnnee() {
        return FactureAnnee;
    }
    public void setFactureAnnee(int factureAnnee) {
        FactureAnnee = factureAnnee;
    }
    public String getFactureMois() {
        return FactureMois;
    }
    public void setFactureMois(String factureMois) {
        FactureMois = factureMois;
    }
    public int getVersementAnnee() {
        return VersementAnnee;
    }
    public void setVersementAnnee(int versementAnnee) {
        VersementAnnee = versementAnnee;
    }
    public String getVersementMois() {
        return VersementMois;
    }
    public void setVersementMois(String versementMois) {
        VersementMois = versementMois;
    }
    public int getVersementJour() {
        return VersementJour;
    }
    public void setVersementJour(int versementJour) {
        VersementJour = versementJour;
    }
    public double getJours() {
        return Jours;
    }
    public void setJours(double jours) {
        Jours = jours;
    }
    public double getTJM() {
        return TJM;
    }
    public void setTJM(double tJM) {
        TJM = tJM;
    }
    public double getTTC() {
        return TTC;
    }
    public void setTTC(double tTC) {
        TTC = tTC;
    }
    public double getHT() {
        return HT;
    }
    public void setHT(double hT) {
        HT = hT;
    }
    public double getTVA() {
        return TVA;
    }
    public void setTVA(double tVA) {
        TVA = tVA;
    }
    public double getTaxes() {
        return Taxes;
    }
    public void setTaxes(double taxes) {
        Taxes = taxes;
    }
    public double getBenefices() {
        return Benefices;
    }
    public void setBenefices(double benefices) {
        Benefices = benefices;
    }
    public String getRepFacture() {
        return RepFacture;
    }
    public void setRepFacture(String repFacture) {
        RepFacture = repFacture;
    }
    public String getRepDecla() {
        return RepDecla;
    }
    public void setRepDecla(String repDecla) {
        RepDecla = repDecla;
    }
    public String getNameFacture() {
        return NameFacture;
    }
    public void setNameFacture(String nameFacture) {
        NameFacture = nameFacture;
    }
    public String getNameDecla() {
        return NameDecla;
    }
    public void setNameDecla(String nameDecla) {
        NameDecla = nameDecla;
    }
}