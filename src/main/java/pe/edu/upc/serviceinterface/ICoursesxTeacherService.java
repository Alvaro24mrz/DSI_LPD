package pe.edu.upc.serviceinterface;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.CoursesxTeacher;

public interface ICoursesxTeacherService {

	public void insert(CoursesxTeacher coursesxteacher);

	List<CoursesxTeacher> list();

	public void delete(int idCoursesxTeacher);

	Optional<CoursesxTeacher> searchId(int idCoursesxTeacher);

	public List<String[]> report1();

	public List<String[]> report2(String param);

	List<CoursesxTeacher> findNameCoursesxTeacherFull(String parametro);
	
	public List<String[]> report2details(String param);
	
	public int validarHoras(Date inicio,Date fin);
	
}
