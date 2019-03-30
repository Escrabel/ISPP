/*
 * administratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public AdminAdminController() {
		super();
	}

	// Create admin
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Assert.isTrue(LoginService.isPrincipalAdmin());
		final Administrator administrator = this.administratorService.create();
		final ModelAndView result = this.createEditModelAndView(administrator, "create", null);

		return result;
	}
	// Save administrator
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result = new ModelAndView();

		if (binding.hasErrors()) {
			assert administrator != null;
			result = this.createEditModelAndView(administrator, "create", "administrator.error.save");
		} else
			try {
				this.administratorService.save(administrator);
				result = new ModelAndView("welcome/index");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "create", "administrator.commit.error");
			}
		return result;

	}

	// Model And View
	private ModelAndView createEditModelAndView(final Administrator administrator, final String action, final String message) {
		final ModelAndView result = new ModelAndView("administrator/" + action);
		result.addObject("administrator", administrator);
		result.addObject("message", message);
		result.addObject("requestURI", "admin/admin/save.do");

		return result;

	}
}
