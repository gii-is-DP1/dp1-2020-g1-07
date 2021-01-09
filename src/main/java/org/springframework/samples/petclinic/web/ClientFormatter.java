package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.service.ClientGainService;
import org.springframework.stereotype.Component;

@Component
public class ClientFormatter implements Formatter<Client>{
	private final ClientGainService cgainService;
	   
    @Autowired
    public ClientFormatter(ClientGainService cgainService) {
        this.cgainService = cgainService;
    }
    @Override
    public String print(Client client, Locale locale) {
        return client.getDni();
    }
    @Override
    public Client parse(String text, Locale locale) throws ParseException {
        Collection<Client> findClients = this.cgainService.findClients();
        for (Client client : findClients) {
            if (client.getDni().equals(text)) {
                return client;
            }
        }
        throw new ParseException("client not found: " + text, 0);
    }
}