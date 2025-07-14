package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.adapters;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out.NotificationPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Locale;

@Repository
public class EmailNotificationAdapter implements NotificationPort {
    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;
    private String emailTemplate;
    @Value("${MAIL_USERNAME}")
    private String mailUsername;

    public EmailNotificationAdapter(
            JavaMailSender mailSender,
            @Qualifier("webApplicationContext") ResourceLoader resourceLoader
    ) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
        loadEmailTemplate();
    }

    private void loadEmailTemplate() {
        try {
            Resource resource = resourceLoader.getResource(
                    "classpath:templates/email/subscription-template.html"
            );

            this.emailTemplate = resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la plantilla de email", e);
        }
    }

    private String populateTemplate(Client client, Fund fund) {
        return emailTemplate
                .replace("{{clientName}}", client.fullName())
                .replace("{{fundName}}", fund.name())
                .replace("{{subscriptionAmount}}", formatCurrency(fund.minimumAmount()))
                .replace("{{newBalance}}", formatCurrency(client.balance()));
    }

    private String formatCurrency(BigDecimal amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return currencyFormatter.format(amount);
    }

    public void sendSubscriptionNotification(Client client, Fund fund) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(client.email());
            helper.setFrom(mailUsername);
            helper.setSubject("BTG Pactual: Confirmación de Suscripción a Fondo");

            // Remplazar los placeholders en la plantilla
            String htmlBody = populateTemplate(client, fund);
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo electrónico", e);
        }
    }

    public boolean supports(Client.NotificationPreference preference) {
        return preference == Client.NotificationPreference.EMAIL;
    }
}
