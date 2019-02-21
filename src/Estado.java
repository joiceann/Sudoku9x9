import java.util.ArrayList;

public class Estado implements Comparable<Estado>{
    int columnas=9;
    int filas=9;
    int heuristica=1000;
    int total;
    int costo=0;

    ArrayList<ArrayList<Integer>> tablero = new ArrayList<ArrayList<Integer>>();

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getHeuristica() {
        return heuristica;
    }

    public void setHeuristica(int heuristica) {
        this.heuristica = heuristica;
    }

    public ArrayList<ArrayList<Integer>> getTablero() {
        return tablero;
    }

    public void setTablero(ArrayList<ArrayList<Integer>> tablero) {
        this.tablero = tablero;
    }

    @Override
    public int compareTo(Estado o) {

        for (int i=0; i<this.tablero.size(); i++){
            for (int j=0; j<this.tablero.get(i).size();j++){
                /*System.out.println(this.tablero.get(i).get(j));
                System.out.println(o.getTablero().get(i).get(j));
                */if (this.tablero.get(i).get(j) != o.getTablero().get(i).get(j)){
                    return 0;
                }
            }
        }
        return 1;
    }
}
