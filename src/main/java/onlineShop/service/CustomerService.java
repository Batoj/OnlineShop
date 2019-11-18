package onlineShop.service;

import onlineShop.model.Customer;

public interface CustomerService {

    void addCustomer(Customer customer); //add

    Customer getCustomerByUserName(String userName); //get
}
