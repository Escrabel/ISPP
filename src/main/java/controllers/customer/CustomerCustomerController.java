package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Customer;
import services.CustomerService;

@Controller
@RequestMapping("/customer/customer")
public class CustomerCustomerController extends AbstractController {

	@Autowired
	CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public CustomerCustomerController() {
		super();
	}

	// Edit Customer GET
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final ModelAndView result;
		final Customer customer = this.customerService.findByPrincipal();
		result = this.createEditModelAndView(customer, "edit", null);
		
		return result;
	}

	// Save Customer
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			assert customer != null;
			result = this.createEditModelAndView(customer, "edit", "customer.error.save");

		} else
			try {
				Assert.isTrue(customer.getId()==this.customerService.findByPrincipal().getId());
				this.customerService.save(customer);
				result = new ModelAndView("welcome/index");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(customer, "create", "customer.commit.error");
			}
		return result;
	}

	// View Tattoo
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		final Customer customer = this.customerService.findByPrincipal();

		result = this.createEditModelAndView(customer, "view", null);

		return result;
	}

	// Metodos create
	protected ModelAndView createEditModelAndView(final Customer customer, final String action, final String msgCode) {
		final ModelAndView result = new ModelAndView("customer/" + action);

		result.addObject("customer", customer);
		result.addObject("message", msgCode);
		result.addObject("requestURI", "customer/customer/save.do");

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		Customer customer = null;
		try {
			customer = this.customerService.findByPrincipal(); 
			this.customerService.delete(customer);

			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (Throwable oops) {
			System.out.println("opps: " + oops);
			result = this.deleteModelAndView(customer, "view", "customer.commit.error");
		}

		return result;
	}

	protected ModelAndView deleteModelAndView(Customer customer, String action, String msgCode) {
		ModelAndView result = new ModelAndView("customer/" + action);

		result.addObject("customer", customer);
		result.addObject("message", msgCode);

		return result;
	}
}
