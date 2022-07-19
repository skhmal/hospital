package com.khmal.hospital.controller;

import com.khmal.hospital.dto.*;
import com.khmal.hospital.service.*;
import com.khmal.hospital.webb.PatientUserRole;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


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
    public PatientDto addNewPatient(@RequestBody PatientUserRole patient) {
        System.out.println(patient);
        patientService.savePatient(patient.getPatientDto());
        userService.saveUser(patient.getUserDto());
        roleService.addRole(patient.getRoleDto());

        return patient.getPatientDto();
    }

//    @GetMapping("/patients")
//    public List<PatientDto> getAllPatients() {
//        List<Patient> patientList = patientService.getAllPatients();
//
//        if (patientList == null) {
//            throw new NoSuchUserException("Patient list is empty");
//        }
//        return PatientMapper.INSTANCE.toDto(patientList);
//    }
//
//    @PutMapping("/patient")
//    public PatientDto updatePatient(@RequestBody PatientUserRole patientDto) {
//
//        Patient patient = PatientMapper.INSTANCE.toEntity(patientDto);
//        //draft, for instance
//        try {
//            patientService.savePatient(patient);
//        } catch (Exception e) {
//            throw new NoSuchUserException("Patient not updated");
//        }
//        return patientDto;
//    }
//
//    @DeleteMapping("/patient")
//    public void deletePatient(@RequestBody PatientDto patientDto) {
//
//        try {
//            patientService.deletePatient(PatientMapper.INSTANCE.toEntity(patientDto));
//        } catch (Exception e) {
//            throw new NoSuchUserException("Patient wasn't delete");
//        }
//
//    }
//
//    @PostMapping("/nurse")
//    public NurseDto addNewNurse(@RequestBody UserDto userDto) {
//        Nurse nurse = new Nurse(userService.addNewUser(UserMapper.INSTANCE.toEntity(userDto)));
//
//        try {
//            nurseService.saveNurse(nurse);
//        } catch (Exception e) {
//            throw new NoSuchUserException("Nurse wasn't saved");
//        }
//
//        roleService.addRole(new Role(userDto.getUsername(), Nurse.ROLE));
//
//        return NurseMapper.INSTANCE.toDto(nurse);
//    }
//
//    @GetMapping("/nurse")
//    public List<NurseDto> getAllNurses() {
//        List<Nurse> nurseList = nurseService.getAllNurses();
//
//        if (nurseList == null) {
//            throw new NoSuchUserException("There is no any nurse");
//        }
//
//        return NurseMapper.INSTANCE.toDto(nurseList);
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
//    public Doctor addNewDoctor(@RequestBody UserDoctorRequest userDoctorRequest) {
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
