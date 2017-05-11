package com.tgs.warehouse.dao;


import java.util.List;
import java.util.Objects;
import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import com.tgs.warehouse.entities.ProductPallet;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PalletDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public PalletDAO(SessionFactory sessionFactory) {
		Objects.requireNonNull(sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public ProductPallet insertProductPallet(ProductPallet productPallet) {

		Session session = sessionFactory.getCurrentSession();
		Long palletId = (Long) session.save(productPallet);
		productPallet.setId(palletId);

		return productPallet;
	}

	@Transactional
	public boolean deleteProductPallet(Long productPalletId) {

		Session session = sessionFactory.getCurrentSession();
		ProductPallet productPallet = session.get(ProductPallet.class, productPalletId);
		session.delete(productPallet);
		System.out.println("Pallet with " + productPalletId + " deleted");

		return true;
	}

	@Transactional
	public List<ProductPallet> search(String description) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "select distinct p from ProductPallet p inner join p.packages pk where lower(p.description)"
				+ " like lower(:descr) or lower(pk.description) like lower(:descr) order by p.id";
		TypedQuery<ProductPallet> query = session.createQuery(hql, ProductPallet.class);
		query.setParameter("descr", "%" + description + "%");
		List<ProductPallet> palletList = query.getResultList();
		System.out.println("SearchedPallets with Hibernate executed");

		return palletList;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ProductPallet> getAllPallets() {

		Session session = sessionFactory.getCurrentSession();
		String hql = "from ProductPallet pp order by pp.id";
		TypedQuery<ProductPallet> query = session.createQuery(hql);
		List<ProductPallet> palletList = query.getResultList();
		System.out.println("GetAllPallets with Hibernate executed");

		return palletList;
	}

	@Transactional
	public ProductPallet getProductPalletById(Long productPalletId) {

		Session session = sessionFactory.getCurrentSession();
		ProductPallet productPallet = session.get(ProductPallet.class, productPalletId);

		return productPallet;
	}

    @SuppressWarnings("unchecked")
    @Transactional
    public List<ProductPallet> getPalletsWithoutShipment() {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(
                "select p.id, p.description \n" +
                        "from product_pallet p left join shipment_detail sd on p.id = sd.product_pallet_id\n" +
                        "where sd.product_pallet_id is null;")
                .addEntity(ProductPallet.class);
        List resultPalletList = query.list();

        return resultPalletList;
    }
}
