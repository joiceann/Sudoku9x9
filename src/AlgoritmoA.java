import java.lang.reflect.Array;
import java.util.ArrayList;

public class AlgoritmoA {
    Estado estadoInicial;
    ArrayList<Estado> openSet = new ArrayList<>();
    ArrayList<Estado> closeSet = new ArrayList<>();
    Integer posibles_numeros[]= new Integer[9];

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public  AlgoritmoA(){
        //Posibles valores que puede tomar una casilla
        for (int i=0; i< posibles_numeros.length; i++ ){
            posibles_numeros[i]=i+1;
        }
    }

    public boolean crear_EstadoInicial(String cadena){
        //verificar la cantidad de elementos del tablero
        ArrayList<ArrayList<Integer>> finnal = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        if (cadena.length()==81){
            int pos=0;
            estadoInicial = new Estado();

            for (int x=0; x<9; x++){
                temp = new ArrayList<>();
                for (int y=0;y<9;y++){
                    if (cadena.charAt(pos)=='.'){
                        temp.add(0);
                    }
                    else{
                        temp.add(Character.getNumericValue(cadena.charAt(pos)));
                    }
                    pos++;
                }
                finnal.add(temp);
            }
            estadoInicial.setTablero(finnal);
            estadoInicial.setHeuristica(0);

            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList buscar_vacios(Estado estado){
        ArrayList<Posicion> vacios = new ArrayList<>();
        for (int i=0; i< estado.tablero.size(); i++){
            for (int j=0; j<estado.tablero.get(i).size();j++){
                if (estado.tablero.get(i).get(j) == 0) {
                    Posicion pos = new Posicion(j, i);
                    vacios.add(pos);
                }
            }
        }
        return vacios;
    }

    public int calcular_heuristica(Estado estado, Integer num){
        int cont =0;
        for (int i=0; i<estado.tablero.size(); i++){
            for (int j=0; j<estado.tablero.get(i).size(); j++){
                if (estado.tablero.get(i).get(j)==num){
                    cont++;
                }
            }
        }
        return cont;
    }


    public void imprimir_estado( Estado estado){
        for (int i=0;i<estado.tablero.size();i++){
            for (int j=0; j<estado.tablero.get(i).size();j++){
                System.out.print("|"+estado.tablero.get(i).get(j));
            }
            System.out.print("|\n---------\n");
        }
    }
    public Estado obtener_menor(ArrayList<Estado> estados){
        Estado estado_menor= new Estado();
        int costo=1000;
        for(int i=0; i< estados.size();i++){
            if (costo> (estados.get(i).getCosto()+ estados.get(i).getHeuristica())){
                estado_menor= estados.get(i);
                costo= (estados.get(i).getCosto()+ estados.get(i).getHeuristica());
            }
        }
        return estado_menor;
    }

    public ArrayList<Integer> buscar_opciones(Estado estado, int x, int y){
        ArrayList<Integer> finales = new ArrayList<>();
        ArrayList<Integer> temp_y = new ArrayList<>();
        ArrayList<Integer> temp_x = new ArrayList<>();
        //verificar columnas y filas y guarda en arreglos los numeros que existen en ella
        for (int i = 0; i < posibles_numeros.length; i++) {
            for (int j = 0; j < estado.tablero.size(); j++) {
                if (!temp_y.contains(estado.tablero.get(y).get(j))) {
                    temp_y.add(estado.tablero.get(y).get(j));
                }
                if (!temp_x.contains(estado.tablero.get(j).get(x))) {
                    temp_x.add(estado.tablero.get(j).get(x));
                }
            }
        }

        //verifica si existen los numeros en columnas y filas
        for (int j = 0; j < posibles_numeros.length; j++) {
            if (!temp_x.contains(posibles_numeros[j]) && !temp_y.contains(posibles_numeros[j])) {
                finales.add(posibles_numeros[j]);
            }
        }
        return finales;
    }


    public boolean goal(Estado estado){
        for (int i=0; i< estado.tablero.size();i++){
            if (estado.tablero.get(i).contains(0)){
                return  false;
            }
        }
        return true;
    }

    public ArrayList<ArrayList<Integer>> copiar_tablero(ArrayList<ArrayList<Integer>> tablero){
        ArrayList<ArrayList<Integer>> tablero_nuevo = new ArrayList<>();
        for (int i=0; i< tablero.size(); i++){
            ArrayList<Integer> individual= new ArrayList<>();
            for (int j=0; j<tablero.get(i).size(); j++){
                individual.add(tablero.get(i).get(j));
            }
            tablero_nuevo.add(individual);
        }
        return tablero_nuevo;
    }

    public ArrayList<Estado> obtener_hijos (ArrayList<Posicion> vacios, Estado estado_padre){
        int costo=10000;
        int index=0;
        int opc=1000;

        //buscar la casilla que tenga menos opciones para ser llenadas
        for(int i=0; i< vacios.size();i++){
            if (vacios.get(i).getOpciones().size()<costo){
                costo= vacios.get(i).getOpciones().size();
                index=i;
            }
        }

        Posicion pos = vacios.get(index);

        //obtener los estados posibles en esa casilla
        ArrayList<Estado> hijos= new ArrayList<>();
        for (int i=0;i< pos.getOpciones().size();i++){
            ArrayList<ArrayList<Integer>> tablero = copiar_tablero( estado_padre.getTablero());
            tablero.get(pos.getY()).set(pos.getX(), pos.getOpciones().get(i));
            Estado temp= new Estado();
            temp.setTablero(tablero);
            temp.setCosto(estado_padre.getCosto()+1);
            hijos.add(temp);
        }

        //calcular la heuristica de los hijos
        for (int j=0; j<hijos.size();j++){
            int heuristica = calcular_heuristica(hijos.get(j),pos.getOpciones().get(j));
            hijos.get(j).setHeuristica(heuristica);

        }

        return hijos;

    }

    public Estado A_Star(){
        ArrayList<Estado> cerrados= new ArrayList<>();
        ArrayList<Estado> abiertos= new ArrayList<>();
        abiertos.add(estadoInicial);

        while (!abiertos.isEmpty()){
            Estado estado_actual= obtener_menor(abiertos);
            cerrados.add(estado_actual);
            System.out.println("EL estado actual es ");
            imprimir_estado(estado_actual);

            ArrayList<Posicion> vacios =buscar_vacios(estado_actual);
            /*System.out.println("Los vacios son ");
            for (int j=0; j< vacios.size();j++){
                System.out.println(vacios.get(j).getX() +" and " + vacios.get(j).getY());
            }*/
            System.out.println("Los vacios son ");
            for (int i=0;i< vacios.size();i++){
                ArrayList<Integer> opciones = buscar_opciones(estado_actual, vacios.get(i).getX(), vacios.get(i).getY());
                vacios.get(i).setOpciones(opciones);

                System.out.println(vacios.get(i).getX() +" and " + vacios.get(i).getY());
                System.out.print("Con opciones ");
                for (int m=0;m< vacios.get(i).getOpciones().size();m++){
                    System.out.print(vacios.get(i).getOpciones().get(m));
                }
                System.out.println("");
            }


            if (goal(estado_actual)){
                //termina
                return estado_actual;
            }

            else{
                //revisa los hijos
                ArrayList<Estado> hijos = obtener_hijos(vacios, estado_actual);

                System.out.println("Los hijos que obtiene son ");
                for (int i=0; i<hijos.size();i++){
                    buscar_vacios(hijos.get(i));
                    if (obtener_hijos(vacios, hijos.get(i)).size()>0 || goal(hijos.get(i))){
                        imprimir_estado(hijos.get(i));
                        abiertos.add(hijos.get(i));
                    }
                }

                ArrayList<Estado> abierto_temp= new ArrayList<>();
                for (int i=0; i< abiertos.size();i++){
                    if (abiertos.get(i).compareTo(estado_actual)==0){
                        abierto_temp.add(abiertos.get(i));
                    }
                }

                abiertos = abierto_temp;

                /*System.out.println("Verificar los abiertos");
                for (int m=0; m<abiertos.size();m++){
                    System.out.println("_________________________________________");
                    imprimir_estado(abiertos.get(m));
                }*/

            }
        }

        return null;


    }


}
