/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhuep.web_development.pizzapolis.util;

import com.jhuep.web_development.pizzapolis.entity.Toppings;
import java.util.List;
import java.util.function.Consumer;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author newcojd1
 */
public class DbUtils {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdownSessionFactory() {
        sessionFactory.close();
    }

    public static class ClosableSession implements AutoCloseable {

        private final Session session;

        public ClosableSession(Session session) {
            this.session = session;
        }

        @Override
        public void close() {
            try {
                session.flush();
            } catch (Exception ex) {
                //gulp
            }
            try {
                session.close();
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }

        public Session getSession() {
            return session;
        }
    }

    public static ClosableSession buildSession() {
        return new ClosableSession(sessionFactory.openSession());
    }

//    public static List<Reservation> getAllReservations() {
//        try (ClosableSession cs = buildSession()) {
//            Session s = cs.getSession();
//            List<Reservation> reservations = s.createQuery("FROM Reservation").list();
//            reservations.forEach(Reservation::toString); // Tostring Eagerly fetches fields, and allows us to close the session.
//            return reservations;
//        }
//    }
//
//    public static List<Reservation> getReservationsAfterSpecifiedStart(Date start) {
//        return getAllReservations()
//                .stream()
//                .filter(r -> r.getStartDay().after(start))
//                .collect(Collectors.toList());
//    }
    @SuppressWarnings("unchecked")
    public static List<Toppings> getAllToppings() {
        try (ClosableSession cs = buildSession()) {
            Session s = cs.getSession();
//            List<Toppings> toppings = s.createQuery("SELECT * FROM toppings").list();
            List<Toppings> toppings = (List<Toppings>) s.createCriteria(Toppings.class).list();
            toppings.forEach(Toppings::toString); // Tostring Eagerly fetches fields, and allows us to close the session.
            return toppings;
        }
    }

    public static void inTransaction(Consumer<Session> sessionHandler) {
        Transaction transaction = null;
        try (ClosableSession closableSession = DbUtils.buildSession()) {
            Session session = closableSession.getSession();
            transaction = session.getTransaction();
            transaction.begin();
            sessionHandler.accept(session);
            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception ex2) {
                    //Gulp
                }
            }
            throw ex;
        }
    }

    public static void main(String[] args) {
        getAllToppings().forEach(System.out::println);
        sessionFactory.close();
//        for (Reservation r : getAllReservations()) {
//            System.out.println("r = " + r);
//        }
    }
}
