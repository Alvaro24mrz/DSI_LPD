package pe.edu.upc.serviceinterface;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Course;

public interface ICourseService {

	public int insert(Course course);

	List<Course> list();

	public void delete(int idCourse);

	Optional<Course> searchId(int idCourse);

	List<Course> findNameCourseFull(String nameCourse);
}
