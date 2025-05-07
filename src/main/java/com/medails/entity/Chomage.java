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
@Table(name = "chomage")
public class Chomage
{
    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Chomage() {}

    /************************************************************ 
                            VARIABLES
    *************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long    id;

    @Column(name = "ChomageAnnee")
    private Integer ChomageAnnee;

    @Column(name = "ChomageMois")
    private String  ChomageMois;

    @Column(name = "ChomageJour")
    private Integer ChomageJour;

    @Column(name = "MoisActualisation")
    private String  MoisActualisation;

    @Column(name = "JourParMois")
    private Integer JourParMois;

    @Column(name = "Coefficient")
    private double  Coefficient;

    @Column(name = "Montant")
    private double  Montant;

    @Column(name = "RepChomage")
    private String  RepChomage;

    @Column(name = "NameChomage")
    private String  NameChomage;


    /************************************************************ 
                            CONSTRUCTEUR
    *************************************************************/

    public Chomage ( Integer ChomageAnnee,
                     String  ChomageMois,
                     Integer ChomageJour,
                     String  MoisActualisation,
                     Integer JourParMois,
                     double  Coefficient,
                     double  Montant,
                     String  RepChomage,
                     String  NameChomage )
    {
                     this.ChomageAnnee       =   ChomageAnnee;  
                     this.ChomageMois        =   ChomageMois;    
                     this.ChomageJour        =   ChomageJour;      
                     this.MoisActualisation  =   MoisActualisation;
                     this.JourParMois        =   JourParMois;
                     this.Coefficient        =   Coefficient;     
                     this.Montant            =   Montant;     
                     this.RepChomage         =   RepChomage;       
                     this.NameChomage        =   NameChomage;      
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

    public Integer getChomageAnnee() {
        return ChomageAnnee;
    }

    public void setChomageAnnee(Integer chomageAnnee) {
        ChomageAnnee = chomageAnnee;
    }

    public String getChomageMois() {
        return ChomageMois;
    }

    public void setChomageMois(String chomageMois) {
        ChomageMois = chomageMois;
    }

    public Integer getChomageJour() {
        return ChomageJour;
    }

    public void setChomageJour(Integer chomageJour) {
        ChomageJour = chomageJour;
    }

    public String getMoisActualisation() {
        return MoisActualisation;
    }

    public void setMoisActualisation(String moisActualisation) {
        MoisActualisation = moisActualisation;
    }

    public Integer getJourParMois() {
        return JourParMois;
    }

    public void setJourParMois(Integer jourParMois) {
        JourParMois = jourParMois;
    }

    public double getCoefficient() {
        return Coefficient;
    }

    public void setCoefficient(double coefficient) {
        Coefficient = coefficient;
    }

    public double getMontant() {
        return Montant;
    }

    public void setMontant(double montant) {
        Montant = montant;
    }

    public String getRepChomage() {
        return RepChomage;
    }

    public void setRepChomage(String repChomage) {
        RepChomage = repChomage;
    }

    public String getNameChomage() {
        return NameChomage;
    }

    public void setNameChomage(String nameChomage) {
        NameChomage = nameChomage;
    }   
}