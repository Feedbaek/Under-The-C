package UnderTheC.DeepSea.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class JsonResponse {
    @Schema(description = "성공 여부")
    private String status;
    @Schema(description = "응답 메세지")
    private String message;

    public JsonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
