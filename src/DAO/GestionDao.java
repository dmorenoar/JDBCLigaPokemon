/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Excepciones.ExcepcionLigaPokemon;
import Models.Entrenador;
import Models.Pokemon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dmorenoar
 *
 * Contiene todos los métodos CRUD para la gestión de las tablas
 */
public class GestionDao {

    Connection conexion;

    /*MÉTODOS PARA POKEMONS*/
    public void insertarPokemon(Pokemon p) throws SQLException, ExcepcionLigaPokemon{

        if(existePokemon(p)){
            throw new ExcepcionLigaPokemon("El pokemon ya existe");
        }
        
        if(!existeEntrenador(p.getEntrenador())){
            throw new ExcepcionLigaPokemon("El entrenador no existe en la BDD");
        }
       
            String insert = "Insert into Pokemon (nombre, tipo, fuerza, entrenador) values(?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(insert);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTipo());
            ps.setDouble(3, p.getFuerza());
            ps.setString(4, p.getEntrenador().getNombre());
            ps.executeUpdate();
            ps.close();
    }
    
    public void borrarPokemon(Pokemon p) throws SQLException, ExcepcionLigaPokemon{
        if(!existePokemon(p)){
            throw new ExcepcionLigaPokemon("El pokemon no existe");
        }
        String delete = "Delete from Pokemon where nombre='" + p.getNombre() + "'";
        Statement st = conexion.createStatement();
        
        st.executeUpdate(delete);
        st.close();
    }
    
    public void subirFuerzaPokemon(Pokemon p) throws SQLException, ExcepcionLigaPokemon{
        
        if(!existePokemon(p)){
            throw new ExcepcionLigaPokemon("El pokemon no existe");
        }
        
        String update = "Update Pokemon set fuerza=? where nombre=?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setDouble(1, p.getFuerza());
        ps.setString(2, p.getNombre());
        ps.executeUpdate();
        ps.close();

    }
  
    public boolean existePokemon(Pokemon p) throws SQLException, ExcepcionLigaPokemon{
        String select = "Select * from Pokemon where nombre = ?";
        
        PreparedStatement ps = conexion.prepareStatement(select);
        ps.setString(1, p.getNombre());
        
        ResultSet rs = ps.executeQuery();
        boolean existe = false;
        
        if(rs.next()){
            existe = true;
        }
        
        rs.close();
        ps.close();
        
        return existe;
    }
    
    
    /*Buscar un pokemon por nombre*/
    public Pokemon getPokemonByNombre(String nombre) throws SQLException, ExcepcionLigaPokemon{
        Pokemon aux = new Pokemon(nombre);
        
        if(!existePokemon(aux)){
            throw new ExcepcionLigaPokemon("El pokemon que busca no existe");
        }
        
        String select = "Select * from Pokemon where nombre ='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        
        Pokemon p = new Pokemon();
        while(rs.next()){
            /*nombre tipo fuerza entrenador*/
            
            p.setNombre(rs.getString("nombre"));
            p.setTipo(rs.getString("tipo"));
            p.setFuerza(rs.getDouble("fuerza"));
            p.setEntrenador(getEntrenadorByNombre(rs.getString("entrenador"))); //Utilizamos el método del getNombreEntrenador que nos devuelve un entrenador completo
        }
        
        rs.close();
        st.close();
        
        return p;
        
    }
    
    
    
    
    /*MÉTODOS PARA ENTRENADOR*/
    /*Insert de un Entrenador*/
    
    public void insertarEntrenador(Entrenador e) throws SQLException, ExcepcionLigaPokemon {
        if (existeEntrenador(e)) {
            throw new ExcepcionLigaPokemon("El entrenador ya existe");
        } else {
            //Si el entrenador no existe lo creamos
            
            //Creamos la sentencia para insertar el usuario en SQL
            String insert = "Insert into Entrenador (nombre, telefono, sexo, edad, experiencia, especialidad) values (?,?,?,?,?,?)";
            
            //Creamos el PreparedStatement para indicarle que valores ocuparán las posiciones que le pasamos.
            PreparedStatement ps = conexion.prepareStatement(insert);
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getTelefono());
            ps.setString(3, e.getSexo());
            ps.setInt(4, e.getEdad());
            ps.setInt(5, e.getExperiencia());
            ps.setString(6, e.getEspecialidad());
            //Ejecutamos la consulta
            ps.executeUpdate();
            //Cerramos nuestro PreparedStatement
            ps.close();

        }

    }

    /*Comprovamos si el entrenador existe en la base de datos*/
    public boolean existeEntrenador(Entrenador e) throws SQLException {
        String select = "Select * from Entrenador where nombre='" + e.getNombre() + "'";
        
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        
        boolean existe = false;
        //Si el ResultSet nos devuelve algún valor es que el entrenador existe
        if(rs.next()){
            existe = true;
        }
        
        rs.close();
        st.close();
        
        return existe;
    }

    
    public Entrenador getEntrenadorByNombre(String nombre) throws SQLException, ExcepcionLigaPokemon{
        Entrenador aux = new Entrenador(nombre);
        
        if(!existeEntrenador(aux)){
            throw new ExcepcionLigaPokemon("No existe un entrenador con ese nombre");
        }
        
        String select = "Select * from Entrenador where nombre='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        
        //Creamos un entrenador vacío para poder rellenarlo con lo que leemos con el resultSet
        Entrenador e = new Entrenador();
        
        if(rs.next()){
            
          /*  nombre, telefono, sexo, edad, experiencia, especialidad */

            e.setNombre(rs.getString("nombre"));
            e.setTelefono(rs.getString("telefono"));
            e.setSexo(rs.getString("sexo"));
            e.setEdad(rs.getInt("edad"));
            e.setExperiencia(rs.getInt("experiencia"));
            e.setEspecialidad(rs.getString("especialidad"));
            
        }
        
        rs.close();
        st.close();
        return e;
        
    }
    
    /*Modificar especialidad entrenador*/    
    public void modificarEspecialidadEntrenador(Entrenador e) throws SQLException, ExcepcionLigaPokemon{
        if(!existeEntrenador(e)){
            throw new ExcepcionLigaPokemon("El pokemon no existe");
        }
        
        String update = "update Entrenador set especialidad = ? where nombre = ?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setString(1, e.getEspecialidad());
        ps.setString(2, e.getNombre());
        ps.executeUpdate();
        ps.close();
 
    }

    /*Borrar un entrenador*/
    public void borrarEntrenador(Entrenador e) throws SQLException, ExcepcionLigaPokemon{
        
        if(!existeEntrenador(e)){
            throw new ExcepcionLigaPokemon("El entrenador a borrar no existe");
        }
        
        String select = "Delete from Entrenador where nombre= '" + e.getNombre() + "'";
        Statement st = conexion.createStatement();
        st.executeUpdate(select);
        st.close();
        
    }
    
  
    
    
    
    
    /*Métodos conexión y desconexión*/
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ligapokemon"; //Conexión con el driver JDBC
        String user = "root";
        String pass = "";
        conexion = DriverManager.getConnection(url, user, pass);
    }

    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }

    }

}
