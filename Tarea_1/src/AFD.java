

import java.util.LinkedList;

public class AFD {
    private LinkedList<Estado> AFD;
    
    public AFD(){
        this.AFD = new LinkedList<>();
    }

    public LinkedList<Estado> getAFD() {
        return AFD;
    }

    public void setAFD(LinkedList<Estado> AFD) {
        this.AFD = AFD;
    }
    
}
