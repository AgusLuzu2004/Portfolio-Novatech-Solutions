package com.novatech.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class PasswordUtilTest {

    @Test
    public void hashearDebeRechazarContraseñasNulasOVacías() {
        assertThrows(IllegalArgumentException.class, () -> PasswordUtil.hashear(null));
        assertThrows(IllegalArgumentException.class, () -> PasswordUtil.hashear(""));
    }

    @Test
    public void pareceHasheadaDebeValidarElFormatoEsperado() {
        assertTrue(PasswordUtil.pareceHasheada("210000:YWJj:ZGVm"));
        assertFalse(PasswordUtil.pareceHasheada(null));
        assertFalse(PasswordUtil.pareceHasheada("texto-plano"));
        assertFalse(PasswordUtil.pareceHasheada("1:2"));
    }

    @Test
    public void verificarDebeAceptarHashesCorrectosYRechazarLosIncorrectos() {
        String contraseña = "MiContraseña123";
        String hash = PasswordUtil.hashear(contraseña);

        assertTrue(PasswordUtil.verificar(contraseña, hash));
        assertFalse(PasswordUtil.verificar("otraClave", hash));
        assertFalse(PasswordUtil.verificar(contraseña, null));
        assertFalse(PasswordUtil.verificar(null, hash));
        assertFalse(PasswordUtil.verificar(contraseña, "hash-invalido"));
    }
}
