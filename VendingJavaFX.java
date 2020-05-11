package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class VendingJavaFX extends Application {

//declaring variables that will be used across eventhandlers and will need to be changed dynamically as the program executes
    public String currentUser = "";
    public String currentPassword = "";
    public String currentBalance = "";
    public String order = "Your Order: \r";
    public String productList = "";
    public String machineBalance = "";
    public String[] productArray = new String[] {"Twix", "Mars", "Wispa", "Twirl", "Tayto", "Dairy Milk", "Lion", "Flake", "Galaxy", "Snickers"}; //this string array is needed for getting an index of an item entered for comparrison in other classes
    public String productFile = "Products.txt";
    public String clientFile = "Clients.dat";
    public String machineFile = "Machine.dat";
    public int[] purchasedProducts = new int[10];
    public double currentOrderCost = 0;
    String newUserBalance = "";
    public String balanceCheckforNull = "-1";

    Client client = new Client();
    Product product = new Product();
    VendingMachine vend = new VendingMachine();
    Writer writer = new Writer();
    Admin admin = new Admin();
    Stage window;
    Scene scene1, scene2, scene3, scene4;


//scene 1 = opening scene, user chooses whether they're admin or client and enters password
    //scene 2 = client scene
    //scene 3 = admin scene
    //scene 4 = extra client scene to show new balance after purchase and allow them to sign out

    public static void main(String[] args) throws IOException  {

        launch(args);
    }

    //main method that establishes the primary scene of the Vending Machine
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

//the following groups of labels are labels that use the setText method across eventhandlers, they need to be declared from the start because some are changed before
//their scenes are written in the program so the labels must be written before that
        Label currentUserLabel = new Label("");
        Label currentUserLabelclient = new Label("");
        Label productListLabel = new Label("");
        Label priceList = new Label("");
        Label currentBalanceLabel = new Label("");
        Label Balance = new Label("");
        Label checkBalanceLabel = new Label("");

//image for the sign in page
        final ImageView selectedImage = new ImageView();
        Image image1 = new Image(new FileInputStream("vending.jpg"));
        selectedImage.setImage(image1);
        selectedImage.setEffect(new DropShadow(20, Color.BLACK));

        Label Welcome = new Label("Welcome, please enter your name and password and select what kind of user you are.");

//design box for the textfields
        HBox hbForTextField = new HBox();
        hbForTextField.setAlignment(Pos.CENTER);


        Label User = new Label("Name:   ");
        Label Password = new Label("  Password:   ");

        TextField user = new TextField();
        user.setPrefWidth(80);
        TextField password = new TextField();
        password.setPrefWidth(80);
        hbForTextField.getChildren().addAll(User, user);
        hbForTextField.getChildren().addAll(Password, password);

//buttons to set up either client or admin user below
        Button Client = new Button("Buy Chocolate");
        Button Admin = new Button("Administrator");



        VBox vb = new VBox();
        vb.setPadding(new Insets(10, 50, 50, 50));
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(18);

        vb.getChildren().add(selectedImage);
        vb.getChildren().add(Welcome);
        vb.getChildren().add(hbForTextField);
        vb.getChildren().addAll(Client, Admin);

        scene1 = new Scene(vb, 650, 450);

//in the event of a client user, check for number input, then verify client then move on to Client scene
        Client.setOnAction(new EventHandler() {
                               @Override
                               public void handle(Event event){


                                   if(user.getText() != null) {
                                       currentUser = user.getText();
                                       currentPassword = password.getText();

                                   }
                                   try {
                                       int passwordInt = Integer.parseInt(password.getText());


                                   } catch (NumberFormatException j) {

                                       AlertMessage.display("Try again", "You must enter a number");
                                   }

                                   try {
                                       if (client.isClient(user.getText(), password.getText())) {

                                           currentBalance = client.getBalance(currentUser, password.getText());
                                           currentBalanceLabel.setText("Your account Balance: €" + currentBalance);
                                           currentUserLabelclient.setText("Welcome " + currentUser);
                                           window.setScene(scene2);
                                       }
                                       else { AlertMessage.display("Try again", "Sorry, User or Password is Incorrect");}

                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   }
                                   //populating the product list from products.dat so the client  can see a product price list
                                   try {
                                       productList = ""; //resetting the product list before each time checking it for changes.
                                       for (int i = 0; i < vend.getProductTypes().length - 1; i++) {

                                           String localProduct = vend.getProductTypes()[i].toString();
                                           String localDescription = vend.getProductTypes()[i].getDescription();
                                           String localQuantity = product.getQuantity(localDescription);

                                           productList = productList.concat(localProduct);
                                           productList = productList.concat(" \n");

                                       }

                                   }

                                   catch (IOException e) {
                                       e.printStackTrace();
                                   }
                                   priceList.setText(productList);
                                   user.clear(); //these text fields are cleared now so that when I return to scene 1 for a new user previous users information is not there.
                                   password.clear();
                               }
                           });


//setting images for the buttons
        final ImageView Twix = new ImageView();
        final ImageView Mars = new ImageView();
        final ImageView Wispa = new ImageView();
        final ImageView Twirl = new ImageView();
        final ImageView Tayto = new ImageView();
        final ImageView DairyMilk = new ImageView();
        final ImageView Lion = new ImageView();
        final ImageView Flake = new ImageView();
        final ImageView Galaxy = new ImageView();
        final ImageView Snickers = new ImageView();

        Image image2 = null;
        Image image3 = null;
        Image image4 = null;
        Image image5 = null;
        Image image6 = null;
        Image image7 = null;
        Image image8 = null;
        Image image9 = null;
        Image image10 = null;
        Image image11 = null;
//taking in the images from the project folder
        try {
            image2 = new Image(new FileInputStream("twix.jpg"));
            image3 = new Image(new FileInputStream("mars.jpg"));
            image4 = new Image(new FileInputStream("wispa.jpg"));
            image5 = new Image(new FileInputStream("twirl.jpg"));
            image6 = new Image(new FileInputStream("tayto.jpg"));
            image7 = new Image(new FileInputStream("dairymilk.jpg"));
            image8 = new Image(new FileInputStream("lion.jpg"));
            image9 = new Image(new FileInputStream("flake.jpg"));
            image10 = new Image(new FileInputStream("galaxy.jpg"));
            image11 = new Image(new FileInputStream("snickers.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //setting dimensions of each picture and maintaining their aspect ratio. This is done here before the buttons they will
        //cover because the image size dictates the button size and all must match each other
        Twix.setImage(image2);
        Twix.setFitHeight(60);
        Twix.setFitWidth(80);
        Twix.setPreserveRatio(true);

        Mars.setImage(image3);
        Mars.setFitHeight(60);
        Mars.setFitWidth(80);
        Mars.setPreserveRatio(true);

        Wispa.setImage(image4);
        Wispa.setFitHeight(60);
        Wispa.setFitWidth(80);
        Wispa.setPreserveRatio(true);

        Twirl.setImage(image5);
        Twirl.setFitHeight(60);
        Twirl.setFitWidth(80);
        Twirl.setPreserveRatio(true);

        Tayto.setImage(image6);
        Tayto.setFitHeight(60);
        Tayto.setFitWidth(80);
        Tayto.setPreserveRatio(true);

        DairyMilk.setImage(image7);
        DairyMilk.setFitHeight(60);
        DairyMilk.setFitWidth(80);
        DairyMilk.setPreserveRatio(true);

        Lion.setImage(image8);
        Lion.setFitHeight(60);
        Lion.setFitWidth(80);
        Lion.setPreserveRatio(true);

        Flake.setImage(image9);
        Flake.setFitHeight(60);
        Flake.setFitWidth(80);
        Flake.setPreserveRatio(true);

        Galaxy.setImage(image10);
        Galaxy.setFitHeight(60);
        Galaxy.setFitWidth(80);
        Galaxy.setPreserveRatio(true);

        Snickers.setImage(image11);
        Snickers.setFitHeight(60);
        Snickers.setFitWidth(80);
        Snickers.setPreserveRatio(true);

        //buttons are now initialised setting the image on top
        Button TwixButton = new Button(null);
        TwixButton.setGraphic(Twix);

        Button MarsButton = new Button(null);
        MarsButton.setGraphic(Mars);

        Button WispaButton = new Button(null);
        WispaButton.setGraphic(Wispa);

        Button TwirlButton = new Button(null);
        TwirlButton.setGraphic(Twirl);

        Button TaytoButton = new Button(null);
        TaytoButton.setGraphic(Tayto);

        Button DairyMilkButton = new Button(null);
        DairyMilkButton.setGraphic(DairyMilk);

        Button LionButton = new Button(null);
        LionButton.setGraphic(Lion);

        Button FlakeButton = new Button(null);
        FlakeButton.setGraphic(Flake);

        Button GalaxyButton = new Button(null);
        GalaxyButton.setGraphic(Galaxy);

        Button SnickersButton = new Button(null);
        SnickersButton.setGraphic(Snickers);

        //keeping the buttons in a seperate box to the other elements for design purposes in the final box which will keep the buttons to one side of the interface
        VBox chocBox = new VBox();
        chocBox.setPadding(new Insets(10, 50, 50, 50));
        chocBox.setAlignment(Pos.CENTER_LEFT);
        chocBox.setSpacing(10);

        chocBox.getChildren().addAll(TwixButton, MarsButton, WispaButton, TwirlButton, TaytoButton, DairyMilkButton, LionButton, FlakeButton, GalaxyButton, SnickersButton);

        Button resetOrder = new Button("Reset Order");
        Button payNow = new Button("Pay Now");

        //Welcoming new user
        VBox vbWelcome = new VBox();
        vbWelcome.setPadding(new Insets(10, 50, 50, 50));
        vbWelcome.setAlignment(Pos.TOP_CENTER);
        vbWelcome.setSpacing(10);
        vbWelcome.getChildren().add(currentUserLabelclient);
        currentUserLabelclient.setStyle("-fx-font-weight: bold;");

        VBox vbScene2 = new VBox();
        vbScene2.setPadding(new Insets(10, 50, 50, 50));
        vbScene2.setAlignment(Pos.CENTER);
        vbScene2.setSpacing(10);


        VBox productPriceBox = new VBox();
        productPriceBox.setPadding(new Insets(10, 50, 50, 50));
        productPriceBox.setAlignment(Pos.CENTER_LEFT);
        productPriceBox.setSpacing(10);
        Label priceListHeading = new Label("Price List");
        priceListHeading.setStyle("-fx-font-weight: bold;");
        priceListHeading.setAlignment(Pos.CENTER_LEFT);
        productPriceBox.getChildren().addAll(priceListHeading,priceList);

        Label currentOrder = new Label(order);
        vbScene2.getChildren().addAll(vbWelcome);
        vbScene2.getChildren().addAll(currentOrder);
        vbScene2.getChildren().addAll(resetOrder);


        Label currentOrderCostLabel = new Label("");
        vbScene2.getChildren().addAll(currentOrderCostLabel);


        vbScene2.getChildren().addAll(currentBalanceLabel);
        vbScene2.getChildren().addAll(payNow);

        TilePane scene2tile = new TilePane();
        scene2tile.setHgap(6);
        scene2tile.setPrefColumns(4);
        scene2tile.getChildren().addAll( chocBox, vbScene2, productPriceBox);
        scene2tile.setAlignment(Pos.CENTER);

        scene2 = new Scene(scene2tile, 960, 550);

        //event handler that resets the order to zero products
        resetOrder.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){ order = order = "Your Order: \r"; currentOrder.setText(order);
                currentOrderCost = 0.0; currentOrderCostLabel.setText("");
                int i = 0;
                while (i < purchasedProducts.length) { purchasedProducts[i] = 0; i++; }}}); //resetting purchased array so customer can fill it again

        //handles all functions relating to purchasing products
        payNow.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                if(currentOrderCost > Double.parseDouble(currentBalance)){
                    AlertMessage.display("Try again", "I'm Sorry You have Insufficient Funds");
                }
                else{
//setting up the lines in the correct format to use the writer method
                    newUserBalance = Double.toString(Double.parseDouble(currentBalance) - currentOrderCost);
                    String oldLine = currentUser + "," + currentPassword + "," + currentBalance;
                    String lineToInsert = currentUser + "," + currentPassword + "," + Float.parseFloat(newUserBalance);

                    writer.writer(clientFile, oldLine, lineToInsert);
//setting new machine balance
                    try {
                        String oldMachineBalance = vend.getMachineBalance();
                        String newBalance = Double.toString(Double.parseDouble(oldMachineBalance) + currentOrderCost);
                        newBalance =""+ Float.parseFloat(newBalance);
                        writer.writer(machineFile, oldMachineBalance, newBalance);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Balance.setText("Your new balance is €" + Float.parseFloat(newUserBalance));


//changing the amount of products after purchase
                    for (int i = 0; i < purchasedProducts.length ; i++){
                        if (purchasedProducts[i] != 0){
                            try {
                                String oldlineproduct = vend.getProductTypes()[i].getDescription() + "," + vend.getProductTypes()[i].getPrice() + "," + product.getQuantity(vend.getProductTypes()[i].getDescription());
                                String newlineproduct = vend.getProductTypes()[i].getDescription() + "," + vend.getProductTypes()[i].getPrice()  +","+ ((Integer.parseInt(product.getQuantity(vend.getProductTypes()[i].getDescription())) - purchasedProducts[i]));
//                                System.out.println(oldlineproduct);
//                                System.out.println(newlineproduct); //confirming products are being composed correctly
                                writer.writer(productFile, oldlineproduct, newlineproduct);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    window.setScene(scene4);
                }
                    ;}});

//the following is the code to handle each product button individually
        TwixButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String twixorder = null;
                try {
                    twixorder = "Twix bar: " +vend.getProductTypes()[0].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(twixorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[0].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
            purchasedProducts[0] = purchasedProducts[0] + 1;}}); //array is set up so the amount of each bar bought is recorded to set the new amount in Products.dat once the purchase is confirmed

        MarsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String marsorder = null;
                try {
                    marsorder = "Mars bar: " +vend.getProductTypes()[1].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(marsorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[1].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[1] = purchasedProducts[1] + 1;}});

              WispaButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String wispaorder = null;
                try {
                    wispaorder = "Wispa bar: " +vend.getProductTypes()[2].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(wispaorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[2].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[2] = purchasedProducts[2] + 1;}});

        TwirlButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String twirlorder = null;
                try {
                    twirlorder = "Twirl bar: " +vend.getProductTypes()[3].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(twirlorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[3].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[3] = purchasedProducts[3] + 1;}});

        TaytoButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String taytoorder = null;
                try {
                    taytoorder= "Tayto bar: " +vend.getProductTypes()[4].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(taytoorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[4].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[4] = purchasedProducts[4] + 1;}});

        DairyMilkButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String dairyorder = null;
                try {
                    dairyorder= "Dairy Milk bar: " +vend.getProductTypes()[5].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(dairyorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[5].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[5] = purchasedProducts[5] + 1;}});

        LionButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String lionorder = null;
                try {
                    lionorder= "Lion bar: " +vend.getProductTypes()[6].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(lionorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[6].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[6] = purchasedProducts[6] + 1;}});


        FlakeButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String flakeorder = null;
                try {
                    flakeorder= "Flake bar: " +vend.getProductTypes()[7].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(flakeorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[7].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[7] = purchasedProducts[7] + 1;}});

        GalaxyButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String galaxyorder = null;
                try {
                    galaxyorder= "Galaxy bar: " +vend.getProductTypes()[8].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(galaxyorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[8].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[8] = purchasedProducts[8] + 1;}});

        SnickersButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                String snickersorder = null;
                try {
                    snickersorder= "Snickers bar: " +vend.getProductTypes()[9].getPrice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order = order.concat(snickersorder) + "\n" ; currentOrder.setText(order);
                try {
                    currentOrderCost = currentOrderCost + Double.parseDouble(vend.getProductTypes()[9].getPrice());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String currentOrderCostString = Double.toString(currentOrderCost);
                currentOrderCostLabel.setText("Your Order Total: " + Float.parseFloat(currentOrderCostString));
                purchasedProducts[9] = purchasedProducts[9] + 1;}});


        //Handling an admin user, confirmation is same system as client confirmation

        Admin.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event){
                if (user.getText() != null) {
                    currentUser = user.getText();
                    currentPassword = password.getText();
                    currentUserLabel.setText("Welcome " + currentUser);

                }

                try {
                    int passwordInt = Integer.parseInt(password.getText());

                } catch (NumberFormatException j) {

                    AlertMessage.display("Try again", "Password Must be a Number");
                }

                try {
                    if (admin.isAdmin(user.getText(), password.getText())) {
                        machineBalance = vend.getMachineBalance();
                        currentUserLabel.setText("Welcome " + currentUser);
                        checkBalanceLabel.setText("Machine Balance:   €"+ machineBalance );
                        window.setScene(scene3);
                    }
                    else { AlertMessage.display("Try again", "Sorry, User or Password is Incorrect");}

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //populating the product list from products.dat so the admin can see it and make changes.
                try {
                    productList = ""; //resetting the product list before each time checking it for changes.
                    for (int i = 0; i < vend.getProductTypes().length - 1; i++) {

                        String localProduct = vend.getProductTypes()[i].toString();
                        String localDescription = vend.getProductTypes()[i].getDescription();
                        String localQuantity = product.getQuantity(localDescription);

                        productList = productList.concat(localProduct);
                        productList = productList.concat(" No. Of Bars Remaining: "+ localQuantity);
                        productList = productList.concat(" \n");

                    }
//                        System.out.println(productList);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }
                productListLabel.setText(productList);

            }
        });


        //setting fields for changing product price
        Label setQuantityDescription = new Label("Please Enter The Product and the New Quantity");
        HBox hbForQuantityDescription = new HBox();
        hbForQuantityDescription.getChildren().add(setQuantityDescription);
        hbForQuantityDescription.setAlignment(Pos.CENTER);

        TextField productSetQuantity = new TextField();
        productSetQuantity.setPrefWidth(50);
        TextField QuantitySetQuantity = new TextField();
        QuantitySetQuantity.setPrefWidth(50);
        Button setQuantity = new Button("Ok");


        Label Product = new Label("Product:   ");
        Label Quantity = new Label("  Quantity:   ");

        HBox hbForTextFieldSetPrice = new HBox();
        hbForTextFieldSetPrice.getChildren().addAll(Product, productSetQuantity);
        hbForTextFieldSetPrice.getChildren().addAll(Quantity, QuantitySetQuantity, setQuantity);
        hbForTextFieldSetPrice.setAlignment(Pos.CENTER);
        hbForTextFieldSetPrice.setSpacing(10);

        Button setMachineBalance = new Button("Set new Balance");
        TextField setBalanceAmount = new TextField();
        TextField passwordConfirmation = new TextField();
        passwordConfirmation.setPrefWidth(80);
        setBalanceAmount.setPrefWidth(50);

        Label balanceInstructions = new Label("Please enter the new machine balance and your password");
        Label setNewBalance = new Label("New Balance:   ");
        Label passwordConfirm = new Label("Password:   ");

        VBox vbForTextFieldSetMachineBalance = new VBox();
        vbForTextFieldSetMachineBalance.getChildren().add(balanceInstructions);
        vbForTextFieldSetMachineBalance.getChildren().add(checkBalanceLabel);
        vbForTextFieldSetMachineBalance.setAlignment(Pos.CENTER);
        vbForTextFieldSetMachineBalance.setSpacing(10);

        HBox hbForTextFieldSetMachineBalance = new HBox();
        hbForTextFieldSetMachineBalance.getChildren().addAll(passwordConfirm, passwordConfirmation);
        hbForTextFieldSetMachineBalance.getChildren().addAll(setNewBalance, setBalanceAmount, setMachineBalance);
        hbForTextFieldSetMachineBalance.setAlignment(Pos.CENTER);
        hbForTextFieldSetMachineBalance.setSpacing(10);

        Button updatePriceList = new Button("Update");

        Button shutItDown = new Button("Shut Down Machine");


        VBox scene3tile = new VBox();
        scene3tile.getChildren().add(currentUserLabel);
        scene3tile.getChildren().add(hbForQuantityDescription);
        scene3tile.getChildren().add(hbForTextFieldSetPrice);
        scene3tile.getChildren().add(productListLabel);
        scene3tile.getChildren().add(updatePriceList);
        scene3tile.getChildren().add(vbForTextFieldSetMachineBalance);
        scene3tile.getChildren().add(hbForTextFieldSetMachineBalance);
        scene3tile.getChildren().add(shutItDown);
        scene3tile.setAlignment(Pos.CENTER);
        scene3tile.setSpacing(30);

        scene3 = new Scene(scene3tile, 500, 650);

        updatePriceList.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                    try {
                        productList = ""; //resetting the product list before each time checking it for changes.
                        for (int i = 0; i < vend.getProductTypes().length - 1; i++) {

                            String localProduct = vend.getProductTypes()[i].toString();
                            String localDescription = vend.getProductTypes()[i].getDescription();
                            String localQuantity = product.getQuantity(localDescription);

                            productList = productList.concat(localProduct);
                            productList = productList.concat(" No. Of Bars Remaining: "+ localQuantity);
                            productList = productList.concat(" \n");
                        }
//                        System.out.println(productList); //checking productlist is being filled
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                productListLabel.setText(productList);

            }
        });

