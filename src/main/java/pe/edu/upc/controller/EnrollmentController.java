package pe.edu.upc.controller;

import java.util.List;
import java.util.Map;
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
import pe.edu.upc.entity.Enrollment;
import pe.edu.upc.serviceinterface.IEnrollmentService;
import pe.edu.upc.serviceinterface.IAccountService;
import pe.edu.upc.serviceinterface.ICoursesxTeacherService;
import pe.edu.upc.serviceinterface.IStudentService;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
	
	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;

	@Autowired
	private ICoursesxTeacherService cxtS;

	@Autowired
	private IEnrollmentService eS;

	@Autowired
	private IStudentService sS;

	@GetMapping("/new")
	public String newEnrollment(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		model.addAttribute("enrollment", new Enrollment());
		model.addAttribute("listStudents", sS.list());
		model.addAttribute("listCoursesxTeacher", cxtS.list());
		return "enrollment/enrollment";
	}

	@PostMapping("/save")
	public String saveEnrollment(@Validated Enrollment enroll, BindingResult result, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		if (result.hasErrors()) {
			model.addAttribute("listStudents", sS.list());
			model.addAttribute("listCoursesxTeacher", cxtS.list());
			return "enrollment/enrollment";
		} else {
			int rpta = 0;
			int rpta2 = 0;
			rpta2 = eS.searchEnroll(enroll);
			rpta = eS.insert(enroll);
			if (rpta2>=1) {
				model.addAttribute("mensaje", "El alumno ya está matriculado");
				model.addAttribute("listStudents", sS.list());
				model.addAttribute("listCoursesxTeacher", cxtS.list());
				return "enrollment/enrollment";
			} else {
				if (rpta >=10) {
					model.addAttribute("mensaje", "Ya hay 10 alummnos matriculados en el curso con el docente seleccionado");
					model.addAttribute("listStudents", sS.list());
					model.addAttribute("listCoursesxTeacher", cxtS.list());
					return "enrollment/enrollment";
				} else {
					eS.insert(enroll);
					model.addAttribute("listStudents", sS.list());
					model.addAttribute("listCoursesxTeacher", cxtS.list());
					model.addAttribute("listEnrollments", eS.list());
					return "redirect:/enrollments/list";
				}
			}
			
			
		}
	}

	@GetMapping("/list")
	public String listEnrollment(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("enrollment", new Enrollment());
			model.addAttribute("listEnrollments", eS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "enrollment/listEnrollments";
	}

	@RequestMapping("/delete/{id}")
	public String deleteEnrollment(Model model, @PathVariable(value = "id") int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("enrollment", new Enrollment());
			if (id > 0) {
				eS.delete(id);
			}
			model.addAttribute("mensaje", "Se eliminó correctamente");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Ocurrió un error, no se pudo eliminar");
		}
		model.addAttribute("listEnrollments", eS.list());
		return "enrollment/listEnrollments";
	}

	@RequestMapping("/irupdate/{id}")
	public String irupdate(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		Optional<Enrollment> objPro = eS.searchId(id);
		if (objPro == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/enrollment/list";
		} else {
			model.addAttribute("listStudents", sS.list());
			model.addAttribute("listCoursesxTeacher", cxtS.list());
			model.addAttribute("listEnrollments", eS.list());
			model.addAttribute("enrollment", objPro.get());
			return "enrollment/modenrollment";
		}
	}

	@RequestMapping("/search")
	public String searchEnrollments(Model model, @Validated Enrollment enrollment) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		List<Enrollment> listEnrollments;
		listEnrollments = eS
				.findSemesterCoursesxTeacherFull(enrollment.getCoursesxteacher().getSemesterCoursesxTeacher());
		if (listEnrollments.isEmpty()) {
			model.addAttribute("mensaje", "No hay registros que coincidan con la búsqueda");
		}
		model.addAttribute("listEnrollments", listEnrollments);
		return "enrollment/listEnrollments";
	}
	
	@GetMapping("/detail/{param}")
	public String detailImportation(@PathVariable(value = "param") String param, Map<String, Object> model, Model model2,
			RedirectAttributes flash) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model2.addAttribute("cuenta", cuenta);
		
		List<String[]> listStudents;
		listStudents = cxtS.report2details(param);
		if (listStudents == null) {
			flash.addFlashAttribute("error", "El Detalle no existe en la base de datos");
			return "enrollment/reportEnrollments"; 
		}
		model.put("listStudents", listStudents);
		model.put("titulo", "Detalle del curso de: " + param);

		return "enrollment/details/listDetail"; 
	}
	
	@PostMapping("/saves")
	public String saveEnrollmentM(@Validated Enrollment enroll, BindingResult result, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		if (result.hasErrors()) {
			model.addAttribute("listStudents", sS.list());
			model.addAttribute("listCoursesxTeacher", cxtS.list());
			return "enrollment/enrollment";
		} else {
					eS.insert1(enroll);
					model.addAttribute("listStudents", sS.list());
					model.addAttribute("listCoursesxTeacher", cxtS.list());
					model.addAttribute("listEnrollments", eS.list());
					return "redirect:/enrollments/list";
		}
	}
}
