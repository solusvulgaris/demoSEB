package com.ak.springbootdemo.sub.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

/**
 * Utility class for reading Subsidiaries from JSON file
 * represents DTO - Data Transfer Object - to represent Subsidiary entity based on min essential repesentation
 */
public class JSONSubsidiary {
    static final String JSON_FILE = "subsidiary.json";

    private String innerCode;
    private String address;
    private String name;
    private String phoneNumber;

    protected JSONSubsidiary() {

    }

    /**
     * Reading Subsidiaries from JSON file
     *
     * @return List of entities read from file
     */
    public static List<JSONSubsidiary> read() throws IOException {
        try (final InputStream resourceAsStream = JSONSubsidiary.class.getClassLoader().getResourceAsStream(JSON_FILE)) {
            return new ObjectMapper().setVisibility(FIELD, ANY).readValue(resourceAsStream, new TypeReference<>() {
            });
        }
    }

    public String getInnerCode() {
        return innerCode;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
