package UnderTheC.DeepSea.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserAdd {
    @Schema(description = "유저 id")
    private String id;
    @Schema(description = "유저 password")
    private String password;

    @Schema(description = "유저 email")
    private String email;

    public UserAdd(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
}
