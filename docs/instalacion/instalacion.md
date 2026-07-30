# Instalación

## Requisitos

Antes de ejecutar la aplicación, asegurarse de tener instalado:

- Java 21 o superior
- Maven 3.9 o superior
- MySQL 8.0
- Git

## Clonar el repositorio

```bash
git clone https://github.com/TU-USUARIO/Portfolio-Novatech-Solutions.git
```

Ingresar al proyecto:

```bash
cd Portfolio-Novatech-Solutions
```

---

## Crear la base de datos

Abrir MySQL Workbench y ejecutar:

```sql
CREATE DATABASE novatech;
```

Luego ejecutar los scripts ubicados en la carpeta:

```text
sql/
```

En el siguiente orden:

1. 01_create_tables.sql
2. 02_insert_data.sql
3. 03_views.sql
4. 04_procedures.sql
5. 05_triggers.sql

---

## Configurar la conexión

Editar el archivo:

```text
src/main/resources/database.properties
```

Ejemplo:

```properties
db.url=jdbc:mysql://localhost:3306/novatech
db.user=root
db.password=123456
```

---

## Ejecutar la aplicación

Desde la raíz del proyecto:

```bash
mvn clean javafx:run
```

O bien ejecutar desde el IDE (IntelliJ IDEA o Visual Studio Code).

---

## Usuario inicial

Administrador

Usuario:

```text
admin
```

Contraseña:

```text
admin123
```

(Se recomienda cambiar la contraseña luego del primer inicio de sesión).
