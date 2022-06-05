package pe.edu.upc.serviceinterface;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Teacher;

public interface ITeacherService {

	public void insert(Teacher teacher);

	List<Teacher> list();

	public void delete(int idTeacher);

	Optional<Teacher> searchId(int idTeacher);

	List<Teacher> findNameTeacherFull(String nameTeacher);
}
