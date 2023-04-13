package com.example.jpaapp;

import com.example.jpaapp.entities.Patient;
import com.example.jpaapp.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaAppApplication implements CommandLineRunner {

    @Autowired
    PatientRepository patientRepository;
    public static void main(String[] args) {

        SpringApplication.run(JpaAppApplication.class, args);  
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0; i<100; i++){
            patientRepository.save(
                    new Patient(null,"yasmina",new Date(),Math.random()>0.5?true:false,(int)(Math.random()*100)));
        }


        Page<Patient> patients=patientRepository.findAll(PageRequest.of(1,5));
        System.out.println("Total pages :"+patients.getTotalPages());
        System.out.println("Tolal elemnts : "+patients.getTotalElements());
        System.out.println("Num Page :"+patients.getNumber());
        List<Patient> content=patients.getContent();
        Page<Patient> byMalade=patientRepository.findByMalade(true, PageRequest.of(0,5));
        List<Patient> patientList=patientRepository.chercherPatients("%s%",14);
        byMalade.forEach(patient -> {
            System.out.println("============================");
            System.out.println(patient.getId());
            System.out.println(patient.getNom());
            System.out.println(patient.getScore());
            System.out.println(patient.getDateNaissance());
            System.out.println(patient.isMalade());
        });
        System.out.println("==========================");
        Patient patient=patientRepository.findById(1L).orElse(null);
        if (patient!=null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        patient.setScore(870);
        patientRepository.save(patient);
        patientRepository.deleteById(1L);
    }
}
