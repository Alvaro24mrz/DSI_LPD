package pe.edu.upc.serviceinterface;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Student;

public interface IStudentService {

	public void insert(Student student);

	List<Student> list();

	public void delete(int idStudent);

	Optional<Student> searchId(int idStudent);

	List<Student> findNameStudentFull(String nameStudent);

}
