/*
 * 
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.TattooService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Tattoo;

@Controller
@RequestMapping("/tattoo")
public class TattooController extends AbstractController {

	// Service -----------------------------------------------------------------
	@Autowired
	private TattooService	tattooService;


	// Constructors -----------------------------------------------------------

	public TattooController() {
		super();
	}

	//	Get tattoo
	@RequestMapping(value = "/getRest", method = RequestMethod.GET)
	public @ResponseBody
	Tattoo getTattoo(@RequestParam(required = true) final Integer tattooId) {
		final Tattoo tattoo = this.tattooService.findOne(tattooId);

		return tattoo;
	}

	// ListRest Tattoos
	@RequestMapping(value = "/listRest", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Tattoo> listRest() {
		final Collection<Tattoo> result = this.tattooService.findAll();
		return result;
	}

	// ListBest8Rest Tattoos
	@RequestMapping(value = "/listBest8Rest", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Tattoo> listBest8Rest() {
		final Collection<Tattoo> allTattoos = this.tattooService.findAll();
		final Collection<Tattoo> result = new ArrayList<Tattoo>(this.getListBest8(allTattoos));
		return result;
	}

	// ListFilerRest Tattoos
	@RequestMapping(value = "/listFilterRest", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Tattoo> listFilterRest(@RequestParam(required = false) final String localization, @RequestParam(required = false) Double precioOne, @RequestParam(required = false) Double precioTwo, @RequestParam(required = false) Date dateOne,
		@RequestParam(required = false) Date dateTwo, @RequestParam(required = false) String name, @RequestParam(required = false) final String description) {

		if (name.contains("ñ"))
			name = name.replace("ñ", "�");

		if (precioOne == null)
			precioOne = 1.;
		if (precioTwo == null)
			precioTwo = 99999999.0;
		if (dateOne == null) {
			dateOne = new Date();
			dateOne.setYear(2000 - 1900);
		}
		if (dateTwo == null) {
			dateTwo = new Date();
			dateTwo.setYear(2100 - 1900);
		}

		final Collection<Tattoo> result = this.tattooService.findFilterTattoo(localization, dateOne, dateTwo, precioOne, precioTwo, name, description);
		return result;
	}

	// List Notes -------------------------------------------------------------

	// List Tattoo
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String localization, @RequestParam(required = false) final Double precioOne, @RequestParam(required = false) final Double precioTwo, @RequestParam(required = false) final Date dateOne,
		@RequestParam(required = false) final Date dateTwo, @RequestParam(required = false) final String name, @RequestParam(required = false) final String description) {
		final ModelAndView result;

		Collection<Tattoo> tattoos = null;

		// No se esta filtrando actualmente porque el filtrado se realiza en los
		// controladores REST
		// de TattooController, pero se deja este findAll para que se muestre la tabla
		// para que micolega Jose Carlos haga el boostrap basandose en la tabla java
		// tranquilamente
		tattoos = this.tattooService.findAll();

		result = this.listModelAndView(tattoos, null);
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

	// Obtener img en formato String de un tattoo
	@RequestMapping(value = "/imgFromTattooId", method = RequestMethod.GET)
	public @ResponseBody
	String imgFromTattooId(@RequestParam(required = true) final int tattooId) {
		final Tattoo tattoo = this.tattooService.findOne(tattooId);
		String result = "";
		if (tattoo.getImg() != null)
			result = new String(tattoo.getImg());

		// convertir el result en json
		final ObjectMapper mapper = new ObjectMapper();
		String jsonInString;
		try {
			jsonInString = mapper.writeValueAsString(result);
		} catch (final JsonProcessingException e) {
			e.printStackTrace();
			jsonInString = "Error";
		}
		return jsonInString;
	}

	// Metodos Tattoo
	protected ModelAndView createEditModelAndView(final Tattoo tattoo, final String action, final String msgCode) {
		final ModelAndView result = new ModelAndView("tattoo/" + action);

		result.addObject("tattoo", tattoo);
		result.addObject("message", msgCode);
		result.addObject("requestURI", "tattoo/save.do");

		return result;
	}

	// Metodos create List
	protected ModelAndView listModelAndView(final Collection<Tattoo> tattoos, final String msgCode) {
		final ModelAndView result = new ModelAndView("tattoo/list");

		result.addObject("tattoos", tattoos);
		result.addObject("desdeFuera", true);

		result.addObject("requestURI", "tattoo/list.do");

		return result;
	}

	// Metodos auxiliares ----------------------
	private Collection<Tattoo> getListBest8(final Collection<Tattoo> tattoos) {
		final Collection<Tattoo> result = new ArrayList<Tattoo>();
		int cont = 0;
		for (final Tattoo t : tattoos) {
			if (cont == 8)
				break;
			result.add(t);
			cont++;
		}
		return result;
	}
}
