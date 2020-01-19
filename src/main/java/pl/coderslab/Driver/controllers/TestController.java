package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.Driver.converters.TestConverter;
import pl.coderslab.Driver.dto.TestDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Test;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.TestRepository;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    TestConverter testConverter;

    @GetMapping("/get/{adviceId}")
    public TestDto getTestByAdviceId(@PathVariable Long adviceId){
        Advice advice = adviceRepository.findAdviceById(adviceId);
        List<Test> tests = testRepository.findTestWithAnswersByAdviceId(advice.getId());
        Test test = tests.get(0);
        return testConverter.convertTestToTestDto(test);
    }

}
