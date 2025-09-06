package com.simulation.cloth.core.geometry;

import com.simulation.cloth.core.utils.Matrix2D;
import com.simulation.cloth.core.utils.Vector2D;

public class Point2D {
    private double x;
    private double y;

    private boolean isExist = false;

    public Point2D() { // point position at origin
        this.x = 0;
        this.y = 0;
        this.isExist = true;
    }

    public Point2D(double x, double y) { // Manual input
        this.x = x;
        this.y = y;
        this.isExist = true;
    }

    public Point2D(Point2D point) { // duplicating the point
        this.x = point.x;
        this.y = point.y;
        this.isExist = true;
    }

    public Point2D(Vector2D vector) { // from vector (for rare cases)
        this.x = vector.getX();
        this.y = vector.getY();
        this.isExist = true;
    }

    // basic methods

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double value) {
        this.x = value;
    }

    public void setY(double value) {
        this.y = value;
    }

    public boolean getExistence() {
        return isExist;
    }

    public double distance(Point2D p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    public void translate(double dx, double dy) {
        this.x = this.x + dx;
        this.y = this.y + dy;
    }

    public void translateByVector(Vector2D vector) {
        this.x = this.x + vector.getX();
        this.y = this.y + vector.getY();
    }

    public Point2D midpoint(Point2D point) {
        return new Point2D((this.x + point.getX()) / 2, (this.y + point.getY()) / 2);
    }

    public boolean isEqual(Point2D point, double epsilon) {
        return Math.abs(this.x - point.getX()) < epsilon && Math.abs(this.y - point.getY()) < epsilon;
    }

    public void rotateAroundOrigin(double theta) {
        Matrix2D rotationMatrix = new Matrix2D(new double[][] {
                { Math.cos(theta), -Math.sin(theta), 0 },
                { Math.sin(theta), Math.cos(theta), 0 },
                { 0, 0, 1 }
        });

        Vector2D point = new Vector2D(this.x, this.y);

        double[][] result = rotationMatrix.multiplyToVector(point);

        this.x = result[0][0];
        this.y = result[1][0];
    }

    public void rotateAroundArbitrary(double cx, double cy, double theta) {
        this.translate(-cx, -cy);
        this.rotateAroundOrigin(theta);
        this.translate(cx, cy);
    }

    public void transform(Matrix2D matrix) {
        Vector2D point = new Vector2D(this.x, this.y);
        double[][] result = matrix.multiplyToVector(point);
        this.x = result[0][0];
        this.y = result[1][0];
    }

    public Vector2D toVector() {
        return new Vector2D(this.x, this.y);
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public double[] toHomogenousArray() {
        return new double[] { this.x, this.y, 1 };
    }

}
