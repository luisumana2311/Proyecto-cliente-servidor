package Server;

import Client.Request;
import Client.Response;
import Model.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author landr
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private static Map<String, Usuario> usuarios = new HashMap<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            System.out.println("Cliente conectado");
            Object obj;
            while ((obj = in.readObject()) != null) {
                if (!(obj instanceof Request)) {
                    continue;
                }
                Request req = (Request) obj;
                System.out.println("Accion recibida: " + req.getAction());
                Response resp = handleRequest(req);
                out.writeObject(resp);
                out.flush();
                System.out.println("Respuesta enviada: " + resp.getMessage());
            }
        } catch (EOFException eof) {
            System.out.println("Cliente desconectado");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
    }

    private Response handleRequest(Request req) {
        try {
            String action = req.getAction();
            Map<String, Object> p = req.getPayload();
            switch (action.toLowerCase()) {
                case "register":
                    return handleRegister(p);
                case "login":
                    return handleLogin(p);
                default:
                    return new Response(false, "Accion no se puede ejecutar" + action);
            }
        } catch (Exception ex) {
            return new Response(false, "Error interno: " + ex.getMessage());
        }
    }

    private Response handleRegister(Map<String, Object> p) {
        try {
            String nombre = (String) p.get("nombre");
            String correo = (String) p.get("correo");
            String contrasena = (String) p.get("password");
            if (usuarios.containsKey(correo)) {
                return new Response(false, "Correo ya registrado");
            }
            Usuario u = new Usuario(nombre, correo, contrasena);
            usuarios.put(correo, u);
            return new Response(true, "Usuario registrado con exito");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error al registrar: " + ex.getMessage());
        }
    }

    private Response handleLogin(Map<String, Object> p) {
        try {
            String correo = (String) p.get("correo");
            String contrasena = (String) p.get("password");
            Usuario u = usuarios.get(correo);
            if (u == null) {
                return new Response(false, "Usuario no encontrado");
            }
            if (u.getContrasena().equals(contrasena)) {
                Response r = new Response(true, "Bienvenido " + u.getNombre());
                r.setData(u);
                return r;
            } else {
                return new Response(false, "Contraseña incorrecta");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error al iniciar sesion: " + ex.getMessage());
        }
    }
}
