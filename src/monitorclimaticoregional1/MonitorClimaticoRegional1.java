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



// varianza = sumaneiva=sumaneiva+(pow(neiva(i)-promedioneiva,z));
//desviacion = desviacionneiva=math.sqrt(varianzaneiva);


///////////////////////////////////////////////////////////////////


import java.util.Arrays;
import java.util.Random;

public class MonitorClimaticoRegional1 {

    public static class MonitorClimaticoRegional {

        public static final String[] MESES = {
            "Enero","Febrero","Marzo","Abril","Mayo","Junio",
            "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
        };

        /**
         * Genera un array de 12 temperaturas (double).
         * Si aleatorio == true genera valores aleatorios entre min y max.
         */
        public static double[] generarTemperaturas(boolean aleatorio, double min, double max) {
            double[] t = new double[12];
            if (min > max) {
                double tmp = min; min = max; max = tmp;
            }
            if (!aleatorio) {
                Arrays.fill(t, 0.0); // puedes reemplazar por lectura manual si quieres
                return t;
            }
            Random r = new Random();
            for (int i = 0; i < 12; i++) {
                t[i] = min + (max - min) * r.nextDouble();
            }
            return t;
        }

        // Mínimo
        public static double minimoArray(double[] arr) {
            if (arr == null || arr.length == 0) throw new IllegalArgumentException("Array vacio");
            double min = arr[0];
            for (double v : arr) if (v < min) min = v;
            return min;
        }

        // Máximo
        public static double maximoArray(double[] arr) {
            if (arr == null || arr.length == 0) throw new IllegalArgumentException("Array vacio");
            double max = arr[0];
            for (double v : arr) if (v > max) max = v;
            return max;
        }

        // Promedio
        public static double promedioArray(double[] arr) {
            if (arr == null || arr.length == 0) throw new IllegalArgumentException("Array vacio");
            double sum = 0;
            for (double v : arr) sum += v;
            return sum / arr.length;
        }

        // Comparación entre 2 subestaciones
        public static String compararSubestaciones(double[] t1, double[] t2, String nombre1, String nombre2) {
            double p1 = promedioArray(t1);
            double p2 = promedioArray(t2);
            if (Math.abs(p1 - p2) < 1e-9) {
                return String.format("Ambas (%s y %s) tienen el mismo promedio anual: %.2f", nombre1, nombre2, p1);
            } else if (p1 > p2) {
                return String.format("%s fue más cálida (%.2f) que %s (%.2f).", nombre1, p1, nombre2, p2);
            } else {
                return String.format("%s fue más cálida (%.2f) que %s (%.2f).", nombre2, p2, nombre1, p1);
            }
        }

        // Detectar anomalías térmicas
        public static int[] detectarAnomalias(double[] temperaturas) {
            double avg = promedioArray(temperaturas);
            double umbral = 0.20 * Math.abs(avg);
            int[] tempIdx = new int[12];
            int count = 0;
            for (int i = 0; i < temperaturas.length; i++) {
                double t = temperaturas[i];
                if (t > avg + umbral || t < avg - umbral) {
                    tempIdx[count++] = i;
                }
            }
            return Arrays.copyOf(tempIdx, count);
        }

        // Reporte mensual con estadísticas
        public static void reporteMensual(String nombre, double[] temperaturas) {
            System.out.println("-------------------------------------------------");
            System.out.println("Reporte para subestacion: " + nombre);
            for (int i = 0; i < temperaturas.length; i++) {
                System.out.printf("%-11s: %.2f%n", MESES[i], temperaturas[i]);
            }
            double min = minimoArray(temperaturas);
            double max = maximoArray(temperaturas);
            double prom = promedioArray(temperaturas);

            StringBuilder mesesMin = new StringBuilder();
            StringBuilder mesesMax = new StringBuilder();
            for (int i = 0; i < temperaturas.length; i++) {
                if (Math.abs(temperaturas[i] - min) < 1e-9) {
                    if (mesesMin.length() > 0) mesesMin.append(", ");
                    mesesMin.append(MESES[i]);
                }
                if (Math.abs(temperaturas[i] - max) < 1e-9) {
                    if (mesesMax.length() > 0) mesesMax.append(", ");
                    mesesMax.append(MESES[i]);
                }
            }

            System.out.printf("Mayor temperatura: %.2f (mes/es: %s)%n", max, mesesMax);
            System.out.printf("Menor temperatura: %.2f (mes/es: %s)%n", min, mesesMin);
            System.out.printf("Promedio anual: %.2f%n", prom);

            int[] anom = detectarAnomalias(temperaturas);
            if (anom.length == 0) {
                System.out.println("No se detectaron anomalías térmicas.");
            } else {
                System.out.print("Anomalías en: ");
                for (int i = 0; i < anom.length; i++) {
                    if (i > 0) System.out.print(", ");
                    System.out.print(MESES[anom[i]]);
                }
                System.out.println();
            }
            System.out.println("-------------------------------------------------");
        }
    }

    public static void main(String[] args) {
        double[] rivera = MonitorClimaticoRegional.generarTemperaturas(true, 18, 32);
        double[] neiva = MonitorClimaticoRegional.generarTemperaturas(true, 20, 36);
        double[] campoalegre = MonitorClimaticoRegional.generarTemperaturas(true, 16, 30);

        MonitorClimaticoRegional.reporteMensual("Rivera", rivera);
        MonitorClimaticoRegional.reporteMensual("Neiva", neiva);
        MonitorClimaticoRegional.reporteMensual("Campoalegre", campoalegre);

        System.out.println("Comparaciones entre subestaciones:");
        System.out.println(MonitorClimaticoRegional.compararSubestaciones(rivera, neiva, "Rivera", "Neiva"));
        System.out.println(MonitorClimaticoRegional.compararSubestaciones(rivera, campoalegre, "Rivera", "Campoalegre"));
        System.out.println(MonitorClimaticoRegional.compararSubestaciones(neiva, campoalegre, "Neiva", "Campoalegre"));

        double pR = MonitorClimaticoRegional.promedioArray(rivera);
        double pN = MonitorClimaticoRegional.promedioArray(neiva);
        double pC = MonitorClimaticoRegional.promedioArray(campoalegre);

        String masCalida;
        if (pR >= pN && pR >= pC) masCalida = "Rivera";
        else if (pN >= pR && pN >= pC) masCalida = "Neiva";
        else masCalida = "Campoalegre";

        System.out.printf("La subestación más cálida del año es: %s%n", masCalida);
    }
}



