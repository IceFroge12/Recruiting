package ua.kpi.nc.persistence.model.enums;

public enum StatusEnum {
	REGISTERED(1L), IN_REVIEW(2L), APPROVED(3L), PENDING_RESULTS(4L), APPROVED_TO_JOB(5L), APPROVED_TO_GENERAL_COURSES(
			6L), APPROVED_TO_ADVANCED_COURSES(7L), REJECTED(8L);
	Long id;

	StatusEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
