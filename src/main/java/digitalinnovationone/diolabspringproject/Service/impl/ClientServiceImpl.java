package digitalinnovationone.diolabspringproject.Service.impl;

import digitalinnovationone.diolabspringproject.Entity.Address;
import digitalinnovationone.diolabspringproject.Entity.AddressRepository;
import digitalinnovationone.diolabspringproject.Entity.Client;
import digitalinnovationone.diolabspringproject.Entity.ClientRepository;
import digitalinnovationone.diolabspringproject.Service.ClientService;
import digitalinnovationone.diolabspringproject.Service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


    @Service
    public class ClientServiceImpl implements ClientService {

        @Autowired
        private ClientRepository clientRepository;
        @Autowired
        private AddressRepository addressRepository;
        @Autowired
        private ViaCepService viaCepService;

        @Override
        public Iterable<Client> searchEveryone() {
            return clientRepository.findAll();
        }

        @Override
        public Client searchForId(Long id) {
            Optional<Client> client = clientRepository.findById(id);
            return client.get();
        }

        @Override
        public void insert(Client client) {
            saveClientWithCep(client);
        }

        @Override
        public void update(Long id, Client client) {
            Optional<Client> clientBd = clientRepository.findById(id);
            if (clientBd.isPresent()) {
                saveClientWithCep(client);
            }
        }

        @Override
        public void delete(Long id) {
           clientRepository.deleteById(id);
        }

        private void saveClientWithCep(Client client) {
            String cep = client.getAddress().getCep();
            Address address = addressRepository.findById(cep).orElseGet(() -> {
                Address newAddress = viaCepService.consultarCep(cep);
                addressRepository.save(newAddress);
                return newAddress;
            });
            client.setAddress(address);
            clientRepository.save(client);
        }
}
