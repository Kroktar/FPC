package com.androidtutorialpoint.androidlogin.mDataObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by germa on 02-05-2017.
 */

public class AsistenteDB {


    static String nombreAsistente;
    static String nombreVeterinario;

    static String rutAsistente;
    static String rutVeterinario;

    public static String getNombreAsistente() {
        return nombreAsistente;
    }

    public static void setNombreAsistente(String nombre_Asistente) {
        nombreAsistente = nombre_Asistente;
    }

    public static String getRutAsistente() {
        return rutAsistente;
    }

    public static void setRutAsistente(String rut_Asistente) {
        rutAsistente = rut_Asistente;
    }

    public static String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public static void setNombreVeterinario(String nombre_Veterinario) {
        nombreVeterinario = nombre_Veterinario;
    }

    public static String getRutVeterinario() {
        return rutVeterinario;
    }

    public static void setRutVeterinario(String rut_Veterinario) {
        rutVeterinario = rut_Veterinario;
    }

    /* FAIL(no es que este malo pero el split estaba malo y lo cambie varias veces y esto quedo reold)

    static String[] listadoMascotas = new String[20];

    public static void setListadoMascotas(String unaMascota)
    {
        listadoMascotas[contador]=unaMascota;
        contador++;
    }

    public static String[] getListadoMascotas() { return listadoMascotas;}

    */

    static int contador=0;

    public static int getContador() { return contador; }

    static ArrayList<String> nombreArrayList = new ArrayList<String>();

    public static void setNombreArrayList(String unaMascota)
    {
        nombreArrayList.add(unaMascota);
        contador++;
    }

    public static ArrayList<String> getNombreArrayList() { return nombreArrayList; }
}
