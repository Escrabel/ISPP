
package controllers.tattooist;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TattooistService;
import controllers.AbstractController;
import domain.Tattoo;
import domain.Tattooist;

@Controller
@RequestMapping("/tattooist/tattooist")
public class TattooistTattooistController extends AbstractController {

	@Autowired
	TattooistService tattooistService;


	// Constructors -----------------------------------------------------------

	public TattooistTattooistController() {
		super();
	}

	// Edit Tattooist GET
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final ModelAndView result;
		final Tattooist tattooist = this.tattooistService.findByPrincipal();
		result = this.createEditModelAndView(tattooist, "edit", null);
		return result;
	}

	// Save Tattooist
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tattooist tattooist, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("binding:   " + binding);
			assert tattooist != null;
			if (tattooist.getId() != 0)
				result = this.createEditModelAndView(tattooist, "edit", "tattooist.error.save");
			else

				result = this.createEditModelAndView(tattooist, "create", "tattooist.error.save");
		} else
			try {
				this.tattooistService.save(tattooist);
				result = new ModelAndView("welcome/index");
			} catch (final Throwable oops) {
				System.out.println("oops: " + oops);
				result = this.createEditModelAndView(tattooist, "create", "tattooist.commit.error");
			}
		return result;
	}

	// View Tattoo
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		final Tattooist tattooist = this.tattooistService.findByPrincipal();

		result = this.createEditModelAndView(tattooist, "view", null);

		return result;
	}

	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		Tattooist tattooist = null;
		try {
			tattooist = this.tattooistService.findByPrincipal();
			this.tattooistService.delete(tattooist);

			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (Throwable oops) {
			result = this.deleteModelAndView(tattooist, "view", "tattooist.commit.error");
		}

		return result;
	}

	protected ModelAndView deleteModelAndView(Tattooist tattooist, String action, String msgCode) {
		ModelAndView result = new ModelAndView("tattooist/" + action);

		result.addObject("tattooist", tattooist);
		result.addObject("message", msgCode);

		return result;
	}
	// Metodos create
	protected ModelAndView createEditModelAndView(final Tattooist tattooist, final String action,
			final String msgCode) {
		final ModelAndView result = new ModelAndView("tattooist/" + action);

		result.addObject("tattooist", tattooist);
		result.addObject("message", msgCode);
		result.addObject("requestURI", "tattooist/tattooist/save.do");
		System.out.println("tattooist del create aux:   " + tattooist);
		System.out.println("tattooist del create:   " + tattooist.getUserAccount());

		return result;
	}

}
