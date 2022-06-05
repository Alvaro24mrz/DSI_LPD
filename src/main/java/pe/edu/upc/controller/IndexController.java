package pe.edu.upc.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Account;
import pe.edu.upc.entity.Role;
import pe.edu.upc.serviceinterface.IAccountService;
import pe.edu.upc.serviceinterface.IRoleService;


@Controller
@RequestMapping
public class IndexController {
	@Autowired
	private IAccountService cS;
	@Autowired
	private IRoleService rS;
	
	private Role rol;
	
	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;
	
	
	
	//@GetMapping
	
	
	@GetMapping("/registry")	
	public String newAccount (Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		model.addAttribute("account", new Account());
		model.addAttribute("error", "");
		return "registro";
	}
	
	@PostMapping("/registry/save")
	public String login(@RequestParam(value = "error", required = false) String error,
			String logout, Model model, Principal principal,
			RedirectAttributes flash,@Validated Account account, BindingResult result)  {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		if (result.hasErrors()) {
			
			return "error";
		} else {
			String password = new BCryptPasswordEncoder().encode(account.getPasswordAccount());
			account.setPasswordAccount(password);
			rol = new Role();
			rol =rS.getrole(1);
			account.setRoleAccount(rol);
			int rpta = cS.insert(account);
			if (rpta > 0) {
					model.addAttribute("error", "El UserName ya está en uso");
					return"redirect:/registro";
				
			} else {
				
				cS.insert(account);
				model.addAttribute("listAccounts", cS.list());
				model.addAttribute("mensaje", "El usuario se registró correctamente");
				return "redirect:/login";
			}
		}
	}
	
	@GetMapping("/registro")	
	public String newAccountError (Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		model.addAttribute("account", new Account());
		model.addAttribute("error", "El usuario ingresado ya existe");
		return "registro";
	}
	
	/*
	@GetMapping("/login")
	public String login (Model model) {
	
		return "login";
	}*/
	
	
	
	
	
}
