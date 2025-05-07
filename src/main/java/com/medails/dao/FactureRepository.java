package com.medails.dao;

import java.util.List;
import java.util.Optional;

import javax.management.Query;

import com.medails.config.HibernateUtil;

import com.medails.entity.Facture;

    /************************************************************ 
                               Interface
    *************************************************************/

public interface FactureRepository 
{
    /* f-1a */ Optional<Facture> findByNameFacture(String nameFacture);
    /* f-1b */ Optional<Facture> findByNameDecla(String nameDecla);

    /* f-2 */ void save (Facture facture);

    /* f-3 */ List<Facture> findAll();

    /* f-4 */ Facture findById(long id);

    /* f-5 */ void delete (Facture facture);

    /* f-6 */ List<String> getDirectoryRepFacture();
    /* f-7 */ List<String> getDirectoryRepDecla();
    /* f-8 */ List<String> getPDFNameFacture();
    /* f-9 */ List<String> getPDFNameDecla();
}