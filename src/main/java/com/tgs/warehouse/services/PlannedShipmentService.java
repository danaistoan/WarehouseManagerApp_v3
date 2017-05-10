package com.tgs.warehouse.services;

import com.tgs.warehouse.dao.PlannedShipmentDAO;
import com.tgs.warehouse.entities.PlannedShipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by dana on 5/10/2017.
 */

@Service
public class PlannedShipmentService {

    private PlannedShipmentDAO plannedShipmentDAO;

    @Autowired
    public PlannedShipmentService(PlannedShipmentDAO plannedShipmentDAO) {
        Objects.requireNonNull(plannedShipmentDAO);
        this.plannedShipmentDAO = plannedShipmentDAO;
    }

    // Saves new Planned Shipment in DB
    public void savePlannedShipment(PlannedShipment plannedShipment) {

        plannedShipmentDAO.insertPlannedShipment(plannedShipment);
    }

    // Search Planned Shipment by customer name
    public List<PlannedShipment> searchPlannedShipments(String customerName) {

        List<PlannedShipment> resultPlannedShipments = plannedShipmentDAO.searchPlannedShipments(customerName);
        return resultPlannedShipments;
    }

    // Retrieve all Planned Shipments from DB
    public List<PlannedShipment> getAllPlannedShipments() {

        List<PlannedShipment> allPlannedShipments = plannedShipmentDAO.getAllPlannedShipments();
        return allPlannedShipments;
    }

    // Retrieve Planned Shipments not included in a Shipment
    public List<PlannedShipment> getPlannedShipmentsWithoutShipment() {

        List<PlannedShipment> nonShippedPlannedShipments = plannedShipmentDAO.getPlannedShipmentsWithoutShipment();
        return nonShippedPlannedShipments;
    }

    public boolean deletePlannedShipment(Long shipmentId) {

        boolean deleteSuccess = plannedShipmentDAO.deletePlannedShipment(shipmentId);
        return deleteSuccess;
    }
}
