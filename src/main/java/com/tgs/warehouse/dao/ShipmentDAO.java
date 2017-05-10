package com.tgs.warehouse.dao;

import com.tgs.warehouse.entities.Shipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

/**
 * Created by dana on 5/2/2017.
 */

@Repository
public class ShipmentDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public ShipmentDAO(SessionFactory sessionFactory) {
        Objects.requireNonNull(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Shipment> searchShipments(Long planned_shipment_id) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "select s from Shipment s inner join s.planned_shipment_id ps where s.planned_shipment_id=?";
        TypedQuery<Shipment> query = session.createQuery(hql, Shipment.class);
        query.setParameter("planned_shipment_id", "%" + planned_shipment_id + "%");
        List<Shipment> shipmentList = query.getResultList();
        System.out.println("SearchShipments with Hibernate executed");

        return shipmentList;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Shipment> getAllShipments() {

        Session session = sessionFactory.getCurrentSession();
        String hql = "from Shipment s order by s.id";
        TypedQuery<Shipment> query = session.createQuery(hql);
        List<Shipment> shipmentList = query.getResultList();
        System.out.println("GetAllShipments with Hibernate executed");

        return shipmentList;
    }

    // Saving the shipment inserts data in 2 tables: shipment(planned_shipment_id, completed) & shipment_detail(shipment_id, PP_id)
    @Transactional
    public void insertShipment(Shipment shipment) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(shipment);
        System.out.println("InsertShipment with Hibernate executed");
    }

    // Deletes from 2 tables: shipment & shipment_detail
    @Transactional
    public boolean deleteShipment(Long shipmentId) {

        Session session = sessionFactory.getCurrentSession();
        Shipment shipment = session.get(Shipment.class, shipmentId);
        session.delete(shipment);
        System.out.println("Pallet with " + shipment + " deleted");

        return true;
    }

    @Transactional
    public void updateShipment(Shipment shipment) {

        Session session = sessionFactory.getCurrentSession();
        session.update(shipment);
        System.out.println("Update Shipment with Hibernate executed");
    }

    @Transactional
    public Shipment getShipmentById(Long shipmentId) {

        Session session = sessionFactory.getCurrentSession();
        Shipment shipment = session.get(Shipment.class, shipmentId);

        return shipment;
    }
}
