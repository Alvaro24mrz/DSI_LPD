package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Teacher;

@Repository
public interface ITeacherRepository extends JpaRepository<Teacher, Integer> {
	@Query(value = "SELECT id_teacher, date_of_admission_teacher, date_of_birth_teacher, lastname_teacher, name_teacher FROM Teachers t WHERE LOWER(t.name_teacher) LIKE LOWER(concat('%',:parametro,'%')) OR LOWER(t.lastname_teacher) LIKE LOWER(concat('%',:parametro,'%'))", nativeQuery = true)
	List<Teacher> findBynameTeacher(@Param("parametro") String nameTeacher);
}
