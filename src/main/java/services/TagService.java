
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TagRepository;

import domain.Tag;

@Service
@Transactional
public class TagService {

	// Managed repository
	// -------------------------------------------------------------------------
	@Autowired
	private TagRepository tagRepository;

	// Suporting service
	// --------------------------------------------------------------------------

	// Simple crud methods
	// ------------------------------------------------------------------------

	public Tag create() {
		final Tag tag = new Tag();

		return tag;

	}

	public Collection<Tag> findAll() {
		Assert.notNull(this.tagRepository.findAll());

		return this.tagRepository.findAll();
	}

	public Tag findOne(final Integer id) {
		Assert.notNull(id);
		Assert.notNull(this.tagRepository.findOne(id));
		Assert.isTrue(this.tagRepository.exists(id));
		return this.tagRepository.findOne(id);
	}

	public Tag save(final Tag tag) {
		Assert.notNull(tag);

		final Tag tagSave = this.tagRepository.save(tag);

		Assert.notNull(tagSave);

		Assert.isTrue(this.tagRepository.exists(tagSave.getId()));

		return tagSave;
	}

	public void delete(final Tag tag) {
		Assert.notNull(tag);
		Assert.notNull(this.tagRepository.findOne(tag.getId()));

		this.tagRepository.delete(tag);

		Assert.isNull(this.tagRepository.findOne(tag.getId()));

	}
	// Other business methods
	// ---------------------------------------------------------------------

	public Collection<Tag> listTagByTattoo(int tattooId) {

		Collection<Tag> res = this.tagRepository.listTagByTattoo(tattooId);

		Assert.notNull(res);
		return res;

	}
	
	public boolean exitsTagByTattooist(int idUserAccount, int tagId) {
		boolean res = this.tagRepository.exitsTagByTattooist(idUserAccount, tagId);
		Assert.notNull(res);

		return res;
	}
	
}
