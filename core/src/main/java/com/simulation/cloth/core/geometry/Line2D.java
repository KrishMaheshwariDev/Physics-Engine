package com.simulation.cloth.core.geometry;

import com.simulation.cloth.core.utils.Vector2D;

public class Line2D {
    // private double x1, x2, y1, y2;

    Point2D point1 = new Point2D();
    Point2D point2 = new Point2D();

    Vector2D direction;

    double A, B, C; // Coefficients for the standard form of line

    public Line2D(Point2D p1, Point2D p2) {
        point1.setX(p1.getX());
        point1.setY(p1.getY());

        point2.setX(p2.getX());
        point2.setY(p2.getY());

        Vector2D v1 = new Vector2D(point1);
        Vector2D v2 = new Vector2D(point2);

        direction = v1.subtract(v2);
    }

    public Line2D(Point2D p, Vector2D d) {
        this.point1.setX(p.getX());
        this.point1.setY(p.getY());

        direction = d;

        this.point2.setX(d.getX() + point1.getX());
        this.point2.setY(d.getY() + point1.getY());
    }

    public Line2D(double a, double b, double c) {
        this.A = a;
        this.B = b;
        this.C = c;

        if (this.B != 0) {
            this.point1.setX(0);
            this.point1.setY(-this.C / this.B);

            this.point2.setX(1);
            this.point2.setY(-(this.A * 1 + this.C) / this.B);
        } else {
            this.point1.setX(-this.C / this.A);
            this.point1.setY(0);

            this.point2.setX(-this.C / this.A);
            this.point2.setY(1);
        }

        this.direction = new Vector2D(point2).subtract(new Vector2D(point1));

    }

    // Basic properties method

    public double getSlope() {
        return (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
    }

    public boolean isVertical() {
        if (this.direction.getX() == 0) {
            return true;
        }
        return false;
    }

    public boolean isHorizontal() {
        if (this.direction.getY() == 0) {
            return true;
        }
        return false;
    }

    public double getLength() {
        return new Vector2D(point1).distance(new Vector2D(point2));
    }

    public Vector2D getDirection() {
        return this.direction.normalize();
    }

    // Point Relation Methods

    public boolean contain(Point2D point, double epsilon) {
        double value = this.A * point.getX() + this.B * point.getY() + this.C;
        if (value <= epsilon) {
            return true;
        }
        return false;
    }

    public boolean segmentContain(Point2D point, double epsilon) {
        if (!contain(point, epsilon)) {
            return false;
        } else {
            if (Math.min(this.point1.getX(), this.point2.getX()) <= point.getX()
                    && Math.max(this.point1.getX(), this.point2.getX()) >= point.getX()
                    && Math.min(this.point1.getY(), this.point2.getY()) <= point.getY()
                    && Math.max(this.point1.getY(), this.point2.getY()) >= point.getY()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public double distanceToPoint(Point2D point) {
        double distance;

        double numerator = Math.abs(this.A * point.getX() + this.B * point.getY() + this.C);
        double denominator = Math.sqrt(this.A * this.A + this.B * this.B);

        distance = numerator / denominator;
        return distance;
    }

    public Point2D projectPoint(Point2D point) {
        Vector2D point1ToP = new Vector2D(point).subtract(new Vector2D(point1));
        double scaler = point1ToP.dot(this.direction) / this.direction.dot(this.direction);
        return new Point2D(point1.getX() + scaler * direction.getX(), point1.getY() + scaler * direction.getY());
    }

    public Point2D reflectPoint(Point2D point) {
        Point2D projectedPoint = this.projectPoint(point);
        return new Point2D(2 * projectedPoint.getX() - point.getX(), 2 * projectedPoint.getY() - point.getY());
    }
}
