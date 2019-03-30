
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tattooist;

@Repository
public interface TattooistRepository extends JpaRepository<Tattooist, Integer> {

	@Query("select t from Tattooist t where userAccount.id =?1")
	Tattooist findByPrincipal(int id);
	
	@Query("select tt from Tattoo t join t.tattooist tt where t.id =?1")
	Tattooist tattoistByTattoo(int tattooId);

}
