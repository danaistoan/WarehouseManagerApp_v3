package com.tgs.warehouse.services;

import com.tgs.warehouse.dao.PalletDAO;
import com.tgs.warehouse.entities.ProductPallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by dana on 5/10/2017.
 */

@Service
public class PalletService {

    private PalletDAO palletDAO;

    @Autowired
    public PalletService(PalletDAO palletDAO){
        Objects.requireNonNull(palletDAO);
        this.palletDAO = palletDAO;
    }

    //Save new Product Pallet in DB
    public ProductPallet saveProductPallet(ProductPallet productPallet){

        ProductPallet savedProductPallet = palletDAO.insertProductPallet(productPallet);

        return  savedProductPallet;
    }

    // Delete Product Pallet from DB based on pallet Id
    public boolean deleteProductPallet(Long productPalletId) {

        boolean deletedSuccess = palletDAO.deleteProductPallet(productPalletId);

        return deletedSuccess;
    }

    // Search Product Pallet by description
    public List<ProductPallet> search(String description) {

        List<ProductPallet> resultPalletsList = palletDAO.search(description);

        return resultPalletsList;
    }

    // Retrieve all pallets from DB
    public List<ProductPallet> getAllPallets(){

        List<ProductPallet> allPalletsList = palletDAO.getAllPallets();
        return allPalletsList;
    }

    // Retrieve Pallet from DB by pallet Id
    public ProductPallet getProductPalletById(Long productPalletId) {

        ProductPallet resultProductPallet = palletDAO.getProductPalletById(productPalletId);
        return resultProductPallet;
    }

    // Retrieve pallets not bounded to a shipment
    public List<ProductPallet> getPalletsWithoutShipment() {

        List<ProductPallet> resultPalletsList = palletDAO.getPalletsWithoutShipment();
        return resultPalletsList;
    }
}
