
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.MessageFolder;

@Repository
public interface MessageFolderRepository extends JpaRepository<MessageFolder, Integer> {

	@Query("select f from MessageFolder f where f.isModificable = true and f.actor = ?1")
	public Collection<MessageFolder> getFoldersModifiable(Actor a);

	@Query("select f from MessageFolder f where f.name = ?2 and f.actor = ?1")
	public MessageFolder getFolderbyName(Actor a, String s);

	@Query("select f from MessageFolder f where f.actor.id = ?1")
	public Collection<MessageFolder> getFoldersByUser(int idActor);
	
	@Query("select b from MessageFolder b where b.actor.id = ?1")
	Collection<MessageFolder> findAllByActorId(Integer actorId);
}
