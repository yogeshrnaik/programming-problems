package com.programming.propertybasedtesting;


import java.util.regex.Pattern;

public class PropertyBasedTestingExample {
}


// Simple User class without validation logic
class User {
    private String name;
    private String email;
    private String phone;
    private String address;

    public User(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}

// UserValidator contains the validation logic
class UserValidator {
    public static boolean isNameValid(String name) {
        return name != null && !name.isEmpty() && name.length() <= 50;
    }

    public static boolean isEmailValid(String email) {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        return email != null && emailPattern.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone) {
        return phone != null && phone.length() >= 10 && phone.length() <= 15;
    }

    public static boolean isAddressValid(String address) {
        return address != null && !address.isEmpty();
    }

    public static boolean isUserValid(User user) {
        return isNameValid(user.getName()) &&
                isEmailValid(user.getEmail()) &&
                isPhoneValid(user.getPhone()) &&
                isAddressValid(user.getAddress());
    }
}

class ProcessedUser {
    private long userId;
    private String fullName;
    private ContactInfo contact;
    private String shippingAddress;

    public ProcessedUser(long userId, String fullName, ContactInfo contact, String shippingAddress) {
        this.userId = userId;
        this.fullName = fullName;
        this.contact = contact;
        this.shippingAddress = shippingAddress;
    }

    // Getters
    public long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public ContactInfo getContact() {
        return contact;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
}

class ContactInfo {
    private String email;
    private String phone;

    public ContactInfo(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}

class UserService {
    public ProcessedUser processUser(User user) {
        // Validate user
        if (!UserValidator.isNameValid(user.getName())) {
            throw new IllegalArgumentException("Name must be between 1 and 50 characters");
        }

        if (!UserValidator.isEmailValid(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!UserValidator.isPhoneValid(user.getPhone())) {
            throw new IllegalArgumentException("Phone number must be between 10 and 15 characters");
        }

        if (!UserValidator.isAddressValid(user.getAddress())) {
            throw new IllegalArgumentException("Address cannot be empty");
        }

        // Process user data
        long userId = user.getEmail().hashCode();
        ContactInfo contactInfo = new ContactInfo(user.getEmail(), user.getPhone());

        return new ProcessedUser(userId, user.getName(), contactInfo, user.getAddress());
    }
}

// Data class containing both User and validity information
class UserData {
    private User user;
    private boolean isValid;

    public UserData(User user, boolean isValid) {
        this.user = user;
        this.isValid = isValid;
    }

    public User getUser() {
        return user;
    }

    public boolean isValid() {
        return isValid;
    }
}
