package data;

public class Coordinates {
    private float x;
    private float y;

    public boolean addCoordinates(float nx, float ny){
        if ((nx > -199) && (ny > -199)) {
            this.x = nx;
            this.y = ny;
            return true;
        } else {
            return false;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
