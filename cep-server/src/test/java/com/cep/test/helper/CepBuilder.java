package com.cep.test.helper;

import com.cep.server.model.Cep;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

public class CepBuilder {

    public static Cep createBasicCep(String cep) {
        Cep ret = new Cep();
        ret.setCep(cep);
        return ret;

    }

    public static Cep createCompleteCep(String cep) {
        Cep ret = new Cep();
        ret.setCep(cep);
        ret.setBairro("bairro ".concat(cep));
        ret.setLogradouro("logradouro ".concat(cep));
        ret.setCidade("cidade ".concat(cep));
        ret.setEstado("estado ".concat(cep));
        ret.setUf("uf ".concat(cep));
        ret.setId("123 ".concat(cep));
        return ret;
    }


    public static Answer<List<Cep>> createBasicCepAnswer() {
        return (InvocationOnMock invocation) -> {
            return Arrays.asList(createBasicCep((String) invocation.getArguments()[0]));
        };
    }

    public static Answer<Cep> createDefaultCepAnswer() {
        return (InvocationOnMock invocation) -> {
            return (Cep) invocation.getArguments()[0];
        };
    }

}
