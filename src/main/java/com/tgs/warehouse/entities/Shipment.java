package com.tgs.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="planned_shipment_id", nullable = false)
    private Long plannedShipmentId;

    @Column(name="completed", nullable = false)
    private boolean completed;

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.REFRESH)
    @JoinColumn(name = "planned_shipment_id", referencedColumnName="id", insertable = false, updatable = false)
    private PlannedShipment plannedShipment;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(
            name = "shipment_detail",
            joinColumns = @JoinColumn(name = "shipment_id"),
            inverseJoinColumns = @JoinColumn(name = "product_pallet_id")
    )
    private List<ProductPallet> productPalletList;

    public Shipment(){
    }

    public Shipment(Long plannedShipmentId, boolean completed){
        this.plannedShipmentId = plannedShipmentId;
        this.completed = completed;
    }

    public List<ProductPallet> getProductPalletList() {
        return productPalletList;
    }

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

    public void setProductPalletList(List<ProductPallet> productPalletList) {
        this.productPalletList = productPalletList;
    }
}
