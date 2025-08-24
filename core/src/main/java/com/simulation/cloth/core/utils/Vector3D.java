package com.simulation.cloth.core.utils;

public class Vector3D {
    private double x, y, z;

    Vector3D(double x, double y, double z) { // Normal Constructor
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vector3D(Vector3D other) { // Copies the vector properties of other object
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    Vector3D() { // object is at origin (0,0,0)
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    // Getter and Setter Methods
    // ---------------------------------------------------------------------------------------------------------

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    // Basic Operations
    // ---------------------------------------------------------------------------------------------------------

    public Vector3D add(Vector3D vector) {
        return new Vector3D(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    public Vector3D subtract(Vector3D vector) {
        return new Vector3D(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    public Vector3D scale(double scaler) {
        return new Vector3D(this.x * scaler, this.y * scaler, this.z * scaler);
    }

    public Vector3D negate() {
        return new Vector3D(-1 * this.x, -1 * this.y, -1 * this.z);
    }

    // Magnitude and Direction Methods
    // --------------------------------------------------------------------------------------------------------

    public double magnitude() {
        double sqX = Math.pow(this.x, 2);
        double sqY = Math.pow(this.y, 2);
        double sqZ = Math.pow(this.z, 2);
        return Math.sqrt(sqX + sqY + sqZ);
    }

    public Vector3D normalize() {
        double mag = this.magnitude();
        if (mag == 0) {
            return new Vector3D();
        }
        return new Vector3D(this.x / mag, this.y / mag, this.z / mag);
    }

    public double distance(Vector3D vector) {
        Vector3D difference = vector.subtract(this);
        return difference.magnitude();
    }

    public double angleBetween(Vector3D vector) {
        double theta = Math.acos(this.dot(vector) / (this.magnitude() * vector.magnitude()));
        return theta;
    }

    public double signedAngleBetween(Vector3D vector, Vector3D refrenceNormal) {
        double sign = Math.signum(this.cross(vector).dot(refrenceNormal)); // it will be 0 if the cross(vector) ->
                                                                           // (0,0,0). if its 0 then check for
                                                                           // collinearity
        double theta = Math.acos(this.dot(vector) / (this.magnitude() * vector.magnitude()));
        return sign * theta;
    }

    // Product Methods
    // ---------------------------------------------------------------------------------------------------------

    public double dot(Vector3D vector) {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

    public Vector3D cross(Vector3D vector) {
        double crossX = this.y * vector.z - this.z * vector.y;
        double crossY = this.z * vector.x - this.x * vector.z;
        double crossZ = this.x * vector.y - this.y * vector.x;

        return new Vector3D(crossX, crossY, crossZ);
    }

    // Utilities Methods
    // ---------------------------------------------------------------------------------------------------------

    public Vector3D clone() {
        return new Vector3D(this.x, this.y, this.z);
    }

    public boolean equals(Vector3D vector) {
        return this.x == vector.x && this.y == vector.y && this.z == vector.z;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
