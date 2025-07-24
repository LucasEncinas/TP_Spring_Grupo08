package com.unla.grupo8.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.helpers.ViewRouteHelper;
import com.unla.grupo8.repositories.EmpleadoRepository;
import com.unla.grupo8.service.IPersonService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final EmpleadoRepository empleadoRepository;

	private IPersonService personService;

	public HomeController(IPersonService personService, EmpleadoRepository empleadoRepository) {
		this.personService = personService;
		this.empleadoRepository = empleadoRepository;
	}

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDEX);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modelAndView.addObject("username", user.getUsername());
		modelAndView.addObject("persons", personService.getAll());
		return modelAndView;
	}

	@GetMapping("/inicio")
	public String vistaHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName(); // El email que usaste para loguearte

		String nombreUsuario = "Admin";

		// Buscar en tus repositorios
		Empleado empleado = empleadoRepository.findByContactoEmail(email);
		if (empleado != null) {
			nombreUsuario = empleado.getNombre();
		}
		model.addAttribute("nombreUsuario", nombreUsuario);

		if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
			return "inicio/home";
		} else if (authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_CLIENTE"))) {
			return "redirect:/cliente/index";
		} else if (authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLEADO"))) {
			return "inicio/home";
		}
		return "redirect:/login";
	}

}