package com.khmal.hospital.controller;

import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.service.HospitalStuffService;
import com.khmal.hospital.service.PatientService;
import com.khmal.hospital.service.RoleService;
import com.khmal.hospital.service.UserServiceImpl;
import com.khmal.hospital.dto.webb.HospitalStuffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.webb.PatientDtoUserDtoRoleDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    private final PatientService patientService;
    private final UserServiceImpl userService;
    private final HospitalStuffService hospitalStuffService;
    private final RoleService roleService;

    public AdministratorController(PatientService patientService, UserServiceImpl userService, HospitalStuffService hospitalStuffService, RoleService roleService) {
        this.patientService = patientService;
        this.userService = userService;
        this.hospitalStuffService = hospitalStuffService;
        this.roleService = roleService;
    }


    @PostMapping("/patient")
    public PatientDto addNewPatient(@RequestBody PatientDtoUserDtoRoleDto patient) {

        patientService.addNewPatient(patient.getPatientDto());

        userService.saveUser(patient.getUserDto());

        roleService.addRole(patient.getRoleDto());

        return patient.getPatientDto();
    }

    @GetMapping("/patients")
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PutMapping("/patient")
    public PatientDto updatePatient(@RequestBody PatientDto patientDto) {
        return patientService.updatePatient(patientDto);
    }

    @DeleteMapping("/patient")
    public void deletePatient(@RequestBody PatientDto patientDto) {
        patientService.deletePatient(patientDto);
    }

    @PostMapping("/employee")
    public HospitalStuffDto addNewEmployee(@RequestBody HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {
        return hospitalStuffService.addNewEmployee(hospitalStuffDtoUserDtoRoleDto);
    }

    @DeleteMapping("/patient")
    public void deleteEmployee(@RequestBody HospitalStuffDto hospitalStuffDto) {
        hospitalStuffService.deleteEmployee(hospitalStuffDto);
    }

//    @GetMapping("/nurse")
//    public List<HospitalStuffDto> getAllNurses() {
//
//        return  hospitalStuffService.getAllNurse();;
//    }
//
//    @PutMapping("/nurse")
//    public NurseDto updateNurse(@RequestBody NurseDto nurseDto) {
//
//        Nurse nurse = NurseMapper.INSTANCE.toEntity(nurseDto);
//
//        try {
//            nurseService.saveNurse(nurse);
//        } catch (Exception e) {
//            throw new NoSuchUserException("Nurse wasn't update");
//        }
//
//        return nurseDto;
//    }
//
//    @DeleteMapping("/nurse")
//    public void deleteNurse(@RequestBody NurseDto nurseDto) {
//
//        try {
//            nurseService.deleteNurse(NurseMapper.INSTANCE.toEntity(nurseDto));
//        } catch (Exception e) {
//            throw new NoSuchUserException("Nurse wasn't delete");
//        }
//
//    }
//
//
//    @PostMapping("/doctor")
//    public HospitalStuffDto addNewDoctor(@RequestBody HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {
//
//        User user = userService.addNewUser(userDoctorRequest.getUser());
//
//        Doctor doctor = new Doctor(userDoctorRequest.getDoctor()
//                .getDoctorSpecialization(), user);
//
//        doctorService.saveDoctor(doctor);
//        roleService.addRole(new Role(userDoctorRequest.getUser().getUsername(), Doctor.ROLE));
//
//        return doctor;
//    }
//
//    @GetMapping("/doctors")
//    public List<DoctorDto> getAllDoctors() {
//
//        List<Doctor> doctorList = doctorService.getAllDoctors();
//
//        if (doctorList.isEmpty()) {
//            throw new NoSuchUserException("Doctor list is empty");
//        }
//
//        List<DoctorDto> doctorDtoList = DoctorMapper.INSTANCE.toDto(doctorList);
//
//        return doctorDtoList;
//    }
//
//    @PutMapping("/doctor")
//    public DoctorDto updateDoctor(DoctorDto doctorDto) {
//
//        try {
//            doctorService.saveDoctor(DoctorMapper.INSTANCE.toEntity(doctorDto));
//        } catch (Exception e) {
//            throw new NoSuchUserException("Doctor wasn't updated");
//        }
//
//        return doctorDto;
//    }
//
//    @PostMapping("/doctor/appoint")
//    public DoctorDto appointPatientToTheDoctor(@RequestBody DoctorPatientRequest doctorPatientRequest) {
//
//        Doctor doctor = doctorService.getDoctorById(doctorPatientRequest.getDoctor().getId());
//        Patient patient = patientService.getPatientById(doctorPatientRequest.getPatient().getId());
//
//        doctor.addPatientToPatientList(patient);
//        doctorService.saveDoctor(doctor);
//
//        return DoctorMapper.INSTANCE.toDto(doctor);
//    }
//
//    @DeleteMapping("/doctor")
//    public void deleteDoctor(DoctorDto doctorDto) {
//
//        try {
//            doctorService.deleteDoctor(DoctorMapper.INSTANCE.toEntity(doctorDto));
//        } catch (Exception e) {
//            throw new NoSuchUserException("Doctor wasn't deleted");
//        }
//    }
//
//    @PostMapping("/admin")
//    public AdministratorDto addNewAdministrator(@RequestBody UserDto userDto) {
//        Administrator administrator = new Administrator(
//                userService.addNewUser(
//                        UserMapper.INSTANCE.toEntity(userDto))
//        );
//        try {
//            administratorService.saveAdmin(administrator);
//            roleService.addRole(new Role(userDto.getUsername(), Administrator.ROLE));
//        } catch (Exception e) {
//            throw new NoSuchUserException("Administrator wasn't created");
//        }
//
//        return AdministratorMapper.INSTANCE.toDto(administrator);
//    }
//
//    @GetMapping("/admin")
//    public List<AdministratorDto> getAllAdministrators() {
//        List<Administrator> administratorList = administratorService.getAllAdmins();
//
//        if (administratorList == null) {
//            throw new NoSuchUserException("There is no any administrators");
//        }
//
//        return AdministratorMapper.INSTANCE.toDto(administratorService.getAllAdmins());
//    }
//
//    @PutMapping("/admin")
//    public AdministratorDto updateAdministrator(AdministratorDto administratorDto) {
//
//        try {
//            administratorService.saveAdmin(
//                    AdministratorMapper.INSTANCE.toEntity(administratorDto));
//        }catch (Exception e){
//            throw new NoSuchUserException("Administrator wasn't updated");
//        }
//
//        return  administratorDto;
//    }
//
//    @DeleteMapping("/admin")
//    public void deleteAdministrator(AdministratorDto administratorDto) {
//
//        try {
//            administratorService.deleteAdmin(
//                    AdministratorMapper.INSTANCE.toEntity(administratorDto));
//        }catch (Exception e){
//            throw new NoSuchUserException("Administrator wasn't deleted");
//        }
//    }


}
