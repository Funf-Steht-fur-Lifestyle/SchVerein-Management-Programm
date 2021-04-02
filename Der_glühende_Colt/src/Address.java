package colt;

/**
 * Address - a data class that holds values that are
 * grouped together to make an address. For example,
 * Skalitzer Strasse 140. 10999 Berlin, Germany.
 *
 * @version 1.0 from 02.04.2021
 * @author Naglis Vidziunas
 */
public class Address {

    protected String street;
    protected int houseNumber;
    protected String houseNumberAdditional;
    protected int postcode;
    protected String location;
    protected String country;
    protected String state;

    /**
     * Builder - a class that corresponds to the Address
     * class. It helps to create an Address entry object
     * given required values, such as street, house number,
     * postcode, and so on.
     *
     * @version 1.0 from 02.04.2021
     * @author Naglis Vidziunas
     */
    public static class Builder {
        Address address = new Address();

        public Builder() {}

        public Builder street(String street) {
            address.street = street;
            return this;
        }

        public Builder houseNumber(int houseNumber) {
            address.houseNumber = houseNumber;
            return this;
        }

        public Builder houseNumberAdditional(String houseNumberAdditional) {
            address.houseNumberAdditional = houseNumberAdditional;
            return this;
        }

        public Builder postcode(int postcode) {
            address.postcode = postcode;
            return this;
        }

        public Builder location(String location) {
            address.location = location;
            return this;
        }

        public Builder country(String country) {
            address.country = country;
            return this;
        }

        public Builder state(String state) {
            address.state = state;
            return this;
        }

        public Address build() {
            return address;
        }
    }
}

