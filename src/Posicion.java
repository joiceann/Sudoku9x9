import java.util.ArrayList;

public class Posicion {
    int x;
    int y;
    ArrayList<Integer> opciones;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Integer> getOpciones() {
        return opciones;
    }

    public void setOpciones(ArrayList<Integer> opciones) {
        this.opciones = opciones;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
