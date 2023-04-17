package shop.mtcoding.metamall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor //<-> Builder
public class ValidationDTO {
    private String key;
    private String value;

}
