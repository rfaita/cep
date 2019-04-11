package com.cep.client;

import com.cep.shared.contract.CepContract;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "cep-service", path = "/api/public")
public interface CepClient extends CepContract {

}
