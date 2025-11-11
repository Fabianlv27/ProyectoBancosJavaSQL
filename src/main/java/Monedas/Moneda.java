package Monedas;

public class Moneda {
	private double Moneda;
	private int Cantidad;
	public Moneda(double moneda, int cantidad) {
		super();
		Moneda = moneda;
		Cantidad = cantidad;
	}
	public double getMoneda() {
		return Moneda;
	}
	public void setMoneda(double moneda) {
		Moneda = moneda;
	}
	public int getCantidad() {
		return Cantidad;
	}
	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
	@Override
	public String toString() {
		return "Moneda [Moneda=" + Moneda + ", Cantidad=" + Cantidad + "]";
	}
	
	
}
