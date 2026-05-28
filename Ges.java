/**
 * Clase principal del sistema de Gestion de Emergencias Sanitarias (GES).
 * Esta clase se entrega como esqueleto. El alumno debe implementar los metodos
 * recursivos de triaje, analisis, altas e indexacion solicitados en la practica.
 *
 * @author profesores ED
 * @version 1.0
 */
package ges;

import java.util.Iterator;

public class Ges {
    private Arbol redSanitaria;
    private ArbolBinarioBusqueda indicePacientes;

    public Ges(Arbol red) {
        this.redSanitaria = red;
        this.indicePacientes = null;
    }

    public ArbolBinarioBusqueda getIndicePacientes() { return indicePacientes; }

    // =====================================================================
    // EJERCICIO 1: Analisis de gravedad de un nodo
    // =====================================================================
    // Clase privada auxiliar para acumular los datos en la recursion
    private static class Estadisticas {
        int n_pacientes;
        double s_gravedad;

        public Estadisticas() {
            this.n_pacientes = 0;
            this.s_gravedad = 0.0;
        }
    }

    public double analizarGravedad(String nombreNodo) {
        /// === INICIO CODIGO ALUMNO === ///
        double resultado= 0.0;
        NodoArbol nodo = redSanitaria.buscar(nombreNodo);
        if (nodo != null) {
            Estadisticas stats = new Estadisticas();
            analizarGravedadRec(nodo, stats);
            if (stats.n_pacientes != 0){
                resultado = stats.s_gravedad / stats.n_pacientes;
            }
        }

        return resultado;
        // TODO: Borrar este valor por defecto. Es solo para que el proyecto compile.
        /// === FIN CODIGO ALUMNO === ///
    }

    /// === INICIO CODIGO ALUMNO (Metodo Auxiliar E1) === ///

    private void analizarGravedadRec(NodoArbol nodo, Estadisticas stats) {
        if (nodo != null) {
            if (nodo.getTipo().equals("PACIENTE")) {
                stats.n_pacientes++;
                stats.s_gravedad += nodo.getPaciente().getGravedad();
            } else {
                Iterator<NodoArbol> it = nodo.getHijos().iterator();
                while (it.hasNext()) {
                    analizarGravedadRec(it.next(), stats);
                }
            }
        }
    }
    /// === FIN CODIGO ALUMNO === ///


    // =====================================================================
    // EJERCICIO 2: Algoritmo de triaje (busqueda de cama en una unidad con capacidad)
    // =====================================================================
    public String buscarCama(String especialidad) {
        /// === INICIO CODIGO ALUMNO === ///
        return buscarCamaRec(redSanitaria.getRaiz(), especialidad, "");
        // TODO: Borrar este valor por defecto. Es solo para que el proyecto compile.
        /// === FIN CODIGO ALUMNO === ///
    }

    /// === INICIO CODIGO ALUMNO (Metodo Auxiliar E2) === ///
    private String buscarCamaRec(NodoArbol nodo, String especialidad, String rutaActual) {
        String resultado = null;

        if (nodo != null) {
            String nuevaRuta = "";
            if (rutaActual.isEmpty()) {
                nuevaRuta = nodo.getNombre();
            } else {
                nuevaRuta = rutaActual + " -> " + nodo.getNombre();
            }

            if (nodo.getTipo().equals("UNIDAD") && nodo.getNombre().equalsIgnoreCase(especialidad)) {
                if (nodo.getHijos().size() < nodo.getCapacidadMaxima()) {
                    resultado = nuevaRuta;
                }
            }

            if (resultado == null) {
                Iterator<NodoArbol> it = nodo.getHijos().iterator();
                while (it.hasNext() && resultado == null) {
                    resultado = buscarCamaRec(it.next(), especialidad, nuevaRuta);
                }
            }
        }

        return resultado;
    }
    /// === FIN CODIGO ALUMNO === ///



    // =====================================================================
    // EJERCICIO 3: Gestion de altas
    // =====================================================================
    public boolean darAltaPaciente(int idPaciente) {
        /// === INICIO CODIGO ALUMNO === ///
        return darAltaPacienteRec(redSanitaria.getRaiz(), idPaciente);
        // TODO: Borrar este valor por defecto. Es solo para que el proyecto compile.
        /// === FIN CODIGO ALUMNO === ///
    }

    /// === INICIO CODIGO ALUMNO (Metodo Auxiliar E3) === ///
    private boolean darAltaPacienteRec(NodoArbol nodo, int idPaciente) {
        boolean resultado = false;
        if (nodo != null && nodo.getHijos() != null) {
            Iterator<NodoArbol> it = nodo.getHijos().iterator();
            while (it.hasNext() && !resultado) {
                NodoArbol hijo = it.next();
                if (hijo.getTipo().equals("PACIENTE") && hijo.getPaciente().getId() == idPaciente) {
                    it.remove();
                    resultado = true;
                } else {
                    resultado = darAltaPacienteRec(hijo, idPaciente);
                }
            }
        }
        return resultado;
    }
    /// === FIN CODIGO ALUMNO === ///



    // =====================================================================
    // EJERCICIO 5: Indexacion masiva
    // =====================================================================
    public void generarIndice() {
        /// === INICIO CODIGO ALUMNO === ///
        indicePacientes = new ArbolBinarioBusqueda();
        generarIndiceRec(redSanitaria.getRaiz());
        /// === FIN CODIGO ALUMNO === ///
    }

    /// === INICIO CODIGO ALUMNO (Metodo Auxiliar E5) === ///
    private void generarIndiceRec(NodoArbol nodo) {
        if (nodo != null) {
            if (nodo.getTipo().equals("PACIENTE")) {
                indicePacientes.insertar(nodo.getPaciente());
            } else {
                Iterator<NodoArbol> it = nodo.getHijos().iterator();
                while (it.hasNext()) {
                    generarIndiceRec(it.next());
                }
            }
        }
    }
    /// === FIN CODIGO ALUMNO === ///

    // =====================================================================
    // EJERCICIO 6: Localizacion rapida
    // =====================================================================
    public Paciente buscarFamiliar(int idPaciente) {
        /// === INICIO CODIGO ALUMNO === ///
        Paciente resultado = null;
        if (indicePacientes != null) {
            resultado = indicePacientes.buscar(idPaciente);
        }
        return resultado;
        // TODO: Borrar este valor por defecto. Es solo para que el proyecto compile.
        /// === FIN CODIGO ALUMNO === ///
    }
}