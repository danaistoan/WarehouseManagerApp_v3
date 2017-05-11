package com.tgs.warehouse.entities;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

@Entity
@Table(name="product_pallet")
public class ProductPallet implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="description")
	private String description;
	
	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "product_pallet", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@OneToMany(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "product_pallet_id", referencedColumnName="id") // id e ala din pallet
	private List<ProductPackage> packages;
	
	public ProductPallet(){
	}

	public void setId(Long id) {
		this.id = id;		
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProductPackage> getPackages() {
		return packages;
	}

	public void setPackages(List<ProductPackage> packages) {		
		this.packages = packages;
	}		
}
