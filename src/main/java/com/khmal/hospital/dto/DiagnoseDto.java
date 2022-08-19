package com.khmal.hospital.dto;

import com.khmal.hospital.service.validator.CreateOrUpdateMarker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DiagnoseDto {

    @Null(groups = CreateOrUpdateMarker.OnCreate.class)
    @NotNull(groups = CreateOrUpdateMarker.OnUpdate.class)
    private int id;

    @NotBlank(message = "Field summary must not be empty")
    private String summary;


    @NotNull(message = "Field diagnose date must not be empty")
    private LocalDate diagnoseDate;

    @Null(groups = CreateOrUpdateMarker.OnCreate.class, message = "Field edit date must be null")
    @NotNull(groups = CreateOrUpdateMarker.OnUpdate.class)
    private LocalDate editDate;

    @NotNull(message = "Field patient must not be empty")
    private PatientDto patient;

    @NotNull(message = "Field employee must not be empty")
    private HospitalStaffDto hospitalStaff;

    public DiagnoseDto(int id, String summary, LocalDate diagnoseDate, LocalDate editDate, PatientDto patient, HospitalStaffDto hospitalStaff) {
        this.id = id;
        this.summary = summary;
        this.diagnoseDate = diagnoseDate;
        this.editDate = editDate;
        this.patient = patient;
        this.hospitalStaff = hospitalStaff;
    }
}
