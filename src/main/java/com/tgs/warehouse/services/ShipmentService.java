package com.tgs.warehouse.services;

import com.tgs.warehouse.dao.PlannedShipmentDAO;
import com.tgs.warehouse.dao.ShipmentDAO;
import com.tgs.warehouse.entities.PlannedShipment;
import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.entities.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by dana on 5/7/2017.
 */
@Service
public class ShipmentService {

    private ShipmentDAO shipmentDAO;
    private PlannedShipmentDAO plannedShipmentDAO;

    @Autowired
    public ShipmentService(ShipmentDAO shipmentDAO, PlannedShipmentDAO plannedShipmentDAO) {
        Objects.requireNonNull(shipmentDAO);
        Objects.requireNonNull(plannedShipmentDAO);
        this.shipmentDAO = shipmentDAO;
        this.plannedShipmentDAO = plannedShipmentDAO;
    }

    // SaveShipment after checking the completed status
    public void saveShipment(Long plannedShipmentId, List<ProductPallet> palletListToShip){

        Shipment shipment = new Shipment();
        boolean completed = false;
        PlannedShipment plannedShipment = plannedShipmentDAO.getPlannedShipmentById(plannedShipmentId);
        int plannedShipmentQty = plannedShipment.getQuantity();
        int noOfPalletsToShip = palletListToShip.size();
        if(noOfPalletsToShip == plannedShipmentQty){
            completed = true;
        }
        shipment.setPlannedShipmentId(plannedShipmentId);
        shipment.setCompleted(completed);
        shipment.setProductPalletList(palletListToShip);
        shipmentDAO.insertShipment(shipment);
    }

    // Search shipment by PlannedShipmentID
    public List<Shipment> searchShipments(String searchValue){

        Long planned_shipment_id = Long.parseLong(searchValue);
        List<Shipment> resultShipments = shipmentDAO.searchShipments(planned_shipment_id);

        return resultShipments;
    }

    // Retrieve all shipments from DB
    public List<Shipment> getAllShipments() {

        List<Shipment> allShipmentsList = shipmentDAO.getAllShipments();
        return allShipmentsList;
    }

    // Deletes shipment by ShipmentID
    public boolean deleteShipment(Long shipmentId) {

        boolean shpmDeleteSuccess = shipmentDAO.deleteShipment(shipmentId);
        return shpmDeleteSuccess;
    }

    // Update shipment by retrieving first the existent shipment in DB
    public void updateShipment(Long shipmentId, Long plannedShipmentId, List<ProductPallet> palletListToShip){

        boolean completed = false;
        PlannedShipment plannedShipment = plannedShipmentDAO.getPlannedShipmentById(plannedShipmentId);
        int plannedShipmentQty = plannedShipment.getQuantity();
        int noOfPalletsToShip = palletListToShip.size();
        if(noOfPalletsToShip == plannedShipmentQty){
            completed = true;
        }

        Shipment oldShipment = shipmentDAO.getShipmentById(shipmentId);
        oldShipment.setProductPalletList(palletListToShip);
        oldShipment.setCompleted(completed);
        shipmentDAO.updateShipment(oldShipment);
    }
}
