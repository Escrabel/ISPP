package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.URL;
@Embeddable
@Access(AccessType.PROPERTY)
public class Picture {

	private String url;

	@URL
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return this.url;
	}

}




