package com.example.crudemo.service;

import com.example.crudemo.model.Contact;
import com.example.crudemo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact updateContact(String id, Contact request) {
        Contact contact = contactRepository.findById(id).orElseThrow();

        contact.setEmail(request.getEmail());
        contact.setFirstName(request.getFirstName());
        contact.setPhoneNumber(request.getPhoneNumber());
        contact.setLastName(request.getLastName());

        return contactRepository.save(contact);
    }

    public List<Contact> getContacts(String searchString) {
        List<Contact> contactList = contactRepository.findAll();
        return searchString != null ? contactList.stream().filter(contact ->
                contact.toString().contains(searchString))
                .collect(Collectors.toList()) : contactList;
    }

}
