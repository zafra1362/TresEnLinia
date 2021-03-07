package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.util.*;

public class Controller {

    @FXML
    Button siguente, empezar, casilla00, casilla01, casilla02, casilla10, casilla11, casilla12, casilla20, casilla21, casilla22;
    @FXML
    ToggleGroup grup1;
    @FXML
    RadioButton jvm, jvj, mvm;
    @FXML
    TextField Jugador1, Jugador2;
    @FXML
    Text NomJugador1, NomJugador2;
    @FXML
    Label estadisticas;

    //Para guardar los datos que introducen los jugadores
    private String jugador1, player2;
    public void guardar(ActionEvent actionEvent) {
        if (jvm.isSelected()) {
            jugador1 = Jugador1.getText();
            Jugador1.setVisible(false);
            NomJugador1.setVisible(false);

        } else if (jvj.isSelected()) {

            jugador1 = Jugador1.getText();
            player2 = Jugador2.getText();
            Jugador1.setVisible(false);
            NomJugador1.setVisible(false);
            NomJugador2.setVisible(false);
            Jugador2.setVisible(false);
        }

    }

    //Defino las casillas y los turnos
     int numero = 0;
     int numeroturnos = 0;
     boolean turno = true;


    //Limpio,activo y desactivo los botones.
    public void limpiar() {
        casilla00.setText("");
        casilla01.setText("");
        casilla02.setText("");
        casilla10.setText("");
        casilla11.setText("");
        casilla12.setText("");
        casilla20.setText("");
        casilla21.setText("");
        casilla22.setText("");
        estadisticas.setText("");
    }

    public void EncenderBotones() {
        casilla00.setDisable(false);
        casilla01.setDisable(false);
        casilla02.setDisable(false);
        casilla10.setDisable(false);
        casilla11.setDisable(false);
        casilla12.setDisable(false);
        casilla20.setDisable(false);
        casilla21.setDisable(false);
        casilla22.setDisable(false);
    }

    public void ApagarBotones() {
        casilla00.setDisable(true);
        casilla01.setDisable(true);
        casilla02.setDisable(true);
        casilla10.setDisable(true);
        casilla11.setDisable(true);
        casilla12.setDisable(true);
        casilla20.setDisable(true);
        casilla21.setDisable(true);
        casilla22.setDisable(true);
    }

    //Boton de reiniciar
    public void reinicia(ActionEvent actionEvent) {
        numero = 0;
        limpiar();
        EncenderBotones();
        if (mvm.isSelected()) {
            siguente.setVisible(true);
            maquinavsmaquina(actionEvent);
        }
    }

    //Seleccion de modos
    public void onClick(ActionEvent actionEvent) {
        RadioButton radiobutton = (RadioButton) grup1.getSelectedToggle();
        if (radiobutton != null) {
            if (jvm.isSelected()) jugadorvsmaquina(actionEvent);
            if (jvj.isSelected()) jugadorvsjugador(actionEvent);
        }
    }

    //Modo de Maquina vs Maquina
    public void maquinavsmaquina(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        List<Button> buttons = new ArrayList<Button>(Arrays.asList(casilla00, casilla01, casilla02, casilla10, casilla11, casilla12, casilla20, casilla21, casilla22));
        Button boton;
        Boolean boton1 = false;
        estadisticas.setVisible(true);
        empezar.setVisible(false);
        while (!boton1) {
            int numerorandom = (int) (Math.random() * 9);
            boton = buttons.get(numerorandom);
            if ("".equals(boton.getText())) {

                if (turno) {
                    estadisticas.setText("Turno de: O");
                    boton.setText("X");
                    if (comprobarGanador()) break;
                    turno = false;
                } else {
                    estadisticas.setText("Turno de: X");
                    boton.setText("O");
                    if (comprobarGanador()) break;
                    turno = true;
                }
                boton1 = true;
            }
        }

    }

    //Para que en Maquina vs Maquina al darle a siguiente hagan el movimiento
    public void movimiento(ActionEvent actionEvent) {
        if (mvm.isSelected()) maquinavsmaquina(actionEvent);
    }

    //Modalidad de Jugador vs Maquina
    public void jugadorvsmaquina(ActionEvent actionEvent) {
        int random;
        boolean boton1 = false;
        Button boton = (Button) actionEvent.getSource();
        List<Button> buttons = new ArrayList<Button>(Arrays.asList(casilla00, casilla01, casilla02, casilla10, casilla11, casilla12, casilla20, casilla21, casilla22));
        Button button;
        empezar.setVisible(false);
        estadisticas.setVisible(true);
        estadisticas.setText("Turno de: 0");
        if (boton.getText().equals("")) {
            boton.setText("X");
            comprobarGanador();
            estadisticas.setText("Turno de: O");
            while (!boton1 && numeroturnos <= 3) {
                random = (int) (Math.random() * 9);
                button = buttons.get(random);
                if (button.getText().equals("")) {
                    button.setText("O");
                    estadisticas.setText(jugador1 + " tu turno.");
                    numeroturnos++;
                    comprobarGanador();
                    boton1 = true;
                }
            }
        }


    }

