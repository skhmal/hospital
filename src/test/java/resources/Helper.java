package resources;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.dto.StaffRoleDto;
import com.khmal.hospital.mapper.HospitalStuffMapper;
import com.khmal.hospital.mapper.PatientMapper;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class Helper {
//    Patient
    private static final String PATIENT_FIRSTNAME = "Jan";
    private static final String PATIENT_LASTNAME = "Grabowski";
    private static final String PATIENT_USERNAME = "jb";
    private static final LocalDate PATIENT_BIRTHDAY = LocalDate.of(1991, 2, 28);
    private static final String ROLE_PATIENT = "ROLE_PATIENT";
    private static final StaffRole STAFF_ROLE_PATIENT = new StaffRole(ROLE_PATIENT);
    private static final Patient PATIENT = new Patient(PATIENT_FIRSTNAME, PATIENT_LASTNAME,
            PATIENT_USERNAME, PATIENT_BIRTHDAY, STAFF_ROLE_PATIENT);

// Doctor
    private static final int DOCTOR_ID = 1;
    private static final String DOCTOR_FIRSTNAME = "john";
    private static final String DOCTOR_LASTNAME = "schwarc";
    private static final String DOCTOR_USERNAME = "js";
    private static final String DOCTOR_SPECIALIZATION = "surgeon";
    private static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    private static final StaffRole STAFF_ROLE_DOCTOR = new StaffRole(ROLE_DOCTOR);
    private static final HospitalStaff DOCTOR = new HospitalStaff(DOCTOR_ID, DOCTOR_FIRSTNAME, DOCTOR_LASTNAME,
            DOCTOR_USERNAME, DOCTOR_SPECIALIZATION, STAFF_ROLE_DOCTOR);

//   Appointment
    private static final LocalDateTime APPOINTMENT_DATE = LocalDateTime.of(2022, 6, 12, 15, 45);
    private static final String APPOINTMENT_TYPE = "CONSULTATION";
    private static final String SUMMARY = "HEALTHY";


//    Diagnose
  private static final LocalDate DIAGNOSE_DATE = LocalDate.of(2022, 3,19);
private static final int DIAGNOSE_ID = 123;

    private static final String PASSWORD = "{noop}1234";

    public static Appointment getAppointment() {

        List<Patient> patientList = new ArrayList<>();
        List<HospitalStaff> doctorList = new ArrayList<>();

        patientList.add(PATIENT);
        PATIENT.setDoctorsList(doctorList);

        doctorList.add(DOCTOR);
        DOCTOR.setPatientsList(patientList);

        return new Appointment(APPOINTMENT_DATE, APPOINTMENT_TYPE, SUMMARY, PATIENT, DOCTOR);
    }

    public static AppointmentDto getAppointmentDto() {
        int appointmentId = 777;
        List<Patient> patientList = new ArrayList<>();
        List<HospitalStaff> doctorList = new ArrayList<>();

        patientList.add(PATIENT);
        PATIENT.setDoctorsList(doctorList);

        doctorList.add(DOCTOR);
        DOCTOR.setPatientsList(patientList);

        return new AppointmentDto(
                appointmentId,
                APPOINTMENT_DATE,
                APPOINTMENT_TYPE,
                PatientMapper.INSTANCE.toDto(PATIENT),
                SUMMARY,
                HospitalStuffMapper.INSTANCE.toDto(DOCTOR));
    }

    public static StaffRole getStaffRole(){
       return new StaffRole(ROLE_DOCTOR);
    }

    public static StaffRoleDto getStaffRoleDto(){
        return new StaffRoleDto(ROLE_DOCTOR);
    }

    public static Diagnose getDiagnose(){
        return new Diagnose(SUMMARY, DIAGNOSE_DATE, PATIENT, DOCTOR);
    }

    public static DiagnoseDto getDiagnoseDto(){
        return new DiagnoseDto(
                DIAGNOSE_ID,
                SUMMARY,
                DIAGNOSE_DATE,
                null,
                PatientMapper.INSTANCE.toDto(PATIENT),
                HospitalStuffMapper.INSTANCE.toDto(DOCTOR));
    }
}
