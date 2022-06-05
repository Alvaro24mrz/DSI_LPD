package pe.edu.upc.serviceimpl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Course;
import pe.edu.upc.repository.ICourseRepository;
import pe.edu.upc.serviceinterface.ICourseService;

@Service
public class CourseServiceImpl implements Serializable, ICourseService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ICourseRepository cR;

	@Override
	public int insert(Course course) {
		int rpta = 0;
		if (rpta == 0) {
			cR.save(course);
		}
		return rpta;
	}

	@Override
	public List<Course> list() {
		return cR.findAll();
	}

	@Override
	public void delete(int idCourse) {
		cR.deleteById(idCourse);
	}

	@Override
	public Optional<Course> searchId(int idCourse) {
		return cR.findById(idCourse);
	}

	@Override
	public List<Course> findNameCourseFull(String nameCourse) {
		return cR.findBynameCourse(nameCourse);
	}

}
