
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Estado {

    private int NumEstado;
    private Map<Character, ArrayList<Estado>> EstadoSiguiente;
    private boolean estadoFinal;
    private Set<Estado> estados;

    // constructor del NFA
    public Estado(int ID) {
        this.NumEstado = ID;
        this.EstadoSiguiente = new HashMap<>();
    }

    //constructor AFD
    public Estado(Set<Estado> estados, int ID) {
        this.setEstados(estados);
        this.setNumEstado(ID);
        this.setEstadoSiguiente(new HashMap<Character, ArrayList<Estado>>());

        //encontrar si hay estado final en este conjunto de estados
        for (Estado p : estados) {
            if (p.isEstadoFinal()) {
                this.setEstadoFinal(true);
                break;
            }
        }
    }

    public void setEstados(Set<Estado> estados) {
        this.estados = estados;
    }

    public void agregarTransicion(Estado siguiente, char clave) {
        ArrayList<Estado> lista = this.EstadoSiguiente.get(clave);
        if (lista == null) {
            lista = new ArrayList<Estado>();
            this.EstadoSiguiente.put(clave, lista);
        }
        lista.add(siguiente);

    }

    public int getNumEstado() {
        return NumEstado;
    }

    public void setNumEstado(int NumEstado) {
        this.NumEstado = NumEstado;
    }

    public Map<Character, ArrayList<Estado>> getEstadoSiguiente() {
        return EstadoSiguiente;
    }

    public void setEstadoSiguiente(Map<Character, ArrayList<Estado>> EstadoSiguiente) {
        this.EstadoSiguiente = EstadoSiguiente;
    }

    public boolean isEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(boolean aceptarEstado) {
        this.estadoFinal = aceptarEstado;
    }

    // Obtener todos los estados de transición basados en el símbolo
    public ArrayList<Estado> getAllTransitions(char c) {
        if (this.EstadoSiguiente.get(c)== null) {
            return new ArrayList<Estado>();
        } else {
            return this.EstadoSiguiente.get(c);
        }
    }

    public Set<Estado> getEstados() {
        return estados;
    }
    
}
