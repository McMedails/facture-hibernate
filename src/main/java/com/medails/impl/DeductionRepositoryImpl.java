package com.medails.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.medails.config.HibernateUtil;
import com.medails.dao.DeductionRepository;

import com.medails.entity.Deduction;

    /*********************************************************** 
                    BDD MySQL -> Table Deduction
    ***********************************************************/

public class DeductionRepositoryImpl implements DeductionRepository
{
    /************************************************************ 
                                Onglet 3
    *************************************************************/

    /****************** Save ****************/

    public void save(Deduction deduction)
    {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(deduction);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
 

    /****************** FindAll ****************/

    public List<Deduction> findAll()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.createQuery("FROM Deduction", Deduction.class).list();
        }
    }


    /****************** FindbyId ****************/

    public Deduction findById(long id)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.get(Deduction.class, id);
        }
    }


    /****************** FindbyName ****************/

    @Override
    public Optional<Deduction> findByNameDeduction(String nameDeduction)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Query<Deduction> query = session.createQuery("FROM Deduction WHERE NameDeduction = :name", Deduction.class);
            query.setParameter("name", nameDeduction);
            return query.uniqueResultOptional();
        }
    }


    /****************** Delete ****************/

    public void delete (Deduction deduction)
    {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.remove(deduction);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    /************************************************************ 
                          POUR POPUPLISTENER
    *************************************************************/

    // Méthode pour récupèration de la colonne RepDeduction
    @Override
    public List<String> getDirectoryRepDeduction()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<String> rep = session.createQuery("SELECT DISTINCT f.RepDeduction FROM Deduction f WHERE f.RepDeduction IS NOT NULL",
                                                                                                    String.class).getResultList();

            rep.removeIf(s -> s == null || s.trim().isEmpty());
            Collections.sort(rep);       
            
            return rep;
        }
    }


    /************************************************************ 
                          AVEC AJOUT PREFIXE
    *************************************************************/

    // Méthode pour récupèration de la colonne NameDeduction
    @Override
    public List<String> getPDFNameDeduction()
    {        
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<String> name = session.createQuery("SELECT DISTINCT f.NameDeduction FROM Deduction f WHERE f.NameDeduction IS NOT NULL",
                                                                                                      String.class).getResultList();

            name.removeIf(n -> n == null || n.trim().isEmpty());

            Collections.sort(name);

            List<String> prefixed = new ArrayList<>();

            for (int ii = 0; ii < name.size(); ii++)
            {
                prefixed.add((ii + 1) + "-  " + name.get(ii));
            }

            return prefixed;
        }        
    }
}