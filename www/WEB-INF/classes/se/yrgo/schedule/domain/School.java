package se.yrgo.schedule.domain;

/**
 * Represents a school with information on the
 * name of the school and the address of the school.
 */
public class School {
    private String name;
    private String address;

    /**
     * Creates a new school
     * 
     * @param name    The name of the School
     * @param address The address of the School
     */
    public School(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Returns the name of the School
     * 
     * @return the schools name
     */
    public String name() {
        return name;
    }

    /**
     * Returns the address of the School
     * 
     * @return the schools address
     */
    public String address() {
        return address;
    }
}
