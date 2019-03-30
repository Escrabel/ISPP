
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Access(AccessType.PROPERTY)
public class MessageFolder extends DomainEntity {

	private String	name;			// Unique
	private Boolean	isModificable;


	@NotBlank
	// @Column(unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getIsModificable() {
		return this.isModificable;
	}

	public void setIsModificable(final Boolean isModificable) {
		this.isModificable = isModificable;
	}


	// Relationships
	private Actor			actor;
	private List<Message>	messages;


	@ManyToOne(optional = true)
	@JsonManagedReference
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@NotNull
	@OneToMany(mappedBy = "messageFolder")
	@JsonBackReference
	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final List<Message> messages) {
		this.messages = messages;
	}

}
