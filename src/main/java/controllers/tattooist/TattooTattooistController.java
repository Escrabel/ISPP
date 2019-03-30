/*
 * 
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.tattooist;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import services.TattooService;
import services.TattooistService;
import controllers.AbstractController;
import domain.Tag;
import domain.Tattoo;
import forms.TattooForm;

@Controller
@RequestMapping("/tattoo/tattooist")
public class TattooTattooistController extends AbstractController {

	// Service -----------------------------------------------------------------
	@Autowired
	private TattooService		tattooService;

	@Autowired
	private TagService			tagService;

	@Autowired
	private TattooistService	tattooistService;


	// Constructors -----------------------------------------------------------

	public TattooTattooistController() {
		super();
	}

	// List Notes -------------------------------------------------------------

	//List Rest
	@RequestMapping(value = "/listRest", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Tattoo> listRest() {
		final int tattooistId = this.tattooistService.findByPrincipal().getId();
		final Collection<Tattoo> result = this.tattooService.listTattooByTattoo(tattooistId);
		return result;
	}

	// List Tattoo
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final int tattooistId = this.tattooistService.findByPrincipal().getId();

		final Collection<Tattoo> tattoos = this.tattooService.listTattooByTattoo(tattooistId);
		result = this.listEditModelAndView(tattoos, null);

		return result;
	}

	// Create Tattoo
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final Tattoo tattoo = this.tattooService.create();
		final TattooForm tattooForm = new TattooForm(tattoo);

		final ModelAndView result = this.createEditModelAndView(tattooForm, "create", null);

		return result;
	}

	// Edit Tattoo GET
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tattooId) {

		final ModelAndView result;
		final int tattooistId = this.tattooistService.findByPrincipal().getUserAccount().getId();

		if (this.tattooService.exitsTattooByTattooist(tattooistId, tattooId)) {
			final Tattoo tattoo = this.tattooService.findOne(tattooId);
			// Conversion de tattoo a tattooForm
			final TattooForm tattooForm = new TattooForm(tattoo);
			result = this.createEditModelAndView(tattooForm, "edit", null);
		} else
			result = new ModelAndView("welcome/index");
		return result;
	}

	// View Tattoo
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int tattooId) {
		ModelAndView result;
		final int tattooistId = this.tattooistService.findByPrincipal().getUserAccount().getId();
		if (this.tattooService.exitsTattooByTattooist(tattooistId, tattooId)) {
			final Tattoo tattoo = this.tattooService.findOne(tattooId);
			// Conversion de tattoo a tattooForm
			final TattooForm tattooForm = new TattooForm(tattoo);
			result = this.createEditModelAndView(tattooForm, "view", null);
		} else
			result = new ModelAndView("welcome/index");
		return result;
	}

	// Save Tattoo
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(TattooForm tattooForm, final BindingResult binding) {
		ModelAndView result;
		tattooForm = this.tattooService.reconstruct(tattooForm, binding);
		final int tattooistId = this.tattooistService.findByPrincipal().getUserAccount().getId();
		if (this.tattooService.exitsTattooByTattooist(tattooistId, tattooForm.getTattoo().getId()) || tattooForm.getTattoo().getId() == 0) {
			if (binding.hasErrors()) {
				assert tattooForm.getTattoo() != null;

				if (tattooForm.getTattoo().getId() != 0)
					result = this.createEditModelAndView(tattooForm, "edit", "tattoo.error.save");
				else {
				}
				result = this.createEditModelAndView(tattooForm, "create", "tattoo.error.save");
			} else
				try {
					this.tattooService.checkExtensionImage(tattooForm.getExtension());
					this.tattooService.checkReadingFile(tattooForm);
					this.tattooService.save(tattooForm.getTattoo());
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					if (oops.getMessage().equals("noHayImagen"))
						result = this.createEditModelAndView(tattooForm, "create", "tattoo.commit.error.noHayImagen");
					else if (oops.getMessage().equals("lecturaImagenFallida"))
						result = this.createEditModelAndView(tattooForm, "create", "tattoo.commit.error.lecturaImagenFallida");
					else if (oops.getMessage().equals("falloExtension"))
						result = this.createEditModelAndView(tattooForm, "create", "tattoo.commit.error.falloExtension");
					else
						result = this.createEditModelAndView(tattooForm, "create", "tattoo.commit.error");
				}

		} else
			result = new ModelAndView("welcome/index");
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam final int tattooId) {
		ModelAndView result;
		final int tattooistId = this.tattooistService.findByPrincipal().getUserAccount().getId();

		final Tattoo tattoo = this.tattooService.findOne(tattooId);

		if (this.tattooService.exitsTattooByTattooist(tattooistId, tattooId))
			try {
				this.tattooService.delete(tattoo);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.deleteEditModelAndView(new TattooForm(tattoo), "tattoo.commit.error");
			}
		else
			result = new ModelAndView("welcome/index");
		return result;
	}

	// Metodos Tattoo
	protected ModelAndView createEditModelAndView(final TattooForm tattooForm, final String action, final String msgCode) {
		final ModelAndView result = new ModelAndView("tattoo/" + action);
		final Collection<Tag> tags = this.tagService.findAll();

		if (action.equals("view")) {
			result.addObject("tattoo", tattooForm.getTattoo());
			// convert byte[] to String
			result.addObject("imgString", new String(tattooForm.getTattoo().getImg()));
		} else
			result.addObject("tattooForm", tattooForm);
		result.addObject("message", msgCode);
		result.addObject("tags", tags);

		result.addObject("requestURI", "tattoo/tattooist/save.do");

		return result;
	}

	// Metodos create List
	protected ModelAndView listEditModelAndView(final Collection<Tattoo> tattoos, final String msgCode) {
		final ModelAndView result = new ModelAndView("tattoo/list");

		result.addObject("tattoos", tattoos);
		result.addObject("requestURI", "tattoo/tattooist/list.do");
		result.addObject("verEdit", true);

		return result;
	}

	protected ModelAndView deleteEditModelAndView(final TattooForm tattooForm, final String msgCode) {
		ModelAndView result;

		result = new ModelAndView("tattoo/edit");
		result.addObject("tattooForm", tattooForm);
		result.addObject("requestURI", "tattoo/tattooist/list.do");
		result.addObject("message", msgCode);

		return result;
	}
}
