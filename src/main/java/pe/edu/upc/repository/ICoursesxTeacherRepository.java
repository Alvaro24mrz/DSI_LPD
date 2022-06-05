package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.CoursesxTeacher;

@Repository
public interface ICoursesxTeacherRepository extends JpaRepository<CoursesxTeacher, Integer> {

	@Query(value = "SELECT c.name_course ,t.name_teacher , z.semester_coursesx_teacher, count(e.id_student) FROM coursesxteachers z join courses c on c.id_course = z.id_course join teachers t on t.id_teacher = z.id_teacher join enrollments e on z.id_coursesx_teacher = e.id_coursesx_teacher where z.semester_coursesx_teacher =:parametro group by c.name_course ,t.name_teacher,z.semester_coursesx_teacher order by count(e.id_student)   ", nativeQuery = true)
	public List<String[]> findBysemesterxCourses(@Param("parametro") String parametro);

	@Query(value = "SELECT c.name_course ,t.name_teacher , z.semester_coursesx_teacher, count(e.id_student) FROM coursesxteachers z join courses c on c.id_course = z.id_course join teachers t on t.id_teacher = z.id_teacher join enrollments e on z.id_coursesx_teacher = e.id_coursesx_teacher group by c.name_course ,t.name_teacher,z.semester_coursesx_teacher order by count(e.id_student) ", nativeQuery = true)
	public List<String[]> findBysemesterxCourses2();

	
	@Query(value = "SELECT cxt.id_coursesx_teacher, cxt.final_hour_coursesx_teacher, cxt.inital_hour_coursesx_teacher, cxt.semester_coursesx_teacher, cxt.id_course, cxt.id_teacher FROM Coursesxteachers cxt INNER JOIN Courses co ON cxt.id_course=co.id_course INNER JOIN Teachers te ON cxt.id_teacher=te.id_teacher WHERE LOWER(co.name_course) LIKE LOWER(concat('%',:parametro,'%')) OR LOWER(te.name_teacher) LIKE LOWER(concat('%',:parametro,'%')) OR LOWER(te.lastname_teacher) LIKE LOWER(concat('%',:parametro,'%'))", nativeQuery = true)
	List<CoursesxTeacher> findBynameCoursexteacher(@Param("parametro") String parametro);
	
	
	@Query(value = "SELECT e.id_student, to_char(e.date_of_admission_student,'DD/MM/YYYY') as Date1, to_char( e.date_of_birth_student, 'DD/MM/YYYY') as Date2, e.lastname_student, e.name_student\r\n" + 
			"	FROM students e join enrollments en on e.id_student = en.id_student join coursesxteachers cxt\r\n" + 
			"	on en.id_coursesx_teacher = cxt.id_coursesx_teacher join courses c on cxt.id_course = c.id_course\r\n" + 
			"	join teachers t on cxt.id_teacher = t.id_teacher where c.name_course =:parametro ", nativeQuery = true)
	public List<String[]> detalleMatricula(@Param("parametro") String parametro);

}
