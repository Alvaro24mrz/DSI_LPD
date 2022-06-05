package pe.edu.upc.serviceimpl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Enrollment;
import pe.edu.upc.repository.IEnrollmentRepository;
import pe.edu.upc.serviceinterface.IEnrollmentService;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IEnrollmentRepository eR;

	@Override
	public int insert(Enrollment enrollment) {
		int rpta = 0;
		int rpta2 =0;
		rpta = eR.nuEnrollments(enrollment.getCoursesxteacher().getIdCoursesxTeacher());
		rpta2 = eR.nuEnrollments1(enrollment.getCoursesxteacher().getIdCoursesxTeacher(),enrollment.getStudent().getIdStudent());
		
		if (rpta2 <1) {
			if(rpta <=10) {
				eR.save(enrollment);
			}
		}
		
		return rpta;
	}

	@Override
	public List<Enrollment> list() {
		return eR.findAll();
	}

	@Override
	public void delete(int idEnrollment) {
		
		eR.deleteById(idEnrollment);
	}

	@Override
	public Optional<Enrollment> searchId(int idEnrollment) {
		return eR.findById(idEnrollment);
	}

	@Override
	public List<Enrollment> findSemesterCoursesxTeacherFull(String semesterCoursesxTeacher) {
		return eR.findBysemesterCoursesxTeacher(semesterCoursesxTeacher);
	}

	@Override
	public int searchEnroll(Enrollment enrollment) {
		int rpta = 0;
		rpta=eR.nuEnrollments1(enrollment.getCoursesxteacher().getIdCoursesxTeacher(),enrollment.getStudent().getIdStudent());
		return rpta;
	}

	@Override
	public void insert1(Enrollment enroll) {
		eR.save(enroll);
	}

	

}
