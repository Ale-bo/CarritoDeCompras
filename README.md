# 🛒 Proyecto de Ejemplo – Carrito de Compras

**Autor:** Ivanna Alexandra Nievecela Pérez

**Fecha:** 2025-07-07

**Carrera:** Ingenieria en Ciencias de la Computación

**Materia:** Programación Orientada a Objetos


Este proyecto fue desarrollado como parte del **Periodo 66** de la asignatura **Programación Orientada a Objetos** en la Universidad Politécnica Salesiana.  
Su objetivo principal es demostrar el uso de **patrones de diseño** en una aplicación Java con interfaz gráfica Swing.

---

## 🎯 Objetivo

Implementar un sistema de carrito de compras que permita a los usuarios:

- Registrar y autenticar usuarios.
- Crear, buscar, actualizar y eliminar productos.
- Armar, modificar, listar y eliminar carritos de compra.
- Recuperar contraseña mediante preguntas de seguridad.
- Cambiar dinámicamente el idioma de la interfaz (español, inglés, francés).

Se sigue una arquitectura **desacoplada** basada en:
- **MVC** (Modelo–Vista–Controlador)
- **DAO** (Data Access Object)
- Principios **SOLID** (SRP, DIP…)

---

## 🛠️ Tecnologías

- **Java 21**
- **Swing** (javax.swing) para UI
- **IntelliJ IDEA** + plugin de diseñador de formularios
- **Maven** o **Gradle** (según configuración)

---

## 🧱 Patrones de Diseño

- **MVC** para separar presentación, lógica de negocio y datos.
- **DAO** para abstraer el acceso a datos en memoria y facilitar migración a BD.
- **Singleton**, **Factory** y **Strategy** (implícitos en el manejo de internacionalización).
- Principios **SOLID** (SRP, OCP, LSP, ISP, DIP).

---

## 📂 Estructura de Carpetas
```
CarritoDeComprasIvanna/
├── .idea/
├── src/
│   └── main/
│       ├── java/
│       │   └── ec.edu.ups/
│       │       ├── Main.java
│       │       ├── controlador/
│       │       │   ├── CarritoController.java
│       │       │   ├── ProductoController.java
│       │       │   └── UsuarioController.java
│       │       ├── dao/
│       │       │   ├── CarritoDAO.java
│       │       │   ├── ProductoDAO.java
│       │       │   ├── UsuarioDAO.java
│       │       │   └── impl/
│       │       │       ├── CarritoDAOMemoria.java
│       │       │       ├── ProductoDAOMemoria.java
│       │       │       └── UsuarioDAOMemoria.java
│       │       ├── modelo/
│       │       │   ├── Carrito.java
│       │       │   ├── ItemCarrito.java
│       │       │   ├── Producto.java
│       │       │   ├── Rol.java
│       │       │   └── Usuario.java
│       │       ├── util/
│       │       │   ├── FormateoUtil.java
│       │       │   └── MensajeInternacionalizacionHandler.java
│       │       └── vista/
│       │           ├── Carrito/
│       │           │   ├── AnadirCarritoView.java
│       │           │   ├── ListarCarritoView.java
│       │           │   ├── ActualizarCarritoView.java
│       │           │   └── EliminarCarritoView.java
│       │           ├── InicioDeSesion/
│       │           │   ├── LoginView.java
│       │           │   ├── RegistroView.java
│       │           │   ├── RecuperarContrasenaView.java
│       │           │   └── CambiarContrasenaView.java
│       │           ├── Producto/
│       │           │   ├── AnadirProductoView.java
│       │           │   ├── ListarProductoView.java
│       │           │   ├── ActualizarProductoView.java
│       │           │   └── EliminarProductoView.java
│       │           ├── Usuario/
│       │           │   ├── RegistroUsuarioView.java
│       │           │   ├── ListarUsuarioView.java
│       │           │   ├── ActualizarUsuarioView.java
│       │           │   └── EliminarUsuarioView.java
│       │           ├── LoginView.java
│       │           ├── MenuPrincipalView.java
│       │           └── MiJDesktopPane.java
│       └── resources/
│           └── mensajes/
│               ├── mensajes.properties
│               ├── mensajes_en_US.properties
│               └── mensajes_fr_FR.properties
└── pom.xml (o build.gradle) 
```
---

## 📖 Cómo ejecutar

1. **Clonar** el repositorio.
2. Importar en IntelliJ IDEA (como proyecto Maven/Gradle).
3. Ejecutar la clase `ec.edu.ups.Main`.
4. Iniciar sesión con un usuario creado o registrar uno nuevo.
5. Explorar menús de **Producto**, **Carrito** y **Usuario** en la ventana principal.

---

## 📊 Diagramas UML

![img_2.png](img_2.png)
*Diagrama de clases: muestra entidades, relaciones y métodos principales.*

![img_4.png](img_4.png)
*Diagrama de secuencia: flujo de creación de carrito de compras.*
---

## 💡 Recomendaciones

- Pruebas con distintos idiomas para ver la internacionalización.
- Sustituir las implementaciones en memoria por DAO que usen JDBC o JPA.
- Añadir validaciones adicionales en formularios (formato de correo, longitud de campos).
- Integrar pruebas unitarias con JUnit para DAO y lógica de negocio.

---

## © Créditos

Universidad Politécnica Salesiana – **Programación Orientada a Objetos** (Periodo 66)  
Desarrollado por: Ivanna Alexandra Nievecela Pérez
2025 – https://ups.edu.ec  
