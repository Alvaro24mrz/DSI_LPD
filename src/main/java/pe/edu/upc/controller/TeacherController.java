package pe.edu.upc.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Account;
import pe.edu.upc.entity.Teacher;
import pe.edu.upc.serviceinterface.IAccountService;
import pe.edu.upc.serviceinterface.ITeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;
	
	@Autowired
	private ITeacherService tS;

	@GetMapping("/new")
	public String newTeacher(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		model.addAttribute("teacher", new Teacher());
		return "teacher/teacher";
	}

	@PostMapping("/save")
	public String saveTeacher(@Validated Teacher teacher, BindingResult result, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		if (result.hasErrors()) {
			return "teacher/teacher";
		}
		else {
		List<Teacher> list;
		 
		list = tS.list();
		for (Teacher teacher2 : list) {
		if (teacher.getIdTeacher() == teacher2.getIdTeacher()) 
		{
			model.addAttribute("mensaje", "Ya existe un docente con ese código");
			return "teacher/teacher";
		} 
		}			
		if (teacher.getDateOfBirthTeacher().before(teacher.getDateOfAdmissionTeacher())) {
				long edadEnDias = (teacher.getDateOfAdmissionTeacher().getTime() - teacher.getDateOfBirthTeacher().getTime())
                        / 1000 / 60 / 60 / 24;
				int anios = Double.valueOf(edadEnDias / 365.25d).intValue();
				if (anios >= 23 && anios <= 60) {
					tS.insert(teacher);
					model.addAttribute("listTeachers", tS.list());
					return "redirect:/teachers/list";
				}
				else {
					model.addAttribute("mensaje", "La edad mínima es de 23 años");
					return "teacher/teacher";
				}
		}
		else {
				model.addAttribute("mensaje", "La fecha de nacimiento debe ser antes de la fecha de admisión");
				return "teacher/teacher";
		}
		}
	}

	@GetMapping("/list")
	public String listTeachers(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("teacher", new Teacher());
			model.addAttribute("listTeachers", tS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "teacher/listTeachers";
	}

	@RequestMapping("/delete/{id}")
	public String deleteTeacher(Model model, @PathVariable(value = "id") int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("teacher", new Teacher());
			if (id > 0) {
				tS.delete(id);
			}
			model.addAttribute("mensaje", "Se eliminó correctamente");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Ocurrió un error, no es posible eliminar al docente, ya que tiene un curso asignado");
		}
		model.addAttribute("listTeachers", tS.list());
		return "teacher/listTeachers";
	}

	@RequestMapping("/irupdate/{id}")
	public String irupdate(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		Optional<Teacher> objPro = tS.searchId(id);
		if (objPro == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/teachers/list";
		} else {
			model.addAttribute("listTeachers", tS.list());
			model.addAttribute("teacher", objPro.get());
			return "teacher/modTeacher";
		}
	}

	@RequestMapping("/search")
	public String searchTeachers(Model model, @Validated Teacher teacher) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		List<Teacher> listTeachers;
		listTeachers = tS.findNameTeacherFull(teacher.getNameTeacher());
		if (listTeachers.isEmpty()) {
			model.addAttribute("mensaje", "No hay registros que coincidan con la búsqueda");
		}
		model.addAttribute("listTeachers", listTeachers);
		return "teacher/listTeachers";
	}
	
	@PostMapping("/saves")
	public String saveTeachermod(@Validated Teacher teacher, BindingResult result, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		if (result.hasErrors()) {
			return "teacher/modTeacher";
		} else {		
			if (teacher.getDateOfBirthTeacher().before(teacher.getDateOfAdmissionTeacher())) {
					long edadEnDias = (teacher.getDateOfAdmissionTeacher().getTime() - teacher.getDateOfBirthTeacher().getTime())
	                        / 1000 / 60 / 60 / 24;
					int anios = Double.valueOf(edadEnDias / 365.25d).intValue();
					if (anios >= 23 && anios <= 60) {
						tS.insert(teacher);
						model.addAttribute("listTeachers", tS.list());
						return "redirect:/teachers/list";
					}
					else {
						model.addAttribute("mensaje", "La edad mínima es de 23 años");
						return "teacher/modTeacher";
					}
			}
			else {
					model.addAttribute("mensaje", "La fecha de nacimiento debe ser antes de la fecha de admisión");
					return "teacher/modTeacher";
			}
		}
	}
	
}
