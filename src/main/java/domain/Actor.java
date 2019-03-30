
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	private String	name;
	private String	surName;
	private String	postalAddress;
	private String	phone;			//unique
	private String	email;			//unique
	private String	pais;
	private String	ciudad;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurName() {
		return this.surName;
	}

	public void setSurName(final String surName) {
		this.surName = surName;
	}

	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	//En .xml poner <> con &lt; y &gt; respectivamente
	@NotBlank
	@Column(unique = true)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	//@Digits(fraction = 1, integer = 11)
	@Column(unique = true)
	@Pattern(regexp = "^[9|6|7][0-9]{8}$")//pattern para números de teléfonos de España
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getPostalAddress() {
		return this.postalAddress;
	}

	public void setPostalAddress(final String postalAddress) {
		this.postalAddress = postalAddress;
	}


	//Relationships

	private UserAccount			userAccount;
	private List<MessageFolder>	messageFolders;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
	@JsonBackReference
	public List<MessageFolder> getMessageFolders() {
		return this.messageFolders;
	}

	public void setMessageFolders(final List<MessageFolder> messageFolders) {
		this.messageFolders = messageFolders;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(final String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(final String ciudad) {
		this.ciudad = ciudad;
	}

}
