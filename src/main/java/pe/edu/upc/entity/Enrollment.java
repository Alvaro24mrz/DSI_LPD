package pe.edu.upc.entity;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enrollments")
public class Enrollment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEnrollment;
	
	@ManyToOne
	@JoinColumn(name = "idCoursesxTeacher")
	private CoursesxTeacher coursesxteacher;
	
	@ManyToOne
	@JoinColumn(name = "idStudent")
	private Student student;

	public Enrollment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Enrollment(int idEnrollment, CoursesxTeacher coursesxteacher, Student student) {
		super();
		this.idEnrollment = idEnrollment;
		this.coursesxteacher = coursesxteacher;
		this.student = student;
	}

	public int getIdEnrollment() {
		return idEnrollment;
	}

	public void setIdEnrollment(int idEnrollment) {
		this.idEnrollment = idEnrollment;
	}

	public CoursesxTeacher getCoursesxteacher() {
		return coursesxteacher;
	}

	public void setCoursesxteacher(CoursesxTeacher coursesxteacher) {
		this.coursesxteacher = coursesxteacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	
}
