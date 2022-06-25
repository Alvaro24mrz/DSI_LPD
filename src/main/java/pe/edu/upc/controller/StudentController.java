package pe.edu.upc.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Account;
import pe.edu.upc.entity.Role;
import pe.edu.upc.entity.Student;
import pe.edu.upc.serviceinterface.IAccountService;
import pe.edu.upc.serviceinterface.IRoleService;
import pe.edu.upc.serviceinterface.IStudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	private Account cuenta;
	@Autowired
	private IAccountService usuarioService;

	@Autowired
	private IStudentService sS;
	
	@Autowired
	private IRoleService rS;
	@Autowired
	private IAccountService cS;

	@GetMapping("/new")
	public String newStudent(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);

		model.addAttribute("student", new Student());
		return "student/student";
	}

	@PostMapping("/save")
	public String saveStudent(@Validated Student student, BindingResult result, Model model) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);

		if (result.hasErrors()) {
			return "student/student";
		} else {
			List<Student> list;
			list = sS.list();
			for (Student student2 : list) {
				if (student.getIdStudent() == student2.getIdStudent()) {
					model.addAttribute("mensaje", "Ya existe un alumno con ese c�digo");

					return "student/student";
				}
			}

			if (student.getDateOfBirthStudent().before(student.getDateOfAdmissionStudent())) {

				long edadEnDias = (student.getDateOfAdmissionStudent().getTime()
						- student.getDateOfBirthStudent().getTime()) / 1000 / 60 / 60 / 24;
				int anios = Double.valueOf(edadEnDias / 365.25d).intValue();

				if (anios >= 16 && anios <= 85) {
					sS.insert(student);
					model.addAttribute("listTeachers", sS.list());
					
					Account aux = new Account();
					String password = new BCryptPasswordEncoder().encode(student.getPasswordAccount());	
					Role aux2 = rS.list().get(0);
					aux.setIdAccount(cS.list().get(cS.list().size()-1).getIdAccount()+1);
					aux.setNameAccount(student.getNameStudent());
					aux.setLastNameAccount(student.getLastnameStudent());
					aux.setUserAccount(Integer.toString(student.getIdStudent()));
					aux.setPasswordAccount(password);
					aux.setRoleAccount(aux2);
					aux.setStudent(student);
					aux.setDif(student.getRol());
					
					cS.insert(aux);
					
					return "redirect:/students/list";
				} else {
					model.addAttribute("mensaje", "La edad m�nima es de 16 a�os y maxima de 85 a�os");
					return "student/student";
				}
			} else {
				model.addAttribute("mensaje", "La fecha de nacimiento debe ser antes de la fecha de admisi�n");
				return "student/student";
			}

		}
	}

	@GetMapping("/list")
	public String listStudents(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);

		try {
			model.addAttribute("student", new Student());
			model.addAttribute("listStudents", sS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "student/listStudents";
	}

	@RequestMapping("/delete/{id}")
	public String deleteStudent(Model model, @PathVariable(value = "id") int id) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);
		
		int aux = 0;
		
		for(int i = 1; i < cS.list().size(); i++) {
			if(id == cS.list().get(i).getStudent().getIdStudent())
					aux = cS.list().get(i).getIdAccount();
		}

		try {
			model.addAttribute("student", new Student());
			if (id > 0) {
				cS.delete(aux);
				sS.delete(id);
			}
			model.addAttribute("mensaje", "Se elimin� correctamente");
		} catch (Exception e) {
			model.addAttribute("mensaje",
					"Ocurri� un error, no es posible eliminar al alumno, ya que est� matriculado");
		}
		model.addAttribute("listStudents", sS.list());
		return "student/listStudents";
	}

	@RequestMapping("/irupdate/{id}")
	public String irupdate(@PathVariable int id, Model model, RedirectAttributes objRedir) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);

		Optional<Student> objPro = sS.searchId(id);
		if (objPro == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurri� un error");
			return "redirect:/student/list";
		} else {
			model.addAttribute("listStudents", sS.list());
			model.addAttribute("student", objPro.get());
			return "student/modStudent";
		}
	}

	@RequestMapping("/search")
	public String searchStudents(Model model, @Validated Student student, BindingResult result) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);

		if (result.hasErrors()) {

			model.addAttribute("listStudents", sS.list());
			model.addAttribute("mensaje2", "No se coloca el caracter a buscar mas un espacio");
			return "student/listStudents";
		} else {
			List<Student> listStudents;
			listStudents = sS.findNameStudentFull(student.getNameStudent());
			if (listStudents.isEmpty()) {
				model.addAttribute("mensaje", "No hay registros que coincidan con la b�squeda");
			}
			model.addAttribute("listStudents", listStudents);
			return "student/listStudents";
		}
	}

	@PostMapping("/saves")
	public String saveStudentmod(@Validated Student student, BindingResult result, Model model) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		cuenta = this.usuarioService.getAccount(userDetail.getUsername());
		model.addAttribute("cuenta", cuenta);

		if (result.hasErrors()) {
			return "student/modStudent";
		} else {
			if (student.getDateOfBirthStudent().before(student.getDateOfAdmissionStudent())) {

				long edadEnDias = (student.getDateOfAdmissionStudent().getTime()
						- student.getDateOfBirthStudent().getTime()) / 1000 / 60 / 60 / 24;
				int anios = Double.valueOf(edadEnDias / 365.25d).intValue();
				
				String password = new String();
				int aux3 = 0;
				
				for(int i = 0; i < sS.list().size(); i++) {
					if(student.getIdStudent() == sS.list().get(i).getIdStudent()) {
							student.setPasswordAccount( sS.list().get(i).getPasswordAccount());
							password = sS.list().get(i).getPasswordAccount();
				}
				}
				
				for(int j = 1; j < cS.list().size(); j++) {
					if(student.getIdStudent() == cS.list().get(j).getStudent().getIdStudent())
					{
						password = cS.list().get(j).getPasswordAccount();
						aux3 = cS.list().get(j).getIdAccount();
					}
				}
				
				Account aux = new Account();
				
				//String password = new BCryptPasswordEncoder().encode(student.getPasswordAccount());	
				Role aux2 = rS.list().get(0);
				aux.setIdAccount(aux3);
				aux.setNameAccount(student.getNameStudent());
				aux.setLastNameAccount(student.getLastnameStudent());
				aux.setUserAccount(Integer.toString(student.getIdStudent()));
				aux.setPasswordAccount(password);
				aux.setRoleAccount(aux2);
				aux.setStudent(student);
				aux.setDif(student.getRol());
				

				if (anios >= 16 && anios <= 85) {
					cS.modificar(aux);
					sS.insert(student);
					model.addAttribute("listTeachers", sS.list());
					return "redirect:/students/list";
				} else {
					model.addAttribute("mensaje", "La edad m�nima es de 16 a�os");
					return "student/student";
				}
			} else {
				model.addAttribute("mensaje", "La fecha de nacimiento debe ser antes de la fecha de admisi�n");
				return "student/student";
			}

		}
	}
}
