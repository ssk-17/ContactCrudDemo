package com.example.crudemo.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "contact")
public class Contact {
    @Id
    @NonNull
    private String id;
    @NonNull
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
