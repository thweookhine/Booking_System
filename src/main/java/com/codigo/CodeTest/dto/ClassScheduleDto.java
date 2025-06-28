package com.codigo.CodeTest.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassScheduleDto {

	private Long id;

	@NotBlank(message = "Title is required!")
	private String title;

	@NotBlank(message = "Country is required!")
	private String country;

	@NotNull(message = "Required credits is required")
	@Min(value = 1, message = "Required credits must be at least 1")
	private int requiredCredits;

	@NotNull(message = "Max slots is required")
	@Min(value = 1, message = "Max slots must be at least 1")
	private Integer maxSlots;

	@NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

	private boolean isEnded;
}
