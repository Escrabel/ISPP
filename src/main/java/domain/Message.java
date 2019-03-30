
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date	date;
	private String	subject;
	private String	body;
	//	private String	priority;
	private String	buyTicker;


	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	@Column(length = 5000)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	//	@Pattern(regexp = "HIGH|NEUTRAL|LOW")
	//	public String getPriority() {
	//		return this.priority;
	//	}
	//
	//	public void setPriority(final String priority) {
	//		this.priority = priority;
	//	}

	public String getBuyTicker() {
		return this.buyTicker;
	}

	public void setBuyTicker(final String buyTicker) {
		this.buyTicker = buyTicker;
	}


	//Relationship
	private Actor			actorSender;
	private Actor			actorReceiver;
	private MessageFolder	messageFolder;


	//	private Buy				buy;

	@ManyToOne(optional = false)
	@JsonManagedReference
	public MessageFolder getMessageFolder() {
		return this.messageFolder;
	}

	public void setMessageFolder(final MessageFolder messageFolder) {
		this.messageFolder = messageFolder;
	}

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	public Actor getActorSender() {
		return this.actorSender;
	}
	public void setActorSender(final Actor actorSender) {
		this.actorSender = actorSender;
	}

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	public Actor getActorReceiver() {
		return this.actorReceiver;
	}
	public void setActorReceiver(final Actor actorReceiver) {
		this.actorReceiver = actorReceiver;
	}

	//	@ManyToOne(optional = true)
	//	@Valid
	//	public Buy getBuy() {
	//		return this.buy;
	//	}
	//
	//	public void setBuy(final Buy b) {
	//		this.buy = b;
	//	}
}
