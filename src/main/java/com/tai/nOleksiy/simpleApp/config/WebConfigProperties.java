package com.tai.nOleksiy.simpleApp.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix="web")
@ConstructorBinding
public class WebConfigProperties {
    private final Cors cors;

    public WebConfigProperties(Cors cors) {
        this.cors = cors;
    }

    public Cors getCors(){
        return this.cors;
    }

    public static class Cors {
        private final String[] allowedOrigins;
        private final String[] allowedMethods;
        private final String[] allowedHeaders;
        private final String[] exposedHeaders;
        private final long maxAge;

        public Cors(String[] allowedOrigins, String[] allowedMethods, String[] allowedHeaders, String[] exposedHeaders, long maxAge){
            this.allowedOrigins=allowedOrigins;
            this.allowedHeaders=allowedHeaders;
            this.allowedMethods=allowedMethods;
            this.exposedHeaders=exposedHeaders;
            this.maxAge=maxAge;

        }
        public String[] getAllowedOrigins(){
            return this.allowedOrigins;
        }

        public String[] getAllowedMethods() {
            return this.allowedMethods;
        }

        public String[] getAllowedHeaders() {
            return this.allowedHeaders;
        }

        public long getMaxAge() {
            return this.maxAge;
        }

        public String[] getExposedHeaders() {
            return this.exposedHeaders;
        }

    }
}
