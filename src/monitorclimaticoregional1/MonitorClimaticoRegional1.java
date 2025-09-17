package monitorclimaticoregional1;

import java.util.Random;
import java.util.Scanner;

public class MonitorClimaticoRegional1 {
    // Arreglos para cada subestación (12 meses)
    private int[] rivera = new int[12];
    private int[] neiva = new int[12];
    private int[] campoalegre = new int[12];

    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    // ---------------- MÉTODOS PRINCIPALES ----------------
    
    // Generar datos simulados para una subestación
    public void generarDatos(int[] estacion, boolean aleatorio, int min, int max) {
        if (aleatorio) {
            for (int i = 0; i < estacion.length; i++) {
                estacion[i] = random.nextInt((max - min) + 1) + min;
            }
        } else {
            System.out.println("Ingrese manualmente las 12 temperaturas (enteros):");
            for (int i = 0; i < estacion.length; i++) {
                System.out.print("Mes " + (i + 1) + ": ");
                estacion[i] = scanner.nextInt();
            }
        }
    }

    // Calcular promedio de temperaturas (retorna double para mayor precisión)
    public double promedio(int[] estacion) {
        int suma = 0;
        for (int t : estacion) suma += t;
        return (double) suma / estacion.length;
    }

    // Calcular temperatura máxima
    public int maxima(int[] estacion) {
        int max = estacion[0];
        for (int t : estacion) if (t > max) max = t;
        return max;
    }

    // Calcular temperatura mínima
    public int minima(int[] estacion) {
        int min = estacion[0];
        for (int t : estacion) if (t < min) min = t;
        return min;
    }

    // ---------------- MÉTODOS ADICIONALES ----------------

    // Comparar dos subestaciones
    public String compararSubestaciones(int[] t1, int[] t2, String nombre1, String nombre2) {
        double prom1 = promedio(t1);
        double prom2 = promedio(t2);
        if (prom1 > prom2) {
            return nombre1 + " fue mas calida con promedio de " + prom1;
        } else if (prom2 > prom1) {
            return nombre2 + " fue mas calida con promedio de " + prom2;
        } else {
            return "Ambas subestaciones tuvieron el mismo promedio de " + prom1;
        }
    }

    // Detectar anomalías: +/-20% del promedio anual
    public int[] detectarAnomalias(int[] estacion) {
        double prom = promedio(estacion);
        double margen = prom * 0.20;
        int[] indices = new int[12];
        int count = 0;

        for (int i = 0; i < estacion.length; i++) {
            if (estacion[i] > prom + margen || estacion[i] < prom - margen) {
                indices[count++] = i + 1; // Guardar número de mes
            }
        }
        // Redimensionar arreglo para devolver solo anomalías encontradas
        int[] resultado = new int[count];
        System.arraycopy(indices, 0, resultado, 0, count);
        return resultado;
    }

    // Reporte mensual por subestación
    public void reporteMensual(String nombre, int[] estacion) {
        System.out.println("\n--- Reporte de " + nombre + " ---");
        for (int i = 0; i < estacion.length; i++) {
            System.out.println("Mes " + (i + 1) + ": " + estacion[i]);
        }
        System.out.println("Promedio anual: " + promedio(estacion));
        System.out.println("Maxima: " + maxima(estacion));
        System.out.println("Minima: " + minima(estacion));
    }

    // ---------------- MÉTODO MAIN ----------------
    public static void main(String[] args) {
        MonitorClimaticoRegional1 monitor = new MonitorClimaticoRegional1();

        // Generar datos automáticos para ejemplo (puede cambiarse a manual)
        monitor.generarDatos(monitor.rivera, true, 20, 35);
        monitor.generarDatos(monitor.neiva, true, 22, 38);
        monitor.generarDatos(monitor.campoalegre, true, 21, 36);

        // Reportes
        monitor.reporteMensual("Rivera", monitor.rivera);
        monitor.reporteMensual("Neiva", monitor.neiva);
        monitor.reporteMensual("Campoalegre", monitor.campoalegre);

        // Comparaciones
        System.out.println("\n" + monitor.compararSubestaciones(monitor.rivera, monitor.neiva, "Rivera", "Neiva"));
        System.out.println(monitor.compararSubestaciones(monitor.neiva, monitor.campoalegre, "Neiva", "Campoalegre"));
        System.out.println(monitor.compararSubestaciones(monitor.rivera, monitor.campoalegre, "Rivera", "Campoalegre"));

        // Anomalías
        int[] anomaliasRivera = monitor.detectarAnomalias(monitor.rivera);
        System.out.print("\nMeses con anomalías en Rivera: ");
        for (int m : anomaliasRivera) System.out.print(m + " ");
    }
}
