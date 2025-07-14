package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.in.*;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.UpdateClient;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.ClientApiMapper;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests.CancelSubscriptionRequest;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests.ClientRequest;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests.SubscribeToFundRequest;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests.UpdateClientRequest;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.responses.ClientResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final CreateClientUseCase createClientUseCase;
    private final GetClientByIdUseCase getClientByIdUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final SubscribeToFundUseCase subscribeToFundUseCase;
    private final CancelSubscriptionUseCase cancelSubscriptionUseCase;
    private final GetClientByEmailUseCase getClientByEmailUseCase;
    private final GetCurrentClientUseCase getCurrentClientUseCase;
    private final ClientApiMapper clientApiMapper;

    public ClientController(
            CreateClientUseCase createClientUseCase,
            GetClientByIdUseCase getClientByIdUseCase,
            UpdateClientUseCase updateClientUseCase,
            SubscribeToFundUseCase subscribeToFundUseCase,
            CancelSubscriptionUseCase cancelSubscriptionUseCase,
            GetClientByEmailUseCase getClientByEmailUseCase,
            GetCurrentClientUseCase getCurrentClientUseCase,
            ClientApiMapper clientApiMapper
    ) {
        this.createClientUseCase = createClientUseCase;
        this.getClientByIdUseCase = getClientByIdUseCase;
        this.updateClientUseCase = updateClientUseCase;
        this.subscribeToFundUseCase = subscribeToFundUseCase;
        this.cancelSubscriptionUseCase = cancelSubscriptionUseCase;
        this.getClientByEmailUseCase = getClientByEmailUseCase;
        this.getCurrentClientUseCase = getCurrentClientUseCase;
        this.clientApiMapper = clientApiMapper;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest clientRequest) {
        Client clientToCreate = clientApiMapper.toDomain(clientRequest);

        Client clientCreated = createClientUseCase.createClient(clientToCreate);

        ClientResponse responseDto = clientApiMapper.toResponseDto(clientCreated);

        URI location = URI.create("/clients/" + responseDto.id());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable String id) {
        Client client = getClientByIdUseCase.findClientById(id).orElseThrow(
                () -> new IllegalArgumentException("Client with id " + id + " not found")
        );
        ClientResponse responseDto = clientApiMapper.toResponseDto(client);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/email")
    public ResponseEntity<ClientResponse> getClientByEmail(@RequestParam String email) {
        Client client = getClientByEmailUseCase.findClientByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Client with email " + email + " not found")
        );
        ClientResponse responseDto = clientApiMapper.toResponseDto(client);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/me")
    public ResponseEntity<ClientResponse> getCurrentClient() {
        Client client = getCurrentClientUseCase.getCurrentClient();
        ClientResponse responseDto = clientApiMapper.toResponseDto(client);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> partialUpdateClient(
            @PathVariable String id,
            @Valid @RequestBody UpdateClientRequest updateRequest) {

        UpdateClient updateClient = clientApiMapper.toDomainForUpdate(updateRequest);

        Optional<Client> updatedClientOptional = updateClientUseCase.updateClient(updateClient, id);

        return updatedClientOptional
                .map(client -> ResponseEntity.ok(clientApiMapper.toResponseDto(client)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/subscribe")
    public ResponseEntity<ClientResponse> subscribeToFund(
            @Valid @RequestBody SubscribeToFundRequest subscribeToFundRequest
    ) {
        Client updatedClient = subscribeToFundUseCase.subscribeToFund(
                subscribeToFundRequest.clientId(),
                subscribeToFundRequest.fundId(),
                subscribeToFundRequest.amount()
        );

        return ResponseEntity.ok(clientApiMapper.toResponseDto(updatedClient));
    }

    @PostMapping("/cancelSubscription")
    public ResponseEntity<ClientResponse> cancelSubscription(
            @Valid @RequestBody CancelSubscriptionRequest cancelSubscriptionRequest
    ) {
        Client updatedClient = cancelSubscriptionUseCase.cancelSubscription(
                cancelSubscriptionRequest.clientId(),
                cancelSubscriptionRequest.fundId()
        );

        return ResponseEntity.ok(clientApiMapper.toResponseDto(updatedClient));
    }
}
