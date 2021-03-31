package Exercicis.MVC;

import java.util.Observable;
import java.util.Observer;
import Exercicis.MVC.Producto.PrecioEvent;
/**
 *
 * @author Enric
 */
public class ProductoObserver implements Observer{
    
    @Override
    public void update(Observable observable, Object args){
        if(args instanceof PrecioEvent){
            PrecioEvent evento = (PrecioEvent) args;
            System.out.printf("El producto %s ha cambiado de precio %s a %s%n", evento.getProducto().getNombre(), evento.getPrecioAntiguo(), evento.getPrecioNuevo());
        }
    }
}
