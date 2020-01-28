package pl.coderslab.driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class TestDto {

    private Long id;
    private Long adviceId;
    private String question;

}
