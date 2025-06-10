
import java.util.ArrayList;

public class CanaleComunicazione {
	
    private final ArrayList<String> canale = new ArrayList<>();

    public void invio(String message) {
    	canale.add(message);
    }

    public String ricezione() {
        if (!canale.isEmpty()) {
            return canale.remove(0);
        }
        return null;
    }
    
}
