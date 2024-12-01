package com.project.books.management.components;

import com.project.books.management.entities.Client;
import com.project.books.management.entities.ClientDetails;
import com.project.books.management.exceptions.ClientNotFoundException;
import com.project.books.management.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByUserName(username).orElseThrow(()
                -> new ClientNotFoundException("Client of username "+ username + " not found"));
        return new ClientDetails(client);
    }
}
