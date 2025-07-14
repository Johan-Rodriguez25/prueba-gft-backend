package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.adapters;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out.NotificationPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Repository
public class SMSNotificationAdapter implements NotificationPort {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String twilioAccountSid;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String twilioAuthToken;
    @Value("${TWILIO_PHONE_NUMBER}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    public void sendSubscriptionNotification(Client client, Fund fund) {
        String messageBody = String.format(
                "BTG Pactual: Hola %s, tu suscripción al fondo %s por %s fue exitosa. Tu nuevo saldo es %s.",
                client.fullName().split(" ")[0],
                fund.name(),
                formatCurrency(fund.minimumAmount()),
                formatCurrency(client.balance())
        );

        PhoneNumber to = new PhoneNumber(client.phoneNumber());
        PhoneNumber from = new PhoneNumber(twilioPhoneNumber);

        Message.creator(to, from, messageBody).create();
    }

    private String formatCurrency(BigDecimal amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return currencyFormatter.format(amount);
    }

    public boolean supports(Client.NotificationPreference preference) {
        return preference == Client.NotificationPreference.SMS;
    }
}
