package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "coursesxteachers")
public class CoursesxTeacher implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCoursesxTeacher;

	@ManyToOne
	@JoinColumn(name = "idCourse")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "idTeacher")
	private Teacher teacher;

	@Column(name = "semesterCoursesxTeacher", nullable = false, length = 10)
	private String semesterCoursesxTeacher;
	@Column(name = "initalHourCoursesxTeacher", nullable = false, length = 20)
	private String initalHourCoursesxTeacher;
	@Column(name = "finalHourCoursesxTeacher", nullable = false, length = 20)
	private String finalHourCoursesxTeacher;

	public CoursesxTeacher() {
		super();
	}

	public CoursesxTeacher(int idCoursesxTeacher, Course course, Teacher teacher, String semesterCoursesxTeacher,
			String initalHourCoursesxTeacher, String finalHourCoursesxTeacher) {
		super();
		this.idCoursesxTeacher = idCoursesxTeacher;
		this.course = course;
		this.teacher = teacher;
		this.semesterCoursesxTeacher = semesterCoursesxTeacher;
		this.initalHourCoursesxTeacher = initalHourCoursesxTeacher;
		this.finalHourCoursesxTeacher = finalHourCoursesxTeacher;
	}

	public int getIdCoursesxTeacher() {
		return idCoursesxTeacher;
	}

	public void setIdCoursesxTeacher(int idCoursesxTeacher) {
		this.idCoursesxTeacher = idCoursesxTeacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getSemesterCoursesxTeacher() {
		return semesterCoursesxTeacher;
	}

	public void setSemesterCoursesxTeacher(String semesterCoursesxTeacher) {
		this.semesterCoursesxTeacher = semesterCoursesxTeacher;
	}

	public String getInitalHourCoursesxTeacher() {
		return initalHourCoursesxTeacher;
	}

	public void setInitalHourCoursesxTeacher(String initalHourCoursesxTeacher) {
		this.initalHourCoursesxTeacher = initalHourCoursesxTeacher;
	}

	public String getFinalHourCoursesxTeacher() {
		return finalHourCoursesxTeacher;
	}

	public void setFinalHourCoursesxTeacher(String finalHourCoursesxTeacher) {
		this.finalHourCoursesxTeacher = finalHourCoursesxTeacher;
	}

}
