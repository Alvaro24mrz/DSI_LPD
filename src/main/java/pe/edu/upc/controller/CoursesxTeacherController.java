package pe.edu.upc.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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
import pe.edu.upc.entity.CoursesxTeacher;
import pe.edu.upc.entity.Enrollment;
import pe.edu.upc.entity.Teacher;
import pe.edu.upc.serviceinterface.IAccountService;
import pe.edu.upc.serviceinterface.ICourseService;
import pe.edu.upc.serviceinterface.ICoursesxTeacherService;
import pe.edu.upc.serviceinterface.IEnrollmentService;
import pe.edu.upc.serviceinterface.ITeacherService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Controller
@RequestMapping("/coursesxteachers")
public class CoursesxTeacherController {
	
	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;

	@Autowired
	private ICoursesxTeacherService cxtS;

	@Autowired
	private ICourseService cS;

	@Autowired
	private ITeacherService tS;

	@Autowired
	private IEnrollmentService eS;

	@GetMapping("/new")
	public String newCoursesxTeacher(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
			
		
		model.addAttribute("coursesxTeacher", new CoursesxTeacher());
		model.addAttribute("listCourses", cS.list());
		model.addAttribute("listTeachers", tS.list());
		return "coursesxteacher/coursesxteacher";
	}

	@PostMapping("/save")
	public String saveCoursesxTeacher(@Validated CoursesxTeacher coursesxteacher, BindingResult result, Model model)
			throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		
		if (result.hasErrors()) {
			model.addAttribute("listCourses", cS.list());
			model.addAttribute("listTeachers", tS.list());
			return "coursesxteacher/coursesxteacher";
		} else {

			DateFormat dateFormat1 = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
			DateFormat dateFormat2 = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
			Date d1 = dateFormat1.parse(coursesxteacher.getInitalHourCoursesxTeacher());
			Date d2 = dateFormat2.parse(coursesxteacher.getFinalHourCoursesxTeacher());
			System.out.println(d1);
			System.out.println(d2);
			if (d1.before(d2)) {
				int hor = cxtS.validarHoras(d1, d2);
				if (hor == 1) {
					cxtS.insert(coursesxteacher);
					model.addAttribute("listCourses", cS.list());
					model.addAttribute("listTeachers", tS.list());
					model.addAttribute("listCoursesxTeachers", cxtS.list());
					return "redirect:/coursesxteachers/list";
				} else {
					model.addAttribute("mensaje",
							"La diferencia de horas entre el inicio y fin debe de ser minimo de 2 y maximo 4 horas");
					model.addAttribute("listCourses", cS.list());
					model.addAttribute("listTeachers", tS.list());
					return "coursesxteacher/coursesxteacher";
				}

			} else {
				model.addAttribute("mensaje", "La hora de inicio debe ser antes de la hora de fin");
				model.addAttribute("listCourses", cS.list());
				model.addAttribute("listTeachers", tS.list());
				return "coursesxteacher/coursesxteacher";
			}

		}
	}

	@GetMapping("/list")
	public String listCoursesxTeacher(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("coursesxTeacher", new CoursesxTeacher());// necesario para el buscar
			model.addAttribute("listCoursesxTeachers", cxtS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "coursesxteacher/listCoursesxTeachers";
	}

	@RequestMapping("/delete/{id}")
	public String deleteCoursesxTeacher(Model model, @PathVariable(value = "id") int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		try {
			model.addAttribute("coursesxTeacher", new CoursesxTeacher());
			if (id > 0) {
				cxtS.delete(id);
			}
			model.addAttribute("mensaje", "Se eliminó correctamente");
		} catch (Exception e) {
			model.addAttribute("mensaje",
					"Ocurrió un error,  no es posible eliminar al Curso con docecnte asignado, ya que existen alumnos matriculados");
		}
		model.addAttribute("listCoursesxTeachers", cxtS.list());
		return "coursesxteacher/listCoursesxTeachers";
	}

	@RequestMapping("/irupdate/{id}")
	public String irupdate(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		Optional<CoursesxTeacher> objPro = cxtS.searchId(id);
		if (objPro == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/coursesxteacher/list";
		} else {
			model.addAttribute("listCourses", cS.list());
			model.addAttribute("listTeachers", tS.list());
			model.addAttribute("listCoursesxTeachers", cS.list());
			model.addAttribute("coursesxTeacher", objPro.get());
			return "coursesxteacher/coursesxteacher";
		}
	}

	@RequestMapping("/reports")
	public String reportsvist(Model model, Map<String, Object> modelMY) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		

		// DATA: ESTUDIANTES POR SEMESTRES

		Map<String, Integer> G = new TreeMap<>();

		List<CoursesxTeacher> lCT = cxtS.list();
		List<Enrollment> lE = eS.list();
		List<Integer> Q = new ArrayList<Integer>();
		List<String> S = new ArrayList<String>();

		for (int i = 0; i < lCT.size(); i++) {
			CoursesxTeacher r = lCT.get(i);
			S.add(r.getSemesterCoursesxTeacher());
		}

		for (int i = 0; i < S.size(); i++) {
			String s = S.get(i);
			List<Integer> aux = new ArrayList<Integer>();
			for (int j = 0; j < lCT.size(); j++) {
				if (s.equals(lCT.get(j).getSemesterCoursesxTeacher()))
					aux.add(lCT.get(j).getIdCoursesxTeacher());
			}
			int c = 0;
			for (int j = 0; j < aux.size(); j++) {
				for (int k = 0; k < lE.size(); k++) {
					if (aux.get(j) == lE.get(k).getCoursesxteacher().getIdCoursesxTeacher())
						c++;
				}
			}
			Q.add(c);
		}

		for (int i = 0; i < S.size(); i++) {
			G.put(S.get(i), Q.get(i));
		}

		model.addAttribute("chartData", G);

		// DATA: ESTUDIANTES POR DOCENTE

		Map<String, Integer> G2 = new TreeMap<>();

		List<Teacher> lT = tS.list();
		List<Integer> l1 = new ArrayList<Integer>();
		List<Integer> Q2 = new ArrayList<Integer>();
		List<String> T = new ArrayList<String>();

		for (int i = 0; i < lT.size(); i++) {
			Teacher r = lT.get(i);
			T.add(r.getNameTeacher() + ", " + r.getLastnameTeacher());
			l1.add(r.getIdTeacher());
		}

		for (int i = 0; i < l1.size(); i++) { // Recorrer lista de profesores
			int aux1 = l1.get(i); // ID de profesor
			List<Integer> l2 = new ArrayList<Integer>();// Lista auxiliar de cursos del profesor
			for (int j = 0; j < lCT.size(); j++) {
				if (lCT.get(j).getTeacher().getIdTeacher() == aux1)
					l2.add(lCT.get(j).getIdCoursesxTeacher());
			}
			int aux2 = 0;
			for (int j = 0; j < l2.size(); j++) {
				
				for (int k = 0; k < lE.size(); k++) {
					if (lE.get(k).getCoursesxteacher().getIdCoursesxTeacher() == l2.get(j))
						aux2++;
				}
			}
			Q2.add(aux2);
		}

		for (int i = 0; i < l1.size(); i++) {
			G2.put(T.get(i), Q2.get(i));
		}

		model.addAttribute("chartData2", G2);
		
		// DATA: DOCENTES POR CURSO
		
		Map<String, Integer> G3 = new TreeMap<>();
		
		//List<CoursesxTeacher> lCT = cxtS.list();
		List<Integer> l2 = new ArrayList<Integer>();
		List<Integer> QT = new ArrayList<Integer>();
		List<String> NC = new ArrayList<String>();
		
		for (int i = 0; i < lCT.size(); i++) {
			CoursesxTeacher r = lCT.get(i);
			NC.add(r.getCourse().getNameCourse());
			l2.add(r.getCourse().getIdCourse());
		}
		
		for (int i = 0; i < l2.size(); i++) {
			int aux1 = l2.get(i);
			int q = 0;// Lista auxiliar de profesores del curso
			for (int j = 0; j < lCT.size(); j++) {
				if (lCT.get(j).getCourse().getIdCourse() == aux1)
					q++;
			}
			QT.add(q);
		}
		
		for (int i = 0; i < l2.size(); i++) {
			G3.put(NC.get(i), QT.get(i));
		}

		model.addAttribute("chartData3", G3);

		return "enrollment/reportEnrollments";

	}

	@RequestMapping("/reports1")
	public String reportfiltr(Model model, @Validated Enrollment enrollment) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		List<String[]> listEnrollments;
		listEnrollments = cxtS.report2(enrollment.getCoursesxteacher().getSemesterCoursesxTeacher());
		if (listEnrollments.isEmpty()) {
			model.addAttribute("mensaje", "No hay registros que coincidan con la búsqueda");
		}
		model.addAttribute("listEnrollments", listEnrollments);
		return "enrollment/reportEnrollments";
	}

	@RequestMapping("/search")
	public String searchCoursesxTeacher(Model model, @Validated CoursesxTeacher coursesxteachers) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		List<CoursesxTeacher> listCoursesxTeachers;
		listCoursesxTeachers = cxtS.findNameCoursesxTeacherFull(coursesxteachers.getTeacher().getNameTeacher());
		if (listCoursesxTeachers.isEmpty()) {
			model.addAttribute("mensaje", "No hay registros que coincidan con la búsqueda");
		}
		model.addAttribute("listCoursesxTeachers", listCoursesxTeachers);
		return "coursesxteacher/listCoursesxTeachers";
	}

}
