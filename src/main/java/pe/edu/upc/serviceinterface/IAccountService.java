package pe.edu.upc.serviceinterface;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Account;

@Transactional
public interface IAccountService {
	
	public int insert (Account account);
	
	List <Account> list();
	
	public void modificar(Account account);
	
	public void delete(int id);
	
	public Account getAccount(String correo);
}
