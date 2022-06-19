package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {//no es long en vez de Integer????
	@Query("select count(a.userAccount) from Account a where a.userAccount=:userAccount")
	public int searchAccount(@Param("userAccount") String userAccount);
	
	//@Query(value ="update Account a set a.idAccount = :id, a.rol = :rol , a.lastNameAccount=:lastname, a.nameAccount=:name, a.passwordAccount=:password, a.user_account=:user, a.roleAccount=:idrole, a.idStudent=:student where a.user_account=:user", nativeQuery = true)
	//public void modificar(@Param("id")int id,@Param("rol") int rol,@Param("lastname") String lastname,@Param("name") String name,@Param("password") String password,@Param("user") String user,@Param("idrole") int idrole,@Param("student") int student);
	
	@Modifying
	@Query(value ="update Account set id_account = ?1, rol = ?2 , last_name_account=?3, "
			+ "name_account=?4, password_account=?5, user_account=?6, id_role=?7, "
			+ "id_student=?8 where user_account=?6", nativeQuery = true)
	@Transactional
	void modificar(int id,int rol,String lastname,String name,String password,String user,int idrole,int student);
	
	
	@Query("from Account a where upper(a.userAccount)=upper(:parametro)")
	public Account findByUserAccount(@Param("parametro")String account);
	
	
}