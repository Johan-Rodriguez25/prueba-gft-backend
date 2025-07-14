package com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.services;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.in.*;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out.ClientRepositoryPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Role;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.UpdateClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService implements
        CreateClientUseCase,
        GetClientByIdUseCase,
        UpdateClientUseCase,
        GetClientByEmailUseCase,
        GetCurrentClientUseCase
{
    private final ClientRepositoryPort clientRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public ClientService(
            ClientRepositoryPort clientRepositoryPort,
                         PasswordEncoder passwordEncoder
    ) {
        this.clientRepositoryPort = clientRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client createClient(Client client) {
        String encryptedPassword = passwordEncoder.encode(client.password());

        Client clientToSave = new Client(
                client.id(),
                client.fullName(),
                client.email(),
                encryptedPassword,
                Role.USER,
                client.phoneNumber(),
                client.balance(),
                client.notificationPreference(),
                client.activeSubscriptions(),
                client.transactionHistory()
        );

        return clientRepositoryPort.save(clientToSave);
    }

    @Override
    public Optional<Client> findClientById(String id) {
        return clientRepositoryPort.findById(id);
    }

    @Override
    public Optional<Client> updateClient(UpdateClient updateClient, String id) {
        return clientRepositoryPort.update(updateClient, id);
    }

    @Override
    public Optional<Client> findClientByEmail(String email) {
        return clientRepositoryPort.findByEmail(email);
    }

    @Override
    public Client getCurrentClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("No authenticated user found");
        }
        String email = authentication.getName();
        return findClientByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found with email: " + email));
    }
}
