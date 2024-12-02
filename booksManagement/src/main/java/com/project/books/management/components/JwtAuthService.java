package com.project.books.management.components;

import com.project.books.management.dto.AuthRequestDTO;
import com.project.books.management.dto.JwtResponseDTO;
import com.project.books.management.exceptions.ClientNotFoundException;
import com.project.books.management.mapping.ClientMapping;
import com.project.books.management.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDTO AuthenticateAndGetToken(AuthRequestDTO authRequestDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                String userName = authRequestDTO.getUsername();
                Long clientId = clientRepository.findClientByUserName(userName).get().getId();
                return JwtResponseDTO.builder()
                        .accessToken(jwtService.GenerateToken(userName, clientId)).build();
            } else {
                throw new ClientNotFoundException("invalid user request..!!");
            }
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Nom d'utilisateur ou mot de passe incorrect ", e);
        }
    }
}
