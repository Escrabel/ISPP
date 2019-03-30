
package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TattooService;
import controllers.AbstractController;
import domain.Tattoo;

@Controller
@RequestMapping("/buy/customer")
public class BuyCustomerController extends AbstractController {

	@Autowired
	private TattooService	tattoService;


	// Edit Customer GET
	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false) final Integer tattooId) {
		final ModelAndView result;
//		if (tattooId != null && tattooId != 0) {
			final Tattoo tattoo = this.tattoService.findOne(tattooId);
			result = new ModelAndView("buy/response");
			result.addObject("tattoo", tattoo);
//		} else {
//
//			result = new ModelAndView("buy/response");
//			result.addObject("error", true);
//		}

		return result;
	}

}
