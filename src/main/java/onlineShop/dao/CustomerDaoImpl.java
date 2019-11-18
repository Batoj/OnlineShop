package onlineShop.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Authorities;
import onlineShop.model.Cart;
import onlineShop.model.Customer;
import onlineShop.model.User;

//create new object
@Repository
public class CustomerDaoImpl implements CustomerDao {

	//Hibernate session factory call autowired to inject
	@Autowired
	private SessionFactory sessionFactory;

	public void addCustomer(Customer customer) {
		customer.getUser().setEnabled(true);	//默认false 

		//security 权限问题 每个用户默认的权限
		Authorities authorities = new Authorities();
		authorities.setAuthorities("ROLE_USER");
		authorities.setEmailId(customer.getUser().getEmailId());

		//设置每个customer和cart的关系
		Cart cart = new Cart();
		cart.setCustomer(customer);
		customer.setCart(cart);					//cart cascade with customer no need to session.save
		
		Session session = null;

		try {
			session = sessionFactory.openSession();  //open session
			session.beginTransaction();			//对多张表进行操作 所以从transaction开始 保持atomicity
			session.save(authorities);			//authorities not cascade; needs to save
			session.save(customer);				//还在缓存
			session.getTransaction().commit();	//都ok就执行
		} catch (Exception e) {
			e.printStackTrace();					
			session.getTransaction().rollback();	//出错就rollback
		} finally {
			if (session != null) {
				session.close();				//最后close session
			}
		}
	}

	@Override
	public Customer getCustomerByUserName(String userName) {
		User user = null;
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);  //query哪个table
			Root<User> root = criteriaQuery.from(User.class);		//MySQL b-tree
			criteriaQuery.select(root).where(builder.equal(root.get("emailId"), userName)); //从节点开始搜如果email id == user name
			user = session.createQuery(criteriaQuery).getSingleResult();	//select where email == usermane
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null)
			return user.getCustomer();		//user id == 传进来的user name
		return null;
	}
}
