package com.medails.dao;

import java.util.List;
import java.util.Optional;

import javax.management.Query;

import com.medails.config.HibernateUtil;

import com.medails.entity.Deduction;

    /************************************************************ 
                               Interface
    *************************************************************/

public interface DeductionRepository 
{
    /* d-1 */ Optional<Deduction> findByNameDeduction(String nameDeduction);

    /* d-2 */ void save (Deduction deduction);

    /* d-3 */ List<Deduction> findAll();

    /* d-4 */ Deduction findById(long id);

    /* d-5 */ void delete (Deduction deduction);

    /* d-6 */ List<String> getDirectoryRepDeduction();
    /* d-7 */ List<String> getPDFNameDeduction();
}