package pe.edu.upc.serviceimpl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Teacher;
import pe.edu.upc.repository.ITeacherRepository;
import pe.edu.upc.serviceinterface.ITeacherService;

@Service
public class TeacherServiceImpl implements Serializable, ITeacherService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ITeacherRepository tR;

	@Override
	public void insert(Teacher teacher) {
		tR.save(teacher);
	}

	@Override
	public List<Teacher> list() {
		return tR.findAll();
	}

	@Override
	public void delete(int idTeacher) {
		tR.deleteById(idTeacher);
	}

	@Override
	public Optional<Teacher> searchId(int idTeacher) {
		return tR.findById(idTeacher);
	}

	@Override
	public List<Teacher> findNameTeacherFull(String nameTeacher) {
		return tR.findBynameTeacher(nameTeacher);
	}

}
