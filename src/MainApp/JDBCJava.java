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
            altaEntrenador(ligaDAO, ash);

            //Testeando insertar entrenador duplicado
            altaEntrenador(ligaDAO, ash);

            //Testeando insertar un pokemon
            altaPokemon(ligaDAO, pikachu);
            
            //Testeando insertar pokemon duplicado
            altaPokemon(ligaDAO, pikachu);
            
            //Testeando insertar un pokemon con un entrenador que no existe
            altaPokemon(ligaDAO, onix);
            
            
            //Testeando borrar Pokemon
            borrarPokemon(ligaDAO, pikachu);
            
            //Testeando modificar la especialidad de un Entrenador
            ash.setEspecialidad("Agua");
            
            
            
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
    
    
}
