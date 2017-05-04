package com.tgs.warehouse.dao;

import com.tgs.warehouse.entities.PlannedShipment;
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
    public void insertPlannedShipment(PlannedShipment plannedShipment) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(plannedShipment);
        //return plannedShipment;
    }

    @Transactional
    public boolean deletePlannedShipment(Long shipmentId) {

        Session session = sessionFactory.getCurrentSession();
        PlannedShipment plannedShipment = session.get(PlannedShipment.class, shipmentId);
        session.delete(plannedShipment);
        System.out.println("Pallet with " + plannedShipment + " deleted");

        return true;
    }

    @Transactional
    public List<PlannedShipment> searchPlannedShipments(String customerName) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "select ps from PlannedShipment ps where lower(ps.customerName)"
                + " like lower(:customerName) order by ps.id";
        TypedQuery<PlannedShipment> query = session.createQuery(hql, PlannedShipment.class);
        query.setParameter("customerName", "%" + customerName + "%");
        List<PlannedShipment> plannedShipments = query.getResultList();
        System.out.println("SearchPlanneShipments with Hibernate executed");

        return plannedShipments;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<PlannedShipment> getAllPlannedShipments() {

        Session session = sessionFactory.getCurrentSession();
        String hql = "from PlannedShipment ps order by ps.id";
        TypedQuery<PlannedShipment> query = session.createQuery(hql);
        List<PlannedShipment> plannedShipments = query.getResultList();
        System.out.println("GetAllPlannedShipments with Hibernate executed");

        return plannedShipments;
    }

    @Transactional
    public List<Shipment> searchShipments(String searchValue) {

        Long planned_shipment_id = Long.parseLong(searchValue);
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

    //Shipment saving has to save in 2 tables: shipment(planned_shipment_id, completed) & shipment_detail(shipment_id, PP_id)
    @Transactional
    public void insertShipment(Shipment shipment) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(shipment);
    }

    @Transactional
    public boolean deleteShipment(Long shipmentId) {

        Session session = sessionFactory.getCurrentSession();
        PlannedShipment plannedShipment = session.get(PlannedShipment.class, shipmentId);
        session.delete(plannedShipment);
        System.out.println("Pallet with " + plannedShipment + " deleted");

        return true;
    }
}
