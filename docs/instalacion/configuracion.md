# Configuración

## Base de datos

Modificar los datos de conexión.

Archivo:

```text
database.properties
```

Ejemplo:

```properties
db.url=jdbc:mysql://localhost:3306/novatech

db.user=root

db.password=123456
```

---

## Logs

Los registros se almacenan en:

```text
logs/
```

Archivo:

```text
novatech.log
```

---

## Exportaciones

Los archivos exportados se guardan por defecto en:

```text
exportaciones/
```

Formato soportado:

- Excel (.xlsx)
- PDF (.pdf)

---

## Backups

Las copias de seguridad se almacenan en:

```text
backups/
```

Formato:

```text
backup_YYYY_MM_DD.sql
```

---

## Tema

La aplicación permite cambiar entre:

- Tema claro
- Tema oscuro

Desde:

Configuración → Preferencias

---

## Idioma

Actualmente disponible:

- Español

Versión futura:

- Inglés
