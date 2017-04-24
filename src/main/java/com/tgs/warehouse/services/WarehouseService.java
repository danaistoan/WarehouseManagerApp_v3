package com.tgs.warehouse.services;

import java.util.ArrayList;
import java.util.List;

import com.tgs.warehouse.dao.LogisticUnitDAO;
import com.tgs.warehouse.entities.ProductPackage;
import com.tgs.warehouse.entities.ProductPallet;

public class WarehouseService {

	public ProductPallet saveProductPallet(String palletDescription, String package1Type, String package1Description, 
											String package2Type, String package2Description){
		
		LogisticUnitDAO logisticUnit = new LogisticUnitDAO();
		
		List<ProductPackage> packages = new ArrayList<ProductPackage>();

		ProductPackage pack1 = new ProductPackage();
		pack1.setType(package1Type);
		pack1.setDescription(package1Description);

		ProductPackage pack2 = new ProductPackage();
		pack2.setType(package2Type);
		pack2.setDescription(package2Description);

		packages.add(pack1);
		packages.add(pack2);

		ProductPallet pallet = new ProductPallet();
		pallet.setDescription(palletDescription);
		pallet.setPackages(packages);

		ProductPallet palletSaved = logisticUnit.insertProductPallet(pallet);
		
		return palletSaved;
	}
}
