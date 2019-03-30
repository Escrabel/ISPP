/*
 * MemberController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

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
import controllers.AbstractController;

@Controller
@RequestMapping("/data/customer")
public class DataCustomerController extends AbstractController {

	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public DataCustomerController() {
		super();
	}

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
		ModelAndView result;
		try {
			response.setContentType("application/pdf;charset=UTF-8");

			response.addHeader("Content-Disposition", "inline; filename=" + "date.pdf");
			final ServletOutputStream out = response.getOutputStream();

			final ByteArrayOutputStream baos = this.customerService.generatePdf();
			baos.writeTo(out);

		} catch (final Exception e) {
			result = new ModelAndView("welcome/index");

		}

	}

}
