package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Student;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
	@Query(value = "SELECT id_student, date_of_admission_student, date_of_birth_student, lastname_student, name_student FROM Students s WHERE LOWER(s.name_student) LIKE LOWER(concat('%',:parametro,'%')) OR LOWER(s.lastname_student) LIKE LOWER(concat('%',:parametro,'%'))", nativeQuery = true)
	List<Student> findBynameStudent(@Param("parametro") String nameStudent);

}
