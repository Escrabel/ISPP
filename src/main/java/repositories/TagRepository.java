
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

	@Query("select ts from Tattoo t join t.tags ts where t.id =?1")
	Collection<Tag> listTagByTattoo(int id);
		
	@Query("select case when (count(tg) > 0)  then true else false end  from Tattoo ta join ta.tattooist tt join ta.tags tg where tt.userAccount.id=?1 and tg.id=?2")
	boolean exitsTagByTattooist( int idUserAccount, int tagId);

}
