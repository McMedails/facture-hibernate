package com.medails.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.medails.config.HibernateUtil;
import com.medails.dao.ChomageRepository;

import com.medails.entity.Chomage;

    /*********************************************************** 
                    BDD MySQL -> Table Chomage
    ***********************************************************/

public class ChomageRepositoryImpl implements ChomageRepository
{
    /************************************************************ 
                                Onglet 4
    *************************************************************/

    /****************** Save ****************/

    public void save(Chomage chomage)
    {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(chomage);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
 

    /****************** FindAll ****************/

    public List<Chomage> findAll()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.createQuery("FROM Chomage", Chomage.class).list();
        }
    }


    /****************** FindbyId ****************/

    public Chomage findById(long id)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.get(Chomage.class, id);
        }
    }


    /****************** FindbyName ****************/

    @Override
    public Optional<Chomage> findByNameChomage(String nameChomage)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Query<Chomage> query = session.createQuery("FROM Chomage WHERE NameChomage = :name", Chomage.class);
            query.setParameter("name", nameChomage);
            return query.uniqueResultOptional();
        }
    }


    /****************** Delete ****************/

    public void delete (Chomage chomage)
    {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.remove(chomage);
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

    // Méthode pour récupèration de la colonne RepChomage
    @Override
    public List<String> getDirectoryRepChomage()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<String> rep = session.createQuery("SELECT DISTINCT f.RepChomage FROM Chomage f WHERE f.RepChomage IS NOT NULL",
                                                                                                    String.class).getResultList();

            rep.removeIf(s -> s == null || s.trim().isEmpty());
            Collections.sort(rep);       
            
            return rep;
        }
    }


    /************************************************************ 
                          AVEC AJOUT PREFIXE
    *************************************************************/

    // Méthode pour récupèration de la colonne NameChomage
    @Override
    public List<String> getPDFNameChomage()
    {        
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<String> name = session.createQuery("SELECT DISTINCT f.NameChomage FROM Chomage f WHERE f.NameChomage IS NOT NULL",
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