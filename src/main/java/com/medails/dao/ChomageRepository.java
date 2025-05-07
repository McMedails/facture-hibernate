package com.medails.dao;

import java.util.List;
import java.util.Optional;

import javax.management.Query;

import com.medails.config.HibernateUtil;

import com.medails.entity.Chomage;

    /************************************************************ 
                               Interface
    *************************************************************/

public interface ChomageRepository 
{
    /* c-1 */ Optional<Chomage> findByNameChomage(String nameChomage);

    /* c-2 */ void save (Chomage chomage);

    /* c-3 */ List<Chomage> findAll();

    /* c-4 */ Chomage findById(long id);

    /* c-5 */ void delete (Chomage chomage);

    /* c-6 */ List<String> getDirectoryRepChomage();
    /* c-7 */ List<String> getPDFNameChomage(); 
}