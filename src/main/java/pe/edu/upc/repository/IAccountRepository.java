package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {//no es long en vez de Integer????
	@Query("select count(a.userAccount) from Account a where a.userAccount=:userAccount")
	public int searchAccount(@Param("userAccount") String userAccount);
	
	
	@Query("from Account a where upper(a.userAccount)=upper(:parametro)")
	public Account findByUserAccount(@Param("parametro")String account);
	
	
}