package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.TestConverter;
import pl.coderslab.Driver.dto.TestDto;
import pl.coderslab.Driver.entities.Test;
import pl.coderslab.Driver.repositories.TestRepository;

@RestController
@RequestMapping("/admin/test")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminTestController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestConverter testConverter;


    @GetMapping("/getTest/{testId}")
    public ResponseEntity<?> getTest(@PathVariable Long testId){

        Test test = testRepository.findTestById(testId);
        if (test == null) {
            return ResponseEntity.ok("No test with such id");
        } else {
            return ResponseEntity.ok(testConverter.convertTestToTestDto(test));
        }
    }

    @PostMapping("/postTest")
    public ResponseEntity<?> postTest(@RequestBody TestDto testDto) {
        Test test = testConverter.convertTestDtoToTest(testDto);
        testDto.setId(testRepository.save(test).getId());
        return ResponseEntity.ok(testDto);
    }

    @PutMapping("/editTest")
    public ResponseEntity<?> putTest(@RequestBody TestDto testDto) {
        Test test = testConverter.updateTest(testDto);
        testRepository.save(test);
        return ResponseEntity.ok(testConverter.convertTestToTestDto(test));
    }

    @DeleteMapping("/deleteTest/{testId}")
    public ResponseEntity<?> deleteTest(@PathVariable Long testId) {
        testRepository.deleteById(testId);
        return ResponseEntity.ok().body("Success");
    }

}
