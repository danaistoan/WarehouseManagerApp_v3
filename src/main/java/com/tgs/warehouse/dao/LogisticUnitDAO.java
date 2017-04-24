package com.tgs.warehouse.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tgs.warehouse.entities.ProductPackage;
import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.util.HibernateUtil;

public class LogisticUnitDAO {

	public ProductPallet insertProductPallet(ProductPallet productPallet) {

		
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{	
			Transaction transaction = session.beginTransaction();
			Long palletId = (Long) session.save(productPallet);
			productPallet.setId(palletId);
			transaction.commit();
			
		} catch (Exception e) {
			System.out.println("Couldn't insert pallet with Hibernate");
			e.printStackTrace();		
		} 	
		return productPallet;
	}
	
	public boolean deleteProductPallet(Long productPalletId) {
		
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			Transaction transaction = session.beginTransaction();
			ProductPallet productPallet = session.get(ProductPallet.class, productPalletId);
			session.delete(productPallet);
			System.out.println("Pallet with " + productPalletId + " deleted");
			transaction.commit();
			
		} catch (Exception e) {
			System.out.println("Couldn't delete pallet with Hibernate");
			e.printStackTrace();		
		} 
		return true;
	}

	public List<ProductPallet> search(String description) {

		List<ProductPallet> palletList = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			Transaction transaction = session.beginTransaction();
			String hql = "select distinct p from ProductPallet p inner join p.packages pk where lower(p.description)"
					+ " like lower(:descr) or lower(pk.description) like lower(:descr) order by p.id";
			TypedQuery<ProductPallet> query = session.createQuery(hql, ProductPallet.class);
			query.setParameter("descr", "%" + description + "%");
			palletList = query.getResultList();  
			System.out.println("SearchedPallets with Hibernate executed");
			transaction.commit();
			
		} catch (Exception e) {
			System.out.println("Couldn't get searched pallets with Hibernate");
			e.printStackTrace();		
		} 
		return palletList;
	}

	@SuppressWarnings("unchecked")
	public List<ProductPallet> getAllPallets() {

		List<ProductPallet> palletList = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			Transaction transaction = session.beginTransaction();
			String hql = "from ProductPallet pp order by pp.id";
			TypedQuery<ProductPallet> query = session.createQuery(hql);
			palletList = query.getResultList();  
			System.out.println("GetAllPallets with Hibernate executed");
			transaction.commit();
			return palletList;
			
		} catch (Exception e) {
			System.out.println("Couldn't get pallets with Hibernate");
			e.printStackTrace();		
		} 
		return palletList;
	}

	public List<ProductPackage> loadPackagesByPalletId(Long palletId) {

		List<ProductPackage> packageList = new ArrayList<ProductPackage>();
		
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			Transaction transaction = session.beginTransaction();
			String hql = "select pk from ProductPackage pk where pk.palletId = :id";
			TypedQuery<ProductPackage> query = session.createQuery(hql, ProductPackage.class);
			query.setParameter("id", palletId);
			packageList = query.getResultList();  
			System.out.println("SearchedPackages with Hibernate executed");
			transaction.commit();
			
		} catch (Exception e) {
			System.out.println("Couldn't get searched packages with Hibernate");
			e.printStackTrace();		
		} 
		
		return packageList;
	}

	public ProductPallet loadPalletById(Long palletId){
		
		ProductPallet pallet = new ProductPallet();
		
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			Transaction transaction = session.beginTransaction();
			pallet = session.get(ProductPallet.class, palletId);
			transaction.commit();
			
		} catch (Exception e) {
			System.out.println("Couldn't get the pallet with Hibernate");
			e.printStackTrace();		
		} 
		return pallet;
	}
}
