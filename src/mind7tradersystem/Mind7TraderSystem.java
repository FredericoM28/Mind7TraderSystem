 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mind7tradersystem;

import javax.swing.UIManager;
import view.TelaLogin;

/**
 *
 * @author HP
 */
public class Mind7TraderSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            e.printStackTrace();
          }
        java.awt.EventQueue.invokeLater(()->{
            new view.TelaLogin().setVisible(true);
        });
    }           
}
 