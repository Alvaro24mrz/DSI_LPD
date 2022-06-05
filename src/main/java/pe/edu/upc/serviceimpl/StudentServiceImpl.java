package pe.edu.upc.serviceimpl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Student;
import pe.edu.upc.repository.IStudentRepository;
import pe.edu.upc.serviceinterface.IStudentService;

@Service
public class StudentServiceImpl implements Serializable, IStudentService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IStudentRepository sR;

	@Override
	public void insert(Student student) {
		sR.save(student);
	}

	@Override
	public List<Student> list() {
		return sR.findAll();
	}

	@Override
	public void delete(int idStudent) {
		sR.deleteById(idStudent);
	}

	@Override
	public Optional<Student> searchId(int idStudent) {
		return sR.findById(idStudent);
	}

	@Override
	public List<Student> findNameStudentFull(String nameStudent) {
		return sR.findBynameStudent(nameStudent);
	}

}
