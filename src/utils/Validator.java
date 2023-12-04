/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.regex.Pattern;

/**
 *
 * @author dell
 */
public class Validator {
    public static void EMAIL (String email) throws Exception {
        String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if(!Pattern.compile(regx).matcher(email).matches())
            throw new Exception("Ingrese un email válido");
    }
    
    public static void DNI(String dni) throws Exception {
        if(!Pattern.compile("^[0-9]{7,8}+$").matcher(dni).matches())
            throw new Exception("Ingrese un DNI válido");
    }
    
    public static void RUC(String ruc) throws Exception {
        if(!Pattern.compile("^[0-9]{11}+$").matcher(ruc).matches())
            throw new Exception("Ingrese un RUC válido");
    }
    
    public static void TELEFONO(String telefono) throws Exception {
        if(!Pattern.compile("^(\\+34|0034|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}").matcher(telefono).matches())
            throw new Exception("Ingrese un teléfono válido");
    }
}
