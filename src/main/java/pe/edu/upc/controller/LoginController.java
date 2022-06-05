package pe.edu.upc.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Account;
import pe.edu.upc.serviceinterface.IAccountService;

@Controller
@RequestMapping
public class LoginController {
	
	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;

	@GetMapping(value = { "/login", "/" })
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash) {
		
		

		if (principal != null) {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			cuenta = this.usuarioService.getAccount(userDetail.getUsername());
			model.addAttribute("cuenta", cuenta);
			
			if (cuenta.getDif() == 1) return "redirect:/courses/list";
			else if (cuenta.getDif() == 2) return "redirect:/enrollments/list";
			
			
		}

		if (error != null) {
			model.addAttribute("error","Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}

		if (logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
			return "login";
		}
		return "login";
		
	}
}

