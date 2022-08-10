package com.khmal.hospital.controller;

import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dto.DoctorDto;
import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.request.HospitalStuffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.service.RegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/administrator")
public class AdminController {

    private final RegistrationService registrationService;

    public AdminController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @GetMapping("/patient")
    public String createNewPatient(Model model) {
        model.addAttribute("patient", new PatientDtoUserDtoRoleDto());
        return "addPatient";
    }

    @RequestMapping(value = "/patient", method = RequestMethod.POST, params = "action=save")
    public RedirectView createNewPatient(@ModelAttribute("patient") PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {

        registrationService.addPatientToTheSystem(patientDtoUserDtoRoleDto);

        return new RedirectView("redirect:/successful");
    }

    @GetMapping("/doctor")
    public String createNewDoctor(Model model) {

        model.addAttribute("doctor", new HospitalStuffDtoUserDtoRoleDto());
        model.addAttribute("specializations", HospitalStuff.DoctorSpecialization.values());

        return "addDoctor";
    }

    @RequestMapping(value = "/doctor", method = RequestMethod.POST, params = "action=save")
    public String createNewDoctor(@ModelAttribute("doctor") HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto,
                                  @RequestParam(value = "doctorSpecialization") String doctorSpecialization) {
        int doctorRoleId = 3;

        hospitalStuffDtoUserDtoRoleDto.setDoctorSpecialization(doctorSpecialization);
        hospitalStuffDtoUserDtoRoleDto.setStuffRoleId(doctorRoleId);

        registrationService.addEmployeeToTheSystem(hospitalStuffDtoUserDtoRoleDto);

        return "redirect:/successful";
    }

    @GetMapping("/administrator")
    public String createNewAdministrator(Model model) {
        model.addAttribute("admin", new HospitalStuffDtoUserDtoRoleDto());
        return "redirect:/addAdministrator";
    }

    @RequestMapping(value = "/administrator", method = RequestMethod.POST, params = "action=save")
    public String createNewAdministrator(@ModelAttribute("admin") HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {
        int administratorRoleId = 1;
        String doctorSpecialization = null;

        hospitalStuffDtoUserDtoRoleDto.setStuffRoleId(administratorRoleId);
        hospitalStuffDtoUserDtoRoleDto.setDoctorSpecialization(doctorSpecialization);

        registrationService.addEmployeeToTheSystem(hospitalStuffDtoUserDtoRoleDto);

        return "redirect:/successful";
    }

    @GetMapping("nurse")
    public String addNewNurse(Model model) {
        model.addAttribute("nurse", new HospitalStuffDtoUserDtoRoleDto());
        return "addNurse";
    }

    @RequestMapping(value = "/nurse", method = RequestMethod.POST, params = "action=save")
    public String addNewNurse(@ModelAttribute("nurse") HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {
        int nurseRoleId = 2;
        String doctorSpecialization = null;

        hospitalStuffDtoUserDtoRoleDto.setStuffRoleId(nurseRoleId);
        hospitalStuffDtoUserDtoRoleDto.setDoctorSpecialization(doctorSpecialization);

        registrationService.addEmployeeToTheSystem(hospitalStuffDtoUserDtoRoleDto);

        return "redirect:/successful";
    }

    @GetMapping("/appoint")
    public String getAppoint(Model model) {
        List<HospitalStuffDto> doctorsList = registrationService.getAllDoctors();
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

        return "redirect:/successful";
    }

    @GetMapping("/patients/{pageNo}")
    public String findPaginatedPatients(@PathVariable(value = "pageNo") int pageNo,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir,
                                        Model model) {
        int pageSize = 5;

        Page<PatientDto> page = registrationService.getAllPatientsPaginated(pageNo, pageSize, sortField, sortDir);
        List<PatientDto> listPatients = page.getContent();

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

        Page<DoctorDto> page = registrationService.getAllDoctorsPaginated1(pageNo, pageSize, sortField, sortDir);
        List<DoctorDto> listDoctors = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listDoctors", listDoctors);
        return "allDoctors1";
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
