# 🛒 Sistema de Carrito de Compras (Proyecto Final)

**Autor:** Ivanna Alexandra Nievecela Pérez
**Fecha:** 2025-07-07 (Actualizado: 18/07/2025)
**Carrera:** Ingeniería en Ciencias de la Computación
**Materia:** Programación Orientada a Objetos

Este proyecto fue desarrollado como parte del **Periodo 66** de la asignatura **Programación Orientada a Objetos** en la Universidad Politécnica Salesiana. Implementa un sistema de carrito de compras con interfaz gráfica Swing, enfocado en una arquitectura **MDI (Multiple Document Interface)**, y cumple con requisitos avanzados de validación, persistencia de datos configurable, y estándares de calidad de código.

---

## 🎯 Objetivo

Implementar un sistema de carrito de compras de escritorio robusto y configurable que permita:

* Registrar y autenticar usuarios con validaciones avanzadas (cédula, contraseña segura).
* Gestionar productos (CRUD).
* Gestionar carritos de compra (creación, adición de ítems, modificación, eliminación, listado).
* Recuperar contraseña mediante preguntas de seguridad.
* Ofrecer persistencia de datos configurable (en memoria, archivos de texto, archivos binarios).
* Generar un ejecutable autónomo (`.jar`) y documentación técnica (`Javadoc`).
* Mantener una interfaz de usuario amigable y adaptable (internacionalización, gráficos personalizados, iconos).

---

## ✨ Funcionalidades Implementadas

El sistema extiende las funcionalidades de la práctica anterior con las siguientes mejoras clave:

### **Validaciones Avanzadas:**

* **Validación de Cédula Ecuatoriana:** El username de los usuarios (en el registro) debe ser una cédula ecuatoriana válida, verificando el último dígito.
* **Validación de Campos Obligatorios y Tipos de Datos:** Todos los campos requeridos en los formularios están validados para asegurar que no estén vacíos y que contengan el tipo de dato correcto (ej. números para teléfono, formato de email, fechas válidas).
* **Validación de Contraseñas Seguras:** Las contraseñas deben cumplir con:
    * Mínimo 6 caracteres.
    * Al menos una letra mayúscula.
    * Al menos una letra minúscula.
    * Al menos uno de los caracteres especiales: `@`, `_`, `-`.
* **Manejo de Excepciones Controladas:** Los errores de validación y de lógica son gestionados mediante excepciones propias (`ValidacionException`) y de Java, mostrando mensajes claros al usuario.

### **Almacenamiento Configurable:**

Al iniciar sesión, el usuario puede seleccionar el tipo de almacenamiento de datos:

* **En Memoria (Predeterminado):** Los datos se mantienen solo durante la sesión de la aplicación.
* **Archivos de Texto:** Los datos se guardan y leen de archivos de texto plano (`.txt`) en una ruta especificada por el usuario.
* **Archivos Binarios:** Los datos se guardan y leen de archivos binarios (`.dat`) en una ruta especificada por el usuario, lo que permite una serialización/deserialización de objetos más eficiente.

### **Gestión de Entidades (Usuarios, Productos, Carritos):**

* **Usuarios:** Registro completo (nombres, fecha de nacimiento, correo, celular), autenticación, recuperación de contraseña por preguntas de seguridad, y gestión CRUD completa (Crear, Listar, Actualizar, Eliminar) para administradores. Los usuarios normales pueden modificar sus propios datos (contraseña).
* **Productos:** Gestión CRUD completa (Crear, Listar, Actualizar, Eliminar).
* **Carritos:** Creación de carritos con código asignado por el usuario y fecha, adición de productos al carrito, listado de ítems, cálculo de subtotal/IVA/total, y gestión CRUD completa de carritos.

### **Internacionalización (i18n):**

* La interfaz de usuario está disponible en múltiples idiomas (Español, Inglés, Francés), configurable desde la pantalla de login.

### **Personalización Visual:**

* Uso de gráficos personalizados en el fondo del JDesktopPane y la incorporación de ImageIcons en los botones para una mejor experiencia de usuario.

---

## 🛠️ Tecnologías Utilizadas

* **Java 21:** Lenguaje de programación principal.
* **Swing (javax.swing):** Para la construcción de la interfaz gráfica de usuario (GUI).
* **IntelliJ IDEA:** IDE principal de desarrollo, con soporte para el diseñador de formularios de Swing.
* **Apache Maven:** Herramienta de gestión de proyectos y construcción para automatizar la compilación, pruebas y empaquetado (`.jar`).

---

## 🧱 Patrones de Diseño y Principios

El sistema sigue una arquitectura **desacoplada** y modular, adhiriéndose a los siguientes patrones y principios:

