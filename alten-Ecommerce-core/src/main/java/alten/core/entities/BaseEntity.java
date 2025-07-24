package alten.core.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

}