//to handle if the quantity is changed by the admin
        setQuantity.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    String newQuantityString = QuantitySetQuantity.getText();
                    String productString = productSetQuantity.getText();

                    //check to see if there is such a product
                    int count = 0;
                    for (int i = 0; i < productArray.length; i++) {
                        if (productArray[i].equals(productString)) {
                            count++;
                        }
                    }
                    if (count == 0) { AlertMessage.display("Try Again", "No Such Product");
                    }

                    for (int i = 0; i < productArray.length; i++) {
                        if (productArray[i].equals(productString)) {

                            String productPrice = vend.getProductTypes()[i].getPrice();
                            String oldQuantity = product.getQuantity(productString);


                            String oldLine = productString + "," + productPrice + "," + oldQuantity;
                            String newQuantityLine = productString + "," + productPrice + "," + newQuantityString;

//                            System.out.println(oldLine + "\n" + newQuantityLine); //Checking whether lines are being composed correctly for writer

                            writer.writer(productFile, oldLine, newQuantityLine);

                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }});

        setMachineBalance.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                //check for no entry
                balanceCheckforNull = setBalanceAmount.getText();
                String nothing = "";

                    try {
                        int balanceInt = Integer.parseInt(setBalanceAmount.getText());


                    } catch (NumberFormatException j) {

                        AlertMessage.display("Try again", "New Balance Must be a Number");
                    }

                    try {
                        int passwordInt = Integer.parseInt(passwordConfirmation.getText());

                    } catch (NumberFormatException j) {

                        AlertMessage.display("Try again", "Password Must be a Number");
                    }

                    if (balanceCheckforNull.equals(nothing)) {AlertMessage.display("Try again", "You Must enter a Value for the New Balance");}
                    else{
                    try {
                        if (admin.isAdmin(currentUser, passwordConfirmation.getText())) {

                            machineBalance = setBalanceAmount.getText();

                            checkBalanceLabel.setText("Machine Balance:   €" + machineBalance);

                            writer.writer(machineFile, vend.getMachineBalance(), machineBalance);

                            AlertMessage.display("Confirmed", "New Machine Balance is €" + machineBalance);
                        } else {
                            AlertMessage.display("Try again", "Sorry, User or Password is Incorrect");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }

            }
        });
        shutItDown.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                AlertMessage.display("Thank You", "Goodbye "+ currentUser);
                //resetting variables before powering off

                currentUser = "";
                currentPassword = "";
                currentBalance = "";
                order = "Your Order: \r";
                productList = "";
                currentOrderCost = 0;
                String newUserBalance = "";

                currentUserLabel.setText(currentUser);
                productListLabel.setText("");
                priceList.setText("");
                currentBalanceLabel.setText("");
                Balance.setText("");


                System.exit(0);

            }
        });

//scene 4: to deal with a successful transaction and to allow client to sign out
        Label Thanks = new Label("Thank you " + currentUser);

        Button signOut = new Button("Sign Out");

        VBox signOutBox = new VBox();
        signOutBox.setPadding(new Insets(10, 50, 50, 50));
        signOutBox.setAlignment(Pos.CENTER);
        signOutBox.setSpacing(10);

        signOutBox.getChildren().add(Thanks);
        signOutBox.getChildren().add(Balance);
        signOutBox.getChildren().add(signOut);


        scene4 = new Scene(signOutBox, 650, 450);

        //when client signs out need to reset variables and return to sign in page
        signOut.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                currentUser = "";
                currentPassword = "";
                currentBalance = "";
                order = "Your Order: \r";
                productList = "";
                currentOrderCost = 0;
                String newUserBalance = "";

                currentUserLabel.setText(currentUser);
                productListLabel.setText("");
                priceList.setText("");
                currentBalanceLabel.setText("");
                Balance.setText("");

                window.setScene(scene1);
            }
        });
        window = primaryStage;
        primaryStage.centerOnScreen();
        window.setScene(scene1);
        window.setTitle("The Chocolate Machine");
        window.show();


    }
}
