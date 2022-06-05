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
@Table(name = "students")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Min(value = 201700000, message = "El código debe ser mayor o igual que 2017")
	@Max(value = 202299999, message = "El código debe ser menor o igual que 2022")
	private int idStudent;
	@Pattern(regexp = "[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$", message = "El nombre debe tener como minimo 1 y maximo 3 palabras (solo letras)")
	@Column(name = "nameStudent", nullable = false, length = 45)
	private String nameStudent;
	@Pattern(regexp = "[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s[a-zA-ZÀ-ÿ\\u00f1\\u00d1])*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$", message = "El apellido debe tener como minimo 1 y maximo 3 palabras (solo letras)")
	@Column(name = "lastnameStudent", nullable = false, length = 45)
	private String lastnameStudent;
	
	@Past(message = "La fecha debe ser pasada")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirthStudent;
	
	@PastOrPresent(message = "La fecha debe ser pasada")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfAdmissionStudent;
	
	@Column (name="passwordAccount", nullable=false, length = 200)
	private String passwordAccount;
	
	@Column (name="rol", nullable=false)
	private int rol;

	public Student() {
		super();
	}

	public Student(int idStudent, String nameStudent, String lastnameStudent, Date dateOfBirthStudent,
			Date dateOfAdmissionStudent, String passwordAccount, int rol) {
		super();
		this.idStudent = idStudent;
		this.nameStudent = nameStudent;
		this.lastnameStudent = lastnameStudent;
		this.dateOfBirthStudent = dateOfBirthStudent;
		this.dateOfAdmissionStudent = dateOfAdmissionStudent;
		this.passwordAccount = passwordAccount;
		this.rol = rol;
	}

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	public String getNameStudent() {
		return nameStudent;
	}

	public void setNameStudent(String nameStudent) {
		this.nameStudent = nameStudent;
	}

	public String getLastnameStudent() {
		return lastnameStudent;
	}

	public void setLastnameStudent(String lastnameStudent) {
		this.lastnameStudent = lastnameStudent;
	}

	public Date getDateOfBirthStudent() {
		return dateOfBirthStudent;
	}

	public void setDateOfBirthStudent(Date dateOfBirthStudent) {
		this.dateOfBirthStudent = dateOfBirthStudent;
	}

	public Date getDateOfAdmissionStudent() {
		return dateOfAdmissionStudent;
	}

	public void setDateOfAdmissionStudent(Date dateOfAdmissionStudent) {
		this.dateOfAdmissionStudent = dateOfAdmissionStudent;
	}

	public String getPasswordAccount() {
		return passwordAccount;
	}

	public void setPasswordAccount(String passwordAccount) {
		this.passwordAccount = passwordAccount;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}
}
