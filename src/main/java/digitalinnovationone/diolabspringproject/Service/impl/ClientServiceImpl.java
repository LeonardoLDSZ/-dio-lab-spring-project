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

        // Strategy: Implementar os métodos definidos na interface.
        // Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

        @Override
        public Iterable<Client> searchEveryone() {
            // Buscar todos os Clientes.
            return clientRepository.findAll();
        }

        @Override
        public Client searchForId(Long id) {
            // Buscar Cliente por ID.
            Optional<Client> client = clientRepository.findById(id);
            return client.get();
        }

        @Override
        public void insert(Client client) {
            saveClientWithCep(client);
        }

        @Override
        public void update(Long id, Client client) {
            // Buscar Cliente por ID, caso exista:
            Optional<Client> clientBd = clientRepository.findById(id);
            if (clientBd.isPresent()) {
                saveClientWithCep(client);
            }
        }

        @Override
        public void delete(Long id) {
            // Deletar Cliente por ID.
            clientRepository.deleteById(id);
        }

        private void saveClientWithCep(Client client) {
            // Verificar se o Endereco do Cliente já existe (pelo CEP).
            String cep = client.getAddress().getCep();
            Address address = addressRepository.findById(cep).orElseGet(() -> {
                // Caso não exista, integrar com o ViaCEP e persistir o retorno.
                Address newAddress = viaCepService.consultarCep(cep);
                addressRepository.save(newAddress);
                return newAddress;
            });
            client.setAddress(address);
            // Inserir Cliente, vinculando o Endereco (novo ou existente).
            clientRepository.save(client);
        }
}