* **MVC (Modelo – Vista – Controlador):** Para una clara separación de la lógica de negocio, la presentación y la interacción del usuario.
* **DAO (Data Access Object):** Para abstraer y desacoplar la lógica de acceso a los datos de la lógica de negocio. Se han implementado DAOs para almacenamiento en memoria, archivos de texto y archivos binarios.
* **Principios SOLID:** Se han aplicado principios como el Principio de Responsabilidad Única (SRP) y la Inversión de Dependencias (DIP) para un diseño de código más mantenible y extensible.
* **Estrategia (Strategy) / Singleton:** Implícitos en el manejo de la internacionalización (`MensajeInternacionalizacionHandler`) y la configuración de DAOs.

---

## 📂 Estructura de Carpetas
CarritoDeCompras/
├── .idea/                              
├── javadoc/                                
├── mis_datos_carrito/                     
├── src/
│   └── main/
│       ├── java/
│       │   └── ec.edu.ups/
│       │       ├── controlador/            
│       │       │   ├── CarritoController.java
│       │       │   ├── ProductoController.java
│       │       │   └── UsuarioController.java
│       │       ├── dao/                    
│       │       │   ├── impl/               
│       │       │   │   ├── CarritoDAOBinario.java
│       │       │   │   ├── CarritoDAOMemoria.java
│       │       │   │   ├── CarritoDAOTexto.java
│       │       │   │   ├── ProductoDAOBinario.java
│       │       │   │   ├── ProductoDAOMemoria.java
│       │       │   │   ├── ProductoDAOTexto.java
│       │       │   │   ├── UsuarioDAOBinario.java
│       │       │   │   ├── UsuarioDAOMemoria.java
│       │       │   │   └── UsuarioDAOTexto.java
│       │       │   ├── CarritoDAO.java     
│       │       │   ├── ProductoDAO.java    
│       │       │   └── UsuarioDAO.java     
│       │       ├── excepciones/            
│       │       │   └── ValidacionException.java
│       │       ├── modelo/                 
│       │       │   ├── Carrito.java
│       │       │   ├── ItemCarrito.java
│       │       │   ├── PreguntaSeguridad.java
│       │       │   ├── Producto.java
│       │       │   ├── Rol.java
│       │       │   └── Usuario.java
│       │       ├── util/                  
│       │       │   ├── CedulaValidator.java
│       │       │   ├── FormateoUtil.java
│       │       │   ├── MensajeInternacionalizacionHandler.java
│       │       │   └── PasswordValidator.java
│       │       └── vista/                  
│       │           ├── Carrito/
│       │           │   ├── ActualizarCarritoView.java
│       │           │   ├── ActualizarCarritoView.form
│       │           │   ├── AnadirCarritoView.java
│       │           │   ├── AnadirCarritoView.form
│       │           │   ├── EliminarCarritoView.java
│       │           │   ├── EliminarCarritoView.form
│       │           │   ├── ListarCarritoView.java
│       │           │   └── ListarCarritoView.form
│       │           ├── InicioDeSesion/
│       │           │   ├── CambiarContrasenaView.java
│       │           │   ├── CambiarContrasenaView.form
│       │           │   ├── RecuperarContrasenaView.java
│       │           │   ├── RecuperarContrasenaView.form
│       │           │   ├── RegistrarUsuarioView.java
│       │           │   └── RegistrarUsuarioView.form
│       │           ├── Producto/
│       │           │   ├── ActualizarProductoView.java
│       │           │   ├── ActualizarProductoView.form
│       │           │   ├── AnadirProductoView.java
│       │           │   ├── AnadirProductoView.form
│       │           │   ├── EliminarProductoView.java
│       │           │   ├── EliminarProductoView.form
│       │           │   ├── ListarProductoView.java
│       │           │   └── ListarProductoView.form
│       │           ├── Usuario/
│       │           │   ├── ActualizarUsuarioView.java
│       │           │   ├── ActualizarUsuarioView.form
│       │           │   ├── EliminarUsuarioView.java
│       │           │   ├── EliminarUsuarioView.form
│       │           │   ├── ListarUsuarioView.java
│       │           │   ├── ListarUsuarioView.form
│       │           │   ├── RegistroView.java
│       │           │   └── RegistroView.form
│       │           ├── LoginView.java
│       │           ├── LoginView.form
│       │           ├── Main.java             
│       │           ├── MenuPrincipalView.java
│       │           └── MiJDesktopPane.java
│       └── resources/                      
│           ├── images/                   
│           └── mensajes/                  
│               ├── mensajes.properties
│               ├── mensajes_en_US.properties
│               └── mensajes_fr_FR.properties
├── target/                                
│   ├── classes/
│   ├── generated-sources/
│   ├── maven-archiver/
│   ├── maven-status/
│   └── CarritoDeCompras-1.0-SNAPSHOT.jar   
├── .gitignore                              
├── pom.xml                                 
└── README.md     
´´´