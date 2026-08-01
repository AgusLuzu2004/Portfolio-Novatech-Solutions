# Atajos y Accesos Rápidos

Por ahora la aplicación no tiene atajos de teclado personalizados implementados (no hay manejo de `Ctrl+N`, `Ctrl+S`, `F5`, `Supr`, etc. en ningún lado del código). Lo único disponible es:

## Login

Enter (estando en el campo de contraseña, o en cualquier lado de la ventana)

Inicia sesión — equivalente a hacer clic en "Ingresar".

---

## Tabla

Clic sobre el encabezado de una columna: ordena por esa columna.

Esto es un comportamiento por defecto de los `TableView` de JavaFX, no algo agregado a mano.

---

## Salir

Menú **Archivo → Salir** (no hay atajo de teclado asignado).

---

Si te interesa, se pueden agregar atajos reales (`Ctrl+N` para nuevo registro, `F5` para refrescar, `Supr` para eliminar, etc.) usando `Scene.getAccelerators()` o `KeyCombination` en cada controller — hoy no existen.
