package com.tgs.warehouse.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dana on 4/25/2017.
 */

@Entity
@Table(name="\"shipment\"")
public class Shipment implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="planned_shipment_id", nullable = false)
    private Long plannedShipmentId;

    @Column(name="completed", nullable = false)
    private boolean completed;

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.REFRESH)
    @JoinColumn(name = "planned_shipment_id", referencedColumnName="id", insertable = false, updatable = false)
    private PlannedShipment plannedShipment;

    /*
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.REFRESH)
    private List<ProductPallet> productPalletList;
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlannedShipmentId() {
        return plannedShipmentId;
    }

    public void setPlannedShipmentId(Long plannedShipmentId) {
        this.plannedShipmentId = plannedShipmentId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public PlannedShipment getPlannedShipment() {
        return plannedShipment;
    }

    public void setPlannedShipment(PlannedShipment plannedShipment) {
        this.plannedShipment = plannedShipment;
    }
}
