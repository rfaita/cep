package com.cep.test.helper;

import com.cep.shared.dto.CepDTO;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

public class CepDTOBuilder {

    public static CepDTO createBasicCepDTO(String cep) {
        return CepDTO.builder().cep(cep).build();
    }

    public static Answer<List<CepDTO>> createBasicCepDTOAnswer() {
        return (InvocationOnMock invocation) -> {
            return Arrays.asList(createBasicCepDTO((String) invocation.getArguments()[0]));
        };
    }

    public static Answer<CepDTO> createDefaultCepDTOAnswer() {
        return (InvocationOnMock invocation) -> {
            return (CepDTO) invocation.getArguments()[0];
        };
    }

}
