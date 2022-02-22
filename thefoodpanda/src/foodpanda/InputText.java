package foodpanda;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class InputText {
    GridPane grid;
    //TableView tableView;
    TableProduct tableProduct;
    Group root;
    public InputText(Group root, TableProduct tableProduct){
        //this.tableView = tableView;
        this.tableProduct = tableProduct;
        this.root = root;
        //Creating a GridPane container
        grid = new GridPane();
        grid.setLayoutX(750);
        grid.setLayoutY(60);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        List<TextField> txtFieldList = new ArrayList<>();
        for (int i=0;i<Product.sampleList.size();i++){
            TextField textField = new TextField();
            textField.setPromptText(Product.sampleList.get(i));
            textField.setPrefColumnCount(10);
            GridPane.setConstraints(textField,0,i);
            grid.getChildren().add(textField);
            txtFieldList.add(textField);
        }


//Defining the Submit button
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);
//Defining the Clear button
        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 1);
        grid.getChildren().add(clear);

        final Label label = new Label();
        GridPane.setConstraints(label, 0, Product.sampleList.size());
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean emptyField = false;

                for(TextField textField : txtFieldList){
                    if(textField.getText() == null || textField.getText().equals("")){
                        emptyField = true;
                        break;
                    }
                }

                if(!emptyField){

                    int _id = Integer.parseInt(txtFieldList.get(0).getText());
                    String _name = txtFieldList.get(1).getText();
                    double _price = Double.parseDouble(txtFieldList.get(2).getText());
                    String _desc = txtFieldList.get(3).getText();

                    DataQuery.addProduct(_id,_name,_price,_desc);
                    //tableView.refresh();
                    root.getChildren().remove(tableProduct.getTableView());
                    tableProduct.setTableView(DataQuery.getProducts(0));

                    //root.getChildren().add(new TableProduct(DataQuery.getProducts(0)).getTableView());
                    root.getChildren().add(tableProduct.getTableView());

                    //label.setText(_id + " " +_name +" "+ _price+ " " + _desc);


                    //label.setText(txtFieldList.get(0).getText()+" "+txtFieldList.get(1).getText());

                }
                else{
                    label.setText("Some field might be empty");
                }


            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(TextField textField : txtFieldList){
                    textField.clear();
                }

                label.setText(null);
            }
        });

        grid.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(10),
                new BorderWidths(1, 1, 1, 1))));
    }

    public GridPane getGrid() {
        return grid;
    }
}
