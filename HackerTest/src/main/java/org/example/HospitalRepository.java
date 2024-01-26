package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HospitalRepository extends CrudRepository<Hospital, Integer> {
    public Hospital findHospitalById(Integer id);
//    public Hospital findHospitalById(Integer id);
    @Override
    public List<Hospital> findAll();
}
