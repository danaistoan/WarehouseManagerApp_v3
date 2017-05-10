package com.tgs.warehouse.dao;

import com.tgs.warehouse.entities.PlannedShipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

/**
 * Created by dana on 5/7/2017.
 */
@Repository
public class PlannedShipmentDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public PlannedShipmentDAO(SessionFactory sessionFactory) {
        Objects.requireNonNull(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void insertPlannedShipment(PlannedShipment plannedShipment) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(plannedShipment);
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

    @SuppressWarnings("unchecked")
    @Transactional
    public List<PlannedShipment> getPlannedShipmentsWithoutShipment() {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(
                "select ps.id, ps.customer_name, ps.quantity \n" +
                        "from planned_shipment ps left join shipment s on ps.id = s.planned_shipment_id\n" +
                        "where s.planned_shipment_id is null;")
                .addEntity(PlannedShipment.class);
        List resultPlannedShipmentList = query.list();

        return resultPlannedShipmentList;
    }

    @Transactional
    public PlannedShipment getPlannedShipmentById(Long plannedShipmentId) {

        Session session = sessionFactory.getCurrentSession();
        PlannedShipment plannedShipment = session.get(PlannedShipment.class, plannedShipmentId);

        return plannedShipment;
    }
}
