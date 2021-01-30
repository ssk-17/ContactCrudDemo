package com.example.crudemo.resource;

import com.example.crudemo.model.Contact;
import com.example.crudemo.repository.ContactRepository;
import com.example.crudemo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ContactResource {

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable String id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() ->
                new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                        "Contact not found with given Id"));
        LOGGER.log(Level.INFO, "fetched contact by id, contact=" + contact);
        return ResponseEntity.ok(contact);
    }

    @PostMapping("/contacts")
    public ResponseEntity<Contact> saveContact(@RequestBody @Validated Contact contact) {
        LOGGER.log(Level.INFO, "request to save contact=" + contact);
        return ResponseEntity.ok(contactRepository.save(contact));
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity deleteContact(@PathVariable String id) {
        LOGGER.log(Level.INFO, "request to delete contact with id=" + id);
        contactRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id,
                                                 @RequestBody Contact updateRequest) {
        if (!id.equals(updateRequest.getId())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Id is different in path param and request body");
        }
        LOGGER.log(Level.INFO, "request to update contact with id=" + id +
                " updateRequest=" + updateRequest);
        Contact updatedContact = contactService.updateContact(id, updateRequest);
        return ResponseEntity.ok(updatedContact);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getContacts(
            @RequestParam(required = false) String searchString) {
        LOGGER.log(Level.INFO, "request to fetch all contacts with filterString=" + searchString);
        return ResponseEntity.ok(contactService.getContacts(searchString));
    }
}
