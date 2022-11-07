package Principal;

import javax.swing.*;
import java.awt.*;
import java.util.regex.*;

public class cDialogoNomArch extends JDialog{

    JLabel etNom;
    JTextField textF;
    String name;
    JButton aceptar,cancelar;
    public cDialogoNomArch(interfaz ref,boolean modal)
    {
        super(ref,modal);
        setTitle("Elige el nombre del archivo");
        setLayout(new FlowLayout());
        setLocationRelativeTo(ref);
        setSize(450,150);

        name="default";

        etNom=new JLabel("Nombre del archivo: ");
        textF=new JTextField(40);

        aceptar=new JButton("Aceptar");
        cancelar=new JButton("Cancelar");


        aceptar.addActionListener(e->{

            String temp=textF.getText();
            if(temp.matches("\\d?\\w+"))
                name=temp;
            else
                System.out.println("Expresion regex fallo");
            dispose();
        });

        cancelar.addActionListener(e->{
            dispose();
        });


        add(etNom);
        add(textF);
        add(aceptar);
        add(cancelar);

        setVisible(false);
    }

    public String mostrar()
    {
        setVisible(true);
        return name;
    }
}

