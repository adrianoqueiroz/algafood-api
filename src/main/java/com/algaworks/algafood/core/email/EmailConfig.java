package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final EmailProperties emailProperties;
    private final freemarker.template.Configuration freemarkerConfig;
    private final JavaMailSender mailSender;

    @Bean
    public EnvioEmailService envioEmailService() {

        return switch (emailProperties.getImpl()) {
            case FAKE -> new FakeEnvioEmailService(emailProperties, freemarkerConfig);
            case SMTP -> new SmtpEnvioEmailService(mailSender, emailProperties, freemarkerConfig);
            case SANDBOX -> new SandboxEnvioEmailService(mailSender, emailProperties, freemarkerConfig);
        };
    }
}
