package PizzaHut_be.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import PizzaHut_be.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUser {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String password;

    private String username;

    private String email;

    @JsonFormat(pattern = DateUtil.RESPONSE_DATE_FORMAT)
    private LocalDate birthday;

    private String address;

    private String locate;

    private String gender;

    private String phone;

    private String description;

    private String avatar;
}
