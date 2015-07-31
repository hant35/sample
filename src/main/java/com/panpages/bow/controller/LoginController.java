package com.panpages.bow.controller;

import java.util.Calendar;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panpages.bow.model.Report;

@Controller
public class LoginController {

	@RequestMapping(value = "/admin.html", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.MONTH, -1);
		fromDate.set(Calendar.DATE, 1);
		Calendar toDate = Calendar.getInstance();
		toDate.add(Calendar.MONTH, -1);
		toDate.set(Calendar.DATE,
				fromDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		Report report = new Report();
		report.setFromDate(fromDate.getTime());
		report.setToDate(toDate.getTime());
		model.addObject("report", report);
		model.setViewName("admin");

		return model;
	}

	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;
	}

	@RequestMapping(value = "/403.html", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");

		return model;
	}
}
