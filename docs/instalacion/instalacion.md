# Instalación

## Requisitos

Antes de ejecutar la aplicación, asegurarse de tener instalado:

- Java 25 o superior
- Maven 3.9 o superior
- MySQL 8.0
- Git

## Clonar el repositorio

```bash
git clone https://github.com/AgusLuzu2004/Portfolio-Novatech-Solutions.git
```

Ingresar al proyecto:

```bash
cd Portfolio-Novatech-Solutions
```

---

## Crear la base de datos

Abrir MySQL Workbench (o la consola de MySQL) y ejecutar los scripts ubicados en la carpeta `sql/`, en este orden:

1. `creacion_bd.sql` — crea la base `novatech`
2. `tablas.sql` — crea las tablas
3. `constraints.sql` — agrega las restricciones (CHECK)
4. `importacion.sql` — carga los datos del dataset (`dataset/*.csv`)
5. `logica_bd.sql` — procedimientos, funciones y triggers
6. `vistas.sql` — vistas

El archivo `consultas.sql` no es parte de la instalación: son consultas de ejemplo para explorar la base ya cargada.

---

## Configurar la conexión

Copiar la plantilla y completar tus propios datos:

```bash
cp java/src/main/resources/db.properties.example java/src/main/resources/db.properties
```

Editar `java/src/main/resources/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/novatech
db.user=root
db.password=tu_contraseña
```

Este archivo no se sube al repositorio (está en `.gitignore`). Alternativamente, podés definir las variables de entorno `DB_URL`, `DB_USER` y `DB_PASSWORD`, que tienen prioridad sobre el archivo.

---

## Crear el usuario administrador inicial

La tabla `usuarios` arranca vacía — sin este paso no vas a poder loguearte la primera vez. Ejecutar:

```text
sql/usuario_admin_inicial.sql
```

---

## Ejecutar la aplicación

Desde la raíz del proyecto:

```bash
cd java
mvn clean javafx:run
```

O bien ejecutar `Main.java` desde el IDE (IntelliJ IDEA o Visual Studio Code).

---

## Usuario inicial

Administrador

Usuario:

```text
admin
```

Contraseña:

```text
Admin1234!
```

(Se recomienda cambiar la contraseña luego del primer inicio de sesión).
