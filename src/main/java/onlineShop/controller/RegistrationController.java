package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Customer;
import onlineShop.service.CustomerService;

@Controller
public class RegistrationController {
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {		//第一步得到表单
		Customer customer = new Customer();
		return new ModelAndView("register", "customer", customer);
	}

	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)	//点submit之后
	public ModelAndView registerCustomer(@ModelAttribute(value = "customer") Customer customer,  
			BindingResult result) {			//ModelAttribute把填的信息弄到customer
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName("register");
			return modelAndView;
		}
		customerService.addCustomer(customer);	
		modelAndView.setViewName("login");
		modelAndView.addObject("registrationSuccess", "Registered Successfully. Login using username and password");
		return modelAndView;
	}
}
