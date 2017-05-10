package com.tgs.warehouse.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dana on 4/25/2017.
 */

@Entity
@Table(name="\"planned_shipment\"")
public class PlannedShipment implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="customer_name", nullable = false)
    private String customerName;

    @Column(name="quantity", nullable = false)
    private int quantity;

    public PlannedShipment(){
    }

    public PlannedShipment(Long plannedShipmentId){
        this.id = plannedShipmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
