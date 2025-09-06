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

        this.A = p2.getY() - p1.getY();
        this.B = p1.getX() - p2.getX();
        this.C = this.A * p1.getX() + this.B * p1.getY();
    }

    public Line2D(Point2D p, Vector2D d) {
        this.point1.setX(p.getX());
        this.point1.setY(p.getY());

        direction = d;

        this.point2.setX(d.getX() + point1.getX());
        this.point2.setY(d.getY() + point1.getY());

        this.A = point2.getY() - point1.getY();
        this.B = point1.getX() - point2.getX();
        this.C = this.A * point1.getX() + this.B * point1.getY();
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

    // Line to Line Relationships

    public boolean isParallel(Line2D line2) {
        double crossProduct = this.direction.cross(line2.direction);
        if (crossProduct < 0.00005) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCollinear(Line2D line2) {
        if (this.isParallel(line2)) {
            Vector2D subtractedVector = new Vector2D(this.point1).subtract(new Vector2D(line2.point1));
            double resultantCrossProduct = subtractedVector.cross(this.direction);

            if (Math.abs(resultantCrossProduct) < 0.00005) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Point2D lineIntersection(Line2D line2) {
        double determinant = this.A * line2.B - this.B * line2.A;
        if (Math.abs(determinant) < 0.00005) {
            System.out.println("Lines are parallel, hence no intersection point found");
            return null;
        } else {
            double x = (line2.B * this.C - this.B * line2.C) / determinant;
            double y = (this.A * line2.C - line2.A * this.C) / determinant;
            return new Point2D(x, y);
        }
    }

    public boolean isSegmentOverlap(Line2D line2) {
        if (this.isCollinear(line2)) {
            boolean checkX = Math.max(Math.min(this.point1.getX(), this.point2.getX()),
                    Math.min(line2.point1.getX(), line2.point2.getX())) <= Math.min(
                            Math.max(this.point1.getX(), this.point2.getX()),
                            Math.max(line2.point1.getX(), line2.point2.getX()));

            boolean checkY = Math.max(Math.min(this.point1.getY(), this.point2.getY()),
                    Math.min(line2.point1.getY(), line2.point2.getY())) <= Math.min(
                            Math.max(this.point1.getY(), this.point2.getY()),
                            Math.max(line2.point1.getY(), line2.point2.getY()));

            if (checkX && checkY) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public double distanceToLine(Line2D line2) {
        if (this.lineIntersection(line2).getExistence()) {
            return 0.0;
        } else {
            if (!isCollinear(line2)) {
                return Math.abs(line2.A * this.point1.getX() + line2.B * this.point1.getY() - line2.C)
                        / (Math.sqrt(line2.A * line2.A + line2.B * line2.B));
            } else {
                return 0.0;
            }
        }
    }

    public double relativeOrientation(Point2D A, Point2D B, Point2D C) {
        return (B.getY() - A.getY()) * (C.getX() - B.getX()) - (B.getX() - A.getX()) * (C.getY() - B.getY());
    }

    // Transformation Methods

    public void translate(double dx, double dy) {

        this.point1.setX(this.point1.getX() + dx);
        this.point2.setX(this.point2.getX() + dx);

        this.point1.setX(this.point1.getY() + dy);
        this.point2.setY(this.point2.getY() + dy);

        this.C = -(this.A * point1.getX() + this.B * point1.getY());
    }

    public void rotateAroundOrigin(double angle) {
        this.point1.setX(point1.getX() * Math.cos(angle) - point1.getY() * Math.sin(angle));
        this.point1.setY(point1.getX() * Math.cos(angle) + point1.getY() * Math.sin(angle));

        this.point2.setX(point2.getX() * Math.cos(angle) - point2.getY() * Math.sin(angle));
        this.point2.setY(point2.getX() * Math.cos(angle) + point2.getY() * Math.sin(angle));

        Vector2D vector = new Vector2D(point2).subtract(new Vector2D(point1));

        direction.setX(vector.getX());
        direction.setY(vector.getY());

        this.A = point2.getY() - point1.getY();
        this.B = point1.getX() - point2.getX();
        this.C = -(this.A * point1.getX() + point2.getY() * this.B);
    }

}
