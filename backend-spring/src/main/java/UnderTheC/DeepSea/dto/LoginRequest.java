package UnderTheC.DeepSea.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Schema(description = "유저 id")
    private String id;
    @Schema(description = "유저 password")
    private String password;

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
