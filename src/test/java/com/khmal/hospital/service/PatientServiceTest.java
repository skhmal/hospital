package com.khmal.hospital.service;

import com.khmal.hospital.dao.repository.AppointmentRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class PatientServiceTest {

   @InjectMocks
   private PatientService patientService;

   @Mock
   private AppointmentRepository appointmentRepository;

    @Test
    @Disabled
    void getAllPatientAppointmentsPaginated() {
//        Page<Appointment> pro= Mockito.mock(Page.class);
//        Pageable pageable = PageRequest.of(1,10 );
//        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(pro);
//
//        assertEquals(pro, patientService.getAllPatientAppointmentsPaginated(1,10,"date",
//                "asc",2));
    }

    @Test
    @Disabled
    void getAllPatientDiagnosesPaginated() {
    }
}