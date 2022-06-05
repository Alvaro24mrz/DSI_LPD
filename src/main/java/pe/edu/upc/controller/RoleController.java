package pe.edu.upc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.entity.Account;
import pe.edu.upc.entity.Role;
import pe.edu.upc.serviceinterface.IAccountService;
import pe.edu.upc.serviceinterface.IRoleService;

@Controller
@RequestMapping("/users/roles")
@Secured("ROLE_ADMIN")
public class RoleController {
	
	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;
	
	@Autowired
	private IRoleService cS;
	
	@GetMapping("/new")
	public String newRole (Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		model.addAttribute("role", new Role());
		return "user/role";
	}
	
	@PostMapping("/save")
	public String saveRole (@Validated Role role, BindingResult result, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		if(result.hasErrors()) {
			return "user/role";
		}
		
		else {
			int rpta = cS.insert(role);
			if (rpta > 0) {
				model.addAttribute("mensaje2", "Ya existe");
				return "user/role";
			} else {
				model.addAttribute("listRoles", cS.list());
				model.addAttribute("mensaje", "El rol se registró correctamente");
				return "user/listRoles";
			}
		}
		
	}
	
	@GetMapping("/list")
	public String listRole(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("listRoles", cS.list());
		}catch(Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		return "user/listRoles";
	
	}
	
}
