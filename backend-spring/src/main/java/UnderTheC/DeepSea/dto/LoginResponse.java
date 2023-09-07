package UnderTheC.DeepSea.dto;

import UnderTheC.DeepSea.Entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginResponse extends JsonResponse {
    @Schema(description = "유저 정보")
    private User user;

    public LoginResponse(String status, String message, User user) {
        super(status, message);
        this.user = user;
    }
}
