package com.tgs.warehouse.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_package")
public class ProductPackage implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="type")
	private String type;
	
	@Column(name="product_pallet_id")
	private Long palletId;
	
	//@ManyToOne
	//@JoinColumn(name="product_pallet_id", referencedColumnName="id")
	//ProductPallet product_pallet;
	
	public ProductPackage(){
		
	}
	
	public ProductPackage(String description, String type){
		this.description = description;
		this.type = type;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPalletId() {
		return palletId;
	}

	public void setPalletId(Long palletId) {
		this.palletId = palletId;
	}
	
}
