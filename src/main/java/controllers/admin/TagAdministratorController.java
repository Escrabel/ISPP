/*
 * 
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationSystemService;
import services.TagService;
import controllers.AbstractController;
import domain.Tag;

@Controller
@RequestMapping("/tag/administrator")
public class TagAdministratorController extends AbstractController {

	// Service -----------------------------------------------------------------
	@Autowired
	private TagService					tagService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors -----------------------------------------------------------

	public TagAdministratorController() {
		super();
	}

	// List Tag -------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Tag> tags = this.tagService.findAll();
		result = this.listEditModelAndView(tags, null);
		return result;

	}

	//	// Create Tag -------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final Tag tag = this.tagService.create();
		result = this.createEditModelAndView(tag, "create", null);

		return result;
	}

	// Edit Tag -------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagId) {
		final Tag tag = this.tagService.findOne(tagId);

		final ModelAndView result = this.createEditModelAndView(tag, "edit", null);

		return result;
	}

	// Save Tag -------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tag tag, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			assert tag != null;
			if (tag.getId() != 0)
				result = this.createEditModelAndView(tag, "edit", "tag.error.save");
			else
				result = this.createEditModelAndView(tag, "create", "tag.error.save");
		} else
			try {
				this.tagService.save(tag);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tag, "create", "tag.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int tagId) {
		ModelAndView result;
		final Tag tag = this.tagService.findOne(tagId);
		try {
			this.tagService.delete(tag);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tag.getId(), tag, "tag.commit.error");
		}

		return result;
	}

	// Metodos aux create
	protected ModelAndView createEditModelAndView(final Tag tag, final String action, final String messageCode) {
		final ModelAndView result = new ModelAndView("tag/" + action);
		final Collection<String> nameTags = this.configurationSystemService.getDefaultTags();
		result.addObject("tag", tag);
		result.addObject("message", messageCode);
		result.addObject("nameTags", nameTags);

		result.addObject("requestURI", "tag/administrator/edit.do");

		return result;
	}

	// Metodos aux List
	protected ModelAndView listEditModelAndView(final Collection<Tag> tags, final String msgCode) {
		final ModelAndView result = new ModelAndView("tag/list");
		result.addObject("tags", tags);
		result.addObject("requestURI", "tag/administrator/list.do");

		return result;
	}

	//Metodo aux Add
	protected ModelAndView createEditModelAndView(final int tattooId, final Tag tag, final String msgCode) {
		ModelAndView result;

		result = new ModelAndView("tag/edit");
		result.addObject("tag", tag);
		result.addObject("requestURI", "tag/administrator/list.do");

		result.addObject("message", msgCode);

		return result;
	}

}
