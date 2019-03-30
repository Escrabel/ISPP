/*
 * MemberController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.tattooist;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.TattooistService;
import controllers.AbstractController;

@Controller
@RequestMapping("/data/tattooist")
public class DataTattooistController extends AbstractController {

	@Autowired
	private TattooistService tattooistService;

	// Constructors -----------------------------------------------------------

	public DataTattooistController() {
		super();
	}

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
		ModelAndView result;
		try {
			response.setContentType("application/pdf;charset=UTF-8");

			response.addHeader("Content-Disposition", "inline; filename=" + "date.pdf");
			final ServletOutputStream out = response.getOutputStream();

			final ByteArrayOutputStream baos = this.tattooistService.generatePdf();
			baos.writeTo(out);

		} catch (final Exception e) {
			result = new ModelAndView("welcome/index");

		}

	}

}
