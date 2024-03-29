package com.khmal.hospital.dto;

import com.khmal.hospital.service.validator.CreateOrUpdateMarker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Validated
@RequiredArgsConstructor
public class AppointmentDto {

    @Null(groups = CreateOrUpdateMarker.OnCreate.class)
    @NotNull(groups = CreateOrUpdateMarker.OnUpdate.class)
    private Integer id;

    @NotNull(message = "Field date must not be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    @NotBlank(message = "Field appointment type must not be empty")
    private String appointmentType;

    @NotNull(message = "Field patient must not be empty")
    private PatientDto patient;

    @NotBlank(message = "Field summary must not be empty")
    private String summary;

    @NotNull(message = "Field employee must not be empty")
    private HospitalStaffDto hospitalStaff;

    private Integer patientId;

    public AppointmentDto(Integer id, LocalDateTime date, String appointmentType, PatientDto patient, String summary, HospitalStaffDto hospitalStaff) {
        this.id = id;
        this.date = date;
        this.appointmentType = appointmentType;
        this.patient = patient;
        this.summary = summary;
        this.hospitalStaff = hospitalStaff;
    }
}
