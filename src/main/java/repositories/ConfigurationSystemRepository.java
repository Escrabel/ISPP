
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ConfigurationSystem;
import services.ConfigurationSystemService;

@Repository
public interface ConfigurationSystemRepository extends JpaRepository<ConfigurationSystem, Integer> {

	

	@Query("select d from ConfigurationSystem c join c.defaultTags d")
	public Collection<String> getDefaultTags();
	
	
	@Query("select co from ConfigurationSystem c join c.codeDiscounts co")
	public Collection<String> getCodeDiscounts();
	
	@Query("select c.discounts from ConfigurationSystem c")
	public Double getDiscounts();

	@Query("select c.comission from ConfigurationSystem c")
	public Double getComission();

	@Query("select c from ConfigurationSystem c")
	public ConfigurationSystem findDefaultConfiguration();
}
