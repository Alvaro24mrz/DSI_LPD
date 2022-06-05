package pe.edu.upc.controller;

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
import pe.edu.upc.entity.Course;
import pe.edu.upc.serviceinterface.IAccountService;
import pe.edu.upc.serviceinterface.ICourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {
	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;
	
	@Autowired
	private ICourseService cS;

	@GetMapping("/new")
	public String newCourse(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		
		model.addAttribute("course", new Course());
		return "course/course";
	}

	@PostMapping("/save")
	public String saveCourse(@Validated Course course, BindingResult result, Model model) throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		if (result.hasErrors()) {
			return "course/course";
		} else {
			List<Course> list;
			list = cS.list();
				for (Course course2 : list) {
					if (course.getNameCourse().contentEquals(course2.getNameCourse())) 
					{
						model.addAttribute("mensaje", "Ya existe un Curso con ese Nombre");
						return "course/course";
					}	
				}	
			
					cS.insert(course);
					model.addAttribute("listCourses", cS.list());
					return "redirect:/courses/list";		
		}

	}
	
	@PostMapping("/saves")
	public String saveCoursesMod(@Validated Course course, BindingResult result, Model model) throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		if (result.hasErrors()) {
			return "course/course";
		} else {
				Optional<Course> objPro = cS.searchId(course.getIdCourse());
				List<Course> list;
				list = cS.list();
					for (Course course2 : list) {
						if (course.getIdCourse() != course2.getIdCourse() ) {
							if (course.getNameCourse().contentEquals(course2.getNameCourse())) 
							{
								model.addAttribute("mensaje", "Ya existe un Curso con ese Nombre");
								return "course/course";
							}
						}		
				}
				cS.insert(course);
				model.addAttribute("listCourses", cS.list());
				return "redirect:/courses/list";
		}

	}

	@GetMapping("/list")
	public String listCourses(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("course", new Course());
			model.addAttribute("listCourses", cS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "course/listCourses";
	}

	@RequestMapping("/delete/{id}")
	public String deleteCourse(Model model, @PathVariable(value = "id") int id) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("course", new Course());
			if (id > 0) {
				cS.delete(id);
			}
			model.addAttribute("mensaje", "Se eliminó correctamente");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Ocurrió un error, no es posible eliminar al curso, ya que esta designado a un docente");
		}
		model.addAttribute("listCourses", cS.list());
		return "course/listCourses";
	}

	@RequestMapping("/irupdate/{id}")
	public String irupdate(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		Optional<Course> objPro = cS.searchId(id);
		if (objPro == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/course/list";
		} else {
			model.addAttribute("listCourses", cS.list());
			model.addAttribute("course", objPro.get());
			return "course/coursemod";
		}
	}

	@RequestMapping("/search")
	public String searchCourses(Model model, @Validated Course course) throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		List<Course> listCourses;
		listCourses = cS.findNameCourseFull(course.getNameCourse());
		if (listCourses.isEmpty()) {
			model.addAttribute("mensaje", "No hay registros que coincidan con la búsqueda");
		}
		model.addAttribute("listCourses", listCourses);
		return "course/listCourses";
	}
}
