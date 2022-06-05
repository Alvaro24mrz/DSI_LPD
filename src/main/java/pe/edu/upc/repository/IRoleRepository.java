package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
	@Query("select count(a.nameRole) from Role a where upper(a.nameRole)=upper(:nameRole)")
	public int searchRole(@Param("nameRole") String nombre);
	
	@Query("from Role r where r.idRole=:parametro")
	public Role findByCorreoAccount(@Param("parametro")int idRole);
}
