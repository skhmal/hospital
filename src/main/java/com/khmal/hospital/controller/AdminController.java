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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Validated
@Controller
@RequestMapping("/administrator")
public class AdminController {

    private final RegistrationService registrationService;

    public AdminController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    final static String SUCCESSFUL = "redirect:/successful";

    @GetMapping("/patient")
    public String createNewPatient(Model model) {
        model.addAttribute("patient", new PatientDtoUserDtoRoleDto());
        return "addPatient";
    }

    @RequestMapping(value = "/patient", method = RequestMethod.POST, params = "action=save")
    public String createNewPatient(@Valid @ModelAttribute("patient") PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {

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
        int doctorRoleId = 3;

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
        int administratorRoleId = 1;
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
        int nurseRoleId = 2;
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

    @Transactional
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
        int pageSize = 5;

        Page<PatientDto> page = registrationService.getAllPatientsPaginated(pageNo, pageSize, sortField, sortDir);
        List<PatientDto> listPatients = page.getContent();
        for (var a:listPatients
             ) {
            System.out.println(a.getUsername() + " " + a.isDischarged());
        }

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listPatients", listPatients);
        return "allPatients";
    }

    @GetMapping("/doctors/{pageNo}")
    public String findPaginatedDoctors(@PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam("sortField") String sortField,
                                       @RequestParam("sortDir") String sortDir,
                                       Model model) {
        int pageSize = 5;

        Page<DoctorDto> page = registrationService.getAllDoctorsPaginated(pageNo, pageSize, sortField, sortDir);
        List<DoctorDto> listDoctors = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listDoctors", listDoctors);
        return "allDoctors";
    }

    @GetMapping("/doctors")
    public String viewDoctorsPage(Model model) {
        return findPaginatedDoctors(1, "firstname", "asc", model);
    }

    @GetMapping("/patients")
    public String viewPatientsPage(Model model) {
        return findPaginatedPatients(1, "firstname", "asc", model);
    }
}
