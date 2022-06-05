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
@Table (name="Account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int idAccount;
	@Column (name="nameAccount", nullable=false, length=45)
	private String nameAccount;	
	@Column (name="lastNameAccount", nullable=false, length=45)	
	private String lastNameAccount;
	
	@Column (name="userAccount", nullable=false, length=45)
	private String userAccount;
	
	@Column (name="passwordAccount", nullable=false, length = 200)
	private String passwordAccount;
	
	@ManyToOne
	@JoinColumn(name = "idRole", nullable = false)
	private Role roleAccount;
	
	@ManyToOne
	@JoinColumn(name = "idStudent", nullable = true)
	private Student student;
	
	@Column (name="rol", nullable=false)
	private int dif;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(int idAccount, String nameAccount, String lastNameAccount, String userAccount,
			String passwordAccount, Role roleAccount, Student student, int dif) {
		super();
		this.idAccount = idAccount;
		this.nameAccount = nameAccount;
		this.lastNameAccount = lastNameAccount;
		this.userAccount = userAccount;
		this.passwordAccount = passwordAccount;
		this.roleAccount = roleAccount;
		this.student = student;
		this.dif = dif;
	}

	public int getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public String getNameAccount() {
		return nameAccount;
	}

	public void setNameAccount(String nameAccount) {
		this.nameAccount = nameAccount;
	}

	public String getLastNameAccount() {
		return lastNameAccount;
	}

	public void setLastNameAccount(String lastNameAccount) {
		this.lastNameAccount = lastNameAccount;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPasswordAccount() {
		return passwordAccount;
	}

	public void setPasswordAccount(String passwordAccount) {
		this.passwordAccount = passwordAccount;
	}

	public Role getRoleAccount() {
		return roleAccount;
	}

	public void setRoleAccount(Role roleAccount) {
		this.roleAccount = roleAccount;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getDif() {
		return dif;
	}

	public void setDif(int dif) {
		this.dif = dif;
	}	
}
