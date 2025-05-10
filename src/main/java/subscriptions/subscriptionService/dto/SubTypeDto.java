package subscriptions.subscriptionService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "Dto модель типа подписки.")
public class SubTypeDto {

    @Schema(description = "Название типа подписки.")
    private String name;

    @Schema(description = "Список уникальных идентификаторов подписок с данным типом.")
    private List<Integer> subscriptionsIds;

}
