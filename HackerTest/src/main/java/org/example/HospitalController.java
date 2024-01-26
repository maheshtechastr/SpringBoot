package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/test/")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public String ping() {
        return "Pong!";
    }

    @GetMapping(value = {"/hospitals/", "/hospitals"})
    public @ResponseBody List<Hospital> getAllHospitals() {
        List<Hospital> hospitalList = hospitalService.getAllHospitals();
        if (hospitalList.size() > 0)
            return hospitalList;
        else
            return Collections.emptyList();
    }


    @GetMapping("/hospitals/{id}")
    public @ResponseBody Hospital getHospital(@PathVariable("id") int id) {
        try {
            return hospitalService.getHospital(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = {"/hospitals/", "/hospitals"})
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> addHospital(@RequestBody Hospital hospital) throws Exception {
        try {
            hospitalService.addHospital(hospital);
            return new ResponseEntity<>("Successfully Added", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error while Adding", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = {"/hospitals/", "/hospitals"})
    public ResponseEntity<String> updateHospital(@RequestBody Hospital hospital) {
        try {
            Hospital result = hospitalService.getHospital(hospital.getId());
            result.setCity(hospital.getCity());
            result.setName(hospital.getName());
            result.setRating(hospital.getRating());
            hospitalService.updateHospital(result);
            return new ResponseEntity<>("Successfully Updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while Updating", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = {"/hospitals/", "/hospitals"})
    public ResponseEntity<String> deleteHospital(@RequestBody Hospital hospital) {
        try {
            if (hospitalService.getHospital(hospital.getId()) != null) {
                hospitalService.deleteHospital(hospital);
                return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
            } else return new ResponseEntity<>("NO_CONTENT", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting", HttpStatus.NOT_FOUND);
        }
    }
}
