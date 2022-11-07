package Principal;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;
import java.io.*;
import java.net.*;

public class interfaz extends JFrame{

    JTextArea editorT,consolaT;
    JScrollPane editScroll, conScroll;
    JPanel editPanel,consPanel;
    JMenuBar menuB,runB;
    JMenu menuA;
    JMenuItem opcAbrirArchivo,opcGuardarArchivoActual,opcGuardarArchivoNuevo,opcNuevoArchivo,opcSalir;
    JButton runProgram;

    JFileChooser selectorArchivo;

    public interfaz()
    {
        setTitle("COMPILADOR");
        setSize(1000,850);
        setLocationRelativeTo(this);
        setLayout(new GridLayout(2,1));

        //Elementos de la barra de menu
        menuB=new JMenuBar();
        setJMenuBar(menuB);
        menuA=new JMenu("Archivo");

        //Boton menu run
        runB=new JMenuBar();
        runProgram=new JButton();
        try {
            Image imagen= ImageIO.read(new File("src/play_boton.png"));
            runProgram.setIcon(new ImageIcon(imagen));
        } catch (IOException e) {
            e.printStackTrace();
        }

        opcAbrirArchivo=new JMenuItem("Abrir");
        opcGuardarArchivoActual=new JMenuItem("Guardado Rapido");
        opcGuardarArchivoNuevo=new JMenuItem("Guardar Como");
        opcNuevoArchivo=new JMenuItem("Nuevo");
        opcSalir=new JMenuItem("Salir");

        menuA.add(opcNuevoArchivo);
        menuA.add(opcAbrirArchivo);
        menuA.add(opcGuardarArchivoActual);
        menuA.add(opcGuardarArchivoNuevo);
        menuA.add(opcSalir);
        menuB.add(menuA);

        menuB.add(runProgram);


        //Definicion de los paneles

        editorT=new JTextArea("Escribe tu codigo");
        editorT.setBorder(new LineBorder(Color.BLACK,2));
        editScroll=new JScrollPane(editorT);

        consolaT=new JTextArea("Consola");
        consolaT.setBorder(new LineBorder(Color.BLACK,2));
        conScroll=new JScrollPane(consolaT);


        //Agregado de los paneles principales
        add(editScroll);
        add(conScroll);

        selectorArchivo = new JFileChooser();

        //Eventos de las opciones de la barra de menu

        //Carga de un archivo para editar

        opcAbrirArchivo.addActionListener(e->{

            selectorArchivo = new JFileChooser();

            //Filtro para que solo abra archivos con la extension jovo
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos Jovo", "jovo");
            selectorArchivo.setFileFilter(filtro);

            //Abre la ventana de seleccion de archivos
            selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
            selectorArchivo.showOpenDialog(this);

            //Se obtiene el archivo seleccionado
            File archivoS = selectorArchivo.getSelectedFile();

            if((archivoS == null) || (archivoS.getName().equals("")))
                JOptionPane.showMessageDialog(this,"Archivo Invalido, el archivo no existe","Archivo Invalido",JOptionPane.ERROR_MESSAGE);
            else
                try {
                    Scanner scn = new Scanner(archivoS);
                    while(scn.hasNext())
                    {
                        editorT.insert(scn.nextLine() + "\n",consolaT.getText().length());
                    }
                    scn.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

        });

        // Opcion que guarda el archivo bajo el mismo nombre y sin cambiar el nombre
        opcGuardarArchivoActual.addActionListener(e->{

            if(selectorArchivo!=null)
            {
                try {
                    FileWriter fw = new FileWriter(selectorArchivo.getSelectedFile());
                    fw.write(editorT.getText());
                    System.out.println(editorT.getText());
                    fw.close();
                    System.out.println("Se escribio correctamente");
                } catch (IOException r) {
                    System.out.println("No se pudo escribir en el archivo");
                }
            }else
                JOptionPane.showMessageDialog(this,"Archivo no creado" ,"Archivo no creado",JOptionPane.ERROR_MESSAGE);
        });

        // Opcion de guardar archivo no creado

        opcGuardarArchivoNuevo.addActionListener(e->{

            try{
                //File archivo=selectorArchivo.getSelectedFile();
                //
                // Se consulta el ChooseFile para ver si tenemos un archivo abierto o es uno nuevo

                if(selectorArchivo==null)   //Si no hay un archivo ya existente se crea uno nuevo
                {
                    selectorArchivo = new JFileChooser();
                    //No hay archivo seleccionado por lo que se crea uno nuevo
                    selectorArchivo.setDialogType(JFileChooser.SAVE_DIALOG);
                    selectorArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    selectorArchivo.setDialogTitle("Selecciona la ruta de tu archivo");

                    int respuesta=selectorArchivo.showSaveDialog(this); // Se apertura la ventana.
                    switch(respuesta){ // segun la opcion del usuario se ejecutan los algoritmos de interes
                        case JFileChooser.APPROVE_OPTION:
                            File archivo=selectorArchivo.getSelectedFile();
                            cDialogoNomArch obj=new cDialogoNomArch(this,true);
                            String nomArch=obj.mostrar();
                            String path=archivo.getAbsolutePath();
                            if(!nomArch.equals("default")) {
                                try {
                                    archivo = new File(path + "/" + nomArch + ".jovo");
                                    FileWriter fw = new FileWriter(archivo);
                                    fw.write(editorT.getText());
                                    System.out.println(editorT.getText());
                                    fw.close();
                                    System.out.println("Se escribio correctamente");
                                } catch (IOException r) {
                                    System.out.println("No se pudo escribir en el archivo");
                                }
                            }else
                                JOptionPane.showMessageDialog(this,"Nombre de archivo invalido, el archivo tiene que comenzar con un numero o letra" ,"Nombre de archivo Inv",JOptionPane.ERROR_MESSAGE);
                            break;
                        case JFileChooser.CANCEL_OPTION:
                            break;
                        default :
                            System.out.println("Error");
                            break;
                    }
                }
                else{   // El archivo en el que se esta trabajando si existe y se tiene que sobreescribir
                    File archivoTemp=selectorArchivo.getSelectedFile();
                    try {
                        FileWriter fw = new FileWriter(archivoTemp);
                        fw.write(editorT.getText());
                        System.out.println(editorT.getText());
                        fw.close();
                        System.out.println("Se escribio correctamente");
                    } catch (IOException r) {
                        System.out.println("No se pudo escribir en el archivo");
                    }

                }


            }catch(Exception r){ System.out.println("No se puede abrir la ventana del guardado de archivo");};
        });

        // Opcion de la creacion de un nuevo archivo

        opcNuevoArchivo.addActionListener(e->{
            //Se borra el texto en el editor de texto y el contenido del selected file
            editorT.setText("");
            selectorArchivo=null;
        });

        //Opcion de salir

        opcSalir.addActionListener(e->{
            setVisible(false);
            dispose();
        });

        // Opcion de correr

        runProgram.addActionListener(e->{

            // Primero se guarda el archivo

            if(selectorArchivo!=null)
            {
                try {
                    FileWriter fw = new FileWriter(selectorArchivo.getSelectedFile());
                    fw.write(editorT.getText());
                    System.out.println(editorT.getText());
                    fw.close();
                    System.out.println("Se escribio correctamente");
                } catch (IOException r) {
                    System.out.println("No se pudo escribir en el archivo");
                }
            }else
                JOptionPane.showMessageDialog(this,"Archivo no creado" ,"Archivo no creado",JOptionPane.ERROR_MESSAGE);


            //

        });


        // Metodos para guardar archivos




        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new interfaz();
    }

}

