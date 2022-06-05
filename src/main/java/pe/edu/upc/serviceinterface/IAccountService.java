package pe.edu.upc.serviceinterface;

import java.util.List;

import pe.edu.upc.entity.Account;

public interface IAccountService {
	
	public int insert (Account account);
	
	List <Account> list();
	
	public Account getAccount(String correo);
}
