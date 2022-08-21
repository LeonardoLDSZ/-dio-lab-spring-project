package digitalinnovationone.diolabspringproject.Service;

import digitalinnovationone.diolabspringproject.Entity.Client;

public interface ClientService {

    Iterable<Client> searchEveryone();

    Client searchForId(Long id);

    void insert(Client client);

    void update(Long id, Client client);

    void delete(Long id);
}
