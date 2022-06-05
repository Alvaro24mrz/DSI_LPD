package pe.edu.upc.serviceimpl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.CoursesxTeacher;
import pe.edu.upc.repository.ICoursesxTeacherRepository;
import pe.edu.upc.serviceinterface.ICoursesxTeacherService;

@Service
public class CoursesxTeacherServiceImpl implements ICoursesxTeacherService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ICoursesxTeacherRepository cxtR;

	@Override
	public void insert(CoursesxTeacher coursesxteacher) {
		cxtR.save(coursesxteacher);
	}

	@Override
	public List<CoursesxTeacher> list() {
		return cxtR.findAll();
	}

	@Override
	public void delete(int idCoursesxTeacher) {
		cxtR.deleteById(idCoursesxTeacher);
	}

	@Override
	public Optional<CoursesxTeacher> searchId(int idCoursesxTeacher) {
		return cxtR.findById(idCoursesxTeacher);
	}

	@Override
	public List<String[]> report1() {
		// TODO Auto-generated method stub
		return cxtR.findBysemesterxCourses2();
	}

	public List<String[]> report2(String param) {
		// TODO Auto-generated method stub
		return cxtR.findBysemesterxCourses(param);
	}

	@Override
	public List<CoursesxTeacher> findNameCoursesxTeacherFull(String parametro) {
		return cxtR.findBynameCoursexteacher(parametro);
	}

	@Override
	public List<String[]> report2details(String param) {
		// TODO Auto-generated method stub
		return cxtR.detalleMatricula(param);
	}

	@Override
	public int validarHoras(Date inicio,Date fin) {
		int rpta=0;
		
		
		if (fin.getTime() - inicio.getTime() >= 7200000 && fin.getTime() - inicio.getTime() <= 14400000 ) {
			rpta=1;
		}
		return rpta;
	}

}
