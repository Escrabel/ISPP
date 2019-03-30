package utilities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import services.ProcessionService;

public class UtilitiesProject {

	static String clientID = "AWbtMzp7U5EibrIwR3D85eDGhM5R6UTWQ2V8-47t4MQ2Et7Op-KJAFWrhoEA6XdeLZNpiJZeO5mlUTpr";
	static String clientSecret = "EHg68q-a1F2xDHHNGszEi88IsJQBPm2rYPHj-6K2WS4abGd_Od3m4jC8GbMDChdfbEyULAyPsXFiVC_6";

	static String mode = "sandbox";

	public static String generateTicker() {

		final int maxLetras = 91;
		final int minLetras = 65;

		String alfanumerica = "";
		final String retorno;
		String fechaCadena;
		char c;

		final Date fecha = new Date();

		fechaCadena = new SimpleDateFormat("yyMMdd").format(fecha);

		for (int i = 0; i < 5; i++) {
			c = (char) (Math.random() * (maxLetras - minLetras) + minLetras);
			alfanumerica += c;

		}

		retorno = fechaCadena + "-" + alfanumerica;

		return retorno;
	}

	public static boolean fechaMayorActual(Date fechaCompra) {
		boolean retorno = false;

		
		System.out.println("fecha de compra"+fechaCompra);

		if (fechaCompra != null) {

			//fecha de compra con dos días más
			Date fechaSumaMes = new Date();
			Calendar calendarMes = Calendar.getInstance();
			calendarMes.setTime(fechaCompra);
			calendarMes.add(Calendar.DAY_OF_YEAR, +2);
			fechaSumaMes = calendarMes.getTime();
			
			System.out.println("fecha dos días después"+fechaSumaMes);
			
			//fecha actual
			Date fechaActual = new Date();
			Calendar calendarActual = Calendar.getInstance();
			calendarActual.setTime(fechaActual);
			fechaActual = calendarActual.getTime();
			System.out.println("fecha actual"+fechaActual);
			
			
			if (fechaSumaMes.compareTo(fechaActual) < 0)
				retorno = true;
		}
		
		System.out.println("retornaa"+retorno);

		return retorno;
	}

	public static Date fechaSumaDosDias() {

		Date fechaActual = new Date();

		Date fechaSumaMes = new Date();
		Calendar calendarMes = Calendar.getInstance();
		calendarMes.setTime(fechaActual);
		calendarMes.add(Calendar.DAY_OF_YEAR, +2);
		fechaSumaMes = calendarMes.getTime();

		return fechaSumaMes;
	}

	public static double truncateTo(double unroundedNumber, int decimalPlaces) {
		int truncatedNumberInt = (int) (unroundedNumber * Math.pow(10, decimalPlaces));
		double truncatedNumber = (double) (truncatedNumberInt / Math.pow(10, decimalPlaces));
		return truncatedNumber;
	}

	public static void main(final String[] args) throws Throwable {
		DecimalFormat format = new DecimalFormat("0.00");

		double d = 0d;

		double e = 1. + d;
		
		Date fechaSumaMes = new Date();
		Calendar calendarMes = Calendar.getInstance();
		calendarMes.setTime(fechaSumaMes);
		calendarMes.add(Calendar.DAY_OF_YEAR, +2);
		fechaSumaMes = calendarMes.getTime();
		
		System.out.println(fechaMayorActual(fechaSumaMes));

	}

}