    //Modo de Jugador vs Jugador
    public void jugadorvsjugador(ActionEvent actionEvent) {
        Button boton = (Button) actionEvent.getSource();
        empezar.setVisible(false);
        estadisticas.setVisible(true);
        if (turno) {
            estadisticas.setText(player2 + " tu turno.");
            if (boton.getText().equals("")) {
                boton.setText("X");
                comprobarGanador();
                turno = false;
            }
        } else {
            estadisticas.setText(jugador1 + " tu turno.");
            if (boton.getText().equals("")) {
                boton.setText("O");
                comprobarGanador();
                turno = true;
            }
        }


    }

    //Comprobar: ganador,horizontales y verticales
    public boolean comprobarGanador() {
        numero++;
        if (!casilla00.getText().equals("") && !casilla01.getText().equals("") && casilla00.getText().equals(casilla01.getText()) && casilla01.getText().equals(casilla02.getText())) {
            ApagarBotones();
            if (jvm.isSelected()) {
                if (casilla00.getText().equals("O")) estadisticas.setText(casilla00.getText() + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (jvj.isSelected()) {
                if (casilla00.getText().equals("O")) estadisticas.setText(player2 + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (mvm.isSelected()) estadisticas.setText(casilla00.getText() + " gana");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;

        }
        if (!casilla10.getText().equals("") && !casilla11.getText().equals("") && casilla10.getText().equals(casilla11.getText()) && casilla11.getText().equals(casilla12.getText())) {
            ApagarBotones();
            if (jvm.isSelected()) {
                if (casilla10.getText().equals("O")) estadisticas.setText(casilla10.getText() + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (jvj.isSelected()) {
                if (casilla10.getText().equals("O")) estadisticas.setText(player2 + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (mvm.isSelected()) estadisticas.setText(casilla10.getText() + " gana");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;

        }
        if (!casilla21.getText().equals("") && !casilla20.getText().equals("") && casilla20.getText().equals(casilla21.getText()) && casilla21.getText().equals(casilla22.getText())) {
            ApagarBotones();
            if (jvm.isSelected()) {
                if (casilla21.getText().equals("O")) estadisticas.setText(casilla21.getText() + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (jvj.isSelected()) {
                if (casilla21.getText().equals("O")) estadisticas.setText(player2 + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (mvm.isSelected()) estadisticas.setText(casilla20.getText() + " gana");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;

        }

        //Diagonales

        if (!casilla00.getText().equals("") && !casilla11.getText().equals("") && casilla00.getText().equals(casilla11.getText()) && casilla11.getText().equals(casilla22.getText())) {
            ApagarBotones();
            if (jvm.isSelected()) {
                if (casilla00.getText().equals("O")) estadisticas.setText(casilla00.getText() + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (jvj.isSelected()) {
                if (casilla00.getText().equals("O")) estadisticas.setText(player2 + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (mvm.isSelected()) estadisticas.setText(casilla00.getText() + " gana");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;

        }
        if (!casilla02.getText().equals("") && !casilla11.getText().equals("") && casilla02.getText().equals(casilla11.getText()) && casilla11.getText().equals(casilla20.getText())) {
            ApagarBotones();
            if (jvm.isSelected()) {
                if (casilla02.getText().equals("O")) estadisticas.setText(casilla02.getText() + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (jvj.isSelected()) {
                if (casilla02.getText().equals("O")) estadisticas.setText(player2 + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (mvm.isSelected()) estadisticas.setText(casilla02.getText() + " gana");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;
        }

        //Verticales
        if (!casilla00.getText().equals("") && !casilla10.getText().equals("") && casilla00.getText().equals(casilla10.getText()) && casilla10.getText().equals(casilla20.getText())) {
            ApagarBotones();
            if (jvm.isSelected()) {
                if (casilla00.getText().equals("O")) estadisticas.setText(casilla00.getText() + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (jvj.isSelected()) {
                if (casilla00.getText().equals("O")) estadisticas.setText(player2 + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (mvm.isSelected()) estadisticas.setText(casilla00.getText() + " gana");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;
        }
        if (!casilla01.getText().equals("") && !casilla11.getText().equals("") && casilla01.getText().equals(casilla11.getText()) && casilla11.getText().equals(casilla21.getText())) {
            ApagarBotones();
            if (jvm.isSelected()) {
                if (casilla01.getText().equals("O")) estadisticas.setText(casilla01.getText() + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (jvj.isSelected()) {
                if (casilla01.getText().equals("O")) estadisticas.setText(player2 + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (mvm.isSelected()) estadisticas.setText(casilla01.getText() + " gana");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;

        }
        if (!casilla02.getText().equals("") && !casilla12.getText().equals("") && casilla02.getText().equals(casilla12.getText()) && casilla12.getText().equals(casilla22.getText())) {
            ApagarBotones();
            if (jvm.isSelected()) {
                if (casilla02.getText().equals("O")) estadisticas.setText(casilla02.getText() + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (jvj.isSelected()) {
                if (casilla02.getText().equals("O")) estadisticas.setText(player2 + " gana");
                else estadisticas.setText(jugador1 + " gana");
            }
            if (mvm.isSelected()) estadisticas.setText(casilla02.getText() + " gana");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;

        }

        //Empate
        if (numero == 9) {
            ApagarBotones();
            estadisticas.setText("Empate");
            if (mvm.isSelected()) siguente.setVisible(false);
            empezar.setVisible(true);
            return true;
        }
        return false;
    }
}