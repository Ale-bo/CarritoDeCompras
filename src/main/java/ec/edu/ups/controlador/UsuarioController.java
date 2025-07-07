package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.InicioDeSesion.RegistrarUsuarioView;
import ec.edu.ups.vista.Usuario.ListarUsuarioView;
import ec.edu.ups.vista.Usuario.EliminarUsuarioView;
import ec.edu.ups.vista.Usuario.ActualizarUsuarioView;
import ec.edu.ups.vista.InicioDeSesion.RecuperarContraseñaView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.lang.NumberFormatException;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final RegistrarUsuarioView registrarUsuarioView;
    private final ListarUsuarioView listarUsuarioView;
    private final EliminarUsuarioView eliminarUsuarioView;
    private final ActualizarUsuarioView actualizarUsuarioView;
    private final RecuperarContraseñaView recuperarContraseñaView;
    private List<String> preguntasAleatorias;
    private MensajeInternacionalizacionHandler mensajeHandler;


    public UsuarioController(UsuarioDAO usuarioDAO,
                             LoginView loginView,
                             RegistrarUsuarioView registrarUsuarioView,
                             ListarUsuarioView listarUsuarioView,
                             EliminarUsuarioView eliminarUsuarioView,
                             ActualizarUsuarioView actualizarUsuarioView,
                             RecuperarContraseñaView recuperarContraseñaView
                             ) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.registrarUsuarioView = registrarUsuarioView;
        this.listarUsuarioView = listarUsuarioView;
        this.eliminarUsuarioView = eliminarUsuarioView;
        this.actualizarUsuarioView = actualizarUsuarioView;
        this.recuperarContraseñaView = recuperarContraseñaView;
        inicializarPreguntas();
        configurarEventos();
    }

    // Configura los eventos para los botones de la vista
    private void configurarEventos() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticarUsuario());
        loginView.getBtnRegistrarse().addActionListener(e -> abrirRegistro());

        registrarUsuarioView.getRegistrarButton().addActionListener(e -> registrarUsuario());
        registrarUsuarioView.getCancelarButton().addActionListener(e -> cancelarRegistro());

        listarUsuarioView.getBtnBuscar().addActionListener(e -> buscarUsuarios());
        listarUsuarioView.getBtnRefrescar().addActionListener(e -> cargarUsuarios());

        eliminarUsuarioView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        eliminarUsuarioView.getBtnEliminar().addActionListener(e -> eliminarUsuarioSeleccionado());

        actualizarUsuarioView.getBtnBuscar().addActionListener(e -> cargarUsuariosParaActualizar());
        actualizarUsuarioView.getBtnActualizar().addActionListener(e -> actualizarUsuario());

        recuperarContraseñaView.addRecuperarListener(e -> recuperarContraseña());
    }



    private void inicializarPreguntas() {

        // Definir las preguntas de seguridad
        List<String> todasLasPreguntas = new ArrayList<>(Arrays.asList(
                "1. ¿Cuál es el nombre de tu primer Novio?",
                "2. ¿Cuál es tu color Favorito?",
                "3. ¿Qué comida te gusta más?",
                "4. ¿Cómo se llama tu primera mascota?",
                "5. ¿Cuál es tu bebida favorita?",
                "6. ¿Cuál es el nombre de tu Escuela?",
                "7. ¿Cómo se llama tu mejor amigo?",
                "8. ¿Cuál es el segundo nombre de tu Madre?",
                "9. ¿De dónde son tus abuelos?",
                "10. ¿Qué ciudad te gustaría visitar?"
        ));


        // Mezclar las preguntas de forma aleatoria
        Collections.shuffle(todasLasPreguntas);

        // Tomar las primeras 3 preguntas aleatorias
        preguntasAleatorias = todasLasPreguntas.subList(0, 3);

        // Mostrar las preguntas en la vista
        registrarUsuarioView.setPregunta1(preguntasAleatorias.get(0));
        registrarUsuarioView.setPregunta2(preguntasAleatorias.get(1));
        registrarUsuarioView.setPregunta3(preguntasAleatorias.get(2));
    }

    private List<String> obtenerPreguntasSeguridad(MensajeInternacionalizacionHandler mensajeHandler) {
        List<String> todasLasPreguntas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String pregunta = mensajeHandler.get("preguntas.seguridad." + i);
            System.out.println("Pregunta " + i + ": " + pregunta);
            todasLasPreguntas.add(pregunta);
        }
        return todasLasPreguntas;
    }


    public void actualizarIdioma(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        System.out.println("Actualizando idioma en UsuarioController");
        inicializarPreguntas();
    }




    // Método para autenticar al usuario
    private void autenticarUsuario() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = new String(loginView.getTxtContrasenia().getPassword());

        // Intentar autenticar al usuario
        Usuario usuario = usuarioDAO.autenticar(username, contrasenia);

        if (usuario == null) {
            // Si no se encuentra el usuario, mostrar mensaje de error
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        } else {
            // Si las credenciales son correctas, cerrar la ventana de login
            loginView.dispose();
            // Aquí podrías abrir la vista principal, por ejemplo:
            // abrirVistaPrincipal();
            JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getUsername());
        }
    }

    // Método para abrir la vista de registro de usuario
    private void abrirRegistro() {
        registrarUsuarioView.setVisible(true);  // Mostrar la ventana de registro
    }

    // Método para registrar un nuevo usuario con preguntas de seguridad aleatorias
    private void registrarUsuario() {
        String nombre = registrarUsuarioView.getTextnombre().getText();
        String correo = registrarUsuarioView.getTextcorreo().getText();
        String celular = registrarUsuarioView.getTextcelular().getText();
        String username = registrarUsuarioView.getTextususario().getText();
        String contrasenia = new String(registrarUsuarioView.getPasswordcontrasena().getPassword());
        String confirmarContrasenia = new String(registrarUsuarioView.getPasswordconfcontrasena().getPassword());
        String dia = registrarUsuarioView.getTextDia().getText();
        String mes = registrarUsuarioView.getTextMes().getText();
        String anio = registrarUsuarioView.getTextAnio().getText();

        String respuestaSeguridad = registrarUsuarioView.getRespuesta1();
        String respuestaSeguridad2 = registrarUsuarioView.getRespuesta2();
        String respuestaSeguridad3 = registrarUsuarioView.getRespuesta3();

        // validar campos vacios
        if(nombre.isEmpty() || dia.isEmpty() || mes.isEmpty() || anio.isEmpty() || correo.isEmpty() || celular.isEmpty() || username.isEmpty() || contrasenia.isEmpty() || confirmarContrasenia.isEmpty() || respuestaSeguridad.isEmpty() || respuestaSeguridad2.isEmpty() || respuestaSeguridad3.isEmpty()){
            registrarUsuarioView.mostrarMensaje("Debes llenar todos los campos.");
            return;
        }

        /// validar fecha

        try {
            int nuemroDia= Integer.parseInt(dia);
            int numeroMes= Integer.parseInt(mes);
            int numeroAnio= Integer.parseInt(anio);

            if(nuemroDia<1 || nuemroDia>31 ){
                registrarUsuarioView.mostrarMensaje("Dia invalido");
                return;
            }
            if(numeroMes<1 || numeroMes>12){
                registrarUsuarioView.mostrarMensaje("Mes invalido");
                return;
            }
            if(numeroAnio<1900 || numeroAnio>2020){
                registrarUsuarioView.mostrarMensaje(("Anio invalido"));
                return;
            }

        } catch (NumberFormatException e){
            registrarUsuarioView.mostrarMensaje("La fecha es incorrecta.");
            return;
        }

        // Validar que las contraseñas coincidan
        if (!contrasenia.equals(confirmarContrasenia)) {
            registrarUsuarioView.mostrarMensaje("Las contraseñas no coinciden.");
            return;
        }

        //validar formato del correo
        if(!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            registrarUsuarioView.mostrarMensaje("El correo no es valido.");
            return;
        }

        //validar que el usuario no exista
        if(usuarioDAO.buscarPorUsername(username)!=null){
            registrarUsuarioView.mostrarMensaje("El usuario ya existe.");
            return;
        }

        //formar la fecha completa
        String fechaNacimiento = dia + "/" + mes + "/" + anio;


        // Selección aleatoria de preguntas de seguridad usando Random (Fisher-Yates)
        List<String> preguntas = new ArrayList<>(Arrays.asList(
               " 1. ¿Cuál es el nombre de tu primer Novio?",
                       " 2. ¿Cuál es tu color Facorito?",
                        "3. ¿Qué comida te gusta más?",
                        "4. ¿Cómo se llama tu primera mascota?",
                        "5. ¿Cuál es tu bebida favorita?",
                        "6. ¿Cuál es el nombre de tu Escuela?",
                        "7. ¿Cómo se llama tu mejor amigo?",
                        "8. ¿Cuál es el segundo nombre de tu Madre?",
                        "9. ¿De dónde son tus abuelos?",
                        "10. ¿Qué ciudad te gustaria visitar?"
        ));

        Random random = new Random();
        preguntasAleatorias = new ArrayList<>();

        // Seleccionar 3 preguntas aleatorias
        while (preguntasAleatorias.size() < 3) {
            int indice = random.nextInt(preguntas.size());
            String pregunta = preguntas.get(indice);
            if (!preguntasAleatorias.contains(pregunta)) {
                preguntasAleatorias.add(pregunta);
            }
        }

        // Cuando el usuario haga clic en registrar, obtener las respuestas
        String respuesta1 = registrarUsuarioView.getRespuesta1();
        String respuesta2 = registrarUsuarioView.getRespuesta2();
        String respuesta3 = registrarUsuarioView.getRespuesta3();

        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setContrasenia(contrasenia);
        usuario.setRol(Rol.USUARIO);
        usuario.setNombre(nombre);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setCorreo(correo);
        usuario.setCelular(celular);
        usuario.setPreguntasSeguridad(preguntasAleatorias); // Variable de clase que contiene las preguntas
        usuario.setRespuestasSeguridad(Arrays.asList(respuesta1, respuesta2, respuesta3));

        //guardar usuario
        try {
            usuarioDAO.crear(usuario);
            registrarUsuarioView.mostrarMensaje("Usuario registrado exitosamente");
            registrarUsuarioView.limpiarCampos();
        } catch (Exception e) {
            registrarUsuarioView.mostrarMensaje("Error al registrar el usuario: " + e.getMessage());
        }

        // Validar que todas las respuestas se hayan proporcionado
        if(respuesta1 == null || respuesta1.isEmpty() ||
                respuesta2 == null || respuesta2.isEmpty() ||
                respuesta3 == null || respuesta3.isEmpty()) {
            registrarUsuarioView.mostrarMensaje("Debes responder las tres preguntas de seguridad");
            return;
        }
        // Mostrar las preguntas en la vista
        registrarUsuarioView.setPregunta1(preguntasAleatorias.get(0));
        registrarUsuarioView.setPregunta2(preguntasAleatorias.get(1));
        registrarUsuarioView.setPregunta3(preguntasAleatorias.get(2));

        // cuando crees un usario, gurada las preguntas y respuestas
        usuario.setPreguntasSeguridad(preguntasAleatorias);
        usuario.setRespuestasSeguridad(Arrays.asList(respuesta1, respuesta2, respuesta3));

        //mostar mensaje de exito y limpiar campos
        registrarUsuarioView.limpiarCampos();
        registrarUsuarioView.mostrarMensaje("Usuario registrado exitosamente.");
    }


    // Método para cancelar el registro y limpiar la vista
    private void cancelarRegistro() {
        registrarUsuarioView.limpiarCampos();
        registrarUsuarioView.setVisible(false); // Ocultar la vista de registro
    }

    // Método para recuperar la contraseña utilizando las preguntas de seguridad
    private void recuperarContraseña() {
        String username = recuperarContraseñaView.getUsername();

        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            recuperarContraseñaView.mostrarMensaje("Usuario no encontrado.");
            return;
        }

        // Mostrar las preguntas de seguridad del usuario
        List<String> preguntasUsuario = usuario.getPreguntasSeguridad();
        recuperarContraseñaView.setPreguntasSeguridad(new ArrayList<>(preguntasUsuario));

        // Obtener las respuestas ingresadas
        String respuesta1 = recuperarContraseñaView.getRespuesta1();
        String respuesta2 = recuperarContraseñaView.getRespuesta2();
        String respuesta3 = recuperarContraseñaView.getRespuesta3();

        // Validar que se hayan ingresado todas las respuestas
        if (respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
            recuperarContraseñaView.mostrarMensaje("Debe completar todas las respuestas.");
            return;
        }

        // Obtener las respuestas almacenadas del usuario
        List<String> respuestasCorrectas = usuario.getRespuestasSeguridad();

        // Validar las respuestas
        if (respuestasCorrectas != null && respuestasCorrectas.size() >= 3) {
            boolean respuestasCorrectas =
                    !(!respuesta1.equals(respuestasCorrectas.get(0)) ||
                            !respuesta2.equals(respuestasCorrectas.get(1)) ||
                            !respuesta3.equals(respuestasCorrectas.get(2)));

            if (respuestasCorrectas) {
                // Validar la nueva contraseña
                String nuevaContraseña = recuperarContraseñaView.getNuevaContraseña();
                String confirmarContraseña = recuperarContraseñaView.getConfirmarContraseña();

                if (nuevaContraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                    recuperarContraseñaView.mostrarMensaje("Debe ingresar la nueva contraseña.");
                    return;
                }

                if (!nuevaContraseña.equals(confirmarContraseña)) {
                    recuperarContraseñaView.mostrarMensaje("Las contraseñas no coinciden.");
                    return;
                }

                // Actualizar la contraseña
                usuario.setContrasenia(nuevaContraseña);
                usuarioDAO.actualizar(usuario);

                recuperarContraseñaView.mostrarMensaje("Contraseña actualizada exitosamente.");
                recuperarContraseñaView.dispose(); // Cerrar la ventana
            } else {
                recuperarContraseñaView.mostrarMensaje("Las respuestas de seguridad son incorrectas.");
            }
        } else {
            recuperarContraseñaView.mostrarMensaje("Error en las preguntas de seguridad del usuario.");
        }
    }


    // Método para validar las respuestas de las preguntas de seguridad
    private boolean validarRespuestas(String respuesta1, String respuesta2, String respuesta3) {
        return true;
    }

    // Método para cargar todos los usuarios
    private void cargarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        DefaultTableModel model = (DefaultTableModel) listarUsuarioView.getTableUsuarios().getModel();
        model.setRowCount(0);  // Limpiar la tabla antes de agregar los usuarios
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{usuario.getUsername(), usuario.getRol()});
        }
    }

    // Método para buscar usuarios
    private void buscarUsuarios() {
        String criterio = listarUsuarioView.getTxtBuscar().getText();
        List<Usuario> usuarios = usuarioDAO.listarPorRol(Rol.USUARIO);  // Buscar por rol
        DefaultTableModel model = (DefaultTableModel) listarUsuarioView.getTableUsuarios().getModel();
        model.setRowCount(0);  // Limpiar la tabla antes de mostrar los resultados
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().contains(criterio)) {
                model.addRow(new Object[]{usuario.getUsername(), usuario.getRol()});
            }
        }
    }

    // Método para buscar un usuario por criterio (nombre de usuario o rol)
    public void buscarUsuarioParaEliminar() {
        // Obtener el criterio de búsqueda (por ejemplo, nombre de usuario o rol)
        String criterio = eliminarUsuarioView.getTxtBuscar().getText();
        String filtro = (String) eliminarUsuarioView.getCbxFiltro().getSelectedItem();

        List<Usuario> usuarios = new ArrayList<>();

        if (filtro.equals("Username")) {
            // Buscar usuarios por nombre de usuario
            Usuario usuario = usuarioDAO.buscarPorUsername(criterio);
            if (usuario != null) {
                usuarios.add(usuario);  // Si se encuentra, agregarlo a la lista
            }
        } else if (filtro.equals("Rol")) {
            // Buscar usuarios por rol
            if (criterio.equalsIgnoreCase("USUARIO")) {
                usuarios = usuarioDAO.listarPorRol(Rol.USUARIO);  // Filtrar por rol 'USUARIO'
            } else if (criterio.equalsIgnoreCase("ADMINISTRADOR")) {
                usuarios = usuarioDAO.listarPorRol(Rol.ADMINISTRADOR);  // Filtrar por rol 'ADMINISTRADOR'
            }
        }

        // Limpiar la tabla antes de cargar los resultados
        DefaultTableModel model = (DefaultTableModel) eliminarUsuarioView.getTableUsuarios().getModel();
        model.setRowCount(0);  // Limpiar la tabla

        // Agregar los usuarios encontrados a la tabla
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{usuario.getUsername(), usuario.getRol()});
        }

        // Si no se encuentra ningún usuario, mostrar un mensaje
        if (usuarios.isEmpty()) {
            eliminarUsuarioView.mostrarMensaje("No se encontraron usuarios con el criterio especificado.");
        }
    }

    // Método para eliminar un usuario seleccionado
    private void eliminarUsuarioSeleccionado() {
        int row = eliminarUsuarioView.getTableUsuarios().getSelectedRow();
        if (row != -1) {
            String username = (String) eliminarUsuarioView.getTableUsuarios().getValueAt(row, 0);
            usuarioDAO.eliminar(username);
            eliminarUsuarioView.mostrarMensaje("Usuario eliminado exitosamente.");
            buscarUsuarioParaEliminar();  // Refrescar la lista de usuarios
        } else {
            eliminarUsuarioView.mostrarMensaje("Selecciona un usuario para eliminar.");
        }
    }

    // Método para cargar los datos del usuario para actualizar
    private void cargarUsuariosParaActualizar() {
        int row = actualizarUsuarioView.getTblUsuarios().getSelectedRow();
        if (row != -1) {
            String username = (String) actualizarUsuarioView.getTblUsuarios().getValueAt(row, 0);
            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario != null) {
                actualizarUsuarioView.getTxtUsername().setText(usuario.getUsername());
                actualizarUsuarioView.getTxtPassword().setText(usuario.getContrasenia());
                actualizarUsuarioView.getTxtPassword().setText(usuario.getContrasenia());
                actualizarUsuarioView.getCbxRol().setSelectedItem(usuario.getRol().toString());
            }
        }
    }

    // Método para actualizar un usuario
    private void actualizarUsuario() {
        String username = actualizarUsuarioView.getTxtUsername().getText();
        String contrasenia = new String(actualizarUsuarioView.getTxtPassword().getPassword());
        String confirmarContrasenia = new String(actualizarUsuarioView.getPasswordconfcontrasenia().getPassword());

        if (!contrasenia.equals(confirmarContrasenia)) {
            actualizarUsuarioView.mostrarMensaje("Las contraseñas no coinciden.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            usuario.setContrasenia(contrasenia);
            usuarioDAO.actualizar(usuario);
            actualizarUsuarioView.mostrarMensaje("Usuario actualizado exitosamente.");
        } else {
            actualizarUsuarioView.mostrarMensaje("Usuario no encontrado.");
        }
    }
}


