/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainApp;

import DAO.GestionDao;
import Excepciones.ExcepcionLigaPokemon;
import Models.Entrenador;
import Models.Pokemon;
import java.sql.SQLException;

/**
 *
 * @author dmorenoar
 */
public class JDBCJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        GestionDao ligaDAO = new GestionDao();

        Entrenador ash = new Entrenador("Ash", "+3412345", "Hombre", 2, 2, "Eléctrico");
        Entrenador brock = new Entrenador("Brock", "+340101", "Hombre", 5, 6, "Roca");
        
        Pokemon pikachu = new Pokemon("Pikachu", "Eléctrico", 22, ash);
        Pokemon onix = new Pokemon("Onix", "Roca", 22, brock);
        
        
        try {

            //Establecemos la conexion 
            System.out.println("Realizando la conexion con la base de datos");
            ligaDAO.conectar();
            System.out.println("Conexion establecida");

            //Testeando insertar un entrenador
            System.out.println("|||||||||Testeando insertar un entrenador|||||||||");
            altaEntrenador(ligaDAO, ash);
            altaEntrenador(ligaDAO, brock);
            
            System.out.println("|||||||||Testeando insertar un entrenador duplicado|||||||||");
            //Testeando insertar entrenador duplicado
            altaEntrenador(ligaDAO, ash);

            System.out.println("|||||||||Testeando insertar un pokemon|||||||||");
            //Testeando insertar un pokemon
            altaPokemon(ligaDAO, pikachu);
            altaPokemon(ligaDAO, onix);
            
            System.out.println("|||||||||Testeando insertar un pokemon duplicado|||||||||");
            //Testeando insertar pokemon duplicado
            altaPokemon(ligaDAO, pikachu);
            
            System.out.println("|||||||||Testeando insertar un pokemon con un entrenador que no existe|||||||||");
            //Testeando insertar un pokemon con un entrenador que no existe
            altaPokemon(ligaDAO, onix);
            
            System.out.println("|||||||||Testeando borrar un pokemon|||||||||");
            //Testeando borrar Pokemon
            borrarPokemon(ligaDAO, pikachu);
            
            System.out.println("|||||||||Testeando modificar la especialidad de un entrenador|||||||||");
            //Testeando modificar la especialidad de un Entrenador
            ash.setEspecialidad("Agua");
            modificarEntrenador(ligaDAO, ash);
            
            System.out.println("|||||||||Testeando buscar un entrenador por el nombre|||||||||");
            //Testeando buscar un entrenador por el nombre
            entrenadorByNombre(ligaDAO, "Ash");
            
            System.out.println("|||||||||Testeando mostrar la lista de todos los entrenadores|||||||||");
            //Testeando mostrar la lista de todos los entrenadores
            
            System.out.println("|||||||||Testeando subir la fuerza a un pokemon|||||||||");
            //Testeando subir la fuerza a un pokemon
            Pokemon charmander = new Pokemon("Charmander", "Fuego", 22, ash);
            altaPokemon(ligaDAO, charmander);
            charmander.setFuerza(99.9);
            subirFuerzaPokemon(ligaDAO, charmander);
            
            //Testeando buscar un pokemon por nombre
            getPokemonByNombre(ligaDAO, "Charmander");
            
            System.out.println("|||||||||Testeando borrar un entrenador|||||||||");
            //Testeando borrar un entrenador
            borrarEntrenador(ligaDAO, ash);
            
            //Realizamos la desconexion
            System.out.println("Desconectando...");
            ligaDAO.desconectar();
            System.out.println("Desconexión realizada.");

        } catch (SQLException ex) {
            System.out.println("Error SQL:" + ex.getMessage());
        }
    }

    public static void altaEntrenador(GestionDao ligaDAO, Entrenador e) throws SQLException {
        try {
            ligaDAO.insertarEntrenador(e);
            System.out.println("Entrenador dado de alta");

        } catch (ExcepcionLigaPokemon ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void altaPokemon(GestionDao ligaDAO, Pokemon p) throws SQLException {

        try {
            ligaDAO.insertarPokemon(p);
            System.out.println("Pokemon dado de alta");
            
        } catch (ExcepcionLigaPokemon ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void borrarPokemon(GestionDao ligaDAO, Pokemon p) throws SQLException{
        
        try{
            ligaDAO.borrarPokemon(p);
            System.out.println("Pokemon eliminado de la pokedex");
        }catch(ExcepcionLigaPokemon ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
    public static void modificarEntrenador(GestionDao ligaDAO, Entrenador e) throws SQLException{
        
        try{
            ligaDAO.modificarEspecialidadEntrenador(e);
            System.out.println("Especialidad modificada correctamente");
        }catch (ExcepcionLigaPokemon ex){
            System.out.println(ex.getMessage());
        }
        
    }
    /*
    Recuperamos un entrenador por su nombre y devolvemos el entrenador
    También podríamos mostrar la información dentro del método.
    */
    public static Entrenador entrenadorByNombre(GestionDao ligaDAO, String nombre) throws SQLException{
        
        Entrenador e = new Entrenador();
        
        try{
        
            e = ligaDAO.getEntrenadorByNombre(nombre);
            System.out.println(e);
            
        }catch(ExcepcionLigaPokemon ex){
            System.out.println(ex.getMessage());
        }
        return e;

    }
    
    /*Borrar un entrenador*/
    public static void borrarEntrenador(GestionDao ligaDAO, Entrenador e) throws SQLException{
        
        try{
            ligaDAO.borrarEntrenador(e);
            System.out.println("Entrenador borrado correctamente");
        }catch(ExcepcionLigaPokemon ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
    /*Subir fuerza a un pokemon*/
    public static void subirFuerzaPokemon(GestionDao ligaDAO, Pokemon p) throws SQLException{
        
        try{
            
            ligaDAO.subirFuerzaPokemon(p);
            System.out.println("Fuerza subida satisfactoriamente");
        }catch(ExcepcionLigaPokemon ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
    /*Buscar un pokemon por nombre*/
    public static Pokemon getPokemonByNombre(GestionDao ligaDAO, String nombre) throws SQLException{
        Pokemon p = new Pokemon();
        try{
            
            p = ligaDAO.getPokemonByNombre(nombre);
            System.out.println(p);

        }catch(ExcepcionLigaPokemon ex){
            System.out.println(ex.getMessage());
        }
        
        return p;
    }
    
    
}
