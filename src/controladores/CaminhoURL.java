
package controladores;

import java.net.URL;


public class CaminhoURL {
    
    public final URL criaURL (String Caminho){
    
        return getClass().getResource(Caminho);
    }

    
}
