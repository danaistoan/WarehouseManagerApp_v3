package com.tgs.warehouse.entities;

import javax.persistence.*;
import java.io.Serializable;

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

}
