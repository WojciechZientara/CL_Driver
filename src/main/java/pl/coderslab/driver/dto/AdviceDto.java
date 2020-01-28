package pl.coderslab.driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class AdviceDto {

    private Long id;
    private String author;
    private Long userId;
    private String title;
    private String content;
    private byte[] appendix;
    private Long testId;
    private LocalDateTime created;





}
