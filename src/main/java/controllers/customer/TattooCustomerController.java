/*
 * 
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

import java.util.Collection;
import java.util.Date;

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
@RequestMapping("/tattoo/customer")
public class TattooCustomerController extends AbstractController {

	// Service -----------------------------------------------------------------
	@Autowired
	private TattooService	tattooService;


	// Constructors -----------------------------------------------------------

	public TattooCustomerController() {
		super();
	}

	// List Notes -------------------------------------------------------------

	// List Tattoo
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String localization, @RequestParam(required = false) final Double precioOne, @RequestParam(required = false) final Double precioTwo, @RequestParam(required = false) final Date dateOne,
		@RequestParam(required = false) final Date dateTwo, @RequestParam(required = false) final String name, @RequestParam(required = false) final String description) {
		final ModelAndView result;

		//No se esta filtrando actualmente porque el filtrado se realiza en los controladores REST
		//de TattooController, pero se deja este findAll para que se muestre la tabla
		//para que micolega Jose Carlos haga el boostrap basandose en la tabla java tranquilamente
		Collection<Tattoo> tattoos;
		tattoos = this.tattooService.findAll();

		result = this.listEditModelAndView(tattoos, null);
		result.addObject("ver", true);

		return result;
	}

	// View Tattoo
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int tattooId) {
		ModelAndView result;

		final Tattoo tattoo = this.tattooService.findOne(tattooId);
		result = this.createEditModelAndView(tattoo, "view", null);

		return result;
	}

	// Metodos Tattoo
	protected ModelAndView createEditModelAndView(final Tattoo tattoo, final String action, final String msgCode) {
		final ModelAndView result = new ModelAndView("tattoo/" + action);

		result.addObject("tattoo", tattoo);
		result.addObject("message", msgCode);
		result.addObject("requestURI", "tattoo/customer/save.do");

		return result;
	}

	// Metodos create List
	protected ModelAndView listEditModelAndView(final Collection<Tattoo> tattoos, final String msgCode) {
		final ModelAndView result = new ModelAndView("tattoo/list");

		result.addObject("tattoos", tattoos);
		result.addObject("requestURI", "tattoo/customer/list.do");

		return result;
	}

	protected ModelAndView deleteEditModelAndView(final Tattoo tattoo, final String msgCode) {
		ModelAndView result;

		result = new ModelAndView("tattoo/edit");
		result.addObject("tattoo", tattoo);
		result.addObject("requestURI", "tattoo/tattooist/list.do");
		result.addObject("message", msgCode);

		return result;
	}
}
