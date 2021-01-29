package com.liquidforte.terra.config;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class LockConfig extends DatabaseConfig {
    @Getter(onMethod = @__({@Override}))
    private String defaultUrl = "jdbc:h2:tcp://localhost/mem:lock";
}
