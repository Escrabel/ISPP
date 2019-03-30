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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import services.TattooistService;
import controllers.AbstractController;
import domain.Tag;

@Controller
@RequestMapping("/tag/tattooist")
public class TagTattooistController extends AbstractController {

	// Service -----------------------------------------------------------------
	@Autowired
	private TagService					tagService;

	@Autowired
	private TattooistService			tattooistService;



	// Constructors -----------------------------------------------------------

	public TagTattooistController() {
		super();
	}

	// List Tag -------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tattooId) {
		ModelAndView result;
		final Collection<Tag> tags = this.tagService.listTagByTattoo(tattooId);
		result = this.listEditModelAndView(tags, null);
		return result;

	}

	// Edit Tag -------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagId) {
		ModelAndView result;
		final int tattooistId = this.tattooistService.findByPrincipal().getUserAccount().getId();
		if (this.tagService.exitsTagByTattooist(tattooistId, tagId)) {
			final Tag tag = this.tagService.findOne(tagId);
			result = this.createEditModelAndView(tag, "edit", null);

		} else
			result = new ModelAndView("welcome/index");

		return result;
	}

//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
//	public ModelAndView delete(@RequestParam final int tagId) {
//		ModelAndView result;
//		final int tattooistId = this.tattooistService.findByPrincipal().getUserAccount().getId();
//
//		final Tattoo tattoo = this.tattooService.tattooByTag(tagId);
//		final Tag tag = this.tagService.findOne(tagId);
//
//		if (this.tagService.exitsTagByTattooist(tattooistId, tagId))
//			try {
//				this.tattooService.deleteTag(tattoo, tag);
//				result = new ModelAndView("redirect:list.do?tattooId=" + tattoo.getId());
//			} catch (final Throwable oops) {
//				result = this.createEditModelAndView(tag.getId(), tag, "tag.commit.error");
//			}
//		else
//			result = new ModelAndView("welcome/index");
//		return result;
//	}

	// **************metodo add phase a app**************************
//	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "save")
//	public ModelAndView add(@Valid final Tag tag, final BindingResult binding, @RequestParam final int tattooId) {
//		ModelAndView result;
//		final int tattooistId = this.tattooistService.findByPrincipal().getUserAccount().getId();
//
//		if (this.tagService.exitsTagByTattooist(tattooistId, tag.getId()) || tag.getId() == 0) {
//			if (binding.hasErrors()) {
//				assert tag != null;
//				result = this.createEditModelAndView(tattooId, tag, "tag.error.save");
//			} else
//				try {
//					if (tag.getId() == 0) {
//						final Tag tagS = this.tagService.save(tag);
//						this.tattooService.addTag(this.tattooService.findOne(tattooId), tagS);
//					} else
//						this.tagService.save(tag);
//					result = new ModelAndView("redirect:list.do?tattooId=" + tattooId);
//				} catch (final Throwable oops) {
//					result = this.createEditModelAndView(tattooId, tag, "tag.commit.error");
//				}
//		} else
//			result = new ModelAndView("welcome/index");
//		return result;
//	}

	// Metodos aux create
	protected ModelAndView createEditModelAndView(final Tag tag, final String action, final String messageCode) {
		final ModelAndView result = new ModelAndView("tag/" + action);

		result.addObject("tag", tag);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "tag/tattooist/edit.do");

		return result;
	}

	// Metodos aux List
	protected ModelAndView listEditModelAndView(final Collection<Tag> tags, final String msgCode) {
		final ModelAndView result = new ModelAndView("tag/list");
		result.addObject("tags", tags);
		result.addObject("requestURI", "tag/tattooist/list.do");

		return result;
	}

	// Metodo aux Add

	// **************metodo create**************************
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tattooId) {
		ModelAndView result;
		final Tag tag = this.tagService.create();

		result = this.createEditModelAndView(tattooId, tag, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final int tattooId, final Tag tag, final String msgCode) {
		ModelAndView result;

		final Collection<Tag> tags = this.tagService.findAll();
		result = new ModelAndView("tag/edit");
		result.addObject("tag", tag);
		result.addObject("requestURI", "tag/tattooist/add.do?tattooId=" + tattooId);

		result.addObject("message", msgCode);
		result.addObject("tags", tags);

		return result;
	}

}
