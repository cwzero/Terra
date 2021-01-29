package com.liquidforte.terra.config;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CacheConfig extends DatabaseConfig {
    @Getter(onMethod = @__({@Override}))
    private String defaultUrl = "jdbc:h2:tcp://localhost/~/.terra2/db/cache";
}
