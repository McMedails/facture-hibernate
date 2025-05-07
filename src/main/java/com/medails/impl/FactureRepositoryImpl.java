package com.medails.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.medails.config.HibernateUtil;
import com.medails.dao.FactureRepository;

import com.medails.entity.Facture;

    /*********************************************************** 
                    BDD MySQL -> Table Facture
    ***********************************************************/

public class FactureRepositoryImpl implements FactureRepository
{
    /************************************************************ 
                                Onglet 1
    *************************************************************/

    /****************** Save ****************/

    public void save(Facture facture)
    {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(facture);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
 

    /****************** FindAll ****************/

    public List<Facture> findAll()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.createQuery("FROM Facture", Facture.class).list();
        }
    }


    /****************** FindbyId ****************/

    public Facture findById(long id)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.get(Facture.class, id);
        }
    }


    /****************** FindbyName ****************/

    @Override
    public Optional<Facture> findByNameFacture(String nameFacture)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Query<Facture> query = session.createQuery("FROM Facture WHERE NameFacture = :name", Facture.class);
            query.setParameter("name", nameFacture);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public Optional<Facture> findByNameDecla(String nameDecla)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Query<Facture> query = session.createQuery("FROM Facture WHERE NameDecla = :name", Facture.class);
            query.setParameter("name", nameDecla);
            return query.uniqueResultOptional();
        }
    }


    /****************** Delete ****************/

    public void delete (Facture facture)
    {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.remove(facture);
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

    // Méthode pour récupèration de la colonne RepFacutre
    @Override
    public List<String> getDirectoryRepFacture()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<String> rep = session.createQuery("SELECT DISTINCT f.RepFacture FROM Facture f WHERE f.RepFacture IS NOT NULL",
                                                                                                    String.class).getResultList();

            rep.removeIf(s -> s == null || s.trim().isEmpty());
            Collections.sort(rep);       
            
            return rep;
        }
    }

    // Méthode pour récupèration de la colonne RepDecla
    @Override
    public List<String> getDirectoryRepDecla()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<String> rep = session.createQuery("SELECT DISTINCT f.RepDecla FROM Facture f WHERE f.RepDecla IS NOT NULL",
                                                                                                    String.class).getResultList();

            rep.removeIf(s -> s == null || s.trim().isEmpty());
            Collections.sort(rep);       
            
            return rep;
        }
    }

    /************************************************************ 
                          AVEC AJOUT PREFIXE
    *************************************************************/

    // Méthode pour récupèration de la colonne NameFacture
    @Override
    public List<String> getPDFNameFacture()
    {        
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<String> name = session.createQuery("SELECT DISTINCT f.NameFacture FROM Facture f WHERE f.NameFacture IS NOT NULL",
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

    // Méthode pour récupèration de la colonne NameDecla
    @Override
    public List<String> getPDFNameDecla()
    {        
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<String> name = session.createQuery("SELECT DISTINCT f.NameDecla FROM Facture f WHERE f.NameDecla IS NOT NULL",
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