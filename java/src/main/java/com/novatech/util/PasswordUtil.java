package com.novatech.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int ITERACIONES = 210_000;
    private static final int LONGITUD_CLAVE = 256;
    private static final int LONGITUD_SAL = 16;

    private static final Pattern PATRON_HASH =
            Pattern.compile("^\\d+:[A-Za-z0-9+/=]+:[A-Za-z0-9+/=]+$");

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {

    }

    public static String hashear(String contraseñaPlana) {

        if (contraseñaPlana == null || contraseñaPlana.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        byte[] sal = new byte[LONGITUD_SAL];
        RANDOM.nextBytes(sal);

        byte[] hash = pbkdf2(contraseñaPlana.toCharArray(), sal, ITERACIONES, LONGITUD_CLAVE);

        return ITERACIONES + ":" +
                Base64.getEncoder().encodeToString(sal) + ":" +
                Base64.getEncoder().encodeToString(hash);

    }

    public static boolean pareceHasheada(String valor) {
        return valor != null && PATRON_HASH.matcher(valor).matches();
    }

    public static boolean verificar(String contraseñaPlana, String hashGuardado) {

        if (contraseñaPlana == null || hashGuardado == null) {
            return false;
        }

        if (!pareceHasheada(hashGuardado)) {
            return false;
        }

        String[] partes = hashGuardado.split(":");

        try {

            int iteraciones = Integer.parseInt(partes[0]);
            byte[] sal = Base64.getDecoder().decode(partes[1]);
            byte[] hashEsperado = Base64.getDecoder().decode(partes[2]);

            byte[] hashCalculado = pbkdf2(
                    contraseñaPlana.toCharArray(),
                    sal,
                    iteraciones,
                    hashEsperado.length * 8
            );

            return comparacionSegura(hashCalculado, hashEsperado);

        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    private static byte[] pbkdf2(char[] contraseña, byte[] sal, int iteraciones, int longitudBits) {

        try {

            PBEKeySpec spec = new PBEKeySpec(contraseña, sal, iteraciones, longitudBits);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Error generando el hash de la contraseña", e);
        }

    }

    private static boolean comparacionSegura(byte[] a, byte[] b) {

        if (a.length != b.length) {
            return false;
        }

        int resultado = 0;

        for (int i = 0; i < a.length; i++) {
            resultado |= a[i] ^ b[i];
        }

        return resultado == 0;

    }

}
