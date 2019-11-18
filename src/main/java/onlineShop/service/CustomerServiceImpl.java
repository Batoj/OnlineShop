package onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CustomerDao;
import onlineShop.model.Customer;

//service 调用dao dao调用controller
//@Service让Spring初始化对象
@Service
public class CustomerServiceImpl implements CustomerService {	
    
    @Autowired
    private CustomerDao customerDao;

    public void addCustomer(Customer customer) {   	 
   	 customerDao.addCustomer(customer);
    }

    public Customer getCustomerByUserName(String userName) {
   	 return customerDao.getCustomerByUserName(userName);
    }
}
