package modelos;

import java.io.Serializable;

/**
 *
 * @author Joseph && Jhovanny
 */
public class Estudiante implements Comparable<Estudiante>, Serializable{

    private String nombres, apellidos, codigo, cedula, semestre, carrera, promedio;

    public Estudiante(String nombres, String apellidos, String codigo, String cedula, String semestre, String carrera, String promedio) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.codigo = codigo;
        this.cedula = cedula;
        this.semestre = semestre;
        this.carrera = carrera;
        this.promedio = promedio;
       
    }
    
    

    /**
     * USAR SÓLO TEMPORALMENTE Constructor para crear un estudiante sólo con el
     * código (FUNCIONALIDAD BUSCAR NORMAL DEL AVLTREE)
     *
     * @param codigo el código del estudiante nuevo
     */
    public Estudiante(String codigo) {
        this.codigo = codigo;
        
    }
    
    

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    /**
     * Compara los estudiantes basado en el código
     *
     * @param o Estudiante a comparar con el que ejecuta el metodo
     * @return Positivo si el Estudiante a comparar tiene un codigo más pequeño,
     * positivo al contrario y 0 si son iguales
     */
    @Override
    public int compareTo(Estudiante o) {
        return Integer.parseInt(this.codigo)-Integer.parseInt(o.getCodigo());
    }

    /**
     * Compara dos estudiantes basado en el tipo de retorno
     *
     * @param o Estudiante a comparar con el que ejeuta el metodo
     * @param returnType Usar Estudiante.class.getDeclaredMethod("getCedula")
     * Usando entre comillas el nombre del metodo por el cual comparar
     * @return Lo normal
     */
//    public int compareTo(Estudiante o, Class<?> returnType) {
//        if (returnType == String.class) {
//            return (this.getNombres()+this.getApellidos()).compareToIgnoreCase(o.getNombres()+o.getApellidos());
//        } else if (returnType == Integer.class) {
//            int a = 5;
//            return a - a;
//        } else {
//            return 0;
//            //OJO REVISAR ESTE CODIGO PORQUE HAY QUE GENERAR UN COMPARE TO
//            //QUE DEPENDE DEL TIPO ESPECIFICO QUE SE VA A USAR (REVISAR OTROS COMPARES)
//        }
//    }
    /**
     * Comparativa dependiendo del tipo de filtro --PONER EL NOMBRE DEL METODO
     * GET SIN EL GET-- Comparo mi estudiante a (sobre el que lanzo el método)
     * con un estudiante b (con quien comparo)
     *
     * @param b Estudiante a comparar con el actual
     * @param filtro Solamente funcionan: Nombres (compara nombre y apellido
     * para evitar errores), Codigo y Cedula
     * @return
     */
    public int compareTo(Estudiante b, String filtro) {
        String mensaje = "";
        if (b == null) {
            mensaje += "Estudiante a comparar null. ";
        } else if (filtro.equals("Nombres")) {
            return (this.getNombres() + this.getApellidos()).compareToIgnoreCase(b.getNombres() + b.getApellidos());
        } else if (filtro.equals("Codigo")) {
            return this.getCodigo().compareTo(b.getCodigo());
        } else if (filtro.equals("Cedula")) {
            return this.getCedula().compareTo(b.getCedula());
        }
        if (!(filtro.equals("Nombre") || filtro.equals("Codigo") || filtro.equals("Cedula"))) {
            mensaje += "Filtro erroneo.";
        }
        throw new RuntimeException(mensaje);
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
    
    /**
     * Método para entregar como Fila los datos del estudiante
     * @return la fila de Strings en un arreglo
     */
    public String[] toRow() {
        String[] s = {codigo, nombres, apellidos, cedula, carrera, semestre, promedio};
        return s;
    }

}
