package pe.edu.upc.serviceimpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Account;
import pe.edu.upc.repository.IAccountRepository;
import pe.edu.upc.serviceinterface.IAccountService;

@Service
@Transactional
public class AccountServiceImpl implements Serializable, IAccountService {

	private static final long serialVersionUID=1L;
	/**/
	//private Account cuenta;
	/**/
	@Autowired
	private IAccountRepository cR;
	
	@Override
	public List<Account> list() {
		// TODO Auto-generated method stub
		return cR.findAll();
	}

	@Override
	public int insert(Account account) {
		int rpta = cR.searchAccount(account.getUserAccount());
		if(rpta == 0) {
			cR.save(account);
		}
		return rpta;
	}
	
	@Override
	public void delete(int id) {
		cR.deleteById(id);
	}
	
	@Override
	public void modificar(Account account) {
		cR.modificar(account.getIdAccount(), account.getDif(), account.getLastNameAccount(), account.getNameAccount(), account.getPasswordAccount(), account.getUserAccount(), account.getRoleAccount().getIdRole(), account.getStudent().getIdStudent());	
	}

	@Override
	public Account getAccount(String correo) {
		Account cuenta=new Account();
		cuenta=cR.findByUserAccount(correo);
		// TODO Auto-generated method stub
		return cuenta;
	}


	
	
	
}
