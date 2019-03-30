
package services;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TattooRepository;
import utilities.UtilitiesProject;
import domain.Tag;
import domain.Tattoo;
import domain.Tattooist;
import forms.TattooForm;

@Service
@Transactional
public class TattooService {

	// Managed repository
	// -------------------------------------------------------------------------

	@Autowired
	private TattooRepository	tattooRepository;

	@Autowired
	private TattooistService	tattooistService;

	@Autowired
	private Validator			validator;


	// Simple crud methods
	// ------------------------------------------------------------------------

	public Tattoo create() {
		Tattoo tattoo;

		tattoo = new Tattoo();
		final List<Tag> tags = new ArrayList<>();

		tattoo.setTags(tags);
		tattoo.setTicker(UtilitiesProject.generateTicker());

		return tattoo;
	}

	public void delete(final Tattoo r) {
		Assert.notNull(r);
		final Collection<Tag> tags = new ArrayList<Tag>();

		r.setTags(tags);

		this.tattooRepository.delete(r);

		Assert.isTrue(!this.tattooRepository.exists(r.getId()));

	}

	public Tattoo save(final Tattoo r) {
		Assert.notNull(r);

		final Tattoo rsave;

		if (r.getId() == 0)
			r.setDateUpload(new Date(System.currentTimeMillis() - 1000));
		final Tattooist tattooist = this.tattooistService.findByPrincipal();

		r.setTattooist(tattooist);
		rsave = this.tattooRepository.save(r);

		Assert.notNull(rsave);

		Assert.isTrue(this.tattooRepository.exists(rsave.getId()));

		return rsave;

	}

	public Collection<Tattoo> findAll() {

		Collection<Tattoo> tattoos;

		tattoos = this.tattooRepository.findAll();

		Assert.notNull(tattoos);

		return tattoos;
	}

	public Tattoo findOne(final Integer id) {

		Assert.isTrue(id >= 0);
		Tattoo tattoo;

		tattoo = this.tattooRepository.findOne(id);

		Assert.notNull(tattoo);
		return tattoo;
	}

	// Other business methods
	// ---------------------------------------------------------------------

	public Tattoo addTattooist(final Tattoo tatto, final Tattooist tattooist) {
		tatto.setTattooist(tattooist);
		return tatto;
	}

	public void addTag(final Tattoo tattoo, final Tag tag) {

		List<Tag> tags;

		tags = new ArrayList<Tag>();

		tags.addAll(tattoo.getTags());

		tags.add(tag);

		tattoo.setTags(tags);

	}

	public void deleteTag(final Tattoo tattoo, final Tag tag) {

		List<Tag> tags;

		tags = new ArrayList<Tag>();

		tags.addAll(tattoo.getTags());

		tags.remove(tag);

		tattoo.setTags(tags);

	}

	public Tattoo tattooByTag(final int tagId) {

		final Tattoo res = this.tattooRepository.tattooByTag(tagId);
		Assert.notNull(res);
		return res;

	}

	public boolean exitsTattooByTattooist(final int idUserAccount, final int tattooId) {
		final boolean res = this.tattooRepository.exitsTattooByTattooist(idUserAccount, tattooId);
		Assert.notNull(res);

		return res;
	}

	public Collection<Tattoo> listTattooByTattoo(final int tattooistId) {

		final Collection<Tattoo> res = this.tattooRepository.listTattooByTattooist(tattooistId);

		Assert.notNull(res);
		return res;

	}

	public Collection<Tattoo> findFilterTattoo(final String localization, final Date dateOne, final Date dateTwo, final Double precioOne, final Double precioTwo, final String name, final String description) {
		final Collection<Tattoo> res = this.tattooRepository.findFilterTattoo(localization, dateOne, dateTwo, precioOne, precioTwo, name, description);
		Assert.notNull(res);
		return res;

	}

	public Collection<Tattoo> findFilterTattooDate(final Date dateOne, final Date dateTwo) {
		final Collection<Tattoo> res = this.tattooRepository.findFilterTattooDate(dateOne, dateTwo);
		Assert.notNull(res);
		return res;

	}

	public Collection<Tattoo> findFilterTattooPrecio(final Double precioOne, final double precioTwo) {
		final Collection<Tattoo> res = this.tattooRepository.findFilterTattooPrecio(precioOne, precioTwo);
		Assert.notNull(res);
		return res;

	}

	public TattooForm reconstruct(final TattooForm tattooForm, final BindingResult binding) {

		if (tattooForm.getTattoo().getId() == 0) {
			tattooForm.getTattoo().setTags(tattooForm.getTags());
			tattooForm.getTattoo().setTicker(UtilitiesProject.generateTicker());
			tattooForm.getTattoo().setImg(tattooForm.getArchivoBase64());
		} else {
			final Tattoo tattooBD = this.findOne(tattooForm.getTattoo().getId());
			tattooForm.getTattoo().setVersion(tattooBD.getVersion());
			tattooForm.getTattoo().setTicker(tattooBD.getTicker());
			tattooForm.getTattoo().setDateUpload(tattooBD.getDateUpload());
			tattooForm.getTattoo().setTags(tattooForm.getTags());
		}

		this.validator.validate(tattooForm, binding);

		return tattooForm;

	}

	public byte[] convertImgSrcToArrayByte(final String imgSrc) {
		byte[] result = null;
		try {
			final FileInputStream f_in = new FileInputStream(imgSrc);
			result = IOUtils.toByteArray(f_in);
		} catch (final Exception e) {
			result = null;
		}
		return result;
	}

	public void checkReadingFile(final TattooForm tattooForm) {
		Assert.isTrue(tattooForm.getTattoo().getImg() != null);
	}

	public void checkExtensionImage(final String extension) {
		Assert.isTrue(extension.equals("jpg") || extension.equals("png"), "falloExtension");
	}
}
