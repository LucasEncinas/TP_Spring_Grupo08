package com.unla.grupo8.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.unla.grupo8.helpers.ViewRouteHelper;
import com.unla.grupo8.service.IPersonService;

@Controller
@RequestMapping("/")
public class HomeController {

	private IPersonService personService;

	public HomeController(IPersonService personService) {
		this.personService = personService;
	}

	// GET Example: SERVER/index
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDEX);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modelAndView.addObject("username", user.getUsername());
		modelAndView.addObject("persons", personService.getAll());
		return modelAndView;
	}

	@GetMapping("/inicio")
	public String vistaHome() {
		return "inicio/home";
	}

	// GET Example: SERVER/hello?name=someName
	// @GetMapping("/hello")
	// public ModelAndView helloParams1(@RequestParam(name="name", required=false,
	// defaultValue="null") String name) {
	// ModelAndView mV = new ModelAndView(ViewRouteHelper.HELLO);
	// mV.addObject("name", name);
	// return mV;
	// }

	// //GET Example: SERVER/hello/someName
	// @GetMapping("/hello/{name}")
	// public ModelAndView helloParams2(@PathVariable("name") String name) {
	// ModelAndView mV = new ModelAndView(ViewRouteHelper.HELLO);
	// mV.addObject("name", name);
	// return mV;
	// }

	// @GetMapping("/")
	// public RedirectView redirectToHomeIndex() {
	// return new RedirectView(ViewRouteHelper.ROUTE);
	// }
}