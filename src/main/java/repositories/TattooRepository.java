
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tattoo;

@Repository
public interface TattooRepository extends JpaRepository<Tattoo, Integer> {

	@Query("select t from Tattoo t join t.tags ts where ts.id=?1")
	Tattoo tattooByTag(int id);

	@Query("select case when (count(ta) > 0)  then true else false end  from Tattoo ta join ta.tattooist tt where tt.userAccount.id=?1 and ta.id=?2")
	boolean exitsTattooByTattooist(int idUserAccount, int tattooId);

	@Query("select t from Tattoo t join t.tattooist ta where ta.id =?1")
	Collection<Tattoo> listTattooByTattooist(int id);

	@Query("select t from Tattoo t join t.tattooist ta where (t.price> ?4 and t.price<= ?5) and (t.dateUpload>=?2 and t.dateUpload< ?3) and (ta.pais LIKE %?1% or ta.ciudad LIKE %?1%) and t.description LIKE %?7% and t.name LIKE %?6%")
	Collection<Tattoo> findFilterTattoo(String localization, Date dateOne, Date dateTwo, Double precioOne, Double precioTwo, String name, String description);

	@Query("select t from Tattoo t where t.dateUpload> ?1 and t.dateUpload< ?2")
	Collection<Tattoo> findFilterTattooDate(Date dateOne, Date dateTwo);

	@Query("select t from Tattoo t where t.price> ?1 and t.price< ?2")
	Collection<Tattoo> findFilterTattooPrecio(Double precioOne, Double preciotwo);
}
