package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Enrollment;

@Repository
public interface IEnrollmentRepository extends JpaRepository<Enrollment, Integer> {
	@Query(value = "SELECT e.id_enrollment, e.id_coursesx_teacher, e.id_student FROM Enrollments e INNER JOIN CoursesxTeachers cxt on e.id_coursesx_teacher=cxt.id_coursesx_teacher WHERE cxt.semester_coursesx_teacher =:parametro", nativeQuery = true)
	List<Enrollment> findBysemesterCoursesxTeacher(@Param("parametro") String semesterCoursesxTeacher);

	@Query(value="select count(e.id_student) from enrollments e where e.id_coursesx_teacher = :numberEnrollments", nativeQuery= true)
	public int nuEnrollments(@Param("numberEnrollments") int numberEnrollments);
	
	
	@Query(value="select count(e.id_student) from enrollments e where e.id_coursesx_teacher = :numberCxt and e.id_student =:numberStudent", nativeQuery= true)
	public int nuEnrollments1(@Param("numberCxt") int idn,@Param("numberStudent") int ids);
	
}
