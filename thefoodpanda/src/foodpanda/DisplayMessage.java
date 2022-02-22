package foodpanda;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DisplayMessage {
    Alert alert;
    Alert.AlertType type;
    String message;
    public DisplayMessage(String message, Alert.AlertType type){
        this.alert = new Alert(type);
        this.type = type;
        this.message = message;
    }
    public void display(){
        if(type == Alert.AlertType.INFORMATION){
            this.alert.setTitle("information");
            this.alert.setContentText(message);
        }
        else if(type == Alert.AlertType.ERROR){
            this.alert.setTitle("error");
            this.alert.setContentText(message);
        }
        this.alert.showAndWait().ifPresent(rs ->{
            if(rs == ButtonType.OK){
                System.out.println("pressed ok");
            }
        });

    }
}
