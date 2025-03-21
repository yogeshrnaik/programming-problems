package com.programming.propertybasedtesting;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.StringArbitrary;

import static org.junit.Assert.*;

public class UserPropertyTest {
    // This is the provider that generates Users and determines validity
    @Provide
    Arbitrary<UserData> userData() {
        // Generate names
        Arbitrary<String> names = Arbitraries.oneOf(
                validNames(),
                emptyNames(),
                tooLongNames()
        );

        // Generate emails
        Arbitrary<String> emails = Arbitraries.oneOf(
                validEmails(),
                invalidEmails()
        );

        // Generate phone numbers
        Arbitrary<String> phones = Arbitraries.oneOf(
                validPhones(),
                invalidPhones()
        );

        // Generate addresses
        Arbitrary<String> addresses = Arbitraries.oneOf(
                validAddresses(),
                emptyAddresses()
        );

        return Combinators.combine(names, emails, phones, addresses)
                .as((name, email, phone, address) -> {
                    User user = new User(name, email, phone, address);
                    // Determine validity in the provider
                    boolean isValid = UserValidator.isUserValid(user);
                    return new UserData(user, isValid);
                });
    }

    private Arbitrary<String> validNames() {
        return Arbitraries.strings().ofMinLength(1).ofMaxLength(50);
    }

    private Arbitrary<String> emptyNames() {
        return Arbitraries.just("");
    }

    private Arbitrary<String> tooLongNames() {
        return Arbitraries.strings().ofMinLength(51).ofMaxLength(100);
    }

    private Arbitrary<String> validEmails() {
        StringArbitrary localParts = Arbitraries.strings()
                .withCharRange('a', 'z')
                .withChars('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '_')
                .ofMinLength(1).ofMaxLength(20);

        Arbitrary<String> domains = Arbitraries.of("gmail.com", "yahoo.com", "example.com");

        return Combinators.combine(localParts, domains)
                .as((local, domain) -> local + "@" + domain);
    }

    private Arbitrary<String> invalidEmails() {
        return Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(20);
    }

    private Arbitrary<String> validPhones() {
        // Generate phone numbers of valid length
        return Arbitraries.strings().numeric().ofMinLength(10).ofMaxLength(15);
    }

    private Arbitrary<String> invalidPhones() {
        // Generate phone numbers that are too short
        return Arbitraries.strings().numeric().ofMinLength(1).ofMaxLength(9);
    }

    private Arbitrary<String> validAddresses() {
        return Arbitraries.strings().ofMinLength(1).ofMaxLength(200);
    }

    private Arbitrary<String> emptyAddresses() {
        return Arbitraries.just("");
    }

    @Property
    void testUserProcessing(@ForAll("userData") UserData userData) {
        UserService service = new UserService();
        User user = userData.getUser();

        if (userData.isValid()) {
            // For valid users, processing should succeed
            ProcessedUser processed = service.processUser(user);

            assertEquals(user.getName(), processed.getFullName());
            assertEquals(user.getEmail(), processed.getContact().getEmail());
            assertEquals(user.getPhone(), processed.getContact().getPhone());
            assertEquals(user.getAddress(), processed.getShippingAddress());
        } else {
            // For invalid users, an exception should be thrown
            assertThrows(IllegalArgumentException.class, () -> {
                service.processUser(user);
            });
        }
    }

    @Property
    void testSpecificErrorMessages(@ForAll("userData") UserData userData) {
        if (userData.isValid()) {
            // Skip valid users
            return;
        }

        UserService service = new UserService();
        User user = userData.getUser();

        try {
            service.processUser(user);
            fail("Expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();

            // Check that error message matches the actual issue
            if (!UserValidator.isNameValid(user.getName())) {
                assertTrue(errorMessage.contains("Name"));
            } else if (!UserValidator.isEmailValid(user.getEmail())) {
                assertTrue(errorMessage.contains("email"));
            } else if (!UserValidator.isPhoneValid(user.getPhone())) {
                assertTrue(errorMessage.contains("Phone"));
            } else if (!UserValidator.isAddressValid(user.getAddress())) {
                assertTrue(errorMessage.contains("Address"));
            }
        }
    }
}