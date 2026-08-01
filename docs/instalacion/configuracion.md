# Configuración

## Base de datos

Modificar los datos de conexión.

Archivo:

```text
java/src/main/resources/db.properties
```

(copiado de `db.properties.example`; no se sube al repositorio)

Ejemplo:

```properties
db.url=jdbc:mysql://localhost:3306/novatech

db.user=root

db.password=tu_contraseña
```

Alternativa: las variables de entorno `DB_URL`, `DB_USER` y `DB_PASSWORD` tienen prioridad sobre el archivo.

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

Al exportar (Excel o PDF), la aplicación abre un cuadro de diálogo para elegir dónde guardar el archivo — no hay una carpeta fija por defecto.

Formato soportado:

- Excel (.xlsx)
- PDF (.pdf)

---

## Backups

Al crear o restaurar un backup, la aplicación abre un cuadro de diálogo para elegir el archivo — no hay una carpeta ni un nombre de archivo fijo.

---

## Tema

La pantalla de Preferencias permite elegir entre:

- Tema claro
- Tema oscuro

Desde:

Administración → Preferencias

---

## Idioma

El combo de Preferencias permite seleccionar:

- Español
- English

Por ahora esta selección solo se guarda como preferencia; la aplicación no traduce su interfaz automáticamente todavía.
