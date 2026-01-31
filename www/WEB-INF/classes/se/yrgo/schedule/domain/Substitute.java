package se.yrgo.schedule.domain;

/**
 * Represents a substitute/teacher with information on the name of the teacher.
 */
public class Substitute {
    private String name;

    /**
     * Creates a new Substitute
     * 
     * @param name The name of the Substitute
     */
    public Substitute(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the teacher/substitute
     * 
     * @return the substitutes/teachers name
     */
    public String name() {
        return name;
    }
}
