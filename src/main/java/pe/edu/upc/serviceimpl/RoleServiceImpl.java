package pe.edu.upc.serviceimpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Role;
import pe.edu.upc.repository.IRoleRepository;
import pe.edu.upc.serviceinterface.IRoleService;

@Service
public class RoleServiceImpl implements Serializable, IRoleService{
	
	private static final long serialVersionUID=1L;
	
	@Autowired
	private IRoleRepository cR;
	
	@Override
	public int insert(Role _role) {
		int rpta = cR.searchRole(_role.getNameRole());
		if (rpta == 0) {
			cR.save(_role);
		}
		return rpta;
	}

	@Override
	public List<Role> list() {
		// TODO Auto-generated method stub
		return cR.findAll();
	}

	@Override
	public Role getrole(int id) {
		Role cuenta=new Role();
		cuenta=cR.findByCorreoAccount(id);
		// TODO Auto-generated method stub
		return cuenta;
	}
	
}
