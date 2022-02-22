package foodpanda;

import javafx.beans.binding.Bindings;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TableProduct {
    private TableView tableView;
    public TableProduct(List<Product> productList){
        this.tableView = new TableView();

        this.tableView.setLayoutX(70);
        this.tableView.setLayoutY(60);

        String [] property = {"id", "name", "price", "description"};


        TableColumn<Product,Integer> col1 = new TableColumn<>(property[0]);
        TableColumn<Product,String> col2 = new TableColumn<>(property[1]);
        TableColumn<Product,Double> col3 = new TableColumn<>(property[2]);
        TableColumn<Product,String> col4 = new TableColumn<>(property[3]);

        col1.setCellValueFactory(new PropertyValueFactory<>(property[0]));
        col2.setCellValueFactory(new PropertyValueFactory<>(property[1]));
        col3.setCellValueFactory(new PropertyValueFactory<>(property[2]));
        col4.setCellValueFactory(new PropertyValueFactory<>(property[3]));


        this.tableView.getColumns().add(col1);
        this.tableView.getColumns().add(col2);
        this.tableView.getColumns().add(col3);
        this.tableView.getColumns().add(col4);

        for (Product p : productList){
            this.tableView.getItems().add(p);
        }

        this.tableView.prefHeightProperty().bind(Bindings.max(3, Bindings.size(tableView.getItems()))
                .multiply(10)
                .add(25)
                .add(40));

        this.tableView.prefWidthProperty().bind(Bindings.max(3, Bindings.size(tableView.getItems()))
                .multiply(50)
                .add(50)
                .add(50));




    }

    public void setTableView(List<Product> productList){
        this.tableView = new TableView();
        this.tableView.setLayoutX(70);
        this.tableView.setLayoutY(60);

        String [] property = {"id", "name", "price", "description"};


        TableColumn<Product,Integer> col1 = new TableColumn<>(property[0]);
        TableColumn<Product,String> col2 = new TableColumn<>(property[1]);
        TableColumn<Product,Double> col3 = new TableColumn<>(property[2]);
        TableColumn<Product,String> col4 = new TableColumn<>(property[3]);

        col1.setCellValueFactory(new PropertyValueFactory<>(property[0]));
        col2.setCellValueFactory(new PropertyValueFactory<>(property[1]));
        col3.setCellValueFactory(new PropertyValueFactory<>(property[2]));
        col4.setCellValueFactory(new PropertyValueFactory<>(property[3]));


        this.tableView.getColumns().add(col1);
        this.tableView.getColumns().add(col2);
        this.tableView.getColumns().add(col3);
        this.tableView.getColumns().add(col4);

        for (Product p : productList){
            this.tableView.getItems().add(p);
        }

        this.tableView.prefHeightProperty().bind(Bindings.max(3, Bindings.size(tableView.getItems()))
                .multiply(10)
                .add(25)
                .add(40));

        this.tableView.prefWidthProperty().bind(Bindings.max(3, Bindings.size(tableView.getItems()))
                .multiply(50)
                .add(50)
                .add(50));



    }

    public TableView getTableView() {
        return tableView;
    }
}
