package com.khmal.hospital.controller;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dto.DoctorDto;
import com.khmal.hospital.dto.HospitalStaffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.request.HospitalStaffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.service.RegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/administrator")
public class AdminController {

    private final RegistrationService registrationService;

    public AdminController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    private static final String SUCCESSFUL = "redirect:/successful";

    private static final int PAGE_SIZE = 5;
    private static final String DESC_SORT = "DESC";
    private static final String ASC_SORT = "ASC";

    @GetMapping("/patient")
    public String createNewPatient(Model model) {
        model.addAttribute("patient", new PatientDtoUserDtoRoleDto());
        return "addPatient";
    }

    @RequestMapping(value = "/patient", method = RequestMethod.POST, params = "action=save")
    public String createNewPatient(@ModelAttribute("patient") PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {

        String patientRoleName = "ROLE_PATIENT";
        int patientRoleId = registrationService.getRoleIdByName(patientRoleName);

        patientDtoUserDtoRoleDto.setRoleId(patientRoleId);

        registrationService.addPatientToTheSystem(patientDtoUserDtoRoleDto);

        return SUCCESSFUL;
    }

    @GetMapping("/doctor")
    public String createNewDoctor(Model model) {

        model.addAttribute("doctor", new HospitalStaffDtoUserDtoRoleDto());
        model.addAttribute("specializations", HospitalStaff.DoctorSpecialization.values());

        return "addDoctor";
    }

    @RequestMapping(value = "/doctor", method = RequestMethod.POST, params = "action=save")
    public String createNewDoctor(@ModelAttribute("doctor") HospitalStaffDtoUserDtoRoleDto hospitalStaffDtoUserDtoRoleDto,
                                  @RequestParam(value = "doctorSpecialization") String doctorSpecialization) {

        String doctorRoleName = "ROLE_DOCTOR";
        int doctorRoleId = registrationService.getRoleIdByName(doctorRoleName);

        hospitalStaffDtoUserDtoRoleDto.setDoctorSpecialization(doctorSpecialization);
        hospitalStaffDtoUserDtoRoleDto.setStuffRoleId(doctorRoleId);

        registrationService.addEmployeeToTheSystem(hospitalStaffDtoUserDtoRoleDto);

        return SUCCESSFUL;
    }

    @GetMapping("/administrator")
    public String createNewAdministrator(Model model) {
        model.addAttribute("admin", new HospitalStaffDtoUserDtoRoleDto());
        return "addAdministrator";
    }

    @RequestMapping(value = "/administrator", method = RequestMethod.POST, params = "action=save")
    public String createNewAdministrator(@ModelAttribute("admin") HospitalStaffDtoUserDtoRoleDto hospitalStaffDtoUserDtoRoleDto) {

        String administratorRoleName = "ROLE_ADMINISTRATOR";
        int administratorRoleId = registrationService.getRoleIdByName(administratorRoleName);

        String doctorSpecialization = null;

        hospitalStaffDtoUserDtoRoleDto.setStuffRoleId(administratorRoleId);
        hospitalStaffDtoUserDtoRoleDto.setDoctorSpecialization(doctorSpecialization);

        registrationService.addEmployeeToTheSystem(hospitalStaffDtoUserDtoRoleDto);

        return SUCCESSFUL;
    }

    @GetMapping("nurse")
    public String addNewNurse(Model model) {
        model.addAttribute("nurse", new HospitalStaffDtoUserDtoRoleDto());
        return "addNurse";
    }

    @RequestMapping(value = "/nurse", method = RequestMethod.POST, params = "action=save")
    public String addNewNurse(@ModelAttribute("nurse") HospitalStaffDtoUserDtoRoleDto hospitalStaffDtoUserDtoRoleDto) {

        String nurseRoleName = "ROLE_NURSE";
        int nurseRoleId = registrationService.getRoleIdByName(nurseRoleName);

        String doctorSpecialization = null;

        hospitalStaffDtoUserDtoRoleDto.setStuffRoleId(nurseRoleId);
        hospitalStaffDtoUserDtoRoleDto.setDoctorSpecialization(doctorSpecialization);

        registrationService.addEmployeeToTheSystem(hospitalStaffDtoUserDtoRoleDto);

        return SUCCESSFUL;
    }

    @GetMapping("/appoint")
    public String getAppoint(Model model) {
        List<HospitalStaffDto> doctorsList = registrationService.getAllDoctors();
        List<PatientDto> patientsList = registrationService.getAllPatients();

        model.addAttribute("doctors", doctorsList);
        model.addAttribute("patients", patientsList);

        return "appoint";
    }

    @RequestMapping(value = "/appoint", method = RequestMethod.POST, params = "action=save")
    public String getAppoint(@RequestParam("doctor") int doctorId,
                             @RequestParam("patient") int patientId) {

        registrationService.appointDoctorToPatient(doctorId, patientId);

        return SUCCESSFUL;
    }

    @GetMapping("/patients/{pageNo}")
    public String findPaginatedPatients(@PathVariable(value = "pageNo") int pageNo,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir,
                                        Model model) {

        Page<PatientDto> page = registrationService.getAllPatientsPaginated(pageNo, PAGE_SIZE, sortField, sortDir);
        List<PatientDto> listPatients = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? DESC_SORT : ASC_SORT);

        model.addAttribute("listPatients", listPatients);
        return "allPatients";
    }

    @GetMapping("/doctors/{pageNo}")
    public String findPaginatedDoctors(@PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam("sortField") String sortField,
                                       @RequestParam("sortDir") String sortDir,
                                       Model model) {

        Page<DoctorDto> page = registrationService.getAllDoctorsPaginated(pageNo, PAGE_SIZE, sortField, sortDir);
        List<DoctorDto> listDoctors = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? DESC_SORT : ASC_SORT);

        model.addAttribute("listDoctors", listDoctors);
        return "allDoctors";
    }

    @GetMapping("/doctors")
    public String viewDoctorsPage(Model model) {
        return findPaginatedDoctors(1, "lastname", ASC_SORT, model);
    }

    @GetMapping("/patients")
    public String viewPatientsPage(Model model) {
        return findPaginatedPatients(1, "lastname", ASC_SORT, model);
    }
}
