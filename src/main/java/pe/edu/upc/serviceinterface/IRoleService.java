package pe.edu.upc.serviceinterface;

import java.util.List;

import pe.edu.upc.entity.Role;

public interface IRoleService {
	public int insert (Role _role);
	List <Role> list();
	
	public Role getrole(int idRole);
}
