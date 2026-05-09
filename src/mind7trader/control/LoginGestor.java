package control;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package mind7trader.control;

public class LoginGestor {

    public boolean autenticar(String user, String senha) {

        return user.equals("admin") && senha.equals("1234");
    }
}
