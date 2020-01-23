package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.AdviceConverter;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.UserRepository;

@RestController
@RequestMapping("/admin/advice")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminAdviceController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    AdviceConverter adviceConverter;

    @GetMapping("/getAdvice/{adviceId}")
    public ResponseEntity<?> getAdvice(@PathVariable Long adviceId){

        Advice advice = adviceRepository.findAdviceById(adviceId);
        if (advice == null) {
            return ResponseEntity.ok("No advice with such id");
        } else {
            return ResponseEntity.ok(adviceConverter.convertAdviceToDto(advice));
        }
    }

    @PutMapping("/editAdvice")
    public ResponseEntity<?> editAdvice(@RequestBody AdviceDto adviceDto) {
        Advice originalAdvice = adviceRepository.findAdviceById(adviceDto.getId());
        User user = userRepository.findUserById(adviceDto.getUserId());
        Advice newAdvice = adviceConverter.convertAdviceDtoToAdvice(adviceDto);
        adviceRepository.save(newAdvice);
        return ResponseEntity.ok(adviceDto);
    }

    @DeleteMapping("/deleteAdvice/{adviceId}")
    public ResponseEntity<?> deleteADvice(@PathVariable Long adviceId) {
        adviceRepository.deleteById(adviceId);
        return ResponseEntity.ok().body("Success");
    }


}
