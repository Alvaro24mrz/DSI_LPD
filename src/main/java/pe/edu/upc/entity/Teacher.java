package pe.edu.upc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "teachers")
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Min(value = 10000000, message = "El DNI debe ser de 8 dígitos")
	@Max(value = 99999999, message = "El DNI debe ser de 8 dígitos")
	private int idTeacher;
	@Pattern(regexp = "[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$", message = "El nombre debe tener como minimo 1 y maximo 3 palabras (solo letras)")
	@Column(name = "nameTeacher", nullable = false, length = 45)
	private String nameTeacher;
	@Pattern(regexp = "[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$", message = "El apellido debe tener como minimo 1 y maximo 3 palabras (solo letras)")
	@Column(name = "lastnameTeacher", nullable = false, length = 45)
	private String lastnameTeacher;
	@Past(message = "La edad debe estar entre 23 años y 60 años")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirthTeacher;
	@PastOrPresent(message = "La fecha debe ser pasada")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfAdmissionTeacher;

	public Teacher() {
		super();
	}

	public Teacher(int idTeacher, String nameTeacher, String lastnameTeacher, Date dateOfBirthTeacher,
			Date dateOfAdmissionTeacher) {
		super();
		this.idTeacher = idTeacher;
		this.nameTeacher = nameTeacher;
		this.lastnameTeacher = lastnameTeacher;
		this.dateOfBirthTeacher = dateOfBirthTeacher;
		this.dateOfAdmissionTeacher = dateOfAdmissionTeacher;
	}

	public int getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(int idTeacher) {
		this.idTeacher = idTeacher;
	}

	public String getNameTeacher() {
		return nameTeacher;
	}

	public void setNameTeacher(String nameTeacher) {
		this.nameTeacher = nameTeacher;
	}

	public String getLastnameTeacher() {
		return lastnameTeacher;
	}

	public void setLastnameTeacher(String lastnameTeacher) {
		this.lastnameTeacher = lastnameTeacher;
	}

	public Date getDateOfBirthTeacher() {
		return dateOfBirthTeacher;
	}

	public void setDateOfBirthTeacher(Date dateOfBirthTeacher) {
		this.dateOfBirthTeacher = dateOfBirthTeacher;
	}

	public Date getDateOfAdmissionTeacher() {
		return dateOfAdmissionTeacher;
	}

	public void setDateOfAdmissionTeacher(Date dateOfAdmissionTeacher) {
		this.dateOfAdmissionTeacher = dateOfAdmissionTeacher;
	}

}
