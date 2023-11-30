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
    static boolean EMAIL(String email){
        String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(regx).matcher(email).matches();
    }
    
    static boolean DNI(String dni){
        return Pattern.compile("^[0-9]{7,8}+$").matcher(dni).matches();
    }
    
    static boolean RUC(String ruc){
        return Pattern.compile("^[0-9]{11}+$").matcher(ruc).matches();
    }
    
    static boolean TELEFONO(String telefono){
        return Pattern.compile("^\\+d{10}$").matcher(telefono).matches();
    }
}
