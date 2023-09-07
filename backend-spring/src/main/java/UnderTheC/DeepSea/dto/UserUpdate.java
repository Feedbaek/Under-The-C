package UnderTheC.DeepSea.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserUpdate {
    @Schema(description = "유저 password")
    private String password;

    @Schema(description = "유저 email")
    private String email;

    public UserUpdate(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
