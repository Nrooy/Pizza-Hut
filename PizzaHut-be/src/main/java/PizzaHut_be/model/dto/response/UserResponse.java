package PizzaHut_be.model.dto.response;

import PizzaHut_be.model.dto.ResponseDto;
import PizzaHut_be.model.entity.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse<T> {
    private UserModel userModel;
    private ResponseEntity<ResponseDto<T>> response;
    private boolean isNewUser;
}
