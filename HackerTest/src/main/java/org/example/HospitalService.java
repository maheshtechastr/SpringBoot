package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {
   @Autowired
    private HospitalRepository hospitalRepository;

   public List<Hospital> getAllHospitals() {
       List<Hospital> hospitals = new ArrayList<>();
       hospitals.addAll(hospitalRepository.findAll());
       return hospitals;
    }
    public Hospital getHospital(int id) throws Exception {
       return hospitalRepository.findHospitalById(id);
    }
    public void addHospital(Hospital hospital){
       hospitalRepository.save(hospital);
    }
    public void updateHospital(Hospital hospital){
        hospitalRepository.save(hospital);
    }
    public void deleteHospital(Hospital hospital){
       hospitalRepository.deleteAll();
       hospitalRepository.delete(hospital);
    }
}
