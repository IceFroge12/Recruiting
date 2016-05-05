package ua.kpi.nc.persistence.dto;

public class MessageDto {

	private String message;

	private MessageDtoType type;

	public MessageDto() {
	}

	public MessageDto(String message, MessageDtoType type) {
		super();
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageDtoType getType() {
		return type;
	}

	public void setType(MessageDtoType type) {
		this.type = type;
	}

}