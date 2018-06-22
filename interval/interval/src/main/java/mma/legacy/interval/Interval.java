package mma.legacy.interval;

/**
 * Clase para el ejemplo de trabajo con Legacy
 * @author Agustin
 * Controla operaciones sobre intervalos que pueden ser abiertos o cerrados
 */
public class Interval {

	private double minimum;  // número entero que indica el limite inferior del intervalo
	private double maximum;  // número entero que indica el limite superior del intervalo
	private Opening opening; // Valor booleano que indica si el intervalo es abierto o cerrado

	/**
	 * Constructor de la clase
	 * @param minimum
	 * @param maximum
	 * @param opening
	 * Todos los parámetros pueden ser nulos
	 */
	public Interval(double minimum, double maximum, Opening opening) {
		this.minimum = minimum;
		this.maximum = maximum;
		this.opening = opening;
		System.out.println("Objeto creado");
	}

	/**
	 * Método que calcula la mediana de un intervalo
	 * @return 		la mediana del intervalo
	 */
	public double midPoint() {		
		return (maximum + minimum) / 2;	
	}

	/**
	 * Método para determinar si un valor pertenece a un intervalo
	 * @param value 	el valor que queremos comprobar si pertenece al intervalo
	 * @return 			<code>true</code> si el valor pertenece al intervalo
	 * 					<code>false</code> en cualquier otro caso
	 */
	public boolean includes(double value) {
		System.out.print("Entro en el método");	
		switch (opening) {
			case BOTH_OPENED:			return minimum < value && value < maximum;
			case LEFT_OPENED: 			return minimum < value && value <= maximum;
			case RIGHT_OPENED:			return minimum <= value && value < maximum;
			case UNOPENED:
				return minimum <= value && value <= maximum;
			default:			
				assert false;
			return false;
		}
	}

	/**
	 * Método para determinar si un intervalo dado está incluido dentro del intervalo
	 * @param interval 	el intervalo sobre el cual queremos comprobar si está incluido dentro de otro
	 * @return			<code>true</code> si el intervalo dado está incluido en el intervalo
	 * 					<code>false</code> en cualquier otro caso
	 */
	public boolean includes(Interval interval) {
		boolean minimumIncluded = this.includes(interval.minimum);
		boolean maximumIncluded = this.includes(interval.maximum);
		switch (opening) {
			case BOTH_OPENED:
				switch (interval.opening) {
					case BOTH_OPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					case LEFT_OPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded);
					case RIGHT_OPENED:
						return (minimumIncluded) && (maximumIncluded || maximum == interval.maximum);
					case UNOPENED:
						return (minimumIncluded) && (maximumIncluded);
					default:
						assert false;
						return false;
				}
			case LEFT_OPENED:
				switch (interval.opening) {
					case BOTH_OPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					case LEFT_OPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					case RIGHT_OPENED:
						return (minimumIncluded) && (maximumIncluded || maximum == interval.maximum);
					case UNOPENED:
						return (minimumIncluded) && (maximumIncluded || maximum == interval.maximum);
					default:
						assert false;
						return false;
				}
			case RIGHT_OPENED:
				switch (interval.opening) {
					case BOTH_OPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					case LEFT_OPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded);
					case RIGHT_OPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					case UNOPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded);
					default:
						assert false;
						return false;
				}
			case UNOPENED:
				switch (interval.opening) {
					case BOTH_OPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					case LEFT_OPENED: 				
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					case RIGHT_OPENED: 				
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					case UNOPENED:
						return (minimumIncluded || minimum == interval.minimum) && (maximumIncluded || maximum == interval.maximum);
					default:
						assert false; 				
						return false; 
				}
			default:
				assert false;
				return false;
		}
	}

	/**
	 * Método que comprueba si un intervalo tiene intersección con otro
	 * @param interval	el intervalo dado para comprobar intersección con este intervalo
	 * @return			<code>true</code> si el intervalo dado tiene intersección con este intervalo
	 * 					<code>false</code> en cualquier otro caso
	 */

	public boolean intersectsWith(Interval interval) {
		if (minimum == interval.maximum) {
			switch (opening) {
				case BOTH_OPENED:
				case LEFT_OPENED:
					return false;
				case RIGHT_OPENED:
				case UNOPENED:
					return interval.opening == Opening.LEFT_OPENED ||interval.opening == Opening.UNOPENED;
				default:
					assert false;
				return false;	
			}
		}
		if (maximum == interval.minimum) {
			switch (opening) {
				case BOTH_OPENED:
				case RIGHT_OPENED:
					return false;
				case LEFT_OPENED:
				case UNOPENED:
					return interval.opening == Opening.RIGHT_OPENED ||
							interval.opening == Opening.UNOPENED;
				default:
					assert false;
				return false;
			}
		}
		return this.includes(interval.minimum) || this.includes(interval.maximum);
	}

	@Override
	public String toString() {
		// TODO
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(maximum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minimum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((opening == null) ? 0 : opening.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interval other = (Interval) obj;
		if (Double.doubleToLongBits(maximum) != Double.doubleToLongBits(other.maximum))
			return false;
		if (Double.doubleToLongBits(minimum) != Double.doubleToLongBits(other.minimum))
			return false;
		if (opening != other.opening)
			return false;
		return true;
	}

}
