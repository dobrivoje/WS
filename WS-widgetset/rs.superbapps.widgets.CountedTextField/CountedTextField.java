
import org.superb.widgets.chromatext.client.ChromaTextPasswordClientRpc;
import org.superb.widgets.chromatext.client.ChromaTextPasswordServerRpc;
import org.superb.widgets.chromatext.client.ChromaTextPasswordState;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractComponent;

public class CountedTextField extends AbstractComponent {
    
    private ChromaTextPasswordServerRpc rpc = new ChromaTextPasswordServerRpc() {
        private int clickCount = 0;
        
        public void clicked(MouseEventDetails mouseDetails) {
            // nag every 5:th click using RPC
            if (++clickCount % 5 == 0) {
                getRpcProxy(ChromaTextPasswordClientRpc.class).alert(
                        "Ok, that's enough!");
            }
            // update shared state
            getState().text = "You have clicked " + clickCount + " times";
        }
    };
    
    public CountedTextField() {
        registerRpc(rpc);
    }
    
    @Override
    public ChromaTextPasswordState getState() {
        return (ChromaTextPasswordState) super.getState();
    }
}
