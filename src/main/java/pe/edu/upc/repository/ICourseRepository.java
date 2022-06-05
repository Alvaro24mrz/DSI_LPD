package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Course;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Integer> {
	@Query("SELECT COUNT(c.nameCourse) FROM Course c WHERE LOWER(c.nameCourse)=LOWER(:nameCourse)")
	public int searchCourse(@Param("nameCourse") String nombre);

	@Query(value = "SELECT id_course, name_course FROM Courses c WHERE LOWER(c.name_course) LIKE LOWER(concat('%',:parametro,'%'))", nativeQuery = true)
	List<Course> findBynameCourse(@Param("parametro") String nameCourse);
}
