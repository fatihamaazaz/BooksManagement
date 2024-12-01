package com.project.books.management.mapping;

import com.project.books.management.entities.Client;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Mapper
@RequiredArgsConstructor
public class ClientMapping {

    private final ModelMapper modelMapper;

    public <T> Client mapToClient(T client){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(client, Client.class);
    }

    public <T> T mapFromClient(Client client, Class<T> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(client, targetClass);
    }
}
