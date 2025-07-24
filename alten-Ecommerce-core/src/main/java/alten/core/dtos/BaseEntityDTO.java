package alten.core.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BaseEntityDTO {
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
