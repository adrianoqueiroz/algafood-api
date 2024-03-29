package com.algaworks.algafood.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

    private S3 s3 = new S3();
    private Local local = new Local();
    private  TipoStorage tipo = TipoStorage.LOCAL;

    @Getter
    @Setter
    public static class Local {
        private Path diretorioFotos;
    }

    @Getter
    @Setter
    public static class S3 {
        private String accessKeyId;
        private String secretAccessKey;
        private String bucket;
        private Regions region;
        private String diretorioFotos;
    }

    public enum TipoStorage {
        S3, LOCAL
    }

}
