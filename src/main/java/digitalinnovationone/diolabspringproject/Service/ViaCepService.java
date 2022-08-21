package digitalinnovationone.diolabspringproject.Service;

import digitalinnovationone.diolabspringproject.Entity.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {

    @GetMapping("/{zipcode}/json/")
    Address consultarCep(@PathVariable("zipcode") String zipcode);
}
