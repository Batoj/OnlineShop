package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Cart;
import onlineShop.model.Customer;
import onlineShop.service.CartService;
import onlineShop.service.CustomerService;

@Controller
public class CartController {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	
	//find CartID
	@RequestMapping(value = "/cart/getCartById", method = RequestMethod.GET)
	public ModelAndView getCartId(){		//when login success save session to get authentication object
		ModelAndView modelAndView = new ModelAndView("cart");
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();  //get user
		String username = loggedInUser.getName();		//get user name thru authentication
		Customer customer = customerService.getCustomerByUserName(username);	//get user name from custromer service
		modelAndView.addObject("cartId", customer.getCart().getId());	
		return modelAndView;
	}
	
	@RequestMapping(value = "/cart/getCart/{cartId}", method = RequestMethod.GET)
	@ResponseBody
	public Cart getCartItems(@PathVariable(value="cartId")int cartId){
		return cartService.getCartById(cartId);
	}
}
