package com.liquidforte.terra.config;

import com.google.common.base.Strings;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class DatabaseConfig {
    @Getter
    @Setter
    private String username = "sa";

    @Getter
    @Setter
    private String password = "sa";

    public abstract String getDefaultUrl();

    @Setter
    private String url = null;

    public String getUrl() {
        if (Strings.isNullOrEmpty(url)) {
            url = getDefaultUrl();
        }
        return url;
    }
}
