package com.simulation.cloth.core.utils;

import com.simulation.cloth.core.geometry.Point2D;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) { // Normal Contructor
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) { // Copy Contructor (used when a new object copies the position of another
        // object)
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2D() { // position of the object is at origin
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(Point2D point) {
        this.x = point.getX();
        this.y = point.getY();
    }
    // Getter and Setter Methods
    // --------------------------------------------------------------------------------------------------

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Basic Operations
    // -------------------------------------------------------------------------------------------------------

    public Vector2D add(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }

    public Vector2D subtract(Vector2D vector) {
        return new Vector2D(this.x - vector.x, this.y - vector.y);
    }

    public Vector2D scale(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public Vector2D negate() {
        return new Vector2D(-1 * this.x, -1 * this.y);
    }

    // Magnitude and Direction Methods
    // -------------------------------------------------------------------------------------------------------

    public double magnitude() {
        double sqX = this.x * this.x;
        double sqY = this.y * this.y;
        double sum = sqX + sqY;
        return Math.sqrt(sum);
    }

    public Vector2D normalize() {
        double mag = this.magnitude();
        if (mag == 0) {
            return new Vector2D();
        } else {
            return new Vector2D(this.x / mag, this.y / mag);
        }
    }

    public double distance(Vector2D vector) {
        Vector2D diff = this.subtract(vector);
        return diff.magnitude();
    }

    public double angleBetween(Vector2D vector) {
        double theta = (this.dot(vector)) / (this.magnitude() * vector.magnitude());
        return Math.acos(theta);
    }

    public double signedAngleBetween(Vector2D vector) {
        return Math.atan2(this.cross(vector), this.dot(vector));
    }

    // Vector Products
    // -------------------------------------------------------------------------------------------------------

    public double dot(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    public double cross(Vector2D vector) {
        return this.x * vector.y - this.y * vector.x;
    }

    // Utility Methods
    // -------------------------------------------------------------------------------------------------------

    public Vector2D clone() {
        return new Vector2D(this.x, this.y);
    }

    public boolean equals(Vector2D vector) {
        return this.x == vector.x && this.y == vector.y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}