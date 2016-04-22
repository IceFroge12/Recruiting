package ua.kpi.nc.persistence.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EmailTemplate implements Serializable{

	private static final long serialVersionUID = -325738497072982583L;

	private Long id;

	private String title;

	private String text;

	private NotificationType notificationType;

	public EmailTemplate() {
	}

	public EmailTemplate(Long id, String title, NotificationType notificationType) {
		super();
		this.id = id;
		this.title = title;
		this.notificationType = notificationType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {return text; }

	public void setText(String text) { this.text = text;}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	@Override
	public String toString() {
		return "EmailTemplate [id=" + id + ", title=" + title + ", notificationType=" + notificationType + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).append(notificationType).append(title).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailTemplate other = (EmailTemplate) obj;
		return new EqualsBuilder().append(id, other.id).append(title, other.title)
				.append(notificationType, other.notificationType).isEquals();
	}

}
