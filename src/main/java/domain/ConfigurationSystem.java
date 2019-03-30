
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	private Collection<String> defaultTags;
	private Collection<String> codeDiscounts;
	private Double discounts;
	private double comission;

	@ElementCollection
	public Collection<String> getDefaultTags() {
		return this.defaultTags;
	}

	public void setDefaultTags(final Collection<String> defaultTags) {
		this.defaultTags = defaultTags;
	}

	//@NotBlank
	//@Pattern(regexp = "^[0-9]{2}[0-1][0-9][0-3][0-9]-([A-Z0-9]{6}$)")
	//@Column(unique = true)

	
	public Double getDiscounts() {
		return this.discounts;
	}

	@ElementCollection
	public Collection<String> getCodeDiscounts() {
		return codeDiscounts;
	}

	public void setCodeDiscounts(Collection<String> codeDiscounts) {
		this.codeDiscounts = codeDiscounts;
	}

	public void setDiscounts(final Double discounts) {
		this.discounts = discounts;
	}

	public double getComission() {
		return comission;
	}

	public void setComission(double comission) {
		this.comission = comission;
	}

}
