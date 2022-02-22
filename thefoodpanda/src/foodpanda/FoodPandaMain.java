package foodpanda;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FoodPandaMain extends Application {
    private GraphicsContext mGraphicsContext;
    Group root;
    Group rootLog,rootReg;
    Person person;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //rootLog = new Group();
        rootReg = new Group();
        root = new Group();
        primaryStage.setTitle("TheFoodPanda");
        Scene scene, scene_init;



        //root as second scene

        scene = new Scene(root);
        //primaryStage.setScene(scene);
        //person = (Person) primaryStage.getUserData();

        Canvas canvas = new Canvas(1000,750);
        this.mGraphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Label currentUserName = new Label();
        currentUserName.setLayoutX(700);
        currentUserName.setLayoutY(10);

        currentUserName.setTextFill(Color.web("#0076a4"));
        currentUserName.setFont(Font.font(30));
        root.getChildren().add(currentUserName);

        Label banner = new Label();
        banner.setLayoutX(250);
        banner.setLayoutY(10);
        banner.setText("Available Item");
        banner.setTextFill(Color.web("#0076a3"));
        banner.setFont(Font.font(20));
        root.getChildren().add(banner);

        //table view and the input Text class
        TableProduct tb = new TableProduct(DataQuery.getProducts(0));

        root.getChildren().add(tb.getTableView());

        GridPane gridPane = new InputText(root, tb).getGrid();
        TextField txtIdTobeDeleted = new TextField();
        Button deletedButton = new Button("delete");
        Button refreshButton = new Button("refresh");

        //////////////////////////////////////





        //choice box to sort the table
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().add(Product.sampleList.get(0).substring(0,2));
        choiceBox.getItems().add(Product.sampleList.get(2).substring(0,6));
        choiceBox.setLayoutX(400);
        choiceBox.setLayoutY(10);
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.setOnAction((event)->{
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            root.getChildren().remove(tb.getTableView());
            root.getChildren().add(new TableProduct(DataQuery.getProducts(selectedIndex)).getTableView());
            System.out.println("selected at :"+ selectedIndex);
        });

        root.getChildren().add(choiceBox);

        // label to create order food
        Label orderBanner = new Label();
        orderBanner.setLayoutX(250);
        orderBanner.setLayoutY(250);
        orderBanner.setText("Order Food");
        orderBanner.setTextFill(Color.web("#0076a3"));
        orderBanner.setFont(Font.font(20));
        root.getChildren().add(orderBanner);

        TextField itemTypeCount = new TextField();
        itemTypeCount.setPromptText("total type of items(int)");
        itemTypeCount.setLayoutX(250);
        itemTypeCount.setLayoutY(280);
        root.getChildren().add(itemTypeCount);

        Button click = new Button("ok");
        click.setLayoutX(420);
        click.setLayoutY(280);
        root.getChildren().add(click);

        Button clearField = new Button("reset/submit");
        clearField.setLayoutX(420);
        clearField.setLayoutY(310);
        root.getChildren().add(clearField);

        List<TextField> txtList = new ArrayList<>();  //item id
        List<TextField> txtItemCount = new ArrayList<>();  //total item

        TextField txtPhoneNumber = new TextField();

        click.setOnAction(actionEvent -> {
            int totalItem = Integer.parseInt(itemTypeCount.getText());
            System.out.println("types of item :"+totalItem);
            System.out.println("the person now :"+ person.getPersonName());

            txtPhoneNumber.setLayoutX(150);
            txtPhoneNumber.setLayoutY(320);
            txtPhoneNumber.setPromptText("Phone number");
            root.getChildren().add(txtPhoneNumber);

            double x_temp = 100;
            double y_temp = 320 + 30;

            double x_another = 300;

            for(int i=0;i<totalItem;i++){
                TextField _demo = new TextField();
                TextField countField = new TextField();
                countField.setMaxWidth(60);
                _demo.setLayoutX(x_temp);
                _demo.setLayoutY(y_temp);
                countField.setLayoutX(x_another);
                countField.setLayoutY(y_temp);
                y_temp += 30;
                _demo.setPromptText(i+" th item");
                countField.setPromptText("count");
                txtList.add(_demo);
                txtItemCount.add(countField);
                root.getChildren().add(_demo);
                root.getChildren().add(countField);
            }
//            List<Integer> am = new ArrayList<>();
//            List<Integer> pm = new ArrayList<>();
//            am.add(31);
//            pm.add(3);
//            DataQuery.billCalculation(am, pm);
        });

        clearField.setOnAction(actionEvent -> {
            itemTypeCount.clear();
            if(txtList.size()>0 || txtItemCount.size()>0){
                List<Integer> productIdList = new ArrayList<>();
                List<Integer> productCountList = new ArrayList<>();

                for(int i=0;i<txtList.size();i++){
                    int product_id = Integer.parseInt(txtList.get(i).getText());
                    int product_count = Integer.parseInt(txtItemCount.get(i).getText());
                    productIdList.add(product_id);
                    productCountList.add(product_count);
                }

                float get_val = DataQuery.billCalculation(productIdList,productCountList);
                System.out.println("the over all bill :"+get_val);
                DataQuery.insertOrder(person,txtPhoneNumber.getText(),get_val,productIdList,productCountList);

                for(int i=0;i<txtList.size();i++){
                    root.getChildren().remove(txtList.get(i));
                    root.getChildren().remove(txtItemCount.get(i));
                }
                root.getChildren().remove(txtPhoneNumber);
                txtList.clear();
                txtItemCount.clear();
                txtPhoneNumber.clear();

            }

        });

        Button backButton = new Button("Log out");
        root.getChildren().add(backButton);


         //preparing new scene

        scene_init = new Scene(rootReg);
        Canvas init_canvas = new Canvas(1000, 750);
        rootReg.getChildren().add(init_canvas);
        primaryStage.setScene(scene_init);

        //log into user
        Label log_label = new Label();
        log_label.setLayoutX(200);
        log_label.setLayoutY(260);
        log_label.setText("Sign in");
        log_label.setTextFill(Color.web("#0076a3"));
        log_label.setFont(Font.font(30));
        rootReg.getChildren().add(log_label);

        TextField signUserId = new TextField();
        signUserId.setLayoutX(200);
        signUserId.setLayoutY(320);
        signUserId.setPromptText("user name");
        rootReg.getChildren().add(signUserId);

        PasswordField signPassword = new PasswordField();
        signPassword.setLayoutX(200);
        signPassword.setLayoutY(350);
        signPassword.setPromptText("password");
        rootReg.getChildren().add(signPassword);

        Button signButton = new Button("Sign in");
        signButton.setLayoutX(200);
        signButton.setLayoutY(380);
        rootReg.getChildren().add(signButton);
        signButton.setOnAction(actionEvent -> {
            person = DataQuery.checkUser(signUserId.getText(), signPassword.getText());
            signUserId.clear();
            signPassword.clear();
            if (person != null){
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage)node.getScene().getWindow();
                stage.setUserData(person);
                currentUserName.setText("Name:"+person.getPersonName());

                if( person.getPersonName().equalsIgnoreCase("admin")){
                    //root.getChildren().add(new InputText(root, tb.getTableView()).getGrid());
                    root.getChildren().add(gridPane);

                    //delete something from database by id

                    //TextField txtIdTobeDeleted = new TextField(); //init
                    txtIdTobeDeleted.setLayoutX(750);
                    txtIdTobeDeleted.setLayoutY(250);
                    txtIdTobeDeleted.setPromptText("id to be deleted!");
                    root.getChildren().add(txtIdTobeDeleted);
                    //Button deletedButton = new Button("delete"); //init
                    deletedButton.setLayoutX(920);
                    deletedButton.setLayoutY(250);
                    root.getChildren().add(deletedButton);
                    //refreshButton = new Button("refresh"); //init
                    refreshButton.setLayoutX(920);
                    refreshButton.setLayoutY(280);
                    root.getChildren().add(refreshButton);
                    refreshButton.setOnAction(event -> {txtIdTobeDeleted.clear();});
                    deletedButton.setOnAction(event -> {
                        DataQuery.deleteProduct(Integer.parseInt(txtIdTobeDeleted.getText()));
                        root.getChildren().remove(tb.getTableView());
                        tb.setTableView(DataQuery.getProducts(0));
                        //root.getChildren().add(new TableProduct(DataQuery.getProducts(0)).getTableView());
                        root.getChildren().add(tb.getTableView());
                        txtIdTobeDeleted.clear();

                    });

                }

                stage.setScene(scene);

            }
        });


        FlowPane pane = new FlowPane(1, 1);
        pane.setOrientation(Orientation.VERTICAL);

        pane.setLayoutX(400);
        pane.setLayoutY(200);
        pane.setBorder(new Border(new BorderStroke(Color.GREEN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        rootReg.getChildren().add(pane);


        //create new user
        Label register = new Label();
        register.setLayoutX(450);
        register.setLayoutY(300);
        register.setText("Register User");
        register.setTextFill(Color.web("#0076a3"));
        register.setFont(Font.font(30));
        rootReg.getChildren().add(register);

        TextField txtUserId = new TextField();
        txtUserId.setLayoutX(450);
        txtUserId.setLayoutY(350);
        txtUserId.setPromptText("user id");
        rootReg.getChildren().add(txtUserId);//adding to root
        TextField txtUserName = new TextField();
        txtUserName.setLayoutX(450);
        txtUserName.setLayoutY(380);
        txtUserName.setPromptText("name");
        rootReg.getChildren().add(txtUserName);//adding to root
        PasswordField txtPassword = new PasswordField();
        txtPassword.setLayoutX(450);
        txtPassword.setLayoutY(410);
        txtPassword.setPromptText("password");
        rootReg.getChildren().add(txtPassword);//adding to root

        Button buttonRegister = new Button("register");
        buttonRegister.setLayoutX(450);
        buttonRegister.setLayoutY(440);
        rootReg.getChildren().add(buttonRegister);
        buttonRegister.setOnAction(actionEvent -> {
            System.out.println("register button clicked");

            DataQuery.addUser(txtUserId.getText(), txtUserName.getText(), txtPassword.getText());
            txtUserName.clear();
            txtPassword.clear();
            txtUserId.clear();
        });




        //..............
        backButton.setOnAction(actionEvent -> {
            primaryStage.setScene(scene_init);
            root.getChildren().remove(gridPane);
            root.getChildren().remove(txtIdTobeDeleted);
            root.getChildren().remove(deletedButton);
            root.getChildren().remove(refreshButton);
        });


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
