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
@Table(name = "deduction")
public class Deduction
{
    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Deduction() {}

    /************************************************************ 
                            VARIABLES
    *************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long    id;

    @Column(name = "DeductionAnnee")
    private int     DeductionAnnee;

    @Column(name = "DeductionMois")
    private String  DeductionMois;

    @Column(name = "DeductionJour")
    private int     DeductionJour;

    @Column(name = "Annee")
    private int     Annee;

    @Column(name = "TTC")
    private double  TTC;

    @Column(name = "HT")
    private double  HT;

    @Column(name = "TVA")
    private double  TVA;   

    @Column(name = "RepDeduction")
    private String  RepDeduction;

    @Column(name = "NameDeduction")
    private String  NameDeduction;


    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Deduction ( int     DeductionAnnee,
                       String  DeductionMois,
                       int     DeductionJour,
                       int     Annee,
                       double  TTC,
                       double  HT,
                       double  TVA,
                       String  RepDeduction,
                       String  NameDeduction )
    {
                       this.DeductionAnnee   =   DeductionAnnee;
                       this.DeductionMois    =   DeductionMois;
                       this.DeductionJour    =   DeductionJour;
                       this.Annee            =   Annee;
                       this.TTC              =   TTC;
                       this.HT               =   HT;
                       this.TVA              =   TVA;
                       this.RepDeduction     =   RepDeduction;
                       this.NameDeduction    =   NameDeduction;
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


    public int getDeductionAnnee() {
        return DeductionAnnee;
    }


    public void setDeductionAnnee(int deductionAnnee) {
        DeductionAnnee = deductionAnnee;
    }


    public String getDeductionMois() {
        return DeductionMois;
    }


    public void setDeductionMois(String deductionMois) {
        DeductionMois = deductionMois;
    }


    public int getDeductionJour() {
        return DeductionJour;
    }


    public void setDeductionJour(int deductionJour) {
        DeductionJour = deductionJour;
    }


    public int getAnnee() {
        return Annee;
    }


    public void setAnnee(int annee) {
        Annee = annee;
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


    public String getRepDeduction() {
        return RepDeduction;
    }


    public void setRepDeduction(String repDeduction) {
        RepDeduction = repDeduction;
    }


    public String getNameDeduction() {
        return NameDeduction;
    }


    public void setNameDeduction(String nameDeduction) {
        NameDeduction = nameDeduction;
    }
}