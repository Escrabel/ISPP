
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Tattoo extends DomainEntity {

	private String	ticker;		// unique
	private String	name;
	private String	description;
	private Double	price;
	private byte[]	img;
	private Date	dateUpload;


	//@Pattern(regexp = "^[0-9]{2}[0-1][0-9][0-3][0-9]-([A-Z0-9]{6}$)")
	@NotBlank
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@Column(length = 5000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Min(1)
	@Digits(fraction = 2, integer = 8)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	//Anotacion para mapear objetos pesados
	@Lob
	@Column(name = "BLOB_COLUMN", nullable = true)
	public byte[] getImg() {
		return this.img;
	}

	public void setImg(final byte[] img) {
		this.img = img;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDateUpload() {
		return this.dateUpload;
	}

	public void setDateUpload(final Date dateUpload) {
		this.dateUpload = dateUpload;
	}


	// Relationships
	private Tattooist		tattooist;
	private Collection<Tag>	tags;


	@ManyToOne(optional = false)
	public Tattooist getTattooist() {
		return this.tattooist;
	}

	public void setTattooist(final Tattooist tattooist) {
		this.tattooist = tattooist;
	}

	@ManyToMany
	public Collection<Tag> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<Tag> tags) {
		this.tags = tags;
	}
}
