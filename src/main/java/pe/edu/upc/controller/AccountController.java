package pe.edu.upc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

import pe.edu.upc.entity.Account;
import pe.edu.upc.serviceinterface.IAccountService;
import pe.edu.upc.serviceinterface.IRoleService;

@Controller
@RequestMapping("/users")
@Secured("ROLE_ADMIN")
public class AccountController {

	@Autowired
	private IAccountService cS;
	@Autowired
	private IRoleService rS;

	/* GET USER DATA */
	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;

	@GetMapping("/list")
	public String listAccounts(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
			

		model.addAttribute("cuenta", "Bienvenido " + cuenta.getNameAccount());
		try {
			model.addAttribute("listAccounts", cS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "user/listUsers";
	}

	@GetMapping("/new")
	public String newAccount(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		
		model.addAttribute("account", new Account());

		model.addAttribute("roles", rS.list());
		return "user/user";
	}

	@PostMapping("/save")
	public String saveAccount(@Validated Account account, BindingResult result, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		
		if (result.hasErrors()) {
			model.addAttribute("roles", rS.list());
			return "user/user";
		} else {
			String password = new BCryptPasswordEncoder().encode(account.getPasswordAccount());
			account.setPasswordAccount(password);
			int rpta = cS.insert(account);
			if (rpta > 0) {
				model.addAttribute("roles", rS.list());
				model.addAttribute("mensaje2", "El el correo ya está(n) en uso");
				return "user/user";
			} else {

				cS.insert(account);
				model.addAttribute("roles", rS.list());
				model.addAttribute("listAccounts", cS.list());
				model.addAttribute("mensaje", "El usuario se registró correctamente");
				return "user/listUsers";
			}
		}
	}
}